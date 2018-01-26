package org.pignat.project.zensand.components;

import java.io.Serializable;

import org.pignat.project.zensand.components.path.IPath;

public interface Drawer extends IPath, Serializable {

	abstract String name();

	abstract void init(double ballSize);
}
