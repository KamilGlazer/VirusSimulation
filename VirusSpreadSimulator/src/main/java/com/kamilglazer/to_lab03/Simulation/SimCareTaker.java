package com.kamilglazer.to_lab03.Simulation;

import java.util.HashMap;
import java.util.Map;

public class SimCareTaker {
    private final Simulation simulation;
    private final Map<Integer,SimMemento> simMementoMap;

    public SimCareTaker(Simulation simulation) {
        this.simulation = simulation;
        this.simMementoMap = new HashMap<>();
    }

    public void save(Integer sec){
        System.out.println(simMementoMap.size());
        simMementoMap.put(sec,simulation.takeSnapshot());
    }


    public boolean load(Integer sec){
        if(simMementoMap.containsKey(sec)){
            SimMemento snapshot = simMementoMap.get(sec);
            simulation.setPeople(snapshot.people);
            return true;
        }else{
            return false;
        }
    }
}
