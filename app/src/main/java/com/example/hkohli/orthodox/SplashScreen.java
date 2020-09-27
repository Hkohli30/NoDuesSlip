package com.example.hkohli.orthodox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.flaviofaria.kenburnsview.TransitionGenerator;

public class SplashScreen extends AppCompatActivity {

    int count;
    TextView textView;
    private int random_generator[] = {R.drawable.one,R.drawable.two,R.drawable.blueballs,
            R.drawable.blueballs2,R.drawable.orbs,R.drawable.rainbow,R.drawable.redballs,
            R.drawable.whiteballs};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textView = (TextView)findViewById(R.id.textview);
        final  KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);

        // Random image generator
        int rand = (int)(Math.random() * random_generator.length);
        kbv.setImageResource(random_generator[rand]);

        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                count++;
                if (count == 2) {
                    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                    textView.startAnimation(fadeIn);
                    textView.startAnimation(fadeOut);
                    //textView.setVisibility(View.VISIBLE);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    fadeIn.setDuration(1200);
                    fadeIn.setFillAfter(true);
                    fadeOut.setDuration(1200);
                    fadeOut.setFillAfter(true);
                    fadeOut.setStartOffset(4200 + fadeIn.getStartOffset());
                }
            }
        });
    }
}

