package edu.oswego.franticphases.gamelogic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Disposable;

import edu.oswego.franticphases.graphics.GraphicComponent;
import edu.oswego.franticphases.graphics.SpriteGraphic;
import edu.oswego.franticphases.objects.DeckObject;
import edu.oswego.franticphases.objects.FaceUpCard;
import edu.oswego.franticphases.objects.HandCardObject;


public class WorldPopulator implements Disposable{
	private final BodyDefBuilder bodyDef = new BodyDefBuilder();
	private final FixtureDefBuilder fixtureDef = new FixtureDefBuilder();
	private final String atlasFile = "data/WorldObjects/cards.txt";
	private final TextureAtlas atlas;
	private final AssetManager assetManager;
	private FaceUpCard faceupCard;
	public WorldPopulator(AssetManager assetManager) {
		this.assetManager = assetManager;
		//assetManager.load(atlasFile, TextureAtlas.class);
		//assetManager.finishLoading();
		atlas = assetManager.get(atlasFile, TextureAtlas.class);
	}
	public ArrayList<HandCardObject> populateWorldFromMap(Phase level,TiledMap map, World world,
			UnitScale scale) {
		ArrayList<HandCardObject> hand = new ArrayList<HandCardObject>();
		MapLayer layer = map.getLayers().get("collision");
		for (MapObject obj : layer.getObjects()) {
			if (obj.getName() != null) {
				if (obj.getName().equals("card")) {
					HandCardObject card = createCard(obj, world, scale);
					
					level.addWorldObject(card);
					
					hand.add(card);
				}else if(obj.getName().equals("faceup")) {
					faceupCard = createFaceup(obj, world, scale);
					level.addWorldObject(faceupCard );
					faceupCard.setGraphic("back");
					
				}else if(obj.getName().equals("deck")) {
					DeckObject deck = createDeck(obj, world,scale);
					level.addWorldObject(deck);
				
					
				}
			}
		}
		return hand;
	}
	
	public FaceUpCard getfCard(){
		return faceupCard;
	}
	
	public HandCardObject createCard(MapObject obj, World world, UnitScale scale) {
		if (!(obj instanceof RectangleMapObject)) {
			throw new IllegalArgumentException(obj.getName()
					+ " Unsupported MapObject: "
					+ obj.getClass().getName());
		}

		Body body = world.createBody(bodyDef
				.reset()
				.type(HandCardObject.BODY_TYPE)
				.build());
		Shape shape = createShape(obj, scale, body);
		body.createFixture(fixtureDef.reset().shape(shape)
				.friction(getFloatProperty(obj, "friction", HandCardObject.FRICTION))
				.density(getFloatProperty(obj, "density", HandCardObject.DENSITY))
				.restitution(getFloatProperty(obj, "restitution", HandCardObject.RESTITUTION))
				.build());
		// dispose after creating fixture
		shape.dispose();
		Vector2 d = getDimensions(obj);

		return new HandCardObject(body, scale, assetManager, d.x, d.y);
		//return new HandCardObject(body, scale, assetManager, width, height);
	}
	
	public FaceUpCard createFaceup(MapObject obj, World world, UnitScale scale) {
		if (!(obj instanceof RectangleMapObject)) {
			throw new IllegalArgumentException(obj.getName()
					+ " Unsupported MapObject: "
					+ obj.getClass().getName());
		}

		Body body = world.createBody(bodyDef
				.reset()
				.type(HandCardObject.BODY_TYPE)
				.build());
		Shape shape = createShape(obj, scale, body);
		body.createFixture(fixtureDef.reset().shape(shape)
				.friction(getFloatProperty(obj, "friction", HandCardObject.FRICTION))
				.density(getFloatProperty(obj, "density", HandCardObject.DENSITY))
				.restitution(getFloatProperty(obj, "restitution", HandCardObject.RESTITUTION))
				.build());
		// dispose after creating fixture
		shape.dispose();
		Vector2 d = getDimensions(obj);

		return new FaceUpCard(body, scale, assetManager, d.x, d.y);
		//return new HandCardObject(body, scale, assetManager, width, height);
	}
	
	public DeckObject createDeck(MapObject obj, World world, UnitScale scale) {
		if (!(obj instanceof RectangleMapObject)) {
			throw new IllegalArgumentException(obj.getName()
					+ " Unsupported MapObject: "
					+ obj.getClass().getName());
		}

		Body body = world.createBody(bodyDef
				.reset()
				.type(HandCardObject.BODY_TYPE)
				.build());
		Shape shape = createShape(obj, scale, body);
		body.createFixture(fixtureDef.reset().shape(shape)
				.friction(getFloatProperty(obj, "friction", HandCardObject.FRICTION))
				.density(getFloatProperty(obj, "density", HandCardObject.DENSITY))
				.restitution(getFloatProperty(obj, "restitution", HandCardObject.RESTITUTION))
				.build());
		// dispose after creating fixture
		shape.dispose();
		Vector2 d = getDimensions(obj);

		Sprite sprite = atlas.createSprite("back");
		sprite.setScale(0.35f);
		//Gdx.app.log("HandCardObject", "Loaded card: " + cardID);
		
		GraphicComponent graphic = new SpriteGraphic(sprite);
		
		return new DeckObject(body,graphic, scale, assetManager, d.x, d.y);
		//return new HandCardObject(body, scale, assetManager, width, height);
	}
	
