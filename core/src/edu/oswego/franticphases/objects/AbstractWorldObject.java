package edu.oswego.franticphases.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract class AbstractWorldObject extends Actor implements WorldObject {
	protected final Body body;

	AbstractWorldObject(Body body) {
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
