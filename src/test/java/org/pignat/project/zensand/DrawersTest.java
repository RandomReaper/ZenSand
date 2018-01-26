package org.pignat.project.zensand;

import org.pignat.project.zensand.components.Drawers;
import org.pignat.project.zensand.components.Drawer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DrawersTest extends TestCase {

	public DrawersTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DrawersTest.class);
	}

	public void testDrawers() {
		int nr = 1000*1000*1000;
		assertTrue(Drawers.size() > 0);
		
		for (int i = 0 ; i < Drawers.size();i++)
		{
			Drawer d = Drawers.get(i, 0.01);

			assertFalse(String.format("drawer '%s' (%s) finished with zero points", d.name(), d.getClass()), d.finished());
			
			for (int j = 0 ; j < nr; j++)
			{
				d.step();
				if (d.finished())
				{
					break;
				}
			}
			
			assertTrue(String.format("drawer '%s' (%s) not finished with %d points", d.name(), d.getClass(), nr), d.finished());
			
			assertTrue(String.format("drawer '%s' (%s) is finished but gives new points ", d.name(), d.getClass()), d.step().same(d.step()));
		}
	}
	
	public void testToString() {
	}
}
