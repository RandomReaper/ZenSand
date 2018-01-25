package org.pignat.project;

import org.pignat.project.zensand.components.C2;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class C2Test extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public C2Test(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(C2Test.class);
	}

	public void testCopyConstructor1() {
		C2 c1 = new C2(1,1);
		C2 c2 = new C2(c1);
		assertTrue(c1.same(c2));
	}
	
	public void testCopyConstructor2() {
		C2 c1 = new C2(1,1);
		C2 c2 = new C2(c1);
		C2 c3 = new C2(c1);
		c2.x = 12;
		assertFalse(c1.same(c2));

		c3.y = 12;
		assertFalse(c1.same(c3));
	}
	
	public void testSame() {
		C2 c1 = new C2(1, 2);
		C2 c2 = new C2(1, 2);
		C2 c3 = new C2(2, 1);
		
		assertTrue(c1.same(c2));
		assertFalse(c1.same(c3));
	}
}
