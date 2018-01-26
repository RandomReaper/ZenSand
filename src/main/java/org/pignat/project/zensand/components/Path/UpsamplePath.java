package org.pignat.project.zensand.components.Path;

import java.util.LinkedList;

import org.pignat.project.zensand.components.C2;

public class UpsamplePath implements IPath {

	private IPath sub;
	private double resolution;

	private LinkedList<C2> subSteps = new LinkedList<C2>();
	private LinkedList<C2> steps = new LinkedList<C2>();

	public UpsamplePath(IPath sub, C2 startPosition, double resolution) {
		this.sub = sub;
		this.resolution = resolution;
		subSteps.add(startPosition);
	}

	private double maxDistance(LinkedList<C2> l) {
		double max = 0;

		C2 old = null;
		for (C2 current : l) {
			if (old != null) {
				double dist = current.distance(old);
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
		for (C2 current : steps) {
			if (old != null) {
				double dx = current.x() - old.x();
				double dy = current.y() - old.y();
				C2 middle = new C2(old.x() + dx / 2, old.y() + dy / 2);
				newlist.add(old);
				newlist.add(middle);
				if (current == steps.getLast()) {
					newlist.add(current);
				}
			}
			old = current;
		}

		steps = newlist;
	}

	@Override
	public boolean finished() {
		return steps.isEmpty() && sub.finished();
	}

	@Override
	public C2 step() {
		if (!steps.isEmpty()) {
			return steps.removeFirst();
		}

		while (subSteps.size() < 2) {
			subSteps.add(sub.step());
		}

		steps.add(subSteps.removeFirst());
		steps.add(subSteps.getFirst());

		while (maxDistance(steps) > resolution) {
			upsample();
		}

		return steps.removeFirst();
	}
}
