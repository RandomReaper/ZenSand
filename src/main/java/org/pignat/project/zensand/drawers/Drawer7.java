package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.C2;

public class Drawer7 implements Drawer {
	private double count;
	private double ballSize;
	private boolean finished;

	@Override
	public String name() {
		return "Drawer7";
	}

	@Override
	public void init(double ballSize) {
		this.ballSize = ballSize;
		finished = false;

		double a = 0.01;
		double b = ballSize / 10;
		double r;

		do {
			r = a + b * count;
			count += 2 * Math.PI;
		} while (r < 1.1);
	}

	@Override
	public C2 step() {
		if (!finished()) {
			count -= 2 * Math.PI / 10000;
		}

		double a = 0.01;
		double b = ballSize / 10;
		double r = a + b * count;

		if (r < ballSize / 10) {
			finished = true;
		}

		return new C2(r * Math.cos(count), r * Math.sin(count));
	}

	@Override
	public boolean finished() {
		return finished;
	}
}
