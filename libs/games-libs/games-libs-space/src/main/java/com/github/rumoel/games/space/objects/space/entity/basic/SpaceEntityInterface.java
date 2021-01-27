package com.github.rumoel.games.space.objects.space.entity.basic;

public interface SpaceEntityInterface {
	public void updatePosition(double x, double y, double z);

	public void setPosition(double x, double y, double z);

	public void spawn();

	public void destroy();

	public boolean damage(float dmg);

	public void spawnResource();

}
