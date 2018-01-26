package org.pignat.project.zensand.components.Path;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Dimensions;
import org.pignat.project.zensand.components.Projection;


public class ResizePath implements IPath {

	private IPath sub;
	private Projection projection;
	private Dimensions dim;
	
	public ResizePath(IPath sub, Projection projection, Dimensions dim)
	{
		this.sub = sub;
		this.projection = projection;
		this.dim = dim;
	}
	
	@Override
	public boolean finished() {
		return sub.finished();
	}

	@Override
	public C2 step() {
		C2 step = projection.p(sub.step(), dim);
		C2 sized = new C2(step).resize(dim.size());
		C2 croped = new C2(step).resize(dim.size(), dim.width(), dim.height(), dim.margin());
		
		if (sized.same(croped))
		{
			return sized;
		}

		return croped;
	}

}