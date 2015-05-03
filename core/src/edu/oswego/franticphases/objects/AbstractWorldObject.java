package edu.oswego.franticphases.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract class AbstractWorldObject implements WorldObject {
	protected final Body body;

	AbstractWorldObject(Body body) {
		super();
		this.body = body;
		body.setUserData(this);
		for (Fixture f : body.getFixtureList()) {
			f.setUserData(this);
		}
	}

	@Override
	public Body getBody() {
		return body;
	}
	
}
