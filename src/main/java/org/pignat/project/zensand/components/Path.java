package org.pignat.project.zensand.components;

import java.util.LinkedList;

public class Path {

	private static final double UPSAMPLE_FACTOR = 5;
	private Drawer drawer;
	private Sizes dim;
	private Projection projection;
	double speed;
	private C2 last_step = null;

	private LinkedList<C2> original_steps;
	private LinkedList<C2> upsample_steps;

	public Path(Drawer _drawer, Projection _projection, Sizes _dim, double _speed) {
		projection = _projection;
		drawer = _drawer;
		dim = _dim;
		speed = _speed;
		original_steps = new LinkedList<C2>();
		upsample_steps = new LinkedList<C2>();
	}

	private double max_dist(LinkedList<C2> l) {
		double max_dist = 0;

		C2 old = null;
		for (C2 current : l) {
			if (!(old == null)) {
				double dist = new C2(old.x - current.x, old.y - current.y).distance();
				if (dist > max_dist) {
					max_dist = dist;
				}
			}
			old = current;
		}

		return max_dist;
	}

	private void upsample() {
		LinkedList<C2> newlist = new LinkedList<C2>();

		C2 old = null;
		for (C2 current : upsample_steps) {
			if (!(old == null)) {
				double dx = current.x - old.x;
				double dy = current.y - old.y;
				C2 middle = new C2(old.x + dx / 2, old.y + dy / 2);
				newlist.add(old);
				newlist.add(middle);
				if (current == upsample_steps.getLast()) {
					newlist.add(current);
				}
			}
			old = current;
		}

		upsample_steps = newlist;
	}

	private C2 upsample_step() {
		if (!upsample_steps.isEmpty()) {
			return upsample_steps.removeFirst();
		}

		while (original_steps.size() < 2) {
			C2 step = projection.p(drawer.step(), dim);
			C2 sized = new C2(step).resize(dim.size());
			C2 croped = new C2(step).resize(dim.size(), dim.width(), dim.height(), dim.margin());

			if (sized.same(croped)) {
				original_steps.add(sized);
			} else {
				if (drawer.finished()) {
					original_steps.add(new C2(0,0));
				}
			}
		}

		upsample_steps.add(original_steps.removeFirst());
		upsample_steps.add(original_steps.getFirst());

		while (max_dist(upsample_steps) > speed * UPSAMPLE_FACTOR) {
			upsample();
		}

		return upsample_steps.removeFirst();
	}

	public C2 step() {
		C2 step;

		if (last_step == null) {
			step = upsample_step();
			last_step = step;
			return step;
		}
		double distance = 0;
		do {
			step = upsample_step();
			distance = new C2(step.x - last_step.x, step.y - last_step.y).distance();

			if (drawer.finished()) {
				last_step = step;
				return step;
			}
		} while (distance < speed);

		last_step = step;

		return step;
	}

	public boolean finished() {
		return upsample_steps.isEmpty() && drawer.finished();
	}
}
