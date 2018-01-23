package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.V2;

public class Drawer4 implements Drawer {
	private double count = 0;

	public String name() {
		return "Drawer 4";
	}

	public V2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 1000;
		}
		return new V2(0.2 * Math.sin(3.22*count) + 0.5*Math.sin(count), 0.2 * Math.cos(3.22*count) + 0.5*Math.cos(count));
	}

	public void init() {
		count = 0;
	}

	public boolean finished() {
		return count > 10 * 2 * Math.PI;
	}
}
