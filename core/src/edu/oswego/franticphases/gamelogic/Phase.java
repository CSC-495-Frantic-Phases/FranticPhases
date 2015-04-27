package edu.oswego.franticphases.gamelogic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import edu.oswego.franticphases.objects.Audible;
import edu.oswego.franticphases.objects.HandCardObject;
import edu.oswego.franticphases.objects.MapRenderable;
import edu.oswego.franticphases.objects.WorldObject;
import edu.oswego.franticphases.objects.WorldUpdateable;


public class Phase implements Disposable, Audible {

	private final World world = new World(new Vector2(0, 0), true);
	//private final ContactListener contactListener;
	private final TiledMap map;

	// NOTE: 1/64 means 1px end up being about 1.6cm in world physics
	private final UnitScale scale = new UnitScale(1f/64f);

	private final int phase;
	private boolean failed = false;
	private State currentState;

	private final int mapWidth;
	private final int mapHeight;

	private boolean playSound= false;
	//private final Sound failSound;

	private final Collection<Disposable> disposableObjects;
	private final Collection<MapRenderable> renderableObjects;
	private final Collection<WorldUpdateable> updateableObjects;
	private final Collection<Audible> audibleObjects;
	private ArrayList<HandCardObject> hand;

	public Phase(int phase, String filename, 
			WorldPopulator populator, AssetManager assetManager) {
		this.phase = phase;
		currentState = State.NOT_STARTED;

		map = loadMap(filename);

		mapWidth = map.getProperties().get("width", Integer.class)
				* map.getProperties().get("tilewidth", Integer.class);
		mapHeight = (map.getProperties().get("height", Integer.class)
				* map.getProperties().get("tileheight", Integer.class));
				//+ 32; // adding extra for HUD

		disposableObjects = new LinkedList<Disposable>();
		renderableObjects = new LinkedList<MapRenderable>();
		updateableObjects = new LinkedList<WorldUpdateable>();
		audibleObjects = new LinkedList<Audible>();

		audibleObjects.add(this);

		hand = populator.populateWorldFromMap(this, map, world, scale);
		
//		playSound = true;
//		String soundFile = "data/soundfx/failure-2.mp3";
//		if (!assetManager.isLoaded(soundFile)) {
//			assetManager.load(soundFile, Sound.class);
//			assetManager.finishLoading();
//		}
//		failSound = assetManager.get(soundFile, Sound.class);

		//contactListener = new OurCollisionListener();
		//world.setContactListener(contactListener);
	}

	public TiledMap getMap() {
		return map;
	}
	
	public ArrayList<HandCardObject> getHand(){
		return hand;
	}

	public World getWorld() {
		return world;
	}

	public UnitScale getScale() {
		return scale;
	}
	
	public float getMapCenterX(){
		return scale.metersToPixels(mapWidth/2);
	}
	
	public float getMapCenterY(){
		return scale.metersToPixels(mapHeight/2);
	}

	public Collection<Audible> getAudibles() {
		return audibleObjects;
	}

	public void addWorldObject(WorldObject obj) {
		if (obj instanceof Disposable) {
			disposableObjects.add((Disposable)obj);
		}
		if (obj instanceof WorldUpdateable) {
			updateableObjects.add((WorldUpdateable)obj);
		}
		if (obj instanceof MapRenderable) {
			renderableObjects.add((MapRenderable)obj);
		}
		if (obj instanceof Audible) {
			audibleObjects.add((Audible)obj);
		}
		// we don't care about other object types at this time.
	}



	public boolean isTurn() {
		return currentState == State.TURN;
	}

	public void endTurn() {
		currentState.endTurn(this, true);
	}

	public void finishPhase() {
		currentState.finishPhase(this);
	}

	public boolean isFailed() {
		return failed;
	}

	public boolean hasFinished() {
		return currentState == State.FINISHED;
	}

	private TiledMap loadMap(String file) {
		if (Gdx.files.internal("data/" + file).exists()) {
			return new TmxMapLoader().load("data/" + file);
		}
		throw new RuntimeException("data/" + file + " does not exist");
	}

	public int getPhaseNumber() {
		return phase;
	}

	@Override
	public void setPlaySound(boolean value) {
		playSound = value;
	}

	@Override
	public void playSound() {
		if (playSound) {
			if (isFailed()) {
				//failSound.play();
			}
		}
	}

	@Override
	public void endSound() {
		//failSound.stop();
	}

	public void draw(float delta, SpriteBatch batch) {
		for (MapRenderable m : renderableObjects) {
			m.drawBeforeBall(delta, batch);
		}
		for(HandCardObject c : hand){
			c.draw(delta, batch);
		}
		for (MapRenderable m : renderableObjects) {
			m.drawAfterBall(delta, batch);
		}
	}

	public void update(float delta) {
		currentState.update(this, delta);
	}


	@Override
	public void dispose() {
		world.dispose();
		for (Disposable d : disposableObjects) {
			d.dispose();
		}
		//failSound.dispose();
	}

	private void changeState(State state) {
		currentState = state;
	}

	private static enum State {
		NOT_STARTED {
			@Override
    		public void start(Phase l) {

    			l.changeState(NOT_TURN);
    		}
        },
		NOT_TURN {
    		@Override
    		public void update(Phase l, float delta) {
    			for (WorldUpdateable w : l.updateableObjects) {
    				w.update(delta);
    			}
    			
    			l.world.step(1 / 45f, 10, 8);
    		}
        },
        TURN {

    		@Override
    		public void endTurn(Phase l, boolean fail) {

    			l.changeState(NOT_TURN);

    		}

    		@Override
    		public void update(Phase l, float delta) {
    			for (WorldUpdateable w : l.updateableObjects) {
    				w.update(delta);
    			}
    			l.world.step(1 / 45f, 10, 8);
    		}
        },
        ENDING {
    		@Override
			public void finishPhase(Phase l) {
    			l.changeState(FINISHED);
    			l.playSound();
    		}
        },
        FINISHED;

        public void start(Phase l) {}
		public void endTurn(Phase l, boolean fail) {}
		public void finishPhase(Phase l) {}
		public void update(Phase l, float delta) {}
    };
}

