package edu.oswego.franticphases.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import edu.oswego.franticphases.FranticPhases;
import edu.oswego.franticphases.datasending.DataSender;
import edu.oswego.franticphases.datasending.GameHandler;
import edu.oswego.franticphases.datasending.Handler;
import edu.oswego.franticphases.datasending.WebCallback;
import edu.oswego.franticphases.dialogs.LoginDialog;
import edu.oswego.franticphases.dialogs.ServerErrorDialog;
import edu.oswego.franticphases.settings.Settings;
import edu.oswego.franticphases.widgets.LoadingBar;

public class LoadingScreen extends AbstractScreen{

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    
    private AssetManager manager;

    private float startX, endX;
    private float percent;

    private Handler handler;
    private WebCallback callBack;
    private WebCallback callBack1;
    private WebCallback callBack2;
    private WebCallback callBack3;
    
    
    private boolean usernamesLoaded = false;
    private boolean allGamesLoaded = false;
    private boolean loggedIn = false;

    private Actor loadingBar;

    public LoadingScreen(FranticPhases game) {
        super(game);
        callBack  = new WebCallback();
        callBack1  = new WebCallback();
        callBack2  = new WebCallback();
        callBack3  = new WebCallback();

    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
    	manager = game.getAssetManager();
        manager.load("data/loading.pack", TextureAtlas.class);
        // Wait until they are finished loading
        manager.finishLoading();

        // Get our textureatlas from the manager
        TextureAtlas atlas = manager.get("data/loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Add the loading bar animation
        Animation anim = new Animation(0.1f, atlas.findRegions("loading-bar-anim") );
        anim.setPlayMode(PlayMode.LOOP);
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
        
        handler = new Handler();


        // Add everything to be loaded, for instance:
        //all sounds/music and images
        this.login();
        
	
		manager.load("data/WorldObjects/worldObjects.txt", TextureAtlas.class);
        // game.manager.load("data/assets1.pack", TextureAtlas.class);
        // game.manager.load("data/assets2.pack", TextureAtlas.class);
        // game.manager.load("data/assets3.pack", TextureAtlas.class);
    }
    
    private void login(){
      if(!loggedIn){
    	if (!Settings.getUsername().equals("")) {
        	callBack1 = new WebCallback();
			DataSender aSender = new DataSender();
			aSender.login(Settings.getUsername(),
					Settings.getPassword(), callBack1);
			loggedIn = true;
			
		} else {
			loggedIn = true;
			Dialog ld = new LoginDialog("Login", skin, game).show(game
					.getStage());
		}
      }
    	
    }
    
    private void loadAllGames(){
    	if(!allGamesLoaded){
    	  callBack2 = new WebCallback();
		  DataSender aSender = new DataSender();
		  aSender.getGames(Settings.getUserID(), callBack2, handler);
		  
		  allGamesLoaded = true;
    	}
    }
    
    private void loadAllUsers(){
    	if(!usernamesLoaded){
    		callBack3 = new WebCallback();
    		DataSender aSender = new DataSender();
    		aSender.getUsers( callBack3, handler);
    		usernamesLoaded = true;
    	}
    	
    }
    
    

    @Override
    public void resize(int width, int height) {
        
        float w = game.getWidth();
        float h = game.getHeight();
       // stage.setViewport(width , height, false);
       // stage.getViewport().update(width, height, true);
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

       
    	   
        if(callBack1.getRecieved()){//did login ack
           if(callBack1.getResult()){//did we login
        	   this.loadAllGames(); 
        	   
           }else{//didnt login something went wrong
        	  
        	  
        	   Dialog ld = new ServerErrorDialog("Login failed.", skin, game, "Please try again later.").show(game
   					.getStage());
           }  
        }
        
        if(callBack2.getRecieved()){//did games ack
           if(callBack2.getResult()){//did we load games
        	   this.loadAllUsers(); 
        	   
           }else{//didnt load games something went wrong
        	
        	   Dialog ld = new ServerErrorDialog("Error loading games", skin, game, "Please try again later.").show(game
   					.getStage());
           }  
        }
        
        if(callBack3.getRecieved()){//did users ack
        	if(callBack3.getResult()){//did we load users
        		
           }else{//didnt load users something went wrong
        	 
        	   Dialog ld = new ServerErrorDialog("Error loading players", skin, game, "Please try again later.").show(game
   					.getStage());
           }  
        }
      
        
        

        // Interpolate the percentage to make it more smooth
        float status = 0;
        
        if(manager.getQueuedAssets() == 0){
        	status += 0.25f;
        }
        if(callBack1.getRecieved()){
        	status += 0.25f;
        }
        if(callBack2.getRecieved()){
        	status += 0.25f;
        }
        if(callBack3.getRecieved()){
        	status += 0.25f;
        }
        									//start   end     a
        percent = Interpolation.linear.apply(percent, status, 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();
        
        if(manager.update()){
        if(callBack1.getResult() && callBack2.getResult() && callBack3.getResult()){
        	if (Gdx.input.isTouched()) {
        	  stage.clear();
              game.showMainScreen();
        	}
        }
        }
       

    }

    @Override
    public void hide() {
      
    }
}
