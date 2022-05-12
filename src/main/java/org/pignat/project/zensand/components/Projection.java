package org.pignat.project.zensand.components;

import java.io.Serializable;

public interface Projection extends Serializable {
    abstract C2 p(C2 in, Dimensions dim);
}
