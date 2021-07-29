package de.thetechcrafter.esr.colorpicker.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import de.thetechcrafter.esr.R;

public class ColorPickerSwatch extends FrameLayout implements View.OnClickListener {
    private int mColor;
    private ImageView mSwatchImage;
    private ImageView mCheckMarkImage;
    private OnColorSelectedListener mOnColorSelectedListener;

    public ColorPickerSwatch(Context context, int color, boolean checked, int width,
                             int strokeColor, OnColorSelectedListener listener) {
        super(context);
        mColor = color;
        mOnColorSelectedListener = listener;

        LayoutInflater.from(context).inflate(R.layout.color_picker_swatch, this);
        mSwatchImage = (ImageView) findViewById(R.id.color_picker_swatch);
        mCheckMarkImage = (ImageView) findViewById(R.id.color_picker_swatch);
        if(width > 0 ) {
            setColor(color, width, strokeColor);
        } else {
            setColor(color);
        }
        setChecked(checked);
        setOnClickListener(this);
    }

    protected void setColor(int color) {
        Drawable[] colorDrawable = new Drawable[]
                { getContext().getResources().getDrawable(R.drawable.color_picker_swatch) };
        mSwatchImage.setImageDrawable(new ColorStateDrawable(colorDrawable, color));
    }

    protected void setColor(int color, int width, int strokeColor) {
        GradientDrawable colorDrawable;

        colorDrawable = new GradientDrawable();
        colorDrawable.setShape(GradientDrawable.OVAL);
        colorDrawable.setColor(color);
        colorDrawable.setStroke(width, strokeColor);

        mSwatchImage.setImageDrawable(colorDrawable);
    }

    private void setChecked(boolean checked) {
        if(checked) {
            mCheckMarkImage.setVisibility(View.VISIBLE);
        } else {
            mCheckMarkImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(mOnColorSelectedListener != null) {
            mOnColorSelectedListener.onColorSelected(mColor);
        }
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }
}
