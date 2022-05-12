package org.pignat.project.zensand.components;

import java.io.Serializable;

public class Arms implements Serializable {
    private A2 speed = new A2(0, 0);

    private A2 motor = new A2(0, Math.PI);
    private double size;

    public Arms(double size) {
        this.size = size;
    }

    public void speed(A2 speed) {
        this.speed = speed;
    }

    public void pos(A2 pos) {
        motor = pos;
    }

    public void step() {
        motor = motor.move(speed);
    }

    public C2 pos() {
        return V2.xy(motor, size);
    }

    public C2 pos1() {
        return V2.xy(new A2(motor.a(), motor.a()), size / 2);
    }

    public A2 m() {
        return motor;
    }
}
