package com.tabbara.mohammad.trailedsheetexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tabbara.mohammad.trailedsheet.TrailedSheetListeners;
import com.tabbara.mohammad.trailedsheet.TrailedSheet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TrailedSheetListeners.EventListener {

    private TrailedSheet trailedSheet;
    private boolean isAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trailedSheet = findViewById(R.id.trailed_sheet);
        Button reset = findViewById(R.id.reset);
        Button moveUp = findViewById(R.id.move_up);
        Button moveDown = findViewById(R.id.move_down);
        RadioButton animatedOn = findViewById(R.id.animation_on);
        RadioButton animatedOff = findViewById(R.id.animation_off);
        animatedOn.toggle();
        trailedSheet.addEventListener(this);
        reset.setOnClickListener(this);
        moveUp.setOnClickListener(this);
        moveUp.setRotation(180);
        moveDown.setOnClickListener(this);
        animatedOn.setOnClickListener(this);
        animatedOff.setOnClickListener(this);
        isAnimated = animatedOn.isChecked();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reset:
                trailedSheet.reset(isAnimated);
                break;
            case R.id.move_up:
                trailedSheet.moveUp();
                break;
            case R.id.move_down:
                trailedSheet.moveDown();
                break;
            case R.id.animation_on:
                isAnimated = ((RadioButton)v).isChecked();
                break;
            case R.id.animation_off:
                isAnimated = !((RadioButton)v).isChecked();
                break;
            default:
                break;
        }
    }

    @Override
    public void onExitUp(int id) {
        Toast.makeText(this,"Up",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExitDown(int id) {
        Toast.makeText(this,"Down",Toast.LENGTH_LONG).show();
    }

}
