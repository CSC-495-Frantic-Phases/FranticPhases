package edu.oswego.franticphases.collisionListener;

import com.badlogic.gdx.physics.box2d.Contact;

import edu.oswego.franticphases.objects.HandCardObject;


public interface CardCollisionListener {
		public void handleBeginCollision(Contact contact, HandCardObject card);
		public void handleEndCollision(Contact contact, HandCardObject card);
	}
