package com.kamilglazer.to_lab03.Model;

import com.kamilglazer.to_lab03.Vector2d.Vector2D;

public class Direction {
    private double speed;
    private Vector2D direction;

    public Direction(double speed, double x, double y) {
        this.speed = speed;
        this.direction = new Vector2D(x,y);
    }

    public Direction() {
        double randomDirection = Math.random() * Math.PI * 2;
        direction = new Vector2D(Math.cos(randomDirection),Math.sin(randomDirection));
        speed = Math.random() * 0.5;
    }


    public Direction changeDirection(double maxAngleChange){
        double angleChange = (Math.random() - 0.5) * 2 * maxAngleChange;
        double currentAngle = Math.atan2(direction.getY(), direction.getX());
        double newAngle = currentAngle+angleChange;
        direction = new Vector2D(Math.cos(newAngle),Math.sin(newAngle));
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public double getX(){
        return direction.getX();
    }

    public double getY(){
        return direction.getY();
    }

    public void bounceX(){
        direction = new Vector2D(-direction.getX(), direction.getY());
    }

    public void bounceY(){
        direction = new Vector2D(direction.getX(), -direction.getY());
    }

}
