package com.kamilglazer.to_lab03.Vector2d;

import java.util.ArrayList;

public class Vector2D implements IVector2D{

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double abs() {
        return Math.sqrt(x*x+y*y);
    }

    @Override
    public double cdot(IVector2D iVector2D) {
        return x*iVector2D.getComponents().get(0)+y*iVector2D.getComponents().get(1);
    }

    @Override
    public ArrayList<Double> getComponents() {
        ArrayList<Double> components = new ArrayList<>();
        components.add(x);
        components.add(y);
        return components;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
