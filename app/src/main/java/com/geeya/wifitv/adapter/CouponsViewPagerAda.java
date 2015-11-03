package com.geeya.wifitv.adapter;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.ui.fragment.CashCouponsFragment;
import com.geeya.wifitv.ui.fragment.DiscountCouponsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CouponsViewPagerAda extends FragmentPagerAdapter {

    public CouponsViewPagerAda(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return AppConfig.COUNPONS_TITLES[position];
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return AppConfig.COUNPONS_TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new CashCouponsFragment();
        else {
            return new DiscountCouponsFragment();
        }
    }
}
