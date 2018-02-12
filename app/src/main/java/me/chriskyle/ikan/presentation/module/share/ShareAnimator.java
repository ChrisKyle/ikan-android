package me.chriskyle.ikan.presentation.module.share;

import android.animation.TypeEvaluator;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/25.
 */
public class ShareAnimator implements TypeEvaluator<Float> {

	private final float s = 1.70158f;
	float duration = 0f;

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public Float evaluate(float fraction, Float startValue, Float endValue) {
		float t = duration * fraction;
		float b = startValue.floatValue();
		float c = endValue.floatValue() - startValue.floatValue();
		float d = duration;
		float result = calculate(t, b, c, d);
		return result;
	}

	public Float calculate(float t, float b, float c, float d) {
		return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
	}
}
