package edu.oswego.franticphases.collisionListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import edu.oswego.franticphases.objects.HandCardObject;


public class OurCollisionListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if (a != null && b != null) {
			Gdx.app.log("begin contact", a.getClass().getName() + " > " + b.getClass().getName());
			if (a instanceof CardCollisionListener && b instanceof HandCardObject) {
				((CardCollisionListener) a).handleBeginCollision(contact, (HandCardObject) b);
			} else if (b instanceof CardCollisionListener && a instanceof HandCardObject) {
				((CardCollisionListener) b).handleBeginCollision(contact, (HandCardObject) a);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Object a = contact.getFixtureA().getUserData();
		Object b = contact.getFixtureB().getUserData();
		if (a != null && b != null) {
			Gdx.app.log("end contact", a.getClass().getName() + " > " + b.getClass().getName());
			if (a instanceof CardCollisionListener && b instanceof HandCardObject) {
				((CardCollisionListener) a).handleEndCollision(contact, (HandCardObject) b);
			} else if (b instanceof CardCollisionListener && a instanceof HandCardObject) {
				((CardCollisionListener) b).handleEndCollision(contact, (HandCardObject) a);
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// not using this at this point
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// not using this at this point
	}
}
