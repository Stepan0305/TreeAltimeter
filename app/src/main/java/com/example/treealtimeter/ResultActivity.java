package com.example.treealtimeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    private PaintView paintView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // получение пути из MainActivity
        String path = getIntent().getExtras().getString("path");

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextPhone);

        //получение характеристик экрана
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        //создание paintView для отрисовки фото и точек
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metricsB.widthPixels,metricsB.heightPixels, path);
    }




    int m = 0;

    //обработка нажатия
    public void add(View view) {
        if(m>=3){
            try {
                //подсчет
                float height = Float.parseFloat(editText.getText().toString());
                textView.setText((paintView.calculate()*height)+"");
            } catch (NumberFormatException e) {
                Toast.makeText(this,"введены не правильные значения",Toast.LENGTH_LONG).show();
            }

        }

        if(m<3){
            //добавление точек
        paintView.add();
        m++;}

        if(m==3){((TextView)view).setText("Посчитать");}
    }
}