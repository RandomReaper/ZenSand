package org.pignat.project.zensand;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.pignat.project.zensand.components.Arms;
import org.pignat.project.zensand.components.Controller;
import org.pignat.project.zensand.components.Drawers;
import org.pignat.project.zensand.components.Dimensions;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class App {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		JFrame f = new JFrame("ZenSand");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new MyPanel(true));
		f.setSize(250, 250);
		f.setVisible(true);
	}
}

class MyPanel extends JPanel {

	transient BufferedImage img;
	int drawerCounter = -1;
	Dimensions dim = new Dimensions(25, 25, 1);
	Arms arms = new Arms(dim.size());
	Controller controller = new Controller(Drawers.get(0, dim.ballSize()), arms, dim, 1);
	int oldWidth;
	int oldHeigth;
	boolean debug;

	public MyPanel(boolean debug) {
		img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		oldWidth = getWidth();
		oldHeigth = getHeight();

		ActionListener repaintPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				MyPanel.this.repaint();
			}
		};

		new Timer(10, repaintPerformer).start();

		ActionListener stepPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				MyPanel.this.step(10);
			}
		};

		new Timer(20, stepPerformer).start();

		ActionListener fadePerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				MyPanel.this.fade();
			}
		};

		new Timer(20, fadePerformer).start();

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void step(int steps) {
		Graphics2D gra = img.createGraphics();

		for (int i = 0; i < steps; i++) {

			double x = controller.arms().pos().x();
			double y = controller.arms().pos().y();
			gra.setColor(Color.WHITE);
			Ellipse2D.Double circle = new Ellipse2D.Double(getWidth() / 2.0 + x - dim.ballSize()/2, getHeight() / 2.0 + y - dim.ballSize()/2, dim.ballSize(), dim.ballSize());
			gra.fill(circle);

			if (controller.finished()) {
				controller.drawer(Drawers.get(drawerCounter++, dim.ballSize()/dim.size()));
			}
			controller.step();
		}

	}

	public void fade() {
		RescaleOp op = new RescaleOp(new float[]{0.999f,0.999f,0.999f}, new float[]{0,0,0}, null);
		op.filter(img, img);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (oldWidth != getWidth() || oldHeigth != getHeight()) {
			oldWidth = getWidth();
			oldHeigth = getHeight();
			drawerCounter = 0;
			dim = new Dimensions(oldWidth, oldHeigth, 5);
			arms = new Arms(dim.size());
			img = new BufferedImage(oldWidth, oldHeigth, BufferedImage.TYPE_INT_RGB);
			controller = new Controller(Drawers.get(drawerCounter++, dim.ballSize()/dim.size()), arms, dim, .5);
		}

		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

		g.setColor(Color.YELLOW);
		int size = (int) controller.dim().size();
		if (debug) {
			g.drawOval(getWidth() / 2 - size, getHeight() / 2 - size, size * 2, size * 2);
		}

		if (debug) {
			g.setColor(Color.ORANGE);
			int x = (int) controller.arms().pos().x();
			int y = (int) controller.arms().pos().y();
			int x1 = (int) controller.arms().pos1().x();
			int y1 = (int) controller.arms().pos1().y();
			g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + x1, getHeight() / 2 + y1);
			g.drawLine(getWidth() / 2 + x1, getHeight() / 2 + y1, getWidth() / 2 + x, getHeight() / 2 + y);
		}

		// Draw arms
		g.setColor(Color.RED);
		int x = (int) arms.pos().x();
		int y = (int) arms.pos().y();
		int x1 = (int) arms.pos1().x();
		int y1 = (int) arms.pos1().y();
		g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + x1, getHeight() / 2 + y1);
		g.drawLine(getWidth() / 2 + x1, getHeight() / 2 + y1, getWidth() / 2 + x, getHeight() / 2 + y);

		if (debug) {
			g.setColor(Color.RED);
			g.drawOval(getWidth() / 2 - size * 2, getHeight() / 2 - size * 2, size * 4, size * 4);
			int margin = (int) controller.dim().margin();
			g.drawRect(margin, margin, getWidth() - 2 * margin, getHeight() - 2 * margin);
		}
	}
}