package org.pignat.project.zensand.components.path;

import org.pignat.project.zensand.components.C2;

public class DownsamplePath implements IPath {

	private IPath sub;
	private double resolution;

	private C2 lastOutputStep = null;

	public DownsamplePath(IPath sub, double resolution) {
		this.sub = sub;
		this.resolution = resolution;
	}
	
	@Override
	public boolean finished() {
		return sub.finished();
	}

	@Override
	public C2 step() {
		C2 step;

		if (lastOutputStep == null) {
			step = sub.step();
			lastOutputStep = step;
			return step;
		}
		double distance = 0;
		do {
			step = sub.step();
			distance = new C2(step.x() - lastOutputStep.x(), step.y() - lastOutputStep.y()).distance();

			if (sub.finished()) {
				lastOutputStep = step;
				return step;
			}
		} while (distance < resolution);

		lastOutputStep = step;

		return step;
	}
}
