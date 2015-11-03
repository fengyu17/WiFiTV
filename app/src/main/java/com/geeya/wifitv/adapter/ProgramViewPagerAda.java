/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-31 上午10:38:40
 * 
 */
package com.geeya.wifitv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.ui.fragment.ProgramCategoryFragment;

/**
 * @author Administrator
 *
 */
public class ProgramViewPagerAda extends FragmentPagerAdapter {
	
	/**
	 * @param fm
	 */
	public ProgramViewPagerAda(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return AppConfig.PROGRAM_TITLES[position];
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return ProgramCategoryFragment.getInstance(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return AppConfig.PROGRAM_TITLES.length;
	}

	

}
