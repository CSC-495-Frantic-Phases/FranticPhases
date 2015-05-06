package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.GameHandler;
import edu.oswego.franticphases.datasending.Handler;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.dialogs.LoginDialog;
import edu.oswego.franticphases.dialogs.ServerErrorDialog;
import edu.oswego.franticphases.gamelogic.CardGame;
import edu.oswego.franticphases.settings.Settings;
import edu.oswego.franticphases.widgets.LoadingBar;

public class GameLoadingScreen extends AbstractScreen{

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    
    private AssetManager manager;

    private float startX, endX;
    private float percent;
    private GameHandler ghandler;
    private Handler handler;
    private WebCallback callBack;
    private WebCallback callBack1;

    private Actor loadingBar;
    private String id;
    private CardGame cardGame;
    private boolean newGame;
    private boolean gameCreated = false;
    private boolean gameLoading = false;

    public GameLoadingScreen(FranticPhases game, CardGame cardGame, boolean newGame) {
        super(game);
        id = cardGame.getGameID();
        this.cardGame = cardGame;
        this.newGame = newGame;
        callBack = new WebCallback();
        callBack1 = new WebCallback();
    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
    	manager = game.getAssetManager();
        
        // Get our textureatlas from the manager
        TextureAtlas atlas = manager.get("data/loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Add the loading bar animation
        Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim") );
        anim.setPlayMode(PlayMode.LOOP_REVERSED);
        loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
        // loadingBar = new Image(atlas.findRegion("loading-bar1"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);
        
        if(newGame){
          handler = new Handler();
        }
        ghandler = new GameHandler(id);


        // Add everything to be loaded, for instance:
		manager.load("data/WorldObjects/shuffle.png", Texture.class);
		manager.load("data/ui/unpacked/PauseButton2.png", Texture.class);
        
    }
    
    private void createGame(){
    	if(!gameCreated){
		  DataSender aSender = new DataSender();
		  aSender.createGame(cardGame, callBack, handler);
		  gameCreated = true;
    	}
		
    }
    
    private void loadGame(){
    	if(!gameLoading){
    	  
    	  ghandler.loadGame(callBack1);
    	  gameLoading = true;
    	}
    }
    

    @Override
    public void resize(int width, int height) {
        // Set our screen to always be XXX x 480 in size
        float w = game.getWidth();
        float h = game.getHeight();
       // stage.setViewport(width , height, false);
        stage.getViewport().update(width, height, true);
        // Make the background fill the screen
        screenBg.setSize(w, h);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((w - logo.getWidth()) / 2);
        logo.setY((h - logo.getHeight()) / 2 + 100);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    
    public void preStageRenderHook(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        boolean done = false;

       if(newGame){
    	   this.createGame();
    	   	if(callBack.getRecieved()){//did create game ack
    	   		if(callBack.getResult()){//did we create the game
        	        newGame = false;
    	   			
    	   		}else{//didnt create game something went wrong
    	   			Dialog ld = new ServerErrorDialog("Create game failed.", skin, game, "Please try again later.").show(game
    	   					.getStage());
    	   		}  
    	   	}
       }else{
    	   this.loadGame();
    	   if(callBack1.getRecieved()){//did game data return
    	     if(callBack1.getResult()){//did we get the game data
    		   done = true;
    	     }else{//nope something went wrong
    		   Dialog ld = new ServerErrorDialog("Game load failed.", skin, game, "Please try again later.").show(game
      					.getStage());
    	     }
    	   }
       }
        
        // Interpolate the percentage to make it more smooth
       float status = 0;
       
       if(newGame){
    	   if(callBack.getRecieved()){
    		   status += 0.50f;
    	   }
    	   
       }else{
    	   status += 0.50f;
       }
       
       if(callBack1.getRecieved()){
       	status += 0.50f;
       }
       									//start   end     a
       percent = Interpolation.linear.apply(percent, status, 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        if(done && manager.update()){
        	if (Gdx.input.isTouched()) {
        	  stage.clear();
              game.showGameScreen(ghandler);
        	}
        }
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
        //manager.unload("data/loading.pack");
    }
}
