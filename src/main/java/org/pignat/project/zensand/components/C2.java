/**
 * XY coordinate 
 */
package org.pignat.project.zensand.components;

public class C2 {
	public double x;
	public double y;

	public String toString() {
		return String.format("C2(%f, %f)", x, y);
	}

	public C2(double _x, double _y) {
		x = _x;
		y = _y;
	}

	public C2 xycrop(double width, double height, double margin) {
		if (x > width / 2 - margin) {
			x = width / 2 - margin;
		}

		if (x < -width / 2 + margin) {
			x = -width / 2 + margin;
		}

		if (y > height / 2 - margin) {
			y = height / 2 - margin;
		}

		if (y < -height / 2 + margin) {
			y = -height / 2 + margin;
		}

		return this;
	}

	public double distance() {
		return Math.sqrt(x * x + y * y);
	}
	
	public static C2 XYCropped(A2 motor_pos, double size, double width, double height, double margin) {
		C2 v = V2.XY(motor_pos, size);
		return v.xycrop(width, height, margin);
	}

	public static double sqdiff(C2 a, C2 b) {
		return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
	}

	public C2 resize(double size, double width, double height, double margin) {
		x *= 2 * size;
		y *= 2 * size;
		return xycrop(width, height, margin);
	}
}
