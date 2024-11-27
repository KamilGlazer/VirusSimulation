package com.kamilglazer.to_lab03.Simulation;

import com.kamilglazer.to_lab03.Model.Person;
import com.kamilglazer.to_lab03.State.StableState;
import com.kamilglazer.to_lab03.State.ImmuneState;
import com.kamilglazer.to_lab03.State.InfectedNoSymptomsState;
import com.kamilglazer.to_lab03.State.InfectedState;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;


public class Simulation {

    private ArrayList<Person> people;
    private final Pane pane;
    SimCareTaker simCareTaker;

    public Simulation(Pane pane,int population,boolean immune){
        this.pane = pane;

        people = new ArrayList<>();
        if(immune){
            for(int person=0;person < population; person++){
                if(Math.random() < 0.15){
                    people.add(new Person(new ImmuneState(),pane,true));
                }else{
                    people.add(new Person(new StableState(),pane,true));
                }
            }
        }else{
            for(int person=0;person < population;person++){
                people.add(new Person(new StableState(),pane,true));
            }
        }

        draw();
        simCareTaker = new SimCareTaker(this);
    }

    public void move(){
        for(Person person : people){
            if(!person.move()){
                person.undraw();
            }
        }
    }

    public void checkIfRecovered(){
        for(Person person : people){
            if(person.getCircle().getStroke().equals(Color.BLACK)){
                person.recoverHealth();
            }
        }
    }


    public void changeDirection(){
        for(Person person : people){
            if(person.getCircle().getStroke().equals(Color.BLACK)){
                person.changeDirection2();
            }
        }
    }


    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }


    public void addPerson(Pane pane){
        if(Math.random() <= 0.1){
            if(Math.random() <= 0.5){
                people.add(new Person(new InfectedState(),pane,false));
            }else{
                people.add(new Person(new InfectedNoSymptomsState(),pane,false));
            }
        }else{
            people.add(new Person(new StableState(),pane,false));
        }
    }

    public void draw(){
        for(Person person : people){
            if(person.getCircle().getStroke().equals(Color.BLACK)){
                person.draw();
            }
        }
    }

    public SimMemento takeSnapshot(){
        return new SimMemento(this.people);
    }

    public void checkEncounter(){
        for(Person person : people){
            if(person.getCircle().getStroke().equals(Color.BLACK)){
                for(Person other : people){
                    if(person != other){
                        if(other.getCircle().getStroke().equals(Color.BLACK)){
                            person.checkSurrounding(other);
                        }
                    }
                }
            }
        }
    }

    public void step(Pane pane){
        move();
        checkEncounter();
        draw();
        changeDirection();
        addPerson(pane);
        checkIfRecovered();
    }

    public void saveSimMemento(Integer sec){
        simCareTaker.save(sec);
    }

    public void savePeopleMemento(Integer sec){
        saveSimMemento(sec);
        for(Person person : people){
            if(person.getCircle().getStroke().equals(Color.BLACK)){
                person.saveMemento(sec);
            }
        }
    }

    public void loaMemento(Integer sec){
        simCareTaker.load(sec);
        for(Person person : people){
            if(person.getCircle().getStroke().equals(Color.BLACK)){
                person.load(sec);
                pane.getChildren().add(person.getCircle());
            }
        }
    }

}
