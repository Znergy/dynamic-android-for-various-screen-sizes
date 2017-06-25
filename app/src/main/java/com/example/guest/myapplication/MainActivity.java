package com.example.guest.myapplication;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int rlHeight;
    private int rlWidth;
    private RelativeLayout relativeLayout;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Assignments */
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        rlWidth = displaymetrics.widthPixels;
        rlHeight = displaymetrics.heightPixels;

        System.out.println("Screen Width: " + rlWidth);
        System.out.println("Screen Height: " + rlHeight);
        System.out.println("Action Bar Height: " + getActionBarHeight());
        System.out.println("Status Bar Height: " + getStatusBarHeight());

        int btnWidth = rlWidth / 2;
        int btnHeight = (rlHeight - getActionBarHeight() - getStatusBarHeight()) / 3;

        btn1.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
        btn2.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
        btn3.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
        btn4.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
        btn5.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
        btn6.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));

        /** Button 2 */
        RelativeLayout.LayoutParams btn2Params = (RelativeLayout.LayoutParams)btn2.getLayoutParams();
        btn2Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        btn2Params.addRule(RelativeLayout.LEFT_OF, R.id.btn1);
        btn2.setLayoutParams(btn2Params); //causes layout update

        /** Button 3 */
        RelativeLayout.LayoutParams btn3Params = (RelativeLayout.LayoutParams)btn3.getLayoutParams();
        btn3Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        btn3Params.addRule(RelativeLayout.BELOW, R.id.btn1);
        btn3.setLayoutParams(btn3Params);

        /** Button 4 */
        RelativeLayout.LayoutParams btn4Params = (RelativeLayout.LayoutParams)btn4.getLayoutParams();
        btn4Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        btn4Params.addRule(RelativeLayout.BELOW, R.id.btn2);
        btn4Params.addRule(RelativeLayout.LEFT_OF, R.id.btn3);
        btn4.setLayoutParams(btn4Params);

        /** Button 5 */
        RelativeLayout.LayoutParams btn5Params = (RelativeLayout.LayoutParams)btn5.getLayoutParams();
        btn5Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        btn5Params.addRule(RelativeLayout.BELOW, R.id.btn3);
        btn5.setLayoutParams(btn5Params);

        /** Button 6 */
        RelativeLayout.LayoutParams btn6Params = (RelativeLayout.LayoutParams)btn6.getLayoutParams();
        btn6Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        btn6Params.addRule(RelativeLayout.BELOW, R.id.btn4);
        btn6Params.addRule(RelativeLayout.LEFT_OF, R.id.btn5);
        btn6.setLayoutParams(btn6Params);

//        ViewTreeObserver viewTreeObserver = relativeLayout.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                rlWidth  = relativeLayout.getMeasuredWidth();
//                rlHeight = relativeLayout.getMeasuredHeight();
//                System.out.println("RL Width: " + rlWidth);
//                System.out.println("RL Height: " + rlHeight);
//            }
//        });

    }

    private int getActionBarHeight() {
        int actionBarHeight = getSupportActionBar().getHeight();
        if (actionBarHeight != 0)
            return actionBarHeight;
        final TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        } else if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        return actionBarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        // TODO Auto-generated method stub
//        super.onWindowFocusChanged(hasFocus);
//        updateSizeInfo();
//    }
//
//    private void updateSizeInfo() {
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
//        rlWidth = relativeLayout.getWidth();
//        rlHeight = relativeLayout.getHeight();
//    }
}
