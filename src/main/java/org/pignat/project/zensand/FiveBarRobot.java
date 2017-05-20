package org.pignat.project.zensand;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class FiveBarRobot extends TestbedTest {

	static final float DENSITY = 1f;

	static final short GROUP_FIXED = -1;
	static final short GROUP_MOVING = -2;

	static final float LENGTH = 10f;
	static final float WIDTH = 1f;
	static final float MARGIN = 2f;

	private void setFilter(Body b, short category, short mask) {
		Fixture f = b.getFixtureList();

		while (f != null) {
			Filter fil = f.getFilterData();
			fil.categoryBits = category;
			fil.maskBits = mask;
			f = f.getNext();
		}
	}

	public class Servo {
		RevoluteJoint motor;
		Body carter;
		Body arm;

		public Servo(World world, int i, int j) {
			Vec2 armPosition = new Vec2(0, 1 * LENGTH + MARGIN);
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(WIDTH, WIDTH);

			BodyDef o1def = new BodyDef();
			o1def.type = BodyType.STATIC;
			o1def.position.set(i * (LENGTH + MARGIN), j);
			o1def.allowSleep = false;
			carter = world.createBody(o1def);
			carter.createFixture(shape, DENSITY);

			shape.setAsBox(WIDTH, LENGTH);

			BodyDef arm_def = new BodyDef();
			arm_def.type = BodyType.DYNAMIC;
			arm_def.position.set(carter.getWorldCenter().add(armPosition));
			arm_def.allowSleep = false;
			arm = world.createBody(arm_def);
			arm.createFixture(shape, DENSITY);

			RevoluteJointDef motordef = new RevoluteJointDef();
			motordef.initialize(carter, arm, carter.getWorldCenter());

			motordef.maxMotorTorque = 10000;
			motordef.enableMotor = false;
			motordef.lowerAngle = (float) Math.toRadians(-100);
			motordef.upperAngle = (float) Math.toRadians(+100);
			motordef.enableLimit = true;

			motor = (RevoluteJoint) getWorld().createJoint(motordef);
		}

		public float angleDeg() {
			return (float) Math.toDegrees(arm.getAngle());
		}

		public void set(int target) {
			motor.enableMotor(true);
			motor.setMotorSpeed(-(target - angleDeg()));
			motor.setMaxMotorTorque(50000);
		}

		public void off() {
			motor.enableMotor(false);
		}
	}

	RevoluteJointDef axec;
	Servo servo1;
	Servo servo2;

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("ZenSand");

		getWorld().setGravity(new Vec2());
		PolygonShape shape = new PolygonShape();

		shape.setAsBox(WIDTH, WIDTH);

		Vec2 o_arm2 = new Vec2(0, 2 * LENGTH + MARGIN);

		servo1 = new Servo(getWorld(), -1, 0);
		servo2 = new Servo(getWorld(), +1, 0);

		shape.setAsBox(1, LENGTH);

		BodyDef arm1_2def = new BodyDef();
		arm1_2def.type = BodyType.DYNAMIC;
		arm1_2def.position.set(servo1.arm.getWorldCenter().add(o_arm2));
		arm1_2def.allowSleep = false;
		Body arm1_2 = getWorld().createBody(arm1_2def);
		arm1_2.createFixture(shape, DENSITY);

		BodyDef arm2_2def = new BodyDef();
		arm2_2def.type = BodyType.DYNAMIC;
		arm2_2def.position.set(servo2.arm.getWorldCenter().add(o_arm2));
		arm2_2def.allowSleep = false;
		Body arm2_2 = getWorld().createBody(arm2_2def);
		arm2_2.createFixture(shape, DENSITY);

		RevoluteJointDef axe1 = new RevoluteJointDef();
		axe1.bodyA = servo1.arm;
		axe1.bodyB = arm1_2;
		axe1.collideConnected = false;
		axe1.localAnchorA.set(new Vec2(0, +LENGTH));
		axe1.localAnchorB.set(new Vec2(0, -LENGTH));

		getWorld().createJoint(axe1);

		RevoluteJointDef axe2 = new RevoluteJointDef();
		axe2.bodyA = servo2.arm;
		axe2.bodyB = arm2_2;
		axe2.collideConnected = false;
		axe2.localAnchorA.set(new Vec2(0, +LENGTH));
		axe2.localAnchorB.set(new Vec2(0, -LENGTH));

		getWorld().createJoint(axe2);

		axec = new RevoluteJointDef();
		axec.bodyA = arm1_2;
		axec.bodyB = arm2_2;
		axec.collideConnected = false;
		axec.localAnchorA.set(new Vec2(0, +LENGTH));
		axec.localAnchorB.set(new Vec2(0, +LENGTH));

		getWorld().createJoint(axec);

		setFilter(servo1.carter, (short) 0x0001, (short) 0xffff);
		setFilter(servo2.carter, (short) 0x0001, (short) 0xffff);
		setFilter(servo1.arm, (short) 0x0002, (short) 0x0003);
		setFilter(servo2.arm, (short) 0x0002, (short) 0x0003);
		setFilter(arm1_2, (short) 0x0002, (short) 0x0000);
		setFilter(arm2_2, (short) 0x0002, (short) 0x0000);
	}

	private static Vec2 pos(Body body) {

		float x = (float) (body.getWorldCenter().x - LENGTH * Math.sin(body.getAngle()));
		float y = (float) (body.getWorldCenter().y + LENGTH * Math.cos(body.getAngle()));
		return new Vec2(x, y);
	}

	@Override
	public void step(TestbedSettings settings) {
		super.step(settings);
		int a = settings.getSetting("A").value;
		int b = settings.getSetting("B").value;
		boolean enable = settings.getSetting("enable").enabled;

		if (enable) {
			servo1.set(a);
			servo2.set(b);
		} else {
			servo1.off();
			servo2.off();
		}
		getDebugDraw().drawCircle(pos(axec.bodyA), 1.0f, Color3f.WHITE);
		addTextLine(String.format("servo1: %f°", servo1.angleDeg()));
		addTextLine(String.format("servo2: %f°", servo2.angleDeg()));
	}

	@Override
	public String getTestName() {
		return "Five bar parallel robot";
	}

	@Override
	public void keyPressed(char keyCar, int keyCode) {
		// TODO Auto-generated method stub
		super.keyPressed(keyCar, keyCode);
	}
}