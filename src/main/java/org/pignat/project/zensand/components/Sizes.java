package org.pignat.project.zensand.components;

public class Sizes {

	private double width;
	private double height;
	private double size;
	private double ball_size;
	private double margin;

	
	public Sizes(double _width, double _height, double _size, double _ball_size, double _margin) {
		width = _width;
		height = _height;
		size = _size;
		ball_size = _ball_size;
		margin = _margin;
	}
	
	private static double size(double width, double height, double margin) {
		double lower_bound = Math.min((width - 2 * margin) / 2, (height - 2 * margin) / 2);
		double upper_bound = Math.sqrt(Math.pow(width - 2 * margin, 2) + Math.pow(height - 2 * margin, 2)) / 4;
		return Math.min(upper_bound, lower_bound);		
	}
	
	public Sizes(double _width, double _height, double _margin) {
		//double lower_bound = Math.min((width - 2 * margin) / 2, (height - 2 * margin) / 2);
		//double upper_bound = Math.sqrt(Math.pow(width - 2 * margin, 2) + Math.pow(height - 2 * margin, 2)) / 4;
		//size = (int) Math.min(upper_bound, lower_bound);
		this(_width, _height, Sizes.size(_width, _height, _margin), Math.min(_width,_height)/100, _margin);
	}

	public double width() {
		return width;
	}
	public double height() {
		return height;
	}
	public double size() {
		return size;
	}
	public double ball_size() {
		return ball_size;
	}
	public double margin() {
		return margin;
	}
}
