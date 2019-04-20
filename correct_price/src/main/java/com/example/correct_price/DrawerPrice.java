package com.example.correct_price;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.MutableInt;
import android.util.Pair;
import android.view.View;

import com.example.correct_price.ElementsToDraw.DigitWithAttr;
import com.example.correct_price.ElementsToDraw.IDrawElement;
import com.example.correct_price.ElementsToDraw.Point;

import java.util.ArrayList;

import static com.example.correct_price.DrawAlghoritms.CHANGE_DIGIT_BUTTON_HEIGHT;
import static com.example.correct_price.DrawAlghoritms.SPACE_BETWEEN_BUTTONS;
import static com.example.correct_price.DrawAlghoritms.drawDigitWithAttr;
import static com.example.correct_price.DrawAlghoritms.drawPoint;
import static com.example.correct_price.DrawAlghoritms.setViewMargin;

public class DrawerPrice {

    public DrawerPrice(Context context, View.OnClickListener onClickListener, MutableInt availableId, ArrayList<IDrawElement> drawElements) {
        context_ = context;
        availableId_ = availableId;
        onClickListener_ = onClickListener;
        drawElements_ = drawElements;
    }

    private Context context_;
    private MutableInt availableId_;
    private View.OnClickListener onClickListener_;
    private ArrayList<IDrawElement> drawElements_;

    private ArrayList<Integer> separateDigits(float price){
        double base1=price%1;//Give you 0.75 as remainder
        double base01=base1%0.1;//Give you 0.75 as remainder

        ArrayList<Integer> result = new ArrayList<Integer>();

        int rubles=(int)price;
        result.add( (int)(base01 * 100));
        result.add( (int)(base1 * 10));
        while (rubles > 0) {
            result.add( rubles % 10);
            rubles = rubles / 10;
        }
        return result;
    }

    private void validateArgs(Integer price, Rect rect){
        if(0 > price){
            throw new IllegalArgumentException("price can't be less then 0(" + price + ")");
        }
    }

    private void setDigitOnClickListen(DigitWithAttr digitWithAttr){
        digitWithAttr.getDownButton().setOnClickListener(onClickListener_);
        digitWithAttr.getUpButton().setOnClickListener(onClickListener_);
        digitWithAttr.getRemoveDigitButton().setOnClickListener(onClickListener_);
    }

    public void drawPriceWithButtons(ConstraintLayout layout, Pair<Float, Rect> priceAndPos){
        Float price = priceAndPos.first;
        Rect rect = priceAndPos.second;

        //final int minTextWidth = max(CHANGE_DIGIT_BUTTON_WIDTH, digitWidth) + SPACE_BETWEEN_BUTTONS;
        // final int minTextHeight = minTextWidth * 3;
        //final int minElementHeight = minTextHeight + 3 * CHANGE_DIGIT_BUTTON_HEIGHT  + 5 * SPACE_BETWEEN_BUTTONS;
        //final int minLayoutWidth = (separateDigits.size() + 1) * minTextWidth;
        //final int minLayoutHeight = minElementHeight;

        //final int availableHeight  = layout.getHeight();
        //final int availableWidth = layout.getWidth();

        ArrayList<Integer> separateDigits = separateDigits(price);
        int marginLeft = rect.left;
        int marginRight = rect.right;
        int marginTop = rect.top;
        int marginBottom = rect.bottom;

        //final int digitLayoutWidth = max(minLayoutWidth, availableWidth - marginLeft - marginRight);
        //final int digitLayoutHeight = max(minLayoutHeight, availableHeight - marginTop - marginBottom);

        ConstraintLayout digitLayout = new ConstraintLayout(context_);
        digitLayout.setId(availableId_.value++);
        digitLayout.setBackgroundColor(Color.WHITE);


        ConstraintSet constraintSet = new ConstraintSet();
        layout.addView(digitLayout);

        //ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) digitLayout.getLayoutParams();
        //lp.height = digitLayoutHeight;
        //lp.width = digitLayoutWidth;
        //layout.setLayoutParams(lp);

        constraintSet.clone(layout);
        setViewMargin(constraintSet, layout.getId(), digitLayout.getId(), marginLeft, marginTop, -1, -1);
        constraintSet.applyTo(layout);

        if(separateDigits.size() < 3){
            throw new RuntimeException("Wrong digit separating ");
        }

        final int digiSize = 22;
        final int digitMarginTop = 0;
        int digitMarginLeft = 8;
        for (int i = separateDigits.size() - 1; i > 1; --i){
            boolean first = i + 1 == separateDigits.size();
            digitMarginLeft = first ? digitMarginLeft: digitMarginLeft + drawElements_.get(drawElements_.size() - 1).maxWidth() ;
            DigitWithAttr digit = drawDigitWithAttr(context_, availableId_, digitLayout, separateDigits.get(i), digiSize, digitMarginLeft, digitMarginTop);
            drawElements_.add(digit);
            setDigitOnClickListen(digit);
        }

        digitMarginLeft += drawElements_.get(1).maxWidth();
        final int pointMarginTop =  + 2 * CHANGE_DIGIT_BUTTON_HEIGHT + SPACE_BETWEEN_BUTTONS;
        Point point = drawPoint(context_, digitLayout, availableId_, digiSize, digitMarginLeft, digitMarginTop + pointMarginTop);
        drawElements_.add(point);

        for (int i = 1; i >= 0; --i) {
            digitMarginLeft += drawElements_.get(drawElements_.size() - 1).maxWidth() ;
            DigitWithAttr digit = drawDigitWithAttr(context_, availableId_, digitLayout, separateDigits.get(i), digiSize, digitMarginLeft, digitMarginTop);
            drawElements_.add(digit);
            setDigitOnClickListen(digit);
        }
    }
}

