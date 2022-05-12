package org.pignat.project.zensand.components;

import org.pignat.project.zensand.components.path.IPath;

import java.io.Serializable;

public interface Drawer extends IPath, Serializable {

    abstract String name();

    abstract void init(double ballSize);
}
