package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.C2;

public class Drawer4 implements Drawer {
	private double count = 0;

	@Override
	public String name() {
		return "Drawer4";
	}

	@Override
	public C2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 1000;
		}
		return new C2(0.2 * Math.sin(3.22*count) + 0.5*Math.sin(count), 0.2 * Math.cos(3.22*count) + 0.5*Math.cos(count));
	}

	@Override
	public void init(double ballSize) {
		count = 0;
	}

	@Override
	public boolean finished() {
		return count > 10 * 2 * Math.PI;
	}
}
