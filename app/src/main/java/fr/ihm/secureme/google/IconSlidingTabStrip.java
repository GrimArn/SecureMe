package fr.ihm.secureme.google;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nonau on 20/10/15.
 */
public class IconSlidingTabStrip extends SlidingTabStrip {

    private static final String TAG = "SlidingTabStrip";
    private int[] imageResId = {
            fr.ihm.secureme.R.drawable.ic_error_outline_white_24dp,
            fr.ihm.secureme.R.drawable.ic_group_white_24dp,
            fr.ihm.secureme.R.drawable.ic_settings_white_24dp
    };

    private int[] colortabs = {
            fr.ihm.secureme.R.color.colortab0,
            fr.ihm.secureme.R.color.colortab1,
            fr.ihm.secureme.R.color.colortab1,
            fr.ihm.secureme.R.color.colortab3,
            fr.ihm.secureme.R.color.colortab3,
            fr.ihm.secureme.R.color.colortab5,
            fr.ihm.secureme.R.color.colortab5,
            fr.ihm.secureme.R.color.colortab7,
            fr.ihm.secureme.R.color.colortab7,
            fr.ihm.secureme.R.color.colortab9,
            fr.ihm.secureme.R.color.colortab9,
    };

    private CharSequence mIcon;

    IconSlidingTabStrip(Context context) {
        super(context);
    }

    IconSlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onViewPagerPageChanged(int position, float positionOffset) {
       super.onViewPagerPageChanged(position, positionOffset);
        TextView tv0 = (TextView) getChildAt(0);
        TextView tv1 = (TextView) getChildAt(1);
        TextView tv2 = (TextView) getChildAt(2);
        if (positionOffset < 0.3f) {
            switch (position) {
                case 0:
                    tv1.setText(getIcon(0, 1));
                    tv2.setText(getIcon(0, 2));
                    break;
                case 2:
                    tv0.setText(getIcon(0, 0));
                    tv1.setText(getIcon(0, 1));
                    break;
                case 1:
                    tv0.setText(getIcon(0, 0));
                    tv2.setText(getIcon(0, 2));
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int childCount = getChildCount();
        final SlidingTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null
                ? mCustomTabColorizer
                : mDefaultTabColorizer;

        // Thick colored underline below the current selection
        if (childCount > 0) {
            View selectedTitle = getChildAt(mSelectedPosition);
            TextView tv = (TextView) selectedTitle;
            tv.setText(getIcon(10, mSelectedPosition));
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
            int color = tabColorizer.getIndicatorColor(mSelectedPosition);

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset);
                }

                // Draw the selection partway between the tabs
                View nextTitle = getChildAt(mSelectedPosition + 1);
                TextView tv1 = (TextView) (nextTitle);
                tv1.setText(getIcon((int) (mSelectionOffset * 10), mSelectedPosition +1 ));
                tv.setText(getIcon((int) (10 - (mSelectionOffset * 10)), mSelectedPosition ));
                left = (int) (mSelectionOffset * nextTitle.getLeft() +
                        (1.0f - mSelectionOffset) * left);
                right = (int) (mSelectionOffset * nextTitle.getRight() +
                        (1.0f - mSelectionOffset) * right);
            }

            mSelectedIndicatorPaint.setColor(color);

//            tv.setText(getIcon(0, mSelectedPosition));
            /*canvas.drawRect(left, height - mSelectedIndicatorThickness, right,
                    height, mSelectedIndicatorPaint);*/
        }

        // Thin underline along the entire bottom edge
        canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
    }

    public CharSequence getIcon(int offset, int icon) {
        Drawable image = getResources().getDrawable(imageResId[icon]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        image.setColorFilter(getResources().getColor(colortabs[offset]), PorterDuff.Mode.MULTIPLY);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }


}
