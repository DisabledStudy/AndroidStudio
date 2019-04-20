package com.example.correct_price;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.StrictMath.max;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout layout;
    int index = 1;
    final int CHANGE_DIGIT_BUTTON_WIDTH = 40;
    final int CHANGE_DIGIT_BUTTON_HEIGHT = 40;
    final int SPACE_BETWEEN_BUTTONS = 8;
    final int TEXT_SOFT_BUFFER_PIXELS = 15;
    final int TEXT_HARD_BUFFER_PIXELS = 30;

    ArrayList<DrawElement> drawElements = new ArrayList<DrawElement>();

    private void setViewMargin(ConstraintSet constraintSet, int layoutId, int viewId,
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

    protected abstract class DrawElement {
        public abstract Integer maxWidth();
        public abstract Boolean hasIndex(int index);
        public abstract Integer getMarginLeft();
        public abstract void setMarginLeft(Integer marginLeft, ConstraintSet constraintSet, ConstraintLayout layout);
        //public abstract void swapElement(DrawElement drawElemnt);
    }

    private class Point extends DrawElement {
        public Point(TextView pointView, Integer marginLeft){
            point_ = pointView;
            marginLeft_ = marginLeft;
        }

        @Override
        public Integer maxWidth() {
            return max(point_.getMaxWidth() - TEXT_SOFT_BUFFER_PIXELS, 15);
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
    }

    private class DigitWithAttr extends DrawElement {
        public DigitWithAttr(TextView digit, Button upBtn, Button downBtn, Button removeBtn, Integer marginLeft){
            hashSet_ = new HashSet<Integer>();
            digit_ = digit; hashSet_.add(digit_.getId());
            upBtn_ = upBtn; hashSet_.add(upBtn_.getId());
            downBtn_ = downBtn; hashSet_.add(downBtn_.getId());
            removeBtn_ = removeBtn; hashSet_.add(removeBtn_.getId());
            marginLeft_ = marginLeft;
        }
        private TextView digit_;
        private Button upBtn_;
        private Button downBtn_;
        private Button removeBtn_;
        private HashSet<Integer> hashSet_;
        private Integer marginLeft_;
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
            int upBtnWidth = upBtn_.getMaxWidth()+ TEXT_SOFT_BUFFER_PIXELS;
            Integer maxWidth = max(viewWidth, upBtnWidth);
            return maxWidth;
        }
    }

    private Point drawPoint (ConstraintLayout layout, int digitSize, int marginLeft, int marginTop){
        TextView viewPoint = new TextView(this);
        viewPoint.setId(index++);
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

        return new Point(viewPoint, marginLeft);
    }



    private DigitWithAttr drawDigitWithAttr(ConstraintLayout layout, Integer digit,
                                            int textSize, int marginLeft, int marginTop){

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        //добавим кнопку для удаления числа
        Button buttonRemoveDigit = new Button(this);
        buttonRemoveDigit.setId(index++);
        buttonRemoveDigit.setText("r");
        buttonRemoveDigit.setBackgroundColor(getResources().getColor(R.color.colorRemoveDigit));
        layout.addView(buttonRemoveDigit);
        int removeBtnMarginTop = marginTop;
        setViewMargin(constraintSet, layout.getId(), buttonRemoveDigit.getId(), marginLeft, removeBtnMarginTop, -1, -1);
        buttonRemoveDigit.setWidth(CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainWidth(buttonRemoveDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonRemoveDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        //добавим кнопку для увеличения числа
        Button buttonUpDigit = new Button(this);
        buttonUpDigit.setId(index++);
        buttonUpDigit.setText("+");
        buttonUpDigit.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        buttonUpDigit.setWidth(CHANGE_DIGIT_BUTTON_WIDTH);
        buttonUpDigit.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        layout.addView(buttonUpDigit);
        int upBtnMarginTop = removeBtnMarginTop + CHANGE_DIGIT_BUTTON_HEIGHT + SPACE_BETWEEN_BUTTONS;
        setViewMargin(constraintSet, layout.getId(), buttonUpDigit.getId(), marginLeft, upBtnMarginTop, -1, -1);
        constraintSet.constrainWidth(buttonUpDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonUpDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        //добавим число соотвествующего размера
        TextView viewDigit = new TextView(this);
        viewDigit.setId(index++);
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
        Button buttonDownDigit = new Button(this);
        buttonDownDigit.setId(index++);
        buttonDownDigit.setText("-");
        buttonDownDigit.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        layout.addView(buttonDownDigit);
        int downBtnMarginTop = digitMarginTop + viewDigit.getMeasuredHeight();
        setViewMargin(constraintSet, layout.getId(), buttonDownDigit.getId(), marginLeft, downBtnMarginTop, -1, -1);
        buttonDownDigit.setWidth(textSize);
        constraintSet.constrainWidth(buttonDownDigit.getId(), CHANGE_DIGIT_BUTTON_WIDTH);
        constraintSet.constrainHeight(buttonDownDigit.getId(), CHANGE_DIGIT_BUTTON_HEIGHT);

        constraintSet.applyTo(layout);
        return new DigitWithAttr(viewDigit, buttonUpDigit, buttonDownDigit, buttonRemoveDigit, marginLeft);
    }


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
        digitWithAttr.getDownButton().setOnClickListener(this);
        digitWithAttr.getUpButton().setOnClickListener(this);
        digitWithAttr.getRemoveDigitButton().setOnClickListener(this);
    }

    public void createPriceInConstraintLayout(ConstraintLayout layout, Pair<Float, Rect> priceAndPos){
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

        ConstraintLayout digitLayout = new ConstraintLayout(this);
        digitLayout.setId(index++);
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
            digitMarginLeft = first ? digitMarginLeft: digitMarginLeft + drawElements.get(drawElements.size() - 1).maxWidth() ;
            DigitWithAttr digit = drawDigitWithAttr(digitLayout, separateDigits.get(i), digiSize, digitMarginLeft, digitMarginTop);
            drawElements.add(digit);
            setDigitOnClickListen(digit);
        }

        digitMarginLeft += drawElements.get(1).maxWidth();
        final int pointMarginTop =  + 2 * CHANGE_DIGIT_BUTTON_HEIGHT + SPACE_BETWEEN_BUTTONS;
        Point point = drawPoint(digitLayout, digiSize, digitMarginLeft, digitMarginTop + pointMarginTop);
        drawElements.add(point);

        for (int i = 1; i >= 0; --i) {
            digitMarginLeft += drawElements.get(drawElements.size() - 1).maxWidth() ;
            DigitWithAttr digit = drawDigitWithAttr(digitLayout, separateDigits.get(i), digiSize, digitMarginLeft, digitMarginTop);
            drawElements.add(digit);
            setDigitOnClickListen(digit);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMetrics = new DisplayMetrics();

        layout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        Pair<Float, Rect> priceAndPos = new Pair<Float, Rect>(102.53f, new Rect(100, 100, 100,100));
        createPriceInConstraintLayout(layout, priceAndPos);

    }


    @Override
    public void onClick(View v) {
        int i = 0;
        for (; i < drawElements.size(); i++) {
            DrawElement element = drawElements.get(i);
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
