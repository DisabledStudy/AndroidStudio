package com.example.correct_price;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout layout;
    int index = 1;
    final int CHANGE_DIGIT_BUTTON_WIDTH = 15;
    final int CHANGE_DIGIT_BUTTON_HEIGHT = 15;
    final int SPACE_BETWEEN_BUTTONS = 8;

    ArrayList<DigitWithAttr> digitsWithAttr = new ArrayList<DigitWithAttr>();

    private void setViewMargin(ConstraintSet constraintSet, int layoutId, int viewId,
                               int marginLeft, int marginTop, int marginRight, int marginBottom){


        if(marginLeft != 0){
            constraintSet.connect(viewId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, marginLeft);
        }
        if(marginTop != 0){
            constraintSet.connect(viewId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, marginTop);
        }
        if(marginRight != 0){
            constraintSet.connect(viewId, ConstraintSet.RIGHT, layoutId, ConstraintSet.RIGHT, marginRight);
        }
        if(marginBottom != 0){
            constraintSet.connect(viewId, ConstraintSet.BOTTOM, layoutId, ConstraintSet.BOTTOM, marginBottom);
        }
    }

    private class DigitWithAttr {
        public DigitWithAttr(TextView digit, Button upBtn, Button downBtn, Button removeBtn){
            hashSet_ = new HashSet<Integer>();
            digit_ = digit; hashSet_.add(digit_.getId());
            upBtn_ = upBtn; hashSet_.add(upBtn_.getId());
            downBtn_ = downBtn; hashSet_.add(downBtn_.getId());
            removeBtn_ = removeBtn; hashSet_.add(removeBtn_.getId());
        }
        private TextView digit_;
        private Button upBtn_;
        private Button downBtn_;
        private Button removeBtn_;
        private HashSet<Integer> hashSet_;
        public TextView getDigitView(){ return digit_; }
        public final Button getDownButton(){ return downBtn_; }
        public final Button getUpButton(){ return upBtn_; }
        public final Button getRemoveDigitButton() { return removeBtn_; }
        public final Boolean hasIndex(int index){return hashSet_.contains(index);}

    }

    private DigitWithAttr drawDigitWithAttr(ConstraintLayout layout, Integer digit,
                                            int width,
                                            int marginLeft, int marginTop){

        TextView viewDigit = new TextView(this);
        viewDigit.setId(index++);
        viewDigit.setTextColor(Color.BLACK);
        viewDigit.setText(digit.toString());
        viewDigit.setTextSize(TypedValue.COMPLEX_UNIT_PX, width);
        viewDigit.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);;
        layout.addView(viewDigit);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        //добавим число соотвествующего размера
        setViewMargin(constraintSet, layout.getId(), viewDigit.getId(), marginLeft, marginTop, 0, 0);
        int expectedHeight = width * 2;
        constraintSet.constrainWidth(viewDigit.getId(), width);

        //добавим кнопки для увеличения и уменьшения числа
        Button buttonUpDigit = new Button(this);
        buttonUpDigit.setId(index++);
        buttonUpDigit.setText("+");
        buttonUpDigit.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        layout.addView(buttonUpDigit);
        int upDigitMarginTop = marginTop - CHANGE_DIGIT_BUTTON_HEIGHT;
        setViewMargin(constraintSet, layout.getId(), buttonUpDigit.getId(), marginLeft, upDigitMarginTop, 0, 0);
        constraintSet.constrainWidth(buttonUpDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonUpDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        Button buttonDownDigit = new Button(this);
        buttonDownDigit.setId(index++);
        buttonDownDigit.setText("-");
        buttonDownDigit.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        layout.addView(buttonDownDigit);
        int downDigitMarginTop = marginTop + viewDigit.getMeasuredHeight();
        setViewMargin(constraintSet, layout.getId(), buttonDownDigit.getId(), marginLeft, downDigitMarginTop, 0, 0);
        constraintSet.constrainWidth(buttonDownDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonDownDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        //добавим кнопку для удаления числа
        Button buttonRemoveDigit = new Button(this);
        buttonRemoveDigit.setId(index++);
        buttonRemoveDigit.setText("r");
        buttonRemoveDigit.setBackgroundColor(getResources().getColor(R.color.colorRemoveDigit));
        layout.addView(buttonRemoveDigit);
        int removeButtonMarginTop = marginTop - 2*CHANGE_DIGIT_BUTTON_HEIGHT - SPACE_BETWEEN_BUTTONS;
        setViewMargin(constraintSet, layout.getId(), buttonRemoveDigit.getId(), marginLeft, removeButtonMarginTop, 0, 0);
        constraintSet.constrainWidth(buttonRemoveDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonRemoveDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        constraintSet.applyTo(layout);
        return new DigitWithAttr(viewDigit, buttonUpDigit, buttonDownDigit, buttonRemoveDigit);
    }


        private ArrayList<Integer> digits(Integer allDigits){
            ArrayList<Integer> result = new ArrayList<Integer>();
            while (allDigits > 0) {
                result.add( allDigits % 10);
                allDigits = allDigits / 10;
            }
            return result;
        }

        private void validateArgs(Integer price, Rect rect){
            if(0 > price){
                throw new IllegalArgumentException("price can't be less then 0(" + price + ")");
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        Pair<Integer, Rect> pair = new Pair<Integer, Rect>(1, new Rect(100, 50, 0, 0));
        DigitWithAttr firstDigit = drawDigitWithAttr(layout, 9, 40, 50, 100);
        digitsWithAttr.add(firstDigit);

        firstDigit.getDownButton().setOnClickListener(this);
        firstDigit.getUpButton().setOnClickListener(this);
        firstDigit.getRemoveDigitButton().setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = 0;
        for (; i < digitsWithAttr.size(); i++) {
            DigitWithAttr digitWithAttr = digitsWithAttr.get(i);
            if(digitWithAttr.hasIndex(v.getId())){
                break;
            }
        }

        if(i < digitsWithAttr.size()){
            DigitWithAttr digitWithAttr = digitsWithAttr.get(i);
            if(digitWithAttr.getUpButton().getId() == v.getId()){
                Integer digit = Integer.valueOf(digitWithAttr.getDigitView().getText().toString());
                digit = (digit + 1) % 10;
                digitWithAttr.getDigitView().setText(digit.toString());
            }
            else if(digitWithAttr.getDownButton().getId() == v.getId()){
                Integer digit = Integer.valueOf(digitWithAttr.getDigitView().getText().toString());
                digit = (digit - 1) % 10;
                digitWithAttr.getDigitView().setText(digit.toString());
            }
            else if(digitWithAttr.getRemoveDigitButton().getId() == v.getId()){
                layout.removeView(digitWithAttr.getDigitView());
                layout.removeView(digitWithAttr.getUpButton());
                layout.removeView(digitWithAttr.getDownButton());
                layout.removeView(digitWithAttr.getRemoveDigitButton());
                digitsWithAttr.remove(i);
            }
            else{
                throw new RuntimeException("wrong index");
            }
        }
    }

}
