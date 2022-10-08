// Java Program to Illustrate Concept of
// Abstract Class

// Importing required classes
import java.io.*;

// Class 1
// Helper abstract class
abstract class Shape {

	// Declare fields
	String objectName = " ";

	// Constructor of this class
	Shape(String name) { this.objectName = name; }

	// Method
	// Non-abstract methods
	// Having as default implementation
	public void moveTo(int x, int y)
	{
		System.out.println(this.objectName + " "
						+ "has been moved to"
						+ " x = " + x + " and y = " + y);
	}

	// Method 2
	// Abstract methods which will be
	// implemented by its subclass(es)
	abstract public double area();
	abstract public void draw();
}

// Class 2
// Helper class extending Class 1
class Rectangle extends Shape {

	// Attributes of rectangle
	int length, width;

	// Constructor
	Rectangle(int length, int width, String name)
	{

		// Super keyword refers to current instance itself
		super(name);

		// this keyword refers to current instance itself
		this.length = length;
		this.width = width;
	}
    @Override public void moveTo(int x, int y) {
        System.out.println("hello world.!");
    }

	// Method 1
	// To draw rectangle
	@Override public void draw()
	{
		System.out.println("Rectangle has been drawn ");
	}

	// Method 2
	// To compute rectangle area
	@Override public double area()
	{
		// Length * Breadth
		return (double)(length * width);
	}
}

// Class 3
// Helper class extending Class 1
class Circle extends Shape {

	// Attributes of a Circle
	double pi = 3.14;
	int radius;

	// Constructor
	Circle(int radius, String name)
	{
		// Super keyword refers to parent class
		super(name);
		// This keyword refers to current instance itself
		this.radius = radius;
	}

	// Method 1
	// To draw circle
	@Override public void draw()
	{
		// Print statement
		System.out.println("Circle has been drawn ");
	}

	// Method 2
	// To compute circle area
	@Override public double area()
	{
		return (double)((pi * radius * radius));
	}
}

// Class 4
// Main class
