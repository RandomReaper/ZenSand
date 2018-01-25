package org.pignat.project.zensand.components;

import java.io.Serializable;

import org.pignat.project.zensand.projection.*;

public class Controller implements Serializable {
	private Dimensions dim;
	private Arms arms;
	private Path path;
	private double speed;

	public Controller(Drawer d, Arms arms, Dimensions dim, double speed) {
		this.speed = speed;
		this.dim = dim;

		this.arms = arms;
		path = new Path(d, new ProjectionNop(), dim, arms.pos(), speed);
	}

	public void drawer(Drawer d) {
		path = new Path(d, new ProjectionNop(), dim, arms.pos(), speed);
	}
	
	public boolean finished() {
		return path.finished();
	}

	public Dimensions dim() {
		return dim;
	}

	public Arms arms() {
		return arms;
	}

	public void step() {
		C2 next = path.step();
		arms.speed(V2.bestSpeed(arms.m(), next, dim.size()));
		arms.step();
	}
}
