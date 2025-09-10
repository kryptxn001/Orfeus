package me.torhash.orfeus.event.events;

import me.torhash.orfeus.event.Event;

public class EventRender3D extends Event {

	public float particlTicks;

	public EventRender3D(float particlTicks) {
		this.particlTicks = particlTicks;
	}

}
