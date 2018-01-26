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
		double a = 0.4*.5;
		double b = 0.6*.5;
		double sa = 3.22 * count;
		double sb = 1.00 * count;
		
		if (!finished()) {
			count += 2 * Math.PI / 1000;
		}
		return new C2(a * Math.sin(sa) + b*Math.sin(sb), a * Math.cos(sa) + b*Math.cos(sb));
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
