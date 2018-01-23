package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.V2;

public class Drawer2 implements Drawer {
	private double count = 0;

	public String name() {
		return "Drawer 2";
	}

	public void init() {
		count = 0;
	}

	public V2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 10000;
		}

		double a = count * 13;
		double b = -count * 17;
		V2 step = V2.XY(new V2(a, b), .5);

		return step;
	}

	public boolean finished() {
		return count > 4 * Math.PI;
	}
}
