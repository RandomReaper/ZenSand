/**
 * C2 and A2 utilities
 */

package org.pignat.project.zensand.components;

public class V2 {
	public static C2 XY(A2 motor_pos, double size) {
		return new C2(size * (Math.cos(motor_pos.a) + Math.cos(motor_pos.b)),
				size * (Math.sin(motor_pos.a) + Math.sin(motor_pos.b)));
	}
	
	public static double lawOfCosine(double a, double b, double c) {
		return Math.acos((a * a + b * b - c * c) / (2 * a * b));
	}

	public static A2 angles_a(C2 dest, double size) {
		double dist = dest.distance();
		double d1 = Math.atan2(dest.y, dest.x);
		double d2 = lawOfCosine(dist, size, size);
		double a1 = d1 + d2;
		double a2 = lawOfCosine(size, size, dist);
		a2 += Math.PI + a1;
		return new A2(a1, a2).norm();
	}

	public static A2 angles_b(C2 dest, double size) {
		double dist = dest.distance();
		double d1 = Math.atan2(dest.y, dest.x);
		double d2 = -lawOfCosine(dist, size, size);
		double a1 = d1 + d2;
		double a2 = -lawOfCosine(size, size, dist);
		a2 += Math.PI + a1;
		return new A2(a1, a2).norm();
	}
	
	/**
	 * Compute the next angle position using the XY destination and current arm's positions 
	 * @param current_angle
	 * @param size
	 * @return
	 */
	public static A2 angles(C2 dest, A2 current_angle, double size) {
		
		if (dest.x == 0 && dest.y == 0)
		{
			return new A2(current_angle.a, -Math.PI);
		}
		
		A2 angles_a = angles_a(dest, size);
		A2 angles_b = angles_b(dest, size);

		/**
		 * dest can be out of reach because of computational error or because of a caller's error
		 * handle computational error where the dest is too far for the size.
		 */
		if (Double.isNaN(angles_a.a) || Double.isNaN(angles_a.b))
		{
			return current_angle;
		}
		
		double speed_a = new A2(angles_a.a - current_angle.a, angles_a.b - current_angle.b).norm().a;
		double speed_b = new A2(angles_b.a - current_angle.a, angles_b.b - current_angle.b).norm().a;

		if (Math.abs(speed_a) < Math.abs(speed_b)) {
			return angles_a;
		} else {
			return angles_b;
		}
	}

	public static A2 bestPos(A2 current_angle, C2 dest, double size) {

		return angles(dest, current_angle, size);
	}

	public static A2 bestSpeed(A2 motor, C2 dest, double size) {
		A2 angles = bestPos(motor, dest, size);

		bestPos(motor, new C2(10000,10000), size);
		angles.a = angles.a - motor.a;
		angles.b = angles.b - motor.b + angles.a;

		return angles;
	}
}
