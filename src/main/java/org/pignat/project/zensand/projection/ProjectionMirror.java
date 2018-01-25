package org.pignat.project.zensand.projection;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Projection;
import org.pignat.project.zensand.components.Dimensions;

public class ProjectionMirror implements Projection {

	@Override
	public C2 p(C2 in, Dimensions dim) {
		//)
		return new C2(in).resize(dim.size()).mirrorMargin(dim.width(), dim.height(), dim.margin()).desize(dim.size());
	}

}
