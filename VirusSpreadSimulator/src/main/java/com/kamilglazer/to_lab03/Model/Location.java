package com.kamilglazer.to_lab03.Model;

import com.kamilglazer.to_lab03.Vector2d.Vector2D;
import javafx.scene.layout.Pane;

public class Location {
    private Vector2D location;

    public Location(double x, double y){
        location = new Vector2D(x,y);
    }

    public Location(Pane pane, double radius){
        this(radius + Math.random() * (pane.getWidth() - 2*radius), radius + Math.random() * (pane.getHeight() - 2*radius));
    }

    public boolean move(Direction direction, Pane pane,double radius){

        location = new Vector2D(location.getX()+(direction.getX()*direction.getSpeed()),location.getY()+(direction.getY()*direction.getSpeed()));
        if(location.getX() < 0 || location.getX()+radius > pane.getWidth()){
            if(Math.random() <= 0.5){
                direction.bounceX();
                location = new Vector2D(location.getX()+direction.getX(),location.getY());
            }else{
                return false;
            }
        }

        if(location.getY() < 0 || location.getY() + radius > pane.getHeight()){
            if(Math.random() <= 0.5){
                direction.bounceY();
                location = new Vector2D(location.getX(),location.getY()+direction.getY());
            }else{
                return false;
            }
        }

        return true;
    }


    public double distanceBetween(Location other){
        double x = location.getX();
        double y = location.getY();
        double distanceX = x - other.location.getX();
        double distanceY = y - other.location.getY();
        return Math.sqrt(distanceX*distanceX+distanceY*distanceY);
    }

    public boolean encounter(Location other){
        return distanceBetween(other) <= 2*Person.radius + 2;
    }

    public double getX(){
        return location.getX();
    }

    public double getY(){
        return location.getY();
    }

}
