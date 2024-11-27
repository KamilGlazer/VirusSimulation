package com.kamilglazer.to_lab03.Simulation;

import com.kamilglazer.to_lab03.Model.Person;

import java.util.ArrayList;

public class SimMemento {
    public ArrayList<Person> people;

    public SimMemento(ArrayList<Person> people){
        this.people=new ArrayList<>();
        this.people.addAll(people);
    }
}
