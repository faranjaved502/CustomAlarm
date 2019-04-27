package dev.faran.myalarm;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import java.util.List;

import dev.faran.myalarm.R;

/**
 * Created by fjaved on 5/4/2018.
 */

public class GeneralAdapter extends ArrayAdapter<String> {
    private String mHintValue;
    public List<String> mList;
    private int mHintTextColor;
    private int mTextColor;
    private float mTextSize;
    public GeneralAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public GeneralAdapter(Context context, int resource, List<String> objects, String hintValue, int hintTextColor, int textColor, float textSize){
        super(context, android.R.layout.simple_spinner_dropdown_item, objects);
        if(textSize == 10)
//            objects.add(hintValue);

        hintTextColor = context.getResources().getColor(R.color.light_gray);
        textColor =  context.getResources().getColor(R.color.black);
        mHintValue = hintValue;
        mList = objects;
        mHintTextColor = hintTextColor;
        mTextColor = textColor;
        mTextSize = textSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  super.getView(position, convertView, parent);
        CheckedTextView v = (CheckedTextView) view;
        v.setTextColor(mTextColor);
        v.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        v.setTextSize(14);
        if (mTextSize == 10)
            if (position == 0) {
                v.setTextColor(mHintTextColor);
            }

        return view;
    }

    @Override
    public int getCount() {
        if(mTextSize == 10) {
            int count = super.getCount();
            /*if (count == 0) {
                return count;
            } else {
                return count - 2;
            }*/
        }
        return super.getCount();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view =  super.getDropDownView(position, convertView, parent);
        CheckedTextView tv = (CheckedTextView)view;
        tv.setBackgroundColor(getContext().getResources().getColor(R.color.theme_gray));
        tv.setTextColor(mTextColor);
        tv.setTextSize(14);
        tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        return view;
    }
}
