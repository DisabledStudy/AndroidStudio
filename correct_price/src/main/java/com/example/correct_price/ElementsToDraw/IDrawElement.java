package com.example.correct_price.ElementsToDraw;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;

public abstract class IDrawElement {
    public abstract Integer maxWidth();
    public abstract Boolean hasIndex(int index);
    public abstract Integer getMarginLeft();
    public abstract void setMarginLeft(Integer marginLeft, ConstraintSet constraintSet, ConstraintLayout layout);
}
