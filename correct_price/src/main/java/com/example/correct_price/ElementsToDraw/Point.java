package com.example.correct_price.ElementsToDraw;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.widget.TextView;

import static com.example.correct_price.DrawAlghoritms.setViewMargin;
import static java.lang.StrictMath.max;

public class Point extends IDrawElement {
    public Point(TextView pointView, Integer marginLeft, Integer bufferSize){
        point_ = pointView;
        marginLeft_ = marginLeft;
        bufferSize_ = bufferSize;
    }

    @Override
    public Integer maxWidth() {
        return max(point_.getMaxWidth() - bufferSize_, 15);
    }

    @Override
    public Boolean hasIndex(int index) {
        return point_.getId() == index;
    }

    @Override
    public Integer getMarginLeft() {
        return marginLeft_;
    }

    @Override
    public void setMarginLeft(Integer marginLeft, ConstraintSet constraintSet, ConstraintLayout layout) {
        marginLeft_ = marginLeft;
        setViewMargin(constraintSet, layout.getId(), point_.getId(), marginLeft_, -1,-1,-1);
    }

    public TextView getInformationView(){ return point_; }

    private TextView point_;
    private Integer marginLeft_;
    private Integer bufferSize_;
}