	private Shape createShape(MapObject object, UnitScale scale, Body body) {
		Shape shape;
		if (object instanceof PolygonMapObject) {
			shape = createShape((PolygonMapObject)object, scale, body);
		} else if (object instanceof RectangleMapObject) {
			shape = createShape((RectangleMapObject)object, scale, body);
		} else if (object instanceof EllipseMapObject) {
			shape = createShape((EllipseMapObject)object, scale, body);
		} else if (object instanceof PolylineMapObject) {
			shape = createShape((PolylineMapObject)object, scale, body);
		} else {
			throw new IllegalArgumentException(object.getName()
					+ " Unsupported MapObject: "
					+ object.getClass().getName());
		}
		Gdx.app.log("WorldPopulator", "Creating " + object.getName()
				+ " - " + object.getClass().getSimpleName()
				+ " > "+ shape.getClass().getSimpleName());
		return shape;
	}

	private Shape createShape(PolylineMapObject object, UnitScale scale, Body body) {
		ChainShape shape = new ChainShape();
		Polyline polyline = object.getPolyline();
		float[] vertices = polyline.getVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		for (int i = 0; i < worldVertices.length; ++i) {
			worldVertices[i] = new Vector2(
				scale.pixelsToMeters(vertices[i * 2]),
				scale.pixelsToMeters(vertices[(i * 2) + 1])
			);
		}
		if (Boolean.valueOf(object.getProperties().get("loop", "false", String.class))) {
			shape.createLoop(worldVertices);
		}
		else {
			shape.createChain(worldVertices);
		}
		body.setTransform(
				scale.pixelsToMeters(polyline.getX()),
				scale.pixelsToMeters(polyline.getY()),
				0);
		return shape;
	}

