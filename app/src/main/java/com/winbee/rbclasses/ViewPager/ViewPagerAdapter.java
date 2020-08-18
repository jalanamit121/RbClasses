package com.winbee.rbclasses.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.winbee.rbclasses.CourseFragment;
import com.winbee.rbclasses.HomeFragment;
import com.winbee.rbclasses.LiveFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
  //    String[] tabarray = new String[]{"Home","Live Classes"};
//    Integer tabnumber = 2;
  int mNumOfTabs;

  public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
    super(fm);
    this.mNumOfTabs=NumOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        HomeFragment one = new HomeFragment();
        return one;
      case 1:
        CourseFragment two = new CourseFragment();
        return two;

    }
    return null;
  }

  @Override
  public int getCount() {
    return mNumOfTabs;
  }
}
