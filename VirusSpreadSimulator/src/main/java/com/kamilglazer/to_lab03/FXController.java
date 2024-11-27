package com.kamilglazer.to_lab03;

import com.kamilglazer.to_lab03.Simulation.Simulation;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;


public class FXController {
    @FXML
    private Pane pane;

    @FXML
    private TextField timeText;

    @FXML
    private CheckBox resistanceCheckBox;

    @FXML
    private ChoiceBox<Integer> snapshotBox;

    private ExtendedClock clock;

    Simulation simulation;




    @FXML
    public void initialize(){
        clock = new ExtendedClock();
        snapshotBox.setOnAction(this::load);
    }

    @FXML
    public void start(){
        if(simulation == null){
            reset();
        }
        clock.start();
    }

    @FXML
    public void reset(){
        stop();
        pane.getChildren().clear();
        simulation = new Simulation(pane,800,resistanceCheckBox.isSelected());
        clock.resetTime();


        if(snapshotBox != null){
            snapshotBox.getItems().clear();
            snapshotBox.getItems().add(0);
            snapshotBox.setValue(0);
        }

        simulation.saveSimMemento(0);
    }

    @FXML
    public void stop(){
        clock.stop();
    }

    @FXML
    public void step(){
        simulation.step(pane);
    }

    @FXML
    public void load(Event event) {
        if (snapshotBox.getValue() == null) {
            System.err.println("Błąd: Brak wybranej wartości w snapshotBox.");
            return;
        }

        int snapshotValue = snapshotBox.getValue();

        pane.getChildren().clear();
        simulation.loaMemento(snapshotValue);
        clock.setTime(snapshotValue);
        clock.setTicks(snapshotValue * 25);

        for (int i = snapshotBox.getItems().size() - 1; i >= snapshotValue; i--) {
            snapshotBox.getItems().remove(i);
        }
    }


    private class ExtendedClock extends AnimationTimer {
        private long last = 0;
        private Integer ticks = 0;
        private double time = 0.0;

        public void setTime(double time){
            this.time = time;
        }

        public void setTicks(Integer ticks){
            this.ticks = ticks;
        }

        @Override
        public void handle(long now){
            long period = 1000000000 / 25;
            if(now - last > period){
                step();
                last = now;
                time  += 1.0 / 25;
                ticks ++;
                timeText.setText(String.format("%.2f", time));
                if(ticks % 25 == 0){
                    simulation.savePeopleMemento(ticks/25);
                    snapshotBox.getItems().add(ticks/25);
                }
            }
        }
        public void resetTime(){
            time = 0.0;
            timeText.setText(String.format("%.2f", time));
            snapshotBox.getItems().clear();
            ticks = 0;
        }
    }


}