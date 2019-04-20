package com.example.correct_price;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.MutableInt;
import android.util.Pair;
import android.view.View;

import com.example.correct_price.ElementsToDraw.DigitWithAttr;
import com.example.correct_price.ElementsToDraw.IDrawElement;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    MutableInt availableId;
    ArrayList<IDrawElement> drawElements = new ArrayList<IDrawElement>();
    ConstraintLayout layout;
    MyOnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        availableId = new MutableInt(1);
        layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        onClickListener = new MyOnClickListener();
        DrawerPrice drawerPrice = new DrawerPrice(this, onClickListener, availableId, drawElements);

        Pair<Float, Rect> priceAndPos = new Pair<Float, Rect>(102.53f, new Rect(100, 100, 100,100));
        drawerPrice.drawPriceWithButtons(layout, priceAndPos);
    }

    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int i = 0;
            for (; i < drawElements.size(); i++) {
                IDrawElement element = drawElements.get(i);
                if(element.hasIndex(v.getId())){
                    break;
                }
            }

            if(i < drawElements.size()){
                DigitWithAttr digitWithAttr = (DigitWithAttr) drawElements.get(i);
                if(digitWithAttr.getUpButton().getId() == v.getId()){
                    Integer digit = Integer.valueOf(digitWithAttr.getDigitView().getText().toString());
                    digit = (digit + 1) % 10;
                    digitWithAttr.getDigitView().setText(digit.toString());
                }
                else if(digitWithAttr.getDownButton().getId() == v.getId()){
                    Integer digit = Integer.valueOf(digitWithAttr.getDigitView().getText().toString());
                    digit = digit == 0 ? 9 : digit - 1;
                    digitWithAttr.getDigitView().setText(digit.toString());
                }
                else if(digitWithAttr.getRemoveDigitButton().getId() == v.getId()){
                    ConstraintSet constraintSet = new ConstraintSet();
                    ConstraintLayout parentLayout = (ConstraintLayout) digitWithAttr.getRemoveDigitButton().getParent();
                    constraintSet.clone(parentLayout);
                    for (int j = drawElements.size() - 1; j > i; j--) {
                        Integer prevMarginLeft = drawElements.get(j - 1).getMarginLeft();
                        drawElements.get(j).setMarginLeft(prevMarginLeft, constraintSet, parentLayout);
                    }
                    constraintSet.applyTo(parentLayout);

                    parentLayout.removeView(digitWithAttr.getDigitView());
                    parentLayout.removeView(digitWithAttr.getUpButton());
                    parentLayout.removeView(digitWithAttr.getDownButton());
                    parentLayout.removeView(digitWithAttr.getRemoveDigitButton());
                    drawElements.remove(i);
                }
                else{
                    throw new RuntimeException("wrong index");
                }
            }
        }
    }
}
