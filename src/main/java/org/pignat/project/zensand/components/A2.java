/**
 * Angle pair
 */

package org.pignat.project.zensand.components;

public class A2 {
	public double a;
	public double b;

	public String toString() {
		return String.format("A2(%f, %f)", a, b);
	}

	public A2(double _a, double _b) {
		a = _a;
		b = _b;
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
