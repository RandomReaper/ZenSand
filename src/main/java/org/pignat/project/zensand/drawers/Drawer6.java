package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.C2;

public class Drawer6 implements Drawer {
	private double count;
	private double ball_size;
	private boolean finished;
	
	public String name() {
		return "Drawer 6";
	}

	public void init(double _ball_size) {
		ball_size = _ball_size;
		count = 0;
		finished = false;
	}
	
	public C2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 10000;
		}
		//return new C2(0,0);
		double a = 0.01;
		double b = ball_size/10;
		double r = a + b*count;
		
		if (r > 1.1) {
			finished  = true;
		}
		
		return new C2(r*Math.cos(count), r*Math.sin(count));
	}

	public boolean finished() {
		return finished;
	}
}
