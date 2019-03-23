package com.example.send_result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
public class MainActivity extends Activity implements OnClickListener {

    TextView tvName;
    Button btnName;
    EditText etFName;
    EditText etLName;

    Button btnSubmit;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = (TextView) findViewById(R.id.tvName);
        btnName = (Button) findViewById(R.id.btnName);
        btnName.setOnClickListener(this);

        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnName.getId()){
            Intent intent = new Intent(this, NameActivity.class);
            startActivityForResult(intent, 1);
        }
        else if(v.getId() == btnSubmit.getId()){
            Intent intent = new Intent(this, ViewActivity.class);
            intent.putExtra("fname", etFName.getText().toString());
            intent.putExtra("lname", etLName.getText().toString());
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("name");
        tvName.setText("Your name is " + name);
    }
}