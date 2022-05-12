package org.pignat.project.zensand.projection;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Dimensions;
import org.pignat.project.zensand.components.Projection;

public class ProjectionNop implements Projection {

    @Override
    public C2 p(C2 in, Dimensions dim) {
        return in;
    }

}
