package org.pignat.project.zensand.components;

public class Controller {
	private double size;
	private double margin;
	private double width;
	private double height;
	private Arms arms;
	private Path path;
	private double speed;

	public Controller(Drawer d, double w, double h, double ma, double _speed) {
		height = h;
		width = w;
		margin = ma;
		speed = _speed;

		double lower_bound = Math.min((width - 2 * margin) / 2, (height - 2 * margin) / 2);
		double upper_bound = Math.sqrt(Math.pow(width - 2 * margin, 2) + Math.pow(height - 2 * margin, 2)) / 4;
		size = (int) Math.min(upper_bound, lower_bound);
		arms = new Arms(size);
		path = new Path(d, size, width, height, margin, speed);
	}

	public void drawer(Drawer d) {
		path = new Path(d, size, width, height, margin, speed);
	}
	
	public boolean finished() {
		return path.finished();
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
		C2 next = path.step();

		A2 best_speed = V2.bestSpeed(arms.m(), next, size);

		arms.speed(best_speed);
		arms.step();
	}
}
