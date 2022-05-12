package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Drawer;

public class Drawer6 implements Drawer {
    private double count;
    private double ballSize;
    private boolean finished;

    @Override
    public String name() {
        return "Drawer6";
    }

    @Override
    public void init(double ballSize) {
        this.ballSize = ballSize;
        count = 0;
        finished = false;
    }

    @Override
    public C2 step() {
        if (!finished()) {
            count += 2 * Math.PI / 10000;
        }

        double a = 0.01;
        double b = ballSize / 10;
        double r = a + b * count;

        if (r > 1.1) {
            finished = true;
        }

        return new C2(r * Math.cos(count), r * Math.sin(count));
    }

    @Override
    public boolean finished() {
        return finished;
    }
}
