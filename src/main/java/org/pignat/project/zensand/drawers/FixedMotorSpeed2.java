package org.pignat.project.zensand.drawers;

public class FixedMotorSpeed2 extends FixedMotorSpeed {

    @Override
    public String name() {
        return "FixedMotorSpeed2";
    }

    @Override
    public void init(double ballSize) {
        super.init(ballSize);
        setSpeed(0.17, -0.12, 100 * Math.PI);
    }
}
