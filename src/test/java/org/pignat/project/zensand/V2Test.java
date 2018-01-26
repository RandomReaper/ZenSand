package org.pignat.project.zensand;

import org.pignat.project.zensand.components.V2;
import org.pignat.project.zensand.components.A2;
import org.pignat.project.zensand.components.C2;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class V2Test extends TestCase {

	public V2Test(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(V2Test.class);
	}

	public void testAngles() {
		double tolerance = 1e-6;
		double size = 10;
		A2 startAngle = new A2(10, 15).norm();
		C2 dest;
		C2 go;
		
		dest = new C2(size/10, size/10);
		go = V2.xy(startAngle.move(V2.bestSpeed(startAngle, dest, size)), size);
		assertTrue(go.distance(dest) < tolerance);
		
		dest = new C2(0,0);
		go = V2.xy(startAngle.move(V2.bestSpeed(startAngle, dest, size)), size);
		assertTrue(go.distance(dest) < tolerance);
		
		dest = new C2(size, size);
		go = V2.xy(startAngle.move(V2.bestSpeed(startAngle, dest, size)), size);
		assertTrue(go.distance(dest) < tolerance);
	}
	
	public void testToString() {
		A2 a1 = new A2(1, 2);
		A2 a2 = new A2(2, 2);
		
		assertFalse(a1.toString().length() == 0);
		assertFalse(a2.toString().length() == 0);
		assertFalse(a1.toString().equals(a2.toString()));
	}
}
