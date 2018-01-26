/**
 * XY coordinate 
 */
package org.pignat.project.zensand.components;

import java.io.Serializable;

public class C2 implements Serializable {
	private double x;
	private double y;
	
	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public void x(double x) {
		this.x = x;
	}

	public void y(double y) {
		this.y = y;
	}
	
	public boolean same(C2 other) {
		return other.x == x && other.y == y;
	}
	
	@Override
	public String toString() {
		return String.format("C2(%f, %f)", x, y);
	}

	public C2(C2 c) {
		x = c.x;
		y = c.y;
	}
	
	public C2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public C2 crop(double width, double height, double margin) {
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
	
	public C2 mirrorMargin(double width, double height, double margin) {
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
		
		return this.crop(width, height, margin);
	}

	public double distance() {
		return Math.sqrt(x * x + y * y);
	}

	public double distance(C2 that) {
		return new C2(that.x()-x, that.y()-y).distance();
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
		return crop(width, height, margin);
	}
}
