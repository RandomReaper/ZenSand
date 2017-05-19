package org.pignat.project.zensand;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class FiveBarRobot extends TestbedTest {

	final float DENSITY = 1f;
	
	final short GROUP_FIXED = -1;
	final short GROUP_MOVING = -2;
	
	final float LENGTH = 10f;
	final float WIDTH = 1f;
	final float MARGIN = 2f;

	private void setFilter(Body b, short category, short mask) {
		Fixture f = b.getFixtureList();

		while (f != null) {
			Filter fil = f.getFilterData();
			fil.categoryBits = category;
			fil.maskBits = mask;
			f = f.getNext();
		}	
	}

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Couple of Things Test");

		getWorld().setGravity(new Vec2((float) 0.0, (float) -9.81));
		PolygonShape shape = new PolygonShape();

		shape.setAsBox(WIDTH, WIDTH);
		Vec2 o_arm1 = new Vec2(0, 1*LENGTH + MARGIN);
		Vec2 o_arm2 = new Vec2(0, 2*LENGTH + MARGIN);

		BodyDef o1def = new BodyDef();
		o1def.type = BodyType.STATIC;
		o1def.position.set(-(LENGTH+MARGIN), 0);
		o1def.allowSleep = false;
		Body o1 = getWorld().createBody(o1def);
		o1.createFixture(shape, DENSITY);

		BodyDef o2def = new BodyDef();
		o2def.type = BodyType.STATIC;
		o2def.position.set(+(LENGTH+MARGIN), 0);
		o2def.allowSleep = false;
		Body o2 = getWorld().createBody(o2def);
		o2.createFixture(shape, DENSITY);

		shape.setAsBox(WIDTH, LENGTH);

		BodyDef arm1_1def = new BodyDef();
		arm1_1def.type = BodyType.DYNAMIC;
		arm1_1def.position.set(o1.getWorldCenter().add(o_arm1));
		arm1_1def.allowSleep = false;
		Body arm1_1 = getWorld().createBody(arm1_1def);
		arm1_1.createFixture(shape, DENSITY);

		BodyDef arm2_2_def = new BodyDef();
		arm2_2_def.type = BodyType.DYNAMIC;
		arm2_2_def.position.set(o2.getWorldCenter().add(o_arm1));
		arm2_2_def.allowSleep = false;
		Body arm2_1 = getWorld().createBody(arm2_2_def);
		arm2_1.createFixture(shape, DENSITY);

		shape.setAsBox(1, 10);

		BodyDef arm1_2def = new BodyDef();
		arm1_2def.type = BodyType.DYNAMIC;
		arm1_2def.position.set(arm1_1.getWorldCenter().add(o_arm2));
		arm1_2def.allowSleep = false;
		Body arm1_2 = getWorld().createBody(arm1_2def);
		arm1_2.createFixture(shape, DENSITY);

		BodyDef arm2_2def = new BodyDef();
		arm2_2def.type = BodyType.DYNAMIC;
		arm2_2def.position.set(arm2_1.getWorldCenter().add(o_arm2));
		arm2_2def.allowSleep = false;
		Body arm2_2 = getWorld().createBody(arm2_2def);
		arm2_2.createFixture(shape, DENSITY);


		RevoluteJointDef motor1 = new RevoluteJointDef();
		motor1.initialize(o1, arm1_1, o1.getWorldCenter());

		motor1.motorSpeed = 0;
		motor1.maxMotorTorque = 0;
		motor1.enableMotor = false;

		getWorld().createJoint(motor1);
		
		RevoluteJointDef motor2 = new RevoluteJointDef();
		motor2.initialize(o2, arm2_1, o2.getWorldCenter());

		motor2.motorSpeed = 0;
		motor2.maxMotorTorque = 0;
		motor2.enableMotor = false;

		getWorld().createJoint(motor2);
		

		RevoluteJointDef axe1 = new RevoluteJointDef();
		axe1.bodyA = arm1_1;
		axe1.bodyB = arm1_2;
		axe1.collideConnected = false;
		axe1.localAnchorA.set(new Vec2(0, +LENGTH));
		axe1.localAnchorB.set(new Vec2(0, -LENGTH));

		getWorld().createJoint(axe1);

		RevoluteJointDef axe2 = new RevoluteJointDef();
		axe2.bodyA = arm2_1;
		axe2.bodyB = arm2_2;
		axe2.collideConnected = false;
		axe2.localAnchorA.set(new Vec2(0, +LENGTH));
		axe2.localAnchorB.set(new Vec2(0, -LENGTH));

		getWorld().createJoint(axe2);
		
		RevoluteJointDef axec = new RevoluteJointDef();
		axec.bodyA = arm1_2;
		axec.bodyB = arm2_2;
		axec.collideConnected = false;
		axec.localAnchorA.set(new Vec2(0, +LENGTH));
		axec.localAnchorB.set(new Vec2(0, +LENGTH));

		getWorld().createJoint(axec);

		setFilter(o1,		(short)0x0001, (short)0xffff);
		setFilter(o2,		(short)0x0001, (short)0xffff);
		setFilter(arm1_1,	(short)0x0002, (short)0x0003);
		setFilter(arm2_1,	(short)0x0002, (short)0x0003);
		setFilter(arm1_2,	(short)0x0002, (short)0x0000);
		setFilter(arm2_2,	(short)0x0002, (short)0x0000);		
	}

	@Override
	public void step(TestbedSettings settings) {
		super.step(settings); // make sure we update the engine!
		TestbedSetting a = settings.getSetting("A");
		getWorld().setGravity(new Vec2(0, a.value));
		// System.out.println("gravity" + a.value);

	}

	@Override
	public String getTestName() {
		return "Five bar parallel robot";
	}
}