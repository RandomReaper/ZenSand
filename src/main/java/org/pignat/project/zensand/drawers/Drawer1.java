package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.V2;

public class Drawer1 implements Drawer {
	private double count = 0;

	public String name() {
		return "Drawer 1";
	}

	public V2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 1000;
		}
		return new V2(0.95 * Math.sin(count), 0.95 * Math.cos(count));
	}

	public void init() {
		count = 0;
	}

	public boolean finished() {
		return count > 2 * Math.PI;
	}
}
