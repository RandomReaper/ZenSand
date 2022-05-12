package org.pignat.project.zensand.components.path;

import org.pignat.project.zensand.components.C2;

import java.io.Serializable;

public interface IPath extends Serializable {
    abstract boolean finished();

    abstract C2 step();
}
