package edu.oswego.franticphases.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import edu.oswego.franticphases.FranticPhases;

public class AndroidLauncher extends AndroidApplication {
	private AndroidTournaments mTournaments = null;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		mTournaments = new AndroidTournaments(this);
		FranticPhases game = new FranticPhases(mTournaments);
		initialize(game, config);
	}
	// Notify the beginning of a user session.
	@Override
	protected void onStart() {
	    super.onStart();
	 
	    if (mTournaments != null) {
	        mTournaments.onStart();
	    }
	}
	 
	// Let Nextpeer know that the user session has ended while in tournament
	@Override 
	public void onStop() {
	    super.onStop();
	 
	    // If there is an on-going tournament make sure to forfeit it
	    if (mTournaments != null && mTournaments.isCurrentlyInTournament()) {
	        mTournaments.reportForfeitForCurrentTournament();
	    }
	}
	 
	/** The user pressed the back button */
	@Override
	public void onBackPressed() {
	    // If the game is in tournament mode, forfeit the tournament:
	    if (mTournaments != null && mTournaments.isCurrentlyInTournament()) {
	        mTournaments.reportForfeitForCurrentTournament();
	    }
	 
	    super.onBackPressed();
	}
	
}
