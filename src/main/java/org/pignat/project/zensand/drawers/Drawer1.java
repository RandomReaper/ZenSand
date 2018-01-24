package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.C2;

public class Drawer1 implements Drawer {
	private double count = 0;

	public String name() {
		return "Drawer 1";
	}

	public C2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 1000;
		}
		return new C2(0.95 * Math.sin(count), 0.95 * Math.cos(count));
	}

	public void init(double ball_size) {
		count = 0;
	}

	public boolean finished() {
		return count > 2 * Math.PI;
	}
}
