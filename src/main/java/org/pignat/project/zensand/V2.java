package org.pignat.project.zensand;

public class V2 {
	double x;
	double y;
	private static double MAX_SPEED = 0.1;
	
	public String toString()
	{
		return String.format("%f, %f", x, y);
	}
	
	public V2(double a, double b)
	{
		x = a;
		y = b;
	}
	
	static V2 XY(V2 motor_pos, double size)
	{
		return new V2
		(
			size*(Math.cos(motor_pos.x)+Math.cos(motor_pos.y)),
			size*(Math.sin(motor_pos.x)+Math.sin(motor_pos.y))
		);
	}
	
	static boolean XYisCropped(V2 motor_pos, double size, double width, double height, double margin)
	{
		V2 v = V2.XY(motor_pos, size);
		
		if (v.x > width/2-margin)
		{
			return true;
		}

		if (v.x < -width/2+margin)
		{
			return true;
		}

		if (v.y > height/2-margin)
		{
			return true;
		}

		if (v.y < -height/2+margin)
		{
			return true;
		}
		
		return false;
	}
	
	static V2 XYCropped(V2 motor_pos, double size, double width, double height, double margin)
	{
		V2 v = V2.XY(motor_pos, size);
		
		if (v.x > width/2-margin)
		{
			v.x = width/2 - margin;
		}

		if (v.x < -width/2+margin)
		{
			v.x = -width/2 + margin;
		}

		if (v.y > height/2-margin)
		{
			v.y = height/2 - margin;
		}

		if (v.y < -height/2+margin)
		{
			v.y = -height/2 + margin;
		}
		
		return v;
	}
	
	static public double sqdiff(V2 a, V2 b)
	{
		return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
	}
	
	public static double error(V2 target_xy, V2 motor, V2 speed, double size)
	{
		V2 new_pos = motor.next(speed);
		V2 motor_xy = XY(new_pos, size);
		return V2.sqdiff(motor_xy, target_xy); 
	}

	static int nr = 20;
	static V2 tests[];
	
	public static V2 bestSpeed(V2 motor, V2 cropped, double size) {
		/*V2 tests[] =
		{
			new V2(0, -MAX_SPEED),
			new V2(0, -MAX_SPEED/2),
			new V2(0, 0),
			new V2(0, +MAX_SPEED/2),
			new V2(0, +MAX_SPEED),
			
			new V2(-MAX_SPEED/2, -MAX_SPEED),
			new V2(-MAX_SPEED/2, -MAX_SPEED/2),
			new V2(-MAX_SPEED/2, 0),
			new V2(-MAX_SPEED/2, +MAX_SPEED),
			new V2(-MAX_SPEED/2, +MAX_SPEED/2),

			new V2(MAX_SPEED/2, -MAX_SPEED),
			new V2(MAX_SPEED/2, -MAX_SPEED/2),
			new V2(MAX_SPEED/2, 0),
			new V2(MAX_SPEED/2, +MAX_SPEED),
			new V2(MAX_SPEED/2, +MAX_SPEED/2),

		};
		*/
		if (tests == null)
		{
			tests = new V2[nr*nr];
			
			for (int x = 0 ; x < nr ;x++)
			{
				for (int y = 0 ; y < nr; y++)
				{
					//System.out.println(String.format("x:%d, y:%d", x, y));
					tests[nr*x+y] = new V2(MAX_SPEED/nr*(x-nr/2), MAX_SPEED/nr*(y-nr/2));
				}
			}
		}
		
		double best_error = Double.MAX_VALUE;
		int best_index = 0;
		
		for (int i = 0 ; i < tests.length; i++)
		{
			double error = V2.error(cropped, motor, tests[i], size);
			//System.out.println("test["+i+"] error : "+ error);
			if (error < best_error)
			{
				best_error = error;
				best_index = i;
			}
		}
		
		//System.out.println("best_error : " + best_error);
		
		return tests[best_index];
	}

	public V2 next(V2 speed) {
		return new V2(x+speed.x, y+speed.y-speed.x);
	}

	
}
