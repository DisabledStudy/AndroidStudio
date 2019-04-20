package com.example.correct_price.ElementsToDraw;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;

import static com.example.correct_price.DrawAlghoritms.setViewMargin;
import static java.lang.StrictMath.max;

public class DigitWithAttr extends IDrawElement {
    public DigitWithAttr(TextView digit, Button upBtn, Button downBtn, Button removeBtn, Integer marginLeft, Integer bufferSize){
        hashSet_ = new HashSet<Integer>();
        digit_ = digit; hashSet_.add(digit_.getId());
        upBtn_ = upBtn; hashSet_.add(upBtn_.getId());
        downBtn_ = downBtn; hashSet_.add(downBtn_.getId());
        removeBtn_ = removeBtn; hashSet_.add(removeBtn_.getId());
        marginLeft_ = marginLeft;
        bufferSize_ = bufferSize;
    }
    private TextView digit_;
    private Button upBtn_;
    private Button downBtn_;
    private Button removeBtn_;
    private HashSet<Integer> hashSet_;
    private Integer marginLeft_;
    private Integer bufferSize_;
    public TextView getDigitView(){ return digit_; }
    public final Button getDownButton(){ return downBtn_; }
    public final Button getUpButton(){ return upBtn_; }
    public final Button getRemoveDigitButton() { return removeBtn_; }
    public final Boolean hasIndex(int index){return hashSet_.contains(index);}

    @Override
    public Integer getMarginLeft() {
        return marginLeft_;
    }

    @Override
    public void setMarginLeft(Integer marginLeft, ConstraintSet constraintSet, ConstraintLayout layout) {
        marginLeft_ = marginLeft;
        setViewMargin(constraintSet, layout.getId(), digit_.getId(), marginLeft_, -1,-1,-1);
        setViewMargin(constraintSet, layout.getId(), upBtn_.getId(), marginLeft_, -1,-1,-1);
        setViewMargin(constraintSet, layout.getId(), downBtn_.getId(), marginLeft_, -1,-1,-1);
        setViewMargin(constraintSet, layout.getId(), removeBtn_.getId(), marginLeft_, -1,-1,-1);
    }

    public final Integer maxWidth(){
        int viewWidth = max(digit_.getMaxWidth(), 30);
        int upBtnWidth = upBtn_.getMaxWidth()+ bufferSize_;
        Integer maxWidth = max(viewWidth, upBtnWidth);
        return maxWidth;
    }
}