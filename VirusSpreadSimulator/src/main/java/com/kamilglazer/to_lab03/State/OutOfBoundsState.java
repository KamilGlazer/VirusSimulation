package com.kamilglazer.to_lab03.State;

import javafx.scene.paint.Color;

public class OutOfBoundsState implements IState{
    @Override
    public Color getColor() {
        return Color.TRANSPARENT;
    }
}
