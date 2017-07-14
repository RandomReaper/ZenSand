package org.pignat.project.zensand;

import javax.swing.JFrame;

import org.jbox2d.testbed.framework.TestbedController.UpdateBehavior;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

public class App {
	public static void main(String[] args) {
		final int servoMaxAngle = 90;
		TestbedModel model = new TestbedModel();

		model.addCategory("Five bar parallel robot");
		model.addTest(new FiveBarRobot(servoMaxAngle));

		model.getSettings().addSetting(new TestbedSetting("A", SettingType.ENGINE, 0, -servoMaxAngle/2, servoMaxAngle/2));
		model.getSettings().addSetting(new TestbedSetting("B", SettingType.ENGINE, 0, -servoMaxAngle/2, servoMaxAngle/2));

		model.getSettings().addSetting(new TestbedSetting("enable", SettingType.ENGINE, false));

		TestbedPanel panel = new TestPanelJ2D(model);

		JFrame testbed = new TestbedFrame(model, panel, UpdateBehavior.UPDATE_CALLED);

		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
