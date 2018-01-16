package com.fuzple.headup;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

public class mytimepicker extends TimePicker {
    // 구분 라인 색상
    private final int m_iColor = 0xFFA3C2FA;

    public mytimepicker(Context context) {
        super(context);
        Create(context, null);
    }

    public mytimepicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        Create(context, attrs);
    }

    public mytimepicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Create(context, attrs);
    }

    private void Create(Context clsContext, AttributeSet attrs) {
        try {
            Class<?> clsParent = Class.forName("com.android.internal.R$id");
            NumberPicker clsAmPm = (NumberPicker) findViewById(clsParent.getField("amPm").getInt(null));
            NumberPicker clsHour = (NumberPicker) findViewById(clsParent.getField("hour").getInt(null));
            NumberPicker clsMin = (NumberPicker) findViewById(clsParent.getField("minute").getInt(null));
            Class<?> clsNumberPicker = Class.forName("android.widget.NumberPicker");
            Field clsSelectionDivider = clsNumberPicker.getDeclaredField("mSelectionDivider");
            clsSelectionDivider.setAccessible(true);
            ColorDrawable clsDrawable = new ColorDrawable(m_iColor);
            setNumberPickerTextColor(clsAmPm);
            setNumberPickerTextColor(clsHour);
            setNumberPickerTextColor(clsMin);
            // 오전/오후, 시, 분 구분 라인의 색상을 변경한다.
            clsSelectionDivider.set(clsAmPm, clsDrawable);
            clsSelectionDivider.set(clsHour, clsDrawable);
            clsSelectionDivider.set(clsMin, clsDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker) throws NoSuchFieldException, IllegalAccessException {

        final int count = numberPicker.getChildCount();
        Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
        selectorWheelPaintField.setAccessible(true);
        ((Paint)selectorWheelPaintField.get(numberPicker)).setTypeface(Typeface.DEFAULT_BOLD);
        ((Paint)selectorWheelPaintField.get(numberPicker)).setTextSize(80);

        for (int i = 0; i < count; i++)
        {
            View child = numberPicker.getChildAt(i);
            ((EditText) child).setTextSize(20);
            ((EditText) child).setTypeface(Typeface.DEFAULT_BOLD);
            numberPicker.invalidate();


        }
        return false;
    }
}
