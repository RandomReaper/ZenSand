package org.pignat.project.zensand;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.pignat.project.zensand.components.Arms;
import org.pignat.project.zensand.components.Controller;
import org.pignat.project.zensand.components.Drawers;
import org.pignat.project.zensand.components.Sizes;

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

	BufferedImage img;
	int drawerCounter = -1;
	Sizes dim = new Sizes(25, 25, 1);
	Controller controller = new Controller(Drawers.get(0, dim.ball_size()), dim, 1);
	int width;
	int height;
	boolean debug;

	public MyPanel(boolean debug) {
		this.debug = false;

		img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		width = getWidth();
		height = getHeight();

		ActionListener repaintPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MyPanel.this.repaint();
			}
		};

		new Timer(10, repaintPerformer).start();

		ActionListener stepPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MyPanel.this.step(100);
			}
		};

		new Timer(20, stepPerformer).start();

		ActionListener fadePerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MyPanel.this.fade();
			}
		};

		new Timer(20, fadePerformer).start();

	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void step(int steps) {
		Graphics2D gra = img.createGraphics();

		for (int i = 0; i < steps; i++) {

			double x = controller.arms().pos().x;
			double y = controller.arms().pos().y;
			gra.setColor(Color.WHITE);
			Ellipse2D.Double circle = new Ellipse2D.Double(getWidth() / 2 + x - dim.ball_size()/2, getHeight() / 2 + y - dim.ball_size()/2, dim.ball_size(), dim.ball_size());
			gra.fill(circle);

			if (controller.finished()) {
				controller.drawer(Drawers.get(drawerCounter++, dim.ball_size()/dim.size()));
			}
			controller.step();
		}

	}

	public void fade() {
		RescaleOp op = new RescaleOp(0.999f, 0.0f, null);
		op.filter(img, img);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (width != getWidth() || height != getHeight()) {
			width = getWidth();
			height = getHeight();
			drawerCounter = 0;
			dim = new Sizes(width, height, 5);
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			controller = new Controller(Drawers.get(drawerCounter++, dim.ball_size()/dim.size()), dim, .5);
		}

		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

		Arms arms = controller.arms();
		g.setColor(Color.YELLOW);
		int size = (int) controller.dim().size();
		if (debug) {
			g.drawOval(getWidth() / 2 - size, getHeight() / 2 - size, size * 2, size * 2);
		}

		if (debug) {
			g.setColor(Color.ORANGE);
			int x = (int) controller.arms().pos().x;
			int y = (int) controller.arms().pos().y;
			int x1 = (int) controller.arms().pos1().x;
			int y1 = (int) controller.arms().pos1().y;
			g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + x1, getHeight() / 2 + y1);
			g.drawLine(getWidth() / 2 + x1, getHeight() / 2 + y1, getWidth() / 2 + x, getHeight() / 2 + y);
		}

		if (true) {
			g.setColor(Color.RED);
			int x = (int) arms.pos().x;
			int y = (int) arms.pos().y;
			int x1 = (int) arms.pos1().x;
			int y1 = (int) arms.pos1().y;
			g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + x1, getHeight() / 2 + y1);
			g.drawLine(getWidth() / 2 + x1, getHeight() / 2 + y1, getWidth() / 2 + x, getHeight() / 2 + y);
		}
		if (debug) {
			g.setColor(Color.RED);
			g.drawOval(getWidth() / 2 - size * 2, getHeight() / 2 - size * 2, size * 4, size * 4);
			int margin = (int) controller.dim().margin();
			g.drawRect(margin, margin, getWidth() - 2 * margin, getHeight() - 2 * margin);
		}
	}
}