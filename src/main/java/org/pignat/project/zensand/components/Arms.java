package org.pignat.project.zensand.components;

public class Arms {
	private A2 speed = new A2(0, 0);

	private A2 m = new A2(0, Math.PI);
	private double size;

	public Arms(double s) {
		size = s;
	}

	public void speed(A2 s) {
		speed = s;
	}

	public void pos(A2 p) {
		m = p;
	}

	public void step() {
		m = m.move(speed);
	}

	public C2 pos() {
		return V2.XY(m, size);
	}

	public C2 pos1() {
		return V2.XY(new A2(m.a, m.a), size / 2);
	}

	public A2 m() {
		return m;
	}
}
