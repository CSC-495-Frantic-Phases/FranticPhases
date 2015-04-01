package edu.oswego.franticphases.settings;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import edu.oswego.franticphases.settings.Settings.Setting;

public class Settings {
	
	public static enum Setting {
		DEBUG_RENDER, MUSIC, SOUND_EFFECTS
	}
	
	private static Preferences thePreferences;
	private final List<SettingsObserver> observers = new ArrayList<SettingsObserver>();

	
	public Settings(){
		thePreferences = Gdx.app.getPreferences("GameData");
		//thePreferences.clear();
	}
	
	public static String getUsername(){
		return thePreferences.getString("username","");
	}
	
	public static void setUsername(String username){
		thePreferences.putString("username", username);
		thePreferences.flush();
	}
	
	public static void setUserID(String userID){
		thePreferences.putString("userid", userID);
		thePreferences.flush();
	}
	
	public static String getUserID(){
		return thePreferences.getString("userid", "");
	}
	

	
	public static String getPassword(){
		return thePreferences.getString("password", "");
	}
	
	public static void setPassword(String password){
		thePreferences.putString("password", password);
		thePreferences.flush();
	}
	
	public boolean isDebugRender() {
		return thePreferences.getBoolean(Setting.DEBUG_RENDER.name(), false);
	}

	public void setDebugRender(boolean debugRender) {
		thePreferences.putBoolean(Setting.DEBUG_RENDER.name(), debugRender);
		notifyObservers(new SettingsUpdate(Setting.DEBUG_RENDER, debugRender));
	}

	public boolean isMusicOn() {
		return thePreferences.getBoolean(Setting.MUSIC.name(), true);
	}

	public void setMusic(boolean music) {
		thePreferences.putBoolean(Setting.MUSIC.name(), music);
		notifyObservers(new SettingsUpdate(Setting.MUSIC, music));
	}

	public boolean isSoundEffectOn() {
		return thePreferences.getBoolean(Setting.SOUND_EFFECTS.name(), true);
	}

	public void setSoundEffect(boolean soundEffect) {
		thePreferences.putBoolean(Setting.SOUND_EFFECTS.name(), soundEffect);
		notifyObservers(new SettingsUpdate(Setting.SOUND_EFFECTS, soundEffect));
	}

	public void save() {
		thePreferences.flush();
	}

	public void addObserver(SettingsObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(SettingsObserver observer) {
		observers.remove(observer);
	}

	void notifyObservers(SettingsUpdate update) {
		for (SettingsObserver o : observers) {
			o.handleSettingsChangeUpdate(update);
		}
	}
	

}
