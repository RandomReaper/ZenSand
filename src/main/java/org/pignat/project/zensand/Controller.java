package org.pignat.project.zensand;

public class Controller
{
	private double size;
	private double margin;
	private double width;
	private double height;
	private Arms arms;
	
	private V2 speed  = new V2(
		+0.37/100,
		-0.38/100
		);

	private V2 m;
	
	public Controller(double w, double h, double ma)
	{
		height = h;
		width = w;
		margin = ma;
		
		double lower_bound = Math.min((width-2*margin)/2, (height-2*margin)/2);
		double upper_bound = Math.sqrt(Math.pow(width-2*margin, 2)+Math.pow(height-2*margin, 2))/4;
		size = (int)Math.min(upper_bound, lower_bound);
		arms = new Arms(size);
		m = arms.m();
	}
	
	public double size()
	{
		return size;
	}
	
	public double margin()
	{
		return margin;
	}
	
	public Arms arms()
	{
		return arms;
	}
	
	public V2 pos()
	{
		return V2.XY(m, size);
	}

	public void step() {
		m = m.next(speed);
		
		V2 cropped = V2.XYCropped(m, size, width, height, margin);
		
		V2 best_speed = speed;
		if (V2.sqdiff(arms.pos(), cropped) > Math.pow(.25, 2))
		{
			//System.out.println("error to big ("+V2.sqdiff(pos(), cropped)+")");
			best_speed = V2.bestSpeed(arms.m(), cropped, size);
		}
		
		arms.speed(best_speed);
		arms.step();
	}
}