	private Shape createShape(PolygonMapObject object, UnitScale scale, Body body) {
		// NOTE: when creating the map objects the polygons must have no
		// more than 8 vertices and must not be concave. this is a
		// limitation of the physics engine. so complex shapes need to be
		// composed of multiple adjacent polygons.
		PolygonShape shape = new PolygonShape();
		Polygon polygon = object.getPolygon();
		float[] vertices = polygon.getVertices();
		float[] worldVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			worldVertices[i] = scale.pixelsToMeters(vertices[i]);
		}
		shape.set(worldVertices);
		body.setTransform(
				scale.pixelsToMeters(polygon.getX()),
				scale.pixelsToMeters(polygon.getY()),
				0);
		return shape;
	}

	private Shape createShape(RectangleMapObject object, UnitScale scale, Body body) {
		PolygonShape shape = new PolygonShape();
		Rectangle rectangle = object.getRectangle();
		shape.setAsBox(scale.pixelsToMeters(rectangle.width * 0.5f),
				scale.pixelsToMeters(rectangle.height * 0.5f));
		Vector2 center = new Vector2();
		rectangle.getCenter(center);
		center.scl(scale.getScale());
		body.setTransform(center, 0);
		return shape;
	}

	private Shape createShape(EllipseMapObject object, UnitScale scale, Body body) {
		Gdx.app.log("warning", "Converting ellipse to a circle");
		// NOTE: there are no ellipse shapes so just convert it to a circle
		CircleShape shape = new CircleShape();

		Vector2 dimensions = getDimensions(object);
		shape.setRadius(scale.pixelsToMeters(dimensions.x * 0.5f));
		Vector2 center = getCenter(object);

		body.setTransform(
				scale.pixelsToMeters(center.x),
				scale.pixelsToMeters(center.y),
				body.getAngle());
		return shape;
	}

	private Vector2 getCenter(MapObject object) {
		Vector2 center = new Vector2();
		if (object instanceof PolygonMapObject) {
			Polygon p = ((PolygonMapObject)object).getPolygon();
			center.x = p.getX();
			center.y = p.getY();
		} else if (object instanceof RectangleMapObject) {
			Rectangle r = ((RectangleMapObject)object).getRectangle();
			r.getCenter(center);
		} else if (object instanceof EllipseMapObject) {
			Ellipse e = ((EllipseMapObject)object).getEllipse();
			center.x = e.x + (e.width * 0.5f);
			center.y = e.y + (e.height * 0.5f);
		} else if (object instanceof PolylineMapObject) {
			Polyline p = ((PolylineMapObject)object).getPolyline();
			center.x = p.getX();
			center.y = p.getY();
		} else {
			throw new IllegalArgumentException(object.getName()
					+ " Unsupported MapObject: "
					+ object.getClass().getName());
		}
		return center;
	}

	private Vector2 getDimensions(MapObject object) {
		Vector2 dimensions = new Vector2();
		if (object instanceof PolygonMapObject) {
			Polygon p = ((PolygonMapObject)object).getPolygon();
			dimensions.x = p.getBoundingRectangle().width;
			dimensions.y = p.getBoundingRectangle().height;
		} else if (object instanceof RectangleMapObject) {
			Rectangle r = ((RectangleMapObject)object).getRectangle();
			dimensions.x = r.getWidth();
			dimensions.y = r.getHeight();
		} else if (object instanceof EllipseMapObject) {
			Ellipse e = ((EllipseMapObject)object).getEllipse();
			dimensions.x = (e.width + e.height) * 0.5f;
			dimensions.y = dimensions.x;
		} else if (object instanceof PolylineMapObject) {
			Polyline p = ((PolylineMapObject)object).getPolyline();
			float maxX = 0;
			float maxY = 0;
			float minX = 0;
			float minY = 0;
			boolean isX = true;
			for (float v : p.getVertices()) {
				if (isX) {
					if (v < minX) {
						minX = v;
					} else if (v > maxX) {
						maxX = v;
					}
				} else {
					if (v < minY) {
						minY = v;
					} else if (v > maxY) {
						maxY = v;
					}
				}
				isX = !isX;
			}
			dimensions.x = maxX - minX;
			dimensions.y = maxY - minY;
		} else {
			throw new IllegalArgumentException(object.getName()
					+ " Unsupported MapObject: "
					+ object.getClass().getName());
		}
		return dimensions;
	}

	private float getFloatProperty(MapObject object, String key, float def) {
		String prop = object.getProperties().get(key, null, String.class);
		if (prop == null) {
			return def;
		}
		return Float.valueOf(prop);
	}

	static final class BodyDefBuilder {
		private final BodyDef def = new BodyDef();

		public BodyDef build() {
			return def;
		}

		/**
		 * how quickly spin degrades over time, range between 0.0 and 1.0
		 *
		 * @param val
		 * @return
		 */
		public BodyDefBuilder angularDampening(float val) {
			def.angularDamping = val;
			return this;
		}

		/**
		 * how quickly speed degrades over time, range between 0.0 and 1.0
		 *
		 * @param val
		 * @return
		 */
		public BodyDefBuilder linearDamping(float val) {
			def.linearDamping = val;
			return this;
		}

		/**
		 * position in the world in meters
		 *
		 * @param x
		 * @param y
		 * @return
		 */
		public BodyDefBuilder position(float x, float y) {
			def.position.set(x, y);
			return this;
		}

		/**
		 * the body type, static bodies do not move, kinematic bodies move but
		 * are not affected by forces in the world, dynamic bodies move and are
		 * affected by the world.
		 *
		 * @param val
		 * @return
		 */
		public BodyDefBuilder type(BodyType val) {
			def.type = val;
			return this;
		}

		/**
		 * prevent spin and angular velocity
		 *
		 * @param val
		 * @return
		 */
		public BodyDefBuilder fixedRotation(boolean val) {
			def.fixedRotation = val;
			return this;
		}

		public BodyDefBuilder reset() {
			def.angularDamping = 0;
			def.linearDamping = 0;
			def.position.set(0, 0);
			def.type = BodyType.StaticBody;
			def.fixedRotation = false;
			return this;
		}
	}

	static final class FixtureDefBuilder {
		private final FixtureDef def = new FixtureDef();

		public FixtureDef build() {
			return def;
		}

		/**
		 * the shape of the fixture
		 *
		 * @param val
		 * @return
		 */
		public FixtureDefBuilder shape(Shape val) {
			def.shape = val;
			return this;
		}

		/**
		 * the friction used when the fixture collides with another fixture
		 * range between 0.0 and 1.0
		 *
		 * @param val
		 * @return
		 */
		public FixtureDefBuilder friction(float val) {
			def.friction = val;
			return this;
		}

		/**
		 * the density, kg/m^2
		 *
		 * @param val
		 * @return
		 */
		public FixtureDefBuilder density(float val) {
			def.density = val;
			return this;
		}

		/**
		 * the bouncyness, range between 0.0 and 1.0
		 *
		 * @param val
		 * @return
		 */
		public FixtureDefBuilder restitution(float val) {
			def.restitution = val;
			return this;
		}

		/**
		 * sensors do not generate a collision response, but do generate
		 * collision events
		 *
		 * @param val
		 * @return
		 */
		public FixtureDefBuilder isSensor(boolean val) {
			def.isSensor = val;
			return this;
		}

		public FixtureDefBuilder reset() {
			def.shape = null;
			def.friction = 0;
			def.density = 1.0f;
			def.restitution = 0;
			def.isSensor = false;
			return this;
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
