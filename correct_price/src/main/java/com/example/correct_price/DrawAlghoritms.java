package com.example.correct_price;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.MutableInt;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.correct_price.ElementsToDraw.DigitWithAttr;
import com.example.correct_price.ElementsToDraw.Point;

public class DrawAlghoritms {
    final static int CHANGE_DIGIT_BUTTON_WIDTH = 40;
    final static int CHANGE_DIGIT_BUTTON_HEIGHT = 40;
    final static int SPACE_BETWEEN_BUTTONS = 8;
    final static int TEXT_SOFT_BUFFER_PIXELS = 15;
    final static int TEXT_HARD_BUFFER_PIXELS = 30;

    public static void setViewMargin(ConstraintSet constraintSet, int layoutId, int viewId,
                               int marginLeft, int marginTop, int marginRight, int marginBottom){
        if(marginLeft != -1){
            constraintSet.connect(viewId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, marginLeft);
        }
        if(marginTop != -1){
            constraintSet.connect(viewId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, marginTop);
        }
        if(marginRight != -1){
            constraintSet.connect(viewId, ConstraintSet.RIGHT, layoutId, ConstraintSet.RIGHT, marginRight);
        }
        if(marginBottom != -1){
            constraintSet.connect(viewId, ConstraintSet.BOTTOM, layoutId, ConstraintSet.BOTTOM, marginBottom);
        }
    }

    public static Point drawPoint (Context context, ConstraintLayout layout, MutableInt availableId, int digitSize, int marginLeft, int marginTop){
        TextView viewPoint = new TextView(context);
        viewPoint.setId(availableId.value++);
        viewPoint.setTextColor(Color.BLACK);
        viewPoint.setText(".");
        viewPoint.setTextSize(digitSize);
        viewPoint.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int measuredWidth = viewPoint.getMeasuredWidth();
        viewPoint.setWidth(measuredWidth);
        layout.addView(viewPoint);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        //добавим число соотвествующего размера
        setViewMargin(constraintSet, layout.getId(), viewPoint.getId(), marginLeft, marginTop, -1, -1);
        constraintSet.constrainWidth(viewPoint.getId(), measuredWidth);

        constraintSet.applyTo(layout);

        return new Point(viewPoint, marginLeft, TEXT_SOFT_BUFFER_PIXELS);
    }

    public static DigitWithAttr drawDigitWithAttr(Context context, MutableInt availableId, ConstraintLayout layout, Integer digit,
                                                  int textSize, int marginLeft, int marginTop){

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        //добавим кнопку для удаления числа
        Button buttonRemoveDigit = new Button(context);
        buttonRemoveDigit.setId(availableId.value++);
        buttonRemoveDigit.setText("r");
        buttonRemoveDigit.setBackgroundColor(context.getResources().getColor(R.color.colorRemoveDigit));
        layout.addView(buttonRemoveDigit);
        int removeBtnMarginTop = marginTop;
        setViewMargin(constraintSet, layout.getId(), buttonRemoveDigit.getId(), marginLeft, removeBtnMarginTop, -1, -1);
        buttonRemoveDigit.setWidth(CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainWidth(buttonRemoveDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonRemoveDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        //добавим кнопку для увеличения числа
        Button buttonUpDigit = new Button(context);
        buttonUpDigit.setId(availableId.value++);
        buttonUpDigit.setText("+");
        buttonUpDigit.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        buttonUpDigit.setWidth(CHANGE_DIGIT_BUTTON_WIDTH);
        buttonUpDigit.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        layout.addView(buttonUpDigit);
        int upBtnMarginTop = removeBtnMarginTop + CHANGE_DIGIT_BUTTON_HEIGHT + SPACE_BETWEEN_BUTTONS;
        setViewMargin(constraintSet, layout.getId(), buttonUpDigit.getId(), marginLeft, upBtnMarginTop, -1, -1);
        constraintSet.constrainWidth(buttonUpDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonUpDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        //добавим число соотвествующего размера
        TextView viewDigit = new TextView(context);
        viewDigit.setId(availableId.value++);
        viewDigit.setTextColor(Color.BLACK);
        viewDigit.setText(digit.toString());
        viewDigit.setTextSize(textSize);
        viewDigit.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int measuredWidth = viewDigit.getMeasuredWidth();
        viewDigit.setWidth(measuredWidth);
        layout.addView(viewDigit);
        int digitMarginTop = upBtnMarginTop + CHANGE_DIGIT_BUTTON_HEIGHT;
        setViewMargin(constraintSet, layout.getId(), viewDigit.getId(), marginLeft, digitMarginTop, -1, -1);
        constraintSet.constrainWidth(viewDigit.getId(), measuredWidth);

        //добавим кнопку для уменьшения числа
        Button buttonDownDigit = new Button(context);
        buttonDownDigit.setId(availableId.value++);
        buttonDownDigit.setText("-");
        buttonDownDigit.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        layout.addView(buttonDownDigit);
        int downBtnMarginTop = digitMarginTop + viewDigit.getMeasuredHeight();
        setViewMargin(constraintSet, layout.getId(), buttonDownDigit.getId(), marginLeft, downBtnMarginTop, -1, -1);
        buttonDownDigit.setWidth(textSize);
        constraintSet.constrainWidth(buttonDownDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonDownDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        constraintSet.applyTo(layout);
        return new DigitWithAttr(viewDigit, buttonUpDigit, buttonDownDigit, buttonRemoveDigit, marginLeft, TEXT_SOFT_BUFFER_PIXELS);
    }
}
