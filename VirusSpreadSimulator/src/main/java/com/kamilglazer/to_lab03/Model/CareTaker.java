package com.kamilglazer.to_lab03.Model;

import java.util.HashMap;
import java.util.Map;

public class CareTaker {
    private Person person;
    private Map<Integer, Person.Memento> mementoMap;

    public CareTaker(Person person){
        this.person = person;
        mementoMap = new HashMap<>();
    }

    public void save(Integer sec){
        mementoMap.put(sec,person.takeSnapshot());
    }

    public boolean load(Integer sec){
        if(mementoMap.containsKey(sec)){
            Person.Memento snapshot = mementoMap.get(sec);
            person.setDirection(snapshot.getDirection());
            person.setCircle(snapshot.getCircle());
            person.setHealTime(snapshot.getHealTime());
            person.setPane(snapshot.getPane());
            person.setLocation(snapshot.getLocation());
            person.setSickTime(snapshot.getSickTime());
            person.setState(snapshot.getState());
            person.setTimeNearSick(snapshot.getTimeNearSick());


            return true;
        }else{
            return false;
        }
    }
}
