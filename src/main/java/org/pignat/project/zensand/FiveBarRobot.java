package org.pignat.project.zensand;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import org.jbox2d.dynamics.joints.RevoluteJointDef;


public class FiveBarRobot extends TestbedTest {

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Couple of Things Test");

		getWorld().setGravity(new Vec2((float) 0.0, (float) -9.81));

		PolygonShape p1 = new PolygonShape();
		p1.setAsBox(1, 1);

		BodyDef def1 = new BodyDef();
		def1.type = BodyType.STATIC;
		def1.position.set(0, 0);
		def1.allowSleep = false;
		Body o1 = getWorld().createBody(def1);
		o1.createFixture(p1, 5.0f);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(10, 1);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(9, 0);
		bodyDef.allowSleep = false;
		Body arm1 = getWorld().createBody(bodyDef);
		arm1.createFixture(polygonShape, 5.0f);

		RevoluteJointDef motor1 = new RevoluteJointDef();
		motor1.initialize(o1, arm1, o1.getWorldCenter());

		motor1.motorSpeed = 0;
		motor1.maxMotorTorque = 0;
		motor1.enableMotor = false;
		
		getWorld().createJoint(motor1);
		
		BodyDef arm2def = new BodyDef();
		arm2def.type = BodyType.DYNAMIC;
		arm2def.position.set(0,0);
		arm2def.allowSleep = false;
		Body arm2 = getWorld().createBody(arm2def);
		polygonShape.setAsBox(8, 1);
		arm2.createFixture(polygonShape, 5.0f);
		
		RevoluteJointDef axe1 = new RevoluteJointDef();
		axe1.bodyA = arm1;
		axe1.bodyB = arm2;
		axe1.collideConnected = false;
		axe1.localAnchorA.set(new Vec2(9,0));
		axe1.localAnchorB.set(new Vec2(7,0));

		getWorld().createJoint(axe1);
		
		
		BodyDef arm3def = new BodyDef();
		arm3def.type = BodyType.DYNAMIC;
		arm3def.position.set(0,0);
		arm3def.allowSleep = false;
		Body arm3 = getWorld().createBody(arm3def);
		polygonShape.setAsBox(8, 1);
		arm3.createFixture(polygonShape, 5.0f);
		
		RevoluteJointDef axec = new RevoluteJointDef();
		axec.bodyA = arm2;
		axec.bodyB = arm3;
		axec.collideConnected = false;
		axec.localAnchorA.set(new Vec2(-7,0));
		axec.localAnchorB.set(new Vec2(7,0));

		getWorld().createJoint(axec);
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