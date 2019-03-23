package com.example.layout_by_code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /** Called when the activity is first created. */
    LinearLayout llMain;
    RadioGroup rgGravity;
    EditText etName;
    Button btnCreate;
    Button btnClear;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // создание LinearLayout
        llMain = new LinearLayout(this);
        // установим вертикальную ориентацию
        llMain.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(llMain, linLayoutParam);

        ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        tv.setText("Press enter text of the new button");
        tv.setLayoutParams(lpView);
        llMain.addView(tv);

        btnCreate = new Button(this);
        btnCreate.setText("Create");
        btnCreate.setId((int)2);
        llMain.addView(btnCreate, lpView);
        btnCreate.setOnClickListener(this);

        etName = new EditText(this);
        llMain.addView(etName);

        rgGravity = new RadioGroup(this);
        llMain.addView(rgGravity);

        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 50;

        btnClear = new Button(this);
        btnClear.setText("Clear");
        btnClear.setId((int)1);
        llMain.addView(btnClear, leftMarginParams);
        btnClear.setOnClickListener(this);

        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;

        Button btn2 = new Button(this);
        btn2.setText("Button2");
        llMain.addView(btn2, rightGravityParams);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Log.d("helper", String.valueOf(id));
        if(v.getId() == btnCreate.getId()){
            // Создание LayoutParams c шириной и высотой по содержимому
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    wrapContent, wrapContent);
            // переменная для хранения значения выравнивания
            // по умолчанию пусть будет LEFT
            int btnGravity = Gravity.LEFT;
            // определяем, какой RadioButton "чекнут" и
            // соответственно заполняем btnGravity
//                switch (rgGravity.getCheckedRadioButtonId()) {
//                    case rgGravity.:
//                        btnGravity = Gravity.LEFT;
//                        break;
//                    case R.id.rbCenter:
//                        btnGravity = Gravity.CENTER_HORIZONTAL;
//                        break;
//                    case R.id.rbRight:
//                        btnGravity = Gravity.RIGHT;
//                        break;
//                }
            // переносим полученное значение выравнивания в LayoutParams
            lParams.gravity = btnGravity;

            // создаем Button, пишем текст и добавляем в LinearLayout
            Button btnNew = new Button(this);
            btnNew.setText(etName.getText().toString());
            llMain.addView(btnNew, lParams);
        }
        else if(v.getId() == btnClear.getId()){
            llMain.removeAllViews();
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        }
    }
}