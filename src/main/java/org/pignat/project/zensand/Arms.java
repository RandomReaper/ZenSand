package org.pignat.project.zensand;

public class Arms
{
	private V2 speed = new V2(0,0);
	
	private V2 m = new V2(0, Math.PI);
	private double size;
	
	public Arms(double s)
	{
		size = s;
	}
	
	public void speed(V2 s)
	{
		speed = s;
	}
	
	public void step()
	{
		m = m.next(speed);
	}

	public V2 pos()
	{
		return V2.XY(m, size);
	}

	public V2 pos1()
	{
		return V2.XY(new V2(m.x, m.x), size/2);
	}
	
	public V2 m() {
		return m;
	}
}
