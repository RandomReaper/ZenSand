/**
 * XY coordinate 
 */
package org.pignat.project.zensand.components;

public class C2 {
	public double x;
	public double y;

	public boolean same(C2 other) {
		return other.x == x && other.y == y;
	}
	
	public String toString() {
		return String.format("C2(%f, %f)", x, y);
	}

	public C2(C2 c) {
		x = c.x;
		y = c.y;
	}
	
	public C2(double _x, double _y) {
		x = _x;
		y = _y;
	}

	public C2 xycrop(double width, double height, double margin) {
		double cx = width / 2 - margin;
		double cy = height / 2 - margin;

		if (x > cx) {
			x = cx;
		}

		if (x < -cx) {
			x = -cx;
		}

		if (y > cy) {
			y = cy;
		}

		if (y < -cy) {
			y = -cy;
		}

		return this;
	}
	
	public C2 mirror_margin(double width, double height, double margin) {
		double cx = width / 2 - margin;
		double cy = height / 2 - margin;
		if (x > cx) {
			x = 2*cx - x;
		}

		if (x < -cx) {
			x = -2*cx - x;
		}

		if (y > cy) {
			y = 2*cy - y;
		}

		if (y < -cy) {
			y = -2*cy - y;
		}
		
		return this.xycrop(width, height, margin);
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

	public C2 resize(double size) {
		x *= 2 * size;
		y *= 2 * size;
		return this;
	}
	
	public C2 desize(double size) {
		x /= 2 * size;
		y /= 2 * size;
		return this;	
	}
	
	public C2 resize(double size, double width, double height, double margin) {
		resize(size);
		return xycrop(width, height, margin);
	}
}
