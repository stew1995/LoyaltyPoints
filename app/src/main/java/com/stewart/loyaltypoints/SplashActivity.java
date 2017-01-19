package com.stewart.loyaltypoints;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button next;

    private ViewPagerAdapter viewPagerAdapter;

    private Intromanager introManager;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Toast.makeText(this, "Swipe Left", Toast.LENGTH_SHORT).show();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        introManager = new Intromanager(this);
        if (!introManager.Check()) {
            introManager.setFirst(false);
            Intent i = new Intent(SplashActivity.this, SigninActivity.class);
            startActivity(i);
            finish();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        viewPager = (ViewPager) findViewById(R.id.view_paper);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        next = (Button) findViewById(R.id.btnNextSplash);
        //define splashscreen layouts
        layouts = new int[]{R.layout.activity_splashscreen1, R.layout.activity_splashscreen2, R.layout.activity_splashscreen3,
                R.layout.activity_splashscreen4, R.layout.activity_splashscreen5};

        addBottonDots(1);
        changeStatusColour();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, SigninActivity.class));

            }
        });

    }

    private void addBottonDots(int position) {
        //Set dots at the bottom of the screen underneither image and text
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for(int i = 0; i<dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setTextSize(50);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length > 0) {
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottonDots(position);
            if(position == layouts.length-1) {
                next.setText("PROCEED");

            } else if (position == layouts.length - 5){
                next.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusColour() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position],container, false);
            container.addView(v);
            return v;
        }
        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        public void destroyItem(ViewGroup container, int position, Object object){
            View v = (View)object;
            container.removeView(v);
        }
    }


}
