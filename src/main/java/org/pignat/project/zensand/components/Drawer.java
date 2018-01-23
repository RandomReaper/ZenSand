package org.pignat.project.zensand.components;

public interface Drawer {

	abstract String name();

	abstract void init();

	abstract C2 step();

	abstract boolean finished();
}
