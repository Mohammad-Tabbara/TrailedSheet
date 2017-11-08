package com.tabbara.mohammad.trailedsheetexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tabbara.mohammad.trailedsheet.TrailedSheet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TrailedSheet trailedSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trailedSheet = findViewById(R.id.trailed_sheet);
        Button reset = findViewById(R.id.reset);
        Button moveUp = findViewById(R.id.move_up);
        Button moveDown = findViewById(R.id.move_down);
        reset.setOnClickListener(this);
        moveUp.setOnClickListener(this);
        moveUp.setRotation(180);
        moveDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reset:
                trailedSheet.reset(true);
                break;
            case R.id.move_up:
                trailedSheet.moveUp();
                break;
            case R.id.move_down:
                trailedSheet.moveDown();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
