package org.pignat.project.zensand.projection;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Projection;
import org.pignat.project.zensand.components.Sizes;

public class ProjectionNop implements Projection {

	@Override
	public C2 p(C2 in, Sizes dim) {
		return in;
	}

}
