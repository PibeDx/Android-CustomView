package com.josecuentas.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by iPibeDx on 5/04/17.
 */

public class StatusBarView extends RelativeLayout {

  private View mKitkat;
  private int mBackground;

  public StatusBarView(Context context) {
    super(context);
    init();
  }

  public StatusBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
    setupAttributeSet(attrs);
  }

  public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
    setupAttributeSet(attrs);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
    setupAttributeSet(attrs);
  }

  protected void init() {
    inflate(getContext(), R.layout.status_bar, this);
    mKitkat = findViewById(R.id.vKitkat);
  }



  private void setupAttributeSet(AttributeSet attrs) {
    TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.StatusBarView, 0, 0);
    try {
      mBackground = a.getColor(R.styleable.StatusBarView_bg_background, 0xff000000);
    } finally {
      a.recycle();
    }
    ui();
  }

  private void ui() {
    mKitkat.setBackgroundColor(mBackground);
  }
}
