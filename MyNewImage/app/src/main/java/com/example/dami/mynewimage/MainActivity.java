package com.example.dami.mynewimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int now = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView bg1 = (ImageView) findViewById(R.id.imgView);
        // 이미지 반절 클릭
        final int[] images = new int[]{R.drawable.bg1,R.drawable.bg6, R.drawable.bg3, R.drawable.bono,R.drawable.bg8, R.drawable.bg4};

        bg1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (motionEvent.getX() < view.getWidth() / 2) {
                        if (now == 0) {
                            bg1.setImageResource(images[now]);
                        } else {
                            bg1.setImageResource(images[now - 1]);
                            now = now - 1;
                        }
                    } else if (motionEvent.getX() >= view.getWidth() / 2) {
                        if (now == images.length - 1) {
                            bg1.setImageResource(images[now]);
                        } else {
                            bg1.setImageResource(images[now + 1]);
                            now = now + 1;
                        }
                    }
                }
                view.performClick();
                return true;
            }
         });



       /* bg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.getX() > 91) {
                    if (now == 0) {
                        bg1.setImageResource(images[now]);
                    } else {
                        bg1.setImageResource(images[now - 1]);
                        now = now - 1;
                    }
                }
                else {
                    if (now == images.length) {
                        bg1.setImageResource(images[now]);
                    } else {
                        bg1.setImageResource(images[now + 1]);
                        now = now + 1;
                    }
                }
            }
        });*/


    }
}