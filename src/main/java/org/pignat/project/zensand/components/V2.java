/**
 * C2 and A2 utilities
 */

package org.pignat.project.zensand.components;

import java.io.Serializable;

public class V2 implements Serializable {
	
	public static C2 xy(A2 motor, double size) {
		return new C2(size * (Math.cos(motor.a()) + Math.cos(motor.b())),
				size * (Math.sin(motor.a()) + Math.sin(motor.b())));
	}
	
	public static double lawOfCosine(double a, double b, double c) {
		return Math.acos((a * a + b * b - c * c) / (2 * a * b));
	}

	public static A2 anglesSolution1(C2 dest, double size) {
		double dist = dest.distance();
		double d1 = Math.atan2(dest.y(), dest.x());
		double d2 = lawOfCosine(dist, size, size);
		double a1 = d1 + d2;
		double a2 = lawOfCosine(size, size, dist);
		a2 += Math.PI + a1;
		return new A2(a1, a2).norm();
	}

	public static A2 anglesSolution2(C2 dest, double size) {
		double dist = dest.distance();
		double d1 = Math.atan2(dest.y(), dest.x());
		double d2 = -lawOfCosine(dist, size, size);
		double a1 = d1 + d2;
		double a2 = -lawOfCosine(size, size, dist);
		a2 += Math.PI + a1;
		return new A2(a1, a2).norm();
	}
	
	/**
	 * Compute the next angle position using the XY destination and current arm's positions 
	 * @param current
	 * @param size
	 * @return
	 */
	public static A2 angles(A2 current, C2 dest, double size) {
		
		if (dest.x() == 0 && dest.y() == 0)
		{
			return new A2(current.a(), -Math.PI);
		}
		
		A2 angles1 = anglesSolution1(dest, size);
		A2 angles2 = anglesSolution2(dest, size);

		/**
		 * dest can be out of reach because of computational error or because of a caller's error
		 * handle computational error where the dest is too far for the size.
		 */
		if (Double.isNaN(angles1.a()) || Double.isNaN(angles1.b()))
		{
			return current;
		}
		
		/**
		 * Select the solution with the lower cost (=movement)
		 */
		double speed1 = new A2(angles1.a() - current.a(), angles1.b() - current.b()).norm().a();
		double speed2 = new A2(angles2.a() - current.a(), angles2.b() - current.b()).norm().a();

		if (Math.abs(speed1) < Math.abs(speed2)) {
			return angles1;
		} else {
			return angles2;
		}
	}

	public static A2 bestPos(A2 current, C2 dest, double size) {

		return angles(current, dest, size);
	}

	public static A2 bestSpeed(A2 current, C2 dest, double size) {
		A2 angles = bestPos(current, dest, size);

		bestPos(current, new C2(10000,10000), size);
		angles.a(angles.a() - current.a());
		angles.b(angles.b() - current.b() + angles.a());

		return angles;
	}
}
