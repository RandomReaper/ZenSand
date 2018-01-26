package org.pignat.project.zensand;

import java.util.LinkedList;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Dimensions;
import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.path.CompletePath;
import org.pignat.project.zensand.projection.ProjectionNop;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PathTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public PathTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(PathTest.class);
	}

	private Dimensions dim = new Dimensions(200, 100, 5);
	
	public void testResample() {
		Drawer d = new Drawer() {

			double count;
			
			@Override
			public String name() {
				return "Test drawer";
			}

			@Override
			public void init(double ballSize) {
				count = 0;
			}

			@Override
			public C2 step() {
				if (!finished()) {
					count += 2 * Math.PI / 1000;
				}
				return new C2(0.5 * Math.sin(count), 0.5 * Math.cos(count));
			}

			@Override
			public boolean finished() {
				return count > 2 * Math.PI;
			}
			
		};
		final int nr = 100*1000;
		d.init(0.01);
		CompletePath p1 = new CompletePath(d, new ProjectionNop(), dim, new C2(0,0), 5);
		LinkedList<C2> steps1 = new LinkedList<C2>();
		
		for (int i = 0 ; i < nr; i++) {
			steps1.add(p1.step());
			if (p1.finished()) {
				break;
			}
		}

		d.init(0.01);
		CompletePath p2 = new CompletePath(d, new ProjectionNop(), dim, new C2(0,0), 2);
		LinkedList<C2> steps2 = new LinkedList<C2>();

		for (int i = 0 ; i < nr; i++) {
			steps2.add(p2.step());
			if (p1.finished()) {
				break;
			}
		}

		assertTrue(String.format("This Path should finish in less than %d steps", nr), steps1.size()<nr);
		assertTrue(String.format("This Path should finish in less than %d steps", nr), steps2.size()<nr);
		
		assertTrue("p2 should have generated at least twice steps as p1", steps2.size()/steps1.size() >= 2);
		
		assertTrue(true);
	}
	
	public void test0SpeedPoints() {
		Drawer doubleDrawer = new Drawer() {

			int count;
			double countd;
			
			@Override
			public String name() {
				return "Test drawer, draw each point twice";
			}

			@Override
			public void init(double ballSize) {
				count = 0;
			}

			@Override
			public C2 step() {
				if (!finished()) {
					count++;
					if (count % 1 == 0)
					{
						countd += 2 * Math.PI / 1000;
					}
				}
				return new C2(0.5 * Math.sin(count), 0.5 * Math.cos(count));
			}

			@Override
			public boolean finished() {
				return countd > 2 * Math.PI;
			}
			
		};
		
		final int nr = 100*1000;
		doubleDrawer.init(0.01);
		CompletePath p1 = new CompletePath(doubleDrawer, new ProjectionNop(), dim, new C2(0,0), 5);
		LinkedList<C2> steps1 = new LinkedList<C2>();
		
		for (int i = 0 ; i < nr; i++) {
			steps1.add(p1.step());
			if (p1.finished()) {
				break;
			}
		}

		System.out.println(steps1.size());
		assertTrue(String.format("This Path should finish in less than %d steps", nr), steps1.size()<nr);
		
		assertTrue(true);
	}
}
