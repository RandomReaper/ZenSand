package org.pignat.project.zensand;

import org.pignat.project.zensand.components.Drawers;
import org.pignat.project.zensand.components.Arms;
import org.pignat.project.zensand.components.Controller;
import org.pignat.project.zensand.components.Dimensions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ControllerTest extends TestCase {

	public ControllerTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(ControllerTest.class);
	}

	private static void testController(double width, double height, double margin) {
		int nr = 1000*1000*1000;
		Dimensions dim = new Dimensions(width, height, margin);
		Arms arms = new Arms(dim.size());
		for (int i = 0 ; i < Drawers.size();i++)
		{
			Controller c = new Controller(Drawers.get(i,  0.01), arms, dim, 0.5);

			for (int j = 0 ; j < nr; j++)
			{
				c.step();
				if (c.finished())
				{
					break;
				}
			}
			
			assertTrue(String.format("drawer '%s' (%s) not finished with %d points", c.drawer().name(), dim.getClass(), nr), c.drawer().finished());
		}	
	}
	
	public void testDrawers() {

		testController(100, 100, 5);
	}
	
	public void testToString() {
	}
}
