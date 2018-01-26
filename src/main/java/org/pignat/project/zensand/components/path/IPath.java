package org.pignat.project.zensand.components.path;

import java.io.Serializable;

import org.pignat.project.zensand.components.C2;

public interface IPath extends Serializable {
	abstract boolean finished();
	abstract C2 step();
}
