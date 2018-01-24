package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.V2;
import org.pignat.project.zensand.components.A2;
import org.pignat.project.zensand.components.C2;

public class Drawer3 implements Drawer {
	private double count = 0;

	public String name() {
		return "Drawer 3";
	}

	public void init(double ball_size) {
		count = 0;
	}

	public C2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 1000;
		}

		double a = count * 13;
		double b = -count * 17;
		C2 step = V2.XY(new A2(a, b), .33);

		return step;
	}

	public boolean finished() {
		return count > 2 * Math.PI;
	}
}
