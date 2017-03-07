package com.flyingfish.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.flyingfish.R;
import com.flyingfish.fragment.Main_Fragment;
import com.flyingfish.fragment.SendServiceMessage_Fragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/10 0010.
 */
public class Page_Activity extends FragmentActivity {
     ViewPager viewpagae;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage_activity);
         viewpagae = (ViewPager)findViewById(R.id.viewpagae);
        init();
    }
    private void init() {
        mFragments.add(new SendServiceMessage_Fragment());
        mFragments.add(new Main_Fragment());


         mAdapter =  new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                fragment = mFragments.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + position);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
                return fragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                Fragment fragment = mFragments.get(position);
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            }
        };
        viewpagae.setAdapter(mAdapter);
        viewpagae.setCurrentItem(1);

    }
}
