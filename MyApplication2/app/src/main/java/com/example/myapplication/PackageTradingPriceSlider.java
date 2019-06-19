package com.example.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.SeekBar;

public class PackageTradingPriceSlider extends FrameLayout {
    private ValueAnimator mAnimator;
    private SmoothSeekBarChangeListener smoothSeekBarChangeListener = null;

    public PackageTradingPriceSlider(Context context) {
        super(context);
        init(context);
    }

    public PackageTradingPriceSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PackageTradingPriceSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // setter and getter functions

    public void setCurrentAndMax(int current, int max) {
        ((SeekBar) findViewById(R.id.seek_bar_to_read_values)).setMax(max);
        ((SeekBar) findViewById(R.id.seek_bar_to_read_values)).setMax(current);
        ((SeekBar) findViewById(R.id.seek_bar_to_show)).setMax(max);
        ((SeekBar) findViewById(R.id.seek_bar_to_show)).setMax(current);
    }

    public int getCurrentlySelectedValue() {
        return ((SeekBar) findViewById(R.id.seek_bar_to_read_values)).getProgress();
    }

    public void setOnChangeListener(SmoothSeekBarChangeListener smoothSeekBarChangeListener) {
        this.smoothSeekBarChangeListener = smoothSeekBarChangeListener;
    }

    private void init(final Context context) {
        setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // show and read seekbar for fetching and display value on seekbar

        android.view.LayoutInflater.from(context).inflate(R.layout.package_trading_price_slider, this, true);
        final SeekBar seekBarToUpdate = findViewById(R.id.seek_bar_to_show);
        seekBarToUpdate.setEnabled(false);
        final SeekBar seekBarToRead = findViewById(R.id.seek_bar_to_read_values);
        seekBarToRead.setEnabled(true);
        seekBarToRead.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                // Value animator for progress
                // Ref : https://stackoverflow.com/questions/9691457/how-can-i-make-an-android-seek-bar-move-smoothly

                if (mAnimator != null) {
                    mAnimator.cancel();
                    mAnimator.removeAllUpdateListeners();
                    mAnimator.removeAllListeners();
                    mAnimator = null;
                }
                if (smoothSeekBarChangeListener != null)
                    smoothSeekBarChangeListener.valueChanged(seekBarToRead.getProgress());
                mAnimator = ValueAnimator.ofInt(seekBarToUpdate.getProgress(), seekBarToRead.getProgress());
                int mDuration = 500; //in millis
                mAnimator.setDuration(mDuration);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        seekBarToUpdate.setProgress(value);
                    }
                });
                mAnimator.start();

            }
        });
    }


}
