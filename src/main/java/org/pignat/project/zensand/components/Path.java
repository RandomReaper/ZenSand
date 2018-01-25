package org.pignat.project.zensand.components;

import java.io.Serializable;
import java.util.LinkedList;

public class Path implements Serializable {

	private static final double UPSAMPLE_FACTOR = 5;
	private Drawer drawer;
	private Dimensions dim;
	private Projection projection;
	double resolution;
	private C2 lastStep = null;

	private LinkedList<C2> drawerSteps;
	private LinkedList<C2> upsampledSteps;

	public Path(Drawer drawer, Projection projection, Dimensions dim, C2 pos, double resolution) {
		this.projection = projection;
		this.drawer = drawer;
		this.dim = dim;
		this.resolution = resolution;
		drawerSteps = new LinkedList<C2>();
		drawerSteps.add(pos);
		upsampledSteps = new LinkedList<C2>();
	}

	private double maxDistance(LinkedList<C2> l) {
		double max = 0;

		C2 old = null;
		for (C2 current : l) {
			if (old != null) {
				double dist = new C2(old.x() - current.x(), old.y() - current.y()).distance();
				if (dist > max) {
					max = dist;
				}
			}
			old = current;
		}

		return max;
	}

	private void upsample() {
		LinkedList<C2> newlist = new LinkedList<C2>();

		C2 old = null;
		for (C2 current : upsampledSteps) {
			if (old != null) {
				double dx = current.x() - old.x();
				double dy = current.y() - old.y();
				C2 middle = new C2(old.x() + dx / 2, old.y() + dy / 2);
				newlist.add(old);
				newlist.add(middle);
				if (current == upsampledSteps.getLast()) {
					newlist.add(current);
				}
			}
			old = current;
		}

		upsampledSteps = newlist;
	}

	private C2 upsampleStep() {
		if (!upsampledSteps.isEmpty()) {
			return upsampledSteps.removeFirst();
		}

		while (drawerSteps.size() < 2) {
			C2 step = projection.p(drawer.step(), dim);
			C2 sized = new C2(step).resize(dim.size());
			C2 croped = new C2(step).resize(dim.size(), dim.width(), dim.height(), dim.margin());

			if (sized.same(croped)) {
				drawerSteps.add(sized);
			} else {
				if (drawer.finished()) {
					drawerSteps.add(new C2(0,0));
				}
			}
		}

		upsampledSteps.add(drawerSteps.removeFirst());
		upsampledSteps.add(drawerSteps.getFirst());

		while (maxDistance(upsampledSteps) > resolution * UPSAMPLE_FACTOR) {
			upsample();
		}

		return upsampledSteps.removeFirst();
	}

	public C2 step() {
		C2 step;

		if (lastStep == null) {
			step = upsampleStep();
			lastStep = step;
			return step;
		}
		double distance = 0;
		do {
			step = upsampleStep();
			distance = new C2(step.x() - lastStep.x(), step.y() - lastStep.y()).distance();

			if (drawer.finished()) {
				lastStep = step;
				return step;
			}
		} while (distance < resolution);

		lastStep = step;

		return step;
	}

	public boolean finished() {
		return upsampledSteps.isEmpty() && drawer.finished();
	}
}
