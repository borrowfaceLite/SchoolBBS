package com.hwmlygr.ground.schoolbbs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vp_guide;
    private Fragment[] mGuideFragments;
    private ImageView iv_indicator;
    private LinearLayout ll_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //隐藏状态栏
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getWindow().setAttributes(lp);

        vp_guide = findViewById(R.id.vp_guide);
        ll_indicator = findViewById(R.id.ll_guide_indicator);

        final int[] imageResoure = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3,R.drawable.guide_4};
        mGuideFragments = new Fragment[4];
        for (int i = 0;i < 4;i++){
            GuideFragment guideFragment = new GuideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imageResource",imageResoure[i]);
            bundle.putBoolean("isLast",i == mGuideFragments.length - 1);
            guideFragment.setArguments(bundle);
            mGuideFragments[i] = guideFragment;
        }
        setCurrentIndicator(0,true);//设置指示器
        vp_guide.setAdapter(new GuidePagerAdapter(getSupportFragmentManager()));
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position,position != mGuideFragments.length - 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setCurrentIndicator(int position,boolean isShow){
        ll_indicator.removeAllViews();
        for (int i = 0;i < mGuideFragments.length;i++){
            ImageView imageView = new ImageView(getApplicationContext());
            if(position == i) {
                imageView.setImageResource(R.drawable.blue_point);
            }
            else {
                imageView.setImageResource(R.drawable.black_point);
            }
            imageView.setPadding(10,0,10,0);
            ll_indicator.addView(imageView);
        }
        ll_indicator.setVisibility(isShow?View.VISIBLE: View.GONE);
    }
    private class GuidePagerAdapter extends FragmentPagerAdapter{

        public GuidePagerAdapter(FragmentManager fm) {
                super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mGuideFragments[position];
        }

        @Override
        public int getCount() {
            return mGuideFragments.length;
        }

    }
}
