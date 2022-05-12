package org.pignat.project.zensand;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.pignat.project.zensand.components.A2;
import org.pignat.project.zensand.components.Arms;
import org.pignat.project.zensand.components.C2;

public class ArmsTest extends TestCase {

    public ArmsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ArmsTest.class);
    }

    public void testDrawers() {
        double tolerance = 1e-6;
        double size = 10;
        Arms a = new Arms(size);

        assertTrue("pos1().distance() should equal size", Math.abs(a.pos().distance()) < tolerance);
        assertTrue(Math.abs(a.pos1().distance() - size) < tolerance);

        a.speed(new A2(Math.PI, Math.PI));
        a.step();

        // Move
        assertTrue("pos1().distance() should equal size", Math.abs(a.pos1().distance() - size) < tolerance);
        assertTrue(a.pos().distance(new C2(-20, 0)) < tolerance);
        a.step();

        // Move back to (0,0)
        assertTrue("pos1().distance() should equal size", Math.abs(a.pos1().distance() - size) < tolerance);
        assertTrue(Math.abs(a.pos().distance()) < tolerance);
    }
}
