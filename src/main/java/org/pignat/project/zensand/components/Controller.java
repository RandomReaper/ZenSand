package org.pignat.project.zensand.components;

import org.pignat.project.zensand.projection.*;

public class Controller {
	private Sizes dim;
	private Arms arms;
	private Path path;
	private double speed;

	public Controller(Drawer d, Sizes _dim, double _speed) {
		speed = _speed;
		dim = _dim;

		arms = new Arms(dim.size());
		path = new Path(d, new ProjectionMirror(), dim, speed);
	}

	public void drawer(Drawer d) {
		path = new Path(d, new ProjectionMirror(), dim, speed);
	}
	
	public boolean finished() {
		return path.finished();
	}

	public Sizes dim() {
		return dim;
	}

	public Arms arms() {
		return arms;
	}

	public void step() {
		C2 next = path.step();

		A2 best_speed = V2.bestSpeed(arms.m(), next, dim.size());

		arms.speed(best_speed);
		arms.step();
	}
}
