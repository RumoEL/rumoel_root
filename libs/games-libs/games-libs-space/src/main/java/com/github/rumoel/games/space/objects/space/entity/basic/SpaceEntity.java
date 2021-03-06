package com.github.rumoel.games.space.objects.space.entity.basic;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.github.rumoel.games.space.objects.space.entity.inspace.Ship;
import com.github.rumoel.utils.NumberUtils;

import lombok.Getter;
import lombok.Setter;

public class SpaceEntity implements Serializable, SpaceEntityInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3343374412307380905L;

	@Getter
	@Setter
	int id;

	public SpaceEntity() {
		setId(NumberUtils.randomIntInRange(1, Integer.MAX_VALUE));
	}

	@Getter
	@Setter
	private float health = 10.0F;

	@Getter
	Vector3d position = new Vector3d(0, 0, 10);

	@Getter
	@Setter
	Vector3f linearVel = new Vector3f();

	@Getter
	Vector4f projectileVelocity = new Vector4f(0, 0, 0, 0);

	//
	@Override
	public void updatePosition(double x, double y, double z) {
		this.position.x = this.position.x + x;
		this.position.y = this.position.y + y;
		this.position.z = this.position.z + z;
	}

	@Override
	public void setPosition(double x, double y, double z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	@Override
	public void spawn() {

	}

	@Override
	public void destroy() {
		if (health != 0F) {
			health = 0F;
		}

		// УДАЛЯЕМ СО ВСЕХ ИСТОЧНИКОВ
		// FIXME ПРОВЕРИТЬ РАБОТУ
		// containers.parallelStream().filter(list -> list.contains(this)).forEach(l ->
		// l.remove(this));
		spawnResource();
	}

	@Override
	public void spawnResource() {

	}

	@Override
	public boolean damage(float dmg) {
		this.health = this.health - dmg;
		if (health <= 0.0F) {
			destroy();
			return true;
		}
		return false;
	}

	public boolean removeFromList(CopyOnWriteArrayList<Ship> localShips) {
		for (Ship ship : localShips) {
			if (id == ship.getId()) {
				localShips.remove(ship);
				return true;
			}
		}
		return false;
	}

}
