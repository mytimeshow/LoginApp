package com.example.administrator.loginapp.Activity;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.Views.MyView;

public class TextActivity extends AppCompatActivity {
    private static final String TAG = "TextActivity";
    private TextView text_tv;
    private Button btn_bt;
    private MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        text_tv= (TextView) findViewById(R.id.text_tv);
        btn_bt= (Button) findViewById(R.id.btn_bt);
        myView= (MyView) findViewById(R.id.myView);


        ViewGroup.LayoutParams l=text_tv.getLayoutParams();
        l.width=ViewGroup.LayoutParams.MATCH_PARENT;
        text_tv.setLayoutParams(l);

        final int left=text_tv.getLeft();
        final int right=text_tv.getRight();
        final int top=text_tv.getTop();
        final int buttom=text_tv.getBottom();
        final int width=text_tv.getWidth();
        final int height=text_tv.getHeight();



        btn_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_tv.setText(String.valueOf(text_tv.getLeft())+" "+
                        String.valueOf(text_tv.getRight())+" "+
                        String.valueOf(text_tv.getTop())+" "+
                        String.valueOf(text_tv.getBottom())+" "+
                        String.valueOf(text_tv.getWidth())+" "+
                        String.valueOf(text_tv.getHeight())+" ");
                startAnimator();
            }
        });
    }

    private void startAnimator() {
        ValueAnimator animator=ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char value= (char)animation.getAnimatedValue();
                text_tv.setText(String.valueOf(value));
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "onAnimationStart: " );
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "onAnimationEnd: " );
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "onAnimationCancel: " );
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "onAnimationRepeat: " );
            }
        });
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }


    private class CharEvaluator implements TypeEvaluator<Character>{
        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {

            int startInt=(int)startValue;
            int endInt=(int)endValue;
            int current= (int) (startInt+fraction*(endInt-startInt));
            char result= (char) current;




            return result;
        }
    }
}
