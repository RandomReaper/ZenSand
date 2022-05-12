package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Drawer;

public class Drawer5 implements Drawer {
    private double count = 0;

    @Override
    public String name() {
        return "Drawer5";
    }

    @Override
    public C2 step() {
        if (!finished()) {
            count += 2 * Math.PI / 1000;
        }

        return new C2(-0.33 * Math.sin(3.22 * count) + 0.5 * Math.sin(count), 0.33 * Math.cos(3.22 * count) + 0.5 * Math.cos(count));
    }

    @Override
    public void init(double ballSize) {
        count = 0;
    }

    @Override
    public boolean finished() {
        return count > 10 * 2 * Math.PI;
    }
}
