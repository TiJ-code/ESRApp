package de.thetechcrafter.esr.colorpicker.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import android.widget.TableRow;
import de.thetechcrafter.esr.colorpicker.utils.ColorPickerSwatch.OnColorSelectedListener;

public class ColorPickerPalette extends TableLayout {

    public OnColorSelectedListener mOnColorSelectedListener;

    private String mDescription;
    private String mDescriptionSelected;

    private int mSwatchLength;
    private int mMarginSize;
    private int mNumColumns;
    private boolean mBackwardsOrder;

    public ColorPickerPalette(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorPickerPalette(Context context) {
        super(context);
    }

    private static void addSwatchToRow(TableRow row, View swatch, int rowNumber,
                                       boolean backwardsOrder) {
        if(backwardsOrder) {
            if(rowNumber % 2 == 0) {
                row.addView(swatch);
            } else {
                row.addView(swatch, 0);
            }
        } else {
            row.addView(swatch);
        }
    }

    public void init(int size, int colums, OnColorSelectedListener listener,
                     boolean backwardsOrder) {
        mNumColumns = colums;
        mBackwardsOrder = backwardsOrder;
        Resources res = getResources();
        if(size == ColorPickerDialog.SIZE_LARGE) {

        }
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(params);
        return row;
    }

    public void drawPalette(int[] colors, int selectedColor, int width, int strokeColor) {
        drawPalette(colors, selectedColor, null, width, strokeColor);
    }

    public void drawPalette(int[] colors, int selectedColor, String[] colorContentDescriptions,
                            int width, int strokeColor) {
        if(colors == null) {
            return;
        }

        this.removeAllViews();
        int tableElements = 0;
        int rowElements = 0;
        int rowNumber = 0;

        TableRow row = createTableRow();
        for(int color : colors) {
            View colorSwatch = createColorSwatch(color, selectedColor, width, strokeColor);
            setSwatchDescription(rowNumber, tableElements, rowElements, color == selectedColor,
                    colorSwatch, colorContentDescriptions);
            addSwatchToRow(row, colorSwatch, rowNumber, mBackwardsOrder);

            tableElements++;
            rowElements++;
            if(rowElements == mNumColumns) {
                addView(row);
                row = createTableRow();
                rowElements = 0;
                rowNumber++;
            }
        }

        if(rowElements > 0) {
            while(rowElements != mNumColumns) {
                addSwatchToRow(row, createBlankSpace(), rowNumber, mBackwardsOrder);
                rowElements++;
            }
            addView(row);
        }
    }

    private void setSwatchDescription(int rowNumber, int index, int rowElements, boolean selected,
                                      View swatch, String[] contentDescriptions) {
        String description;
        if(contentDescriptions != null && contentDescriptions.length > index) {
            description = contentDescriptions[index];
        } else {
            int accessibilityIndex;
            if(mBackwardsOrder) {
                if(rowNumber % 2 == 0) {
                    accessibilityIndex = index + 1;
                } else {
                    int rowMax = ((rowNumber + 1) * mNumColumns);
                    accessibilityIndex = rowMax - rowElements;
                }
            } else {
                accessibilityIndex = index + 1;
            }
            if(selected) {
                description = String.format(mDescriptionSelected, accessibilityIndex);
            } else {
                description = String.format(mDescription, accessibilityIndex);
            }
        }
        swatch.setContentDescription(description);
    }

    private ImageView createBlankSpace() {
        ImageView view = new ImageView(getContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
        params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
        view.setLayoutParams(params);
        return view;
    }

    private ColorPickerSwatch createColorSwatch(int color, int selectedColor, int width, int strokeColor) {
        ColorPickerSwatch view = new ColorPickerSwatch(getContext(), color,
                color == selectedColor, width, strokeColor, mOnColorSelectedListener);
        TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
        params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
        view.setLayoutParams(params);
        return view;
    }
}
