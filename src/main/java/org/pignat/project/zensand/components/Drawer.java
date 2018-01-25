package org.pignat.project.zensand.components;

import java.io.Serializable;

public interface Drawer extends Serializable {

	abstract String name();

	abstract void init(double ballSize);

	abstract C2 step();

	abstract boolean finished();
}
