package org.pignat.project.zensand.drawers;

import org.pignat.project.zensand.components.A2;
import org.pignat.project.zensand.components.C2;
import org.pignat.project.zensand.components.Drawer;
import org.pignat.project.zensand.components.V2;

public class Drawer2 implements Drawer {
    private double count = 0;

    @Override
    public String name() {
        return "Drawer2";
    }

    @Override
    public void init(double ballSize) {
        count = 0;
    }

    @Override
    public C2 step() {
        if (!finished()) {
            count += 2 * Math.PI / 10000;
        }

        double a = count * 13;
        double b = -count * 17;

        return V2.xy(new A2(a, b), .5);
    }

    @Override
    public boolean finished() {
        return count > 4 * Math.PI;
    }
}
