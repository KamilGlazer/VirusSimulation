package com.kamilglazer.to_lab03.Model;

import com.kamilglazer.to_lab03.State.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Person {
    public static double radius = 6.0;
    private Location location;
    private Direction direction;
    private IState state;
    private Pane pane;
    private Circle circle;
    private final CareTaker careTaker;
    private Map<Person,Double> timeNearSick = new HashMap<>();
    public double healTime;
    private double sickTime = 0.0;



    public Person(IState state,Pane pane,boolean live){
        this.state = state;
        this.direction = new Direction();
        this.circle = new Circle(radius,state.getColor());
        this.pane = pane;
        this.careTaker = new CareTaker(this);
        this.healTime = Math.random() * (10) + 20;
        circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);

        if(live){
            this.location = new Location(pane,radius);
        }else{
            double[][] edges = {
                    {pane.getWidth(), pane.getHeight() * Math.random()},
                    {pane.getWidth() * Math.random(), pane.getHeight()},
                    {0, pane.getPrefHeight() * Math.random()},
                    {pane.getWidth() * Math.random(), 0}
            };

          
            int randomIndex = new Random().nextInt(edges.length);
            this.location = new Location(edges[randomIndex][0], edges[randomIndex][1]);
        }
    }

    public void checkSurrounding(Person other) {
        boolean encountered = location.encounter(other.getLocation());
        boolean thisIsHealthy = this.state instanceof StableState;

        if (encountered) {
            if(timeNearSick.containsKey(other)){
                if (thisIsHealthy && other.getState() instanceof InfectedState) {
                    timeNearSick.putIfAbsent(other, 0.0);
                    double timeNear = timeNearSick.get(other);

                    if (timeNear >= 3.0) {
                        if (Math.random() <= 0.5) {
                            this.setState(new InfectedState());
                        }else{
                            this.setState(new InfectedNoSymptomsState());
                        }
                    } else {
                        timeNearSick.put(other, timeNear + 0.04);
                    }
                }else if(thisIsHealthy && other.getState() instanceof InfectedNoSymptomsState){
                    timeNearSick.putIfAbsent(other, 0.0);
                    double timeNear = timeNearSick.get(other);

                    if (timeNear >= 3.0) {
                        if(Math.random() <= 0.5){
                            if (Math.random() <= 0.5) {
                                this.setState(new InfectedState());
                            }else{
                                this.setState(new InfectedNoSymptomsState());
                            }
                        }
                    } else {
                        timeNearSick.put(other, timeNear + 0.04);
                    }
                }
            }else{
                timeNearSick.put(other,0.0);
            }
        }else{
            timeNearSick.remove(other);
        }
    }

    public void changeDirection2(){
        this.direction = direction.changeDirection(Math.PI/8);
    }

    public void recoverHealth(){
        if(state instanceof InfectedNoSymptomsState || state instanceof InfectedState){
            sickTime+=0.04;
            if(sickTime >= healTime){
                setState(new ImmuneState());
            }
        }
    }

    public void changeDirection(){
        direction = new Direction();
    }

    public boolean move(){
        return location.move(direction,pane,radius);
    }

    public void draw(){
        circle.setRadius(radius);
        circle.setTranslateX(location.getX());
        circle.setTranslateY(location.getY());
        circle.setFill(state.getColor());
        circle.setStroke(Color.BLACK);
    }

    public void undraw(){
        circle.setRadius(0);
        circle.setStroke(Color.TRANSPARENT);
    }

    public void saveMemento(Integer sec){
        careTaker.save(sec);
    }

    public boolean load(Integer sec){
        boolean momento = careTaker.load(sec);
        draw();
        return momento;
    }

    public static void setRadius(double radius) {
        Person.radius = radius;
    }



    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setCircle(Color color, Double radius){
        circle.setFill(color);
        circle.setRadius(radius);
    }

    public void setTimeNearSick(Map<Person, Double> timeNearSick) {
        this.timeNearSick = timeNearSick;
    }

    public void setHealTime(double healTime) {
        this.healTime = healTime;
    }

    public void setSickTime(double sickTime) {
        this.sickTime = sickTime;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public static double getRadius() {
        return radius;
    }

    public Location getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }

    public IState getState() {
        return state;
    }

    public Pane getPane() {
        return pane;
    }

    public Circle getCircle() {
        return circle;
    }

    public CareTaker getCareTaker() {
        return careTaker;
    }

    public Map<Person, Double> getTimeNearSick() {
        return timeNearSick;
    }

    public double getHealTime() {
        return healTime;
    }


    public static class Memento {
        private double healTime;
        private IState state;
        private Direction direction;
        private Location location;
        private Pane pane;
        private Circle circle;
        private double sickTime = 0.0;
        private Map<Person, Double> timeNearSick;

        public double getHealTime() {
            return healTime;
        }

        public IState getState() {
            return state;
        }

        public Direction getDirection() {
            return direction;
        }

        public Location getLocation() {
            return location;
        }

        public Pane getPane() {
            return pane;
        }

        public Circle getCircle() {
            return circle;
        }

        public double getSickTime() {
            return sickTime;
        }

        public Map<Person, Double> getTimeNearSick() {
            return timeNearSick;
        }

        public Memento(double healTime, IState state, Direction direction, Location location, Pane pane, Circle circle, double sickTime) {
            this.healTime = healTime;
            this.direction = new Direction(direction.getX(),direction.getY(),direction.getSpeed());
            this.location = new Location(location.getX(),location.getY());
            this.pane = pane;
            this.circle = new Circle(Person.radius,state.getColor());
            this.sickTime = sickTime;
            this.timeNearSick = new HashMap<>();
            this.circle.setStroke(Color.BLACK);
            for(Person person : timeNearSick.keySet()){
                this.timeNearSick.put(person,timeNearSick.get(person));
            }

            if(state instanceof StableState){
                this.state = new StableState();
            }else if(state instanceof ImmuneState){
                this.state = new ImmuneState();
            }else if(state instanceof InfectedNoSymptomsState){
                this.state = new InfectedNoSymptomsState();
            }else if(state instanceof InfectedState){
                this.state = new InfectedState();
            }



        }

    }
    public Memento takeSnapshot(){
        return new Memento(healTime,state,direction,location,pane,circle,sickTime);
    }
}
