package org.pignat.project.zensand.components;

public interface Drawer {

	abstract String name();

	abstract void init(double ball_size);

	abstract C2 step();

	abstract boolean finished();
}
