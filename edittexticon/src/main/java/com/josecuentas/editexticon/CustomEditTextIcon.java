package com.josecuentas.editexticon;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.osp.projects.edittexticon.R;

/**
 * Created by jcuentas on 4/04/17.
 */

public class CustomEditTextIcon extends RelativeLayout
        implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "CustomEditTextIcon";
    private RelativeLayout mRlaContainer;
    private TextView mTextView;
    private EditText mEditText;
    private ImageView mImageView;

    private String mTextHint;
    private int mSrc;
    private int mTextHintColor;
    private int mTextColor;
    private int mInputType;
    private float mTextSize; // pixels
    private float mTextHintSize; //pixels

    public CustomEditTextIcon(Context context) {
        super(context);
        init(context);
    }

    public CustomEditTextIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setupAttributeSet(context, attrs);
        ui();
    }

    public CustomEditTextIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setupAttributeSet(context, attrs);
        ui();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomEditTextIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        setupAttributeSet(context, attrs);
        ui();
    }

    private void init(Context context) {
        //View view = LayoutInflater.from(context).inflate(R.layout.custom_edittext_icon, this, true);
        inflate(context, R.layout.custom_edittext_icon, this);
        mRlaContainer = (RelativeLayout) findViewById(R.id.rlaContainer);
        mTextView = (TextView) findViewById(R.id.textView);
        mEditText = (EditText) findViewById(R.id.editText);
        mImageView = (ImageView) findViewById(R.id.imageView);
        events();
    }

    private void setupAttributeSet(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomEditTextIcon, 0, 0);
        try {
            mTextSize = a.getDimensionPixelSize(R.styleable.CustomEditTextIcon_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
            mTextHintSize = a.getDimensionPixelSize(R.styleable.CustomEditTextIcon_textHintSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            Log.i(TAG, "setupAttributeSet: mTextSize: " + mTextSize);
            mTextHint = (String) a.getText(R.styleable.CustomEditTextIcon_textHint);
            mTextHintColor = a.getColor(R.styleable.CustomEditTextIcon_textHintColor, 0xff000000);
            mTextColor = a.getColor(R.styleable.CustomEditTextIcon_textColor, 0xff000000);
            mInputType = a.getInt(R.styleable.CustomEditTextIcon_inputType, EditorInfo.TYPE_CLASS_TEXT);

            //ImageView src
            TypedValue typedValue = new TypedValue();
            a.getValue(R.styleable.CustomEditTextIcon_src, typedValue);
            mSrc = typedValue.resourceId;
        } finally {
            a.recycle();
        }
    }

    private void ui() {
        if (isInEditMode()) {
            mImageView.setVisibility(View.GONE);
            mRlaContainer.setVisibility(View.VISIBLE);
        } else {
            mImageView.setVisibility(View.VISIBLE);
            mRlaContainer.setVisibility(View.INVISIBLE);
        }
        setTextHint(mTextHint);
        setTextSize(mTextSize);
        setIcon(mSrc);
        setTextHintColor(mTextHintColor);
        setInputType(mInputType);
        setTextHintSize(mTextHintSize);
        setTextColor(mTextColor);
    }

    private void events() {
        mImageView.setOnClickListener(this);
        mEditText.setOnFocusChangeListener(this);
        OnTouchListener exitSoftKeyBoard = new OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                /*InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                imm.showSoftInputFromInputMethod(v.getWindowToken(), InputMethodManager.RESULT_SHOWN);
                if(v.equals(mEditText)){
                    mEditText.requestFocus();
                    //relLayKeyboard.setVisibility(View.VISIBLE);
                }*/

                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
                return true;
            }
        };
//        mImageView.setOnTouchListener(exitSoftKeyBoard);
    }

    @Override public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.imageView) {
            mImageView.setVisibility(View.INVISIBLE);
            mRlaContainer.setVisibility(View.VISIBLE);
            mEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
        } else if (i == R.id.textView) {

        } else if (i == R.id.editText) {

        }
    }

    public RelativeLayout getRlaContainer() {
        return mRlaContainer;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setTextSize(int size) {
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
        mEditText.setTextSize(mTextSize);
        invalidate();
        requestLayout();
    }

    public void setTextSize(float size) {
        Log.i(TAG, "setTextSize: mTextSize: " + size);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        invalidate();
        requestLayout();
    }

    public void setTextHint(String textHint) {
        mTextHint = textHint;
        mTextView.setText(mTextHint);
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public void setIcon(int resource) {
        mSrc = resource;
        mImageView.setImageResource(mSrc);
    }

    public void setTextHintColor(int textHintColor) {
        mTextView.setTextColor(textHintColor);
    }

    public void setInputType(int inputType) {
        mEditText.setInputType(inputType);
    }

    public void setTextHintSize(int size) {
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
        mEditText.setTextSize(mTextSize);
        invalidate();
        requestLayout();
    }

    public void setTextHintSize(float size) {
        Log.i(TAG, "setTextHintSize: mTextSize: " + size);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        invalidate();
        requestLayout();
    }

    public void setTextColor(int textColor) {
        mEditText.setTextColor(textColor);
    }

    @Override public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if (mEditText.getText().toString().isEmpty()) {
                mImageView.setVisibility(View.VISIBLE);
                mRlaContainer.setVisibility(View.INVISIBLE);
            }
        }
    }

    /*@Override public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG, "onFocusChange() called with: v = [" + v + "], hasFocus = [" + hasFocus + "]");
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus) {
            //imm.showSoftInput(v, 0);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }*/
}
