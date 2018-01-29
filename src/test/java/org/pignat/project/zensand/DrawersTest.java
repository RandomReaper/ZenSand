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

	private static void testDrawer(Drawer d) {
		int nr = 1000*1000*1000;
		
		assertFalse(String.format("drawer '%s' (%s) finished with zero points", d.name(), d.getClass()), d.finished());
		
		int j;
		for (j = 0 ; j < nr; j++)
		{
			if (d.finished())
			{
				break;
			}
			d.step();
		}
		
		assertFalse(String.format("drawer '%s' (%s) finished with 0 points", d.name(), d.getClass()), j == 0);
		assertTrue(String.format("drawer '%s' (%s) not finished with %d points", d.name(), d.getClass(), nr), d.finished());
		
		assertTrue(String.format("drawer '%s' (%s) is finished but gives new points ", d.name(), d.getClass()), d.step().same(d.step()));		
	}
	
	public void testDrawers() {

		assertTrue(Drawers.size() > 0);
		
		for (int i = 0 ; i < Drawers.size();i++)
		{
			testDrawer(Drawers.get(i, 0.01));
		}
	}
	
	public void testToString() {
	}
}
