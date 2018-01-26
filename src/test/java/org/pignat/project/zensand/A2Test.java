package org.pignat.project.zensand;

import org.pignat.project.zensand.components.A2;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class A2Test extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public A2Test(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(A2Test.class);
	}

	public void testNorm() {
		double angle = Math.PI;
		double offset = Math.PI/10;
		
		assertTrue(new A2(+angle+offset, +angle+offset).norm().a() <= +angle+offset);
		assertTrue(new A2(+angle+offset, +angle+offset).norm().b() <= +angle+offset);
		assertTrue(new A2(-angle-offset, -angle-offset).norm().a() >= -angle-offset);
		assertTrue(new A2(-angle-offset, -angle-offset).norm().b() >= -angle-offset);
	}
	
	public void testToString() {
		A2 a1 = new A2(1, 2);
		A2 a2 = new A2(2, 2);
		
		assertFalse(a1.toString().length() == 0);
		assertFalse(a2.toString().length() == 0);
		assertFalse(a1.toString().equals(a2.toString()));
	}
}
