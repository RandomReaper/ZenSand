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
		TestbedModel model = new TestbedModel();

		// add tests
		//TestList.populateModel(model);
		model.addCategory("Five bar parallel robot");
		model.addTest(new FiveBarRobot());

		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
		model.getSettings().addSetting(new TestbedSetting("A", SettingType.ENGINE, -10, -10, 10));

		TestbedPanel panel = new TestPanelJ2D(model);

		JFrame testbed = new TestbedFrame(model, panel, UpdateBehavior.UPDATE_CALLED);

		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
