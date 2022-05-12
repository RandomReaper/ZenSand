package org.pignat.project.zensand;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.pignat.project.zensand.components.Dimensions;

public class DimensionsTest extends TestCase {

    public DimensionsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DimensionsTest.class);
    }

    public void testDrawers() {
        Dimensions d = new Dimensions(100, 200, 5);

        assertFalse(d.size() > d.width());
        assertFalse(d.size() > d.height());
    }

}
