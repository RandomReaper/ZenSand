package org.pignat.project.zensand.components.path;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Dimensions;
import org.pignat.project.zensand.components.Projection;


public class CompletePath implements IPath {

    private static final double UPSAMPLE_FACTOR = 10;

    private DownsamplePath downsample;

    public CompletePath(IPath sub, Projection projection, Dimensions dim, C2 startPosition, double resolution) {
        ResizePath resize = new ResizePath(sub, projection, dim);
        UpsamplePath upsample = new UpsamplePath(resize, startPosition, resolution * UPSAMPLE_FACTOR);
        downsample = new DownsamplePath(upsample, resolution);
    }

    @Override
    public boolean finished() {
        return downsample.finished();
    }

    @Override
    public C2 step() {
        return downsample.step();
    }
}