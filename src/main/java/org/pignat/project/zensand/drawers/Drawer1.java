package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Drawer;

public class Drawer1 implements Drawer {
    private double count = 0;

    @Override
    public String name() {
        return "Drawer1";
    }

    @Override
    public C2 step() {
        if (!finished()) {
            count += 2 * Math.PI / 1000;
        }
        return new C2(1 * Math.sin(count), 1 * Math.cos(count));
    }

    @Override
    public void init(double ballSize) {
        count = 0;
    }

    @Override
    public boolean finished() {
        return count > 2 * Math.PI;
    }
}
