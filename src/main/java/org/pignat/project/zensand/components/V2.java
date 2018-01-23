package org.pignat.project.zensand.components;

public class V2 {
	public double x;
	public double y;

	public String toString() {
		return String.format("%f, %f", x, y);
	}

	public V2(double a, double b) {
		x = a;
		y = b;
	}

	public static V2 XY(V2 motor_pos, double size) {
		return new V2(size * (Math.cos(motor_pos.x) + Math.cos(motor_pos.y)),
				size * (Math.sin(motor_pos.x) + Math.sin(motor_pos.y)));
	}

	public V2 xycrop(double width, double height, double margin) {
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

	public static V2 XYCropped(V2 motor_pos, double size, double width, double height, double margin) {
		V2 v = V2.XY(motor_pos, size);
		return v.xycrop(width, height, margin);
	}

	public static double sqdiff(V2 a, V2 b) {
		return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
	}

	public static double error(V2 target_xy, V2 motor, V2 speed, double size) {
		V2 new_pos = motor.next(speed);
		V2 motor_xy = XY(new_pos, size);
		return V2.sqdiff(motor_xy, target_xy);
	}

	public V2 norm() {
		if (x > Math.PI) {
			x -= 2 * Math.PI;
		}

		if (x < -Math.PI) {
			x += 2 * Math.PI;
		}

		if (y > Math.PI) {
			y -= 2 * Math.PI;
		}

		if (y < -Math.PI) {
			y += 2 * Math.PI;
		}

		return this;
	}

	public V2 next(V2 speed) {
		V2 n = new V2(x + speed.x, y + speed.y - speed.x);

		return n.norm();
	}

	public static double lawOfCosine(double a, double b, double c) {
		return Math.acos((a * a + b * b - c * c) / (2 * a * b));
	}

	public double distance() {
		return Math.sqrt(x * x + y * y);
	}

	public V2 angles_a(double size) {
		double dist = distance();
		double d1 = Math.atan2(y, x);
		double d2 = lawOfCosine(dist, size, size);
		double a1 = d1 + d2;
		double a2 = lawOfCosine(size, size, dist);
		a2 += Math.PI + a1;
		return new V2(a1, a2).norm();
	}

	public V2 angles_b(double size) {
		if (x == 0 && y == 0) {
			System.err.println("uhoh");
		}
		double dist = distance();
		double d1 = Math.atan2(y, x);
		double d2 = -lawOfCosine(dist, size, size);
		double a1 = d1 + d2;
		double a2 = -lawOfCosine(size, size, dist);
		a2 += Math.PI + a1;
		return new V2(a1, a2).norm();
	}

	public V2 angles(V2 current_angle, double size) {
		V2 angles_a = angles_a(size);
		V2 angles_b = angles_b(size);

		double speed_a = new V2(angles_a.x - current_angle.x, angles_a.y - current_angle.y).norm().x;
		double speed_b = new V2(angles_b.x - current_angle.x, angles_b.y - current_angle.y).norm().x;

		if (Math.abs(speed_a) < Math.abs(speed_b)) {
			return angles_a;
		} else {
			return angles_b;
		}
	}

	public static V2 bestPos(V2 current_angle, V2 cropped, double size) {

		return cropped.angles(current_angle, size);
	}

	public static V2 bestSpeed(V2 motor, V2 cropped, double size) {
		V2 angles = bestPos(motor, cropped, size);

		angles.x = angles.x - motor.x;
		angles.y = angles.y - motor.y + angles.x;

		return angles;
	}

	public V2 resize(double size, double width, double height, double margin) {
		x *= 2 * size;
		y *= 2 * size;
		return xycrop(width, height, margin);
	}
}
