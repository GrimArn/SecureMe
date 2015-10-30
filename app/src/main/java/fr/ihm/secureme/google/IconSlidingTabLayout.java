package fr.ihm.secureme.google;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by nonau on 21/10/15.
 */
public class IconSlidingTabLayout extends SlidingTabLayout {
    public IconSlidingTabLayout(Context context) {
        super(context);
    }

    public IconSlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconSlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new SlidingTabStrip(context);
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }
}
