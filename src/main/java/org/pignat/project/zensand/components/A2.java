/**
 * Angle pair
 */

package org.pignat.project.zensand.components;

import java.io.Serializable;

public class A2 implements Serializable {
	private double a;
	private double b;
	
	public double a() {
		return a;
	}

	public double b() {
		return b;
	}
	
	public void a(double a) {
		this.a = a;
	}

	public void b(double b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return String.format("A2(%f, %f)", a, b);
	}
	
	public A2(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public A2(A2 that) {
		this(that.a, that.b);
	}

	public A2 norm() {
		if (a > Math.PI) {
			a -= 2 * Math.PI;
		}

		if (a < -Math.PI) {
			a += 2 * Math.PI;
		}

		if (b > Math.PI) {
			b -= 2 * Math.PI;
		}

		if (b < -Math.PI) {
			b += 2 * Math.PI;
		}

		return this;
	}

	public A2 move(A2 speed) {
		A2 n = new A2(a + speed.a, b + speed.b - speed.a);

		return n.norm();
	}
}
