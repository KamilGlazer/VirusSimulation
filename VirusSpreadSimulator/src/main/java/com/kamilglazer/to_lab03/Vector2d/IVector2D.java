package com.kamilglazer.to_lab03.Vector2d;

import java.util.ArrayList;

public interface IVector2D {
    double abs();
    double cdot(IVector2D iVector2D);
    ArrayList<Double> getComponents();
}
