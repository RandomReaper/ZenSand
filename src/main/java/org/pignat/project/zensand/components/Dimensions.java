package org.pignat.project.zensand.components;

import java.io.Serializable;

public class Dimensions implements Serializable {

	private double width;
	private double height;
	private double size;
	private double ballSize;
	private double margin;
	
	public Dimensions(double width, double height, double size, double ballSize, double margin) {
		this.width = width;
		this.height = height;
		this.size = size;
		this.ballSize = ballSize;
		this.margin = margin;
	}
	
	private static double size(double width, double height) {
		double lowerBound = Math.min(width / 2, height / 2);
		double upperBound = Math.sqrt(width*width+height*height) / 4;
		return Math.max(upperBound, lowerBound);		
	}
	
	public Dimensions(double width, double height, double margin) {
		this(width, height, Dimensions.size(width, height), Math.min(width,height)/100, margin);
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
	public double ballSize() {
		return ballSize;
	}
	public double margin() {
		return margin;
	}
}
