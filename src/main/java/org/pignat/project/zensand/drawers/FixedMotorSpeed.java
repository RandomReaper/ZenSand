package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.V2;
import org.pignat.project.zensand.components.A2;
import org.pignat.project.zensand.components.C2;

public class FixedMotorSpeed implements Drawer {
	private double count = 0;
	private double speedA;
	private double speedB;
	private double duration;
	
	public void setSpeed(double speedA, double speedB, double duration) {
		this.speedA = speedA;
		this.speedB = speedB;
		this.duration = duration;
	}
	
	@Override
	public String name() {
		return "FixedMotorSpeed";
	}

	@Override
	public void init(double ballSize) {
		count = 0;
		setSpeed(0.1, 0.11, 100 * Math.PI);
	}

	@Override
	public C2 step() {
		if (!finished()) {
			count += 2 * Math.PI / 100;
		}
		
		double a = count * speedA;
		double b = count * speedB;

		return V2.xy(new A2(a, b), 1/(2*Math.sqrt(2)));
	}

	@Override
	public boolean finished() {
		return count > duration;
	}
}
