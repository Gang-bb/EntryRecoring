package com.example.entryrecording.utils;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 震动控件工具
 */
public class ShakeAnimatorUtil {
    public static void shakeX(View v) {
        ObjectAnimator.ofFloat(v, "translationX", 0, 10, -10, 0, 10, -10, 0, 10, -10, 0).setDuration(500).start();
    }
}