package org.pignat.project.zensand.components;

public class Controller {
	private double size;
	private double margin;
	private double width;
	private double height;
	private Arms arms;
	private Drawer drawer;

	public Controller(Drawer d, double w, double h, double ma) {
		drawer = d;
		height = h;
		width = w;
		margin = ma;

		double lower_bound = Math.min((width - 2 * margin) / 2, (height - 2 * margin) / 2);
		double upper_bound = Math.sqrt(Math.pow(width - 2 * margin, 2) + Math.pow(height - 2 * margin, 2)) / 4;
		size = (int) Math.min(upper_bound, lower_bound);
		arms = new Arms(size);
	}

	public void drawer(Drawer d) {
		drawer = d;
	}

	public Drawer drawer() {
		return drawer;
	}

	public double size() {
		return size;
	}

	public double margin() {
		return margin;
	}

	public Arms arms() {
		return arms;
	}

	public void step() {
		V2 next = drawer.step().resize(size, width, height, margin);

		V2 best_speed = V2.bestSpeed(arms.m(), next, size);

		arms.speed(best_speed);
		arms.step();
	}
}
