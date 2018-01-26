package org.pignat.project.zensand.components;

import java.io.Serializable;

import org.pignat.project.zensand.components.Path.CompletePath;
import org.pignat.project.zensand.components.Path.IPath;
import org.pignat.project.zensand.projection.*;

public class Controller implements Serializable {
	private Dimensions dim;
	private Arms arms;
	private IPath path;
	private double speed;
	private Drawer drawer;

	public Controller(Drawer drawer, Arms arms, Dimensions dim, double speed) {
		this.drawer = drawer;
		this.arms = arms;
		this.dim = dim;
		this.speed = speed;
		path = new CompletePath(drawer, new ProjectionNop(), dim, arms.pos(), speed);
	}

	public void drawer(Drawer drawer) {
		this.drawer = drawer;
		path = new CompletePath(drawer, new ProjectionNop(), dim, arms.pos(), speed);
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
	
	public Drawer drawer() {
		return drawer;
	}

	public void step() {
		C2 next = path.step();
		arms.speed(V2.bestSpeed(arms.m(), next, dim.size()));
		arms.step();
	}
}
