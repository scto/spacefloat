package me.fahien.spacefloat.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import me.fahien.spacefloat.utils.JsonString;

/**
 * The Collision {@link Component}
 */
public class CollisionComponent extends btCollisionObject implements Component, Json.Serializable {
	private static final float DEFAULT_RADIUS = 1.0f;

	private float radius;

	public CollisionComponent() {
		this(DEFAULT_RADIUS);
	}

	public CollisionComponent(float radius) {
		this.radius = radius;
	}

	/**
	 * Returns the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Sets the {@link btCollisionObject} transform
	 */
	public void setTransform(Matrix4 transform) {
		setWorldTransform(transform);
	}

	@Override
	public void write(Json json) {
		json.writeValue(JsonString.RADIUS, radius);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		radius = jsonData.getFloat(JsonString.RADIUS);
	}
}
