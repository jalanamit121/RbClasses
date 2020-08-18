package com.winbee.rbclasses.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.winbee.rbclasses.FragmentNotes;
import com.winbee.rbclasses.LiveFragment;

public class ViewPagerLiveAdapter extends FragmentStatePagerAdapter {
  //    String[] tabarray = new String[]{"Home","Live Classes"};
//    Integer tabnumber = 2;
  int mNumOfTabs;

  public ViewPagerLiveAdapter(FragmentManager fm, int NumOfTabs) {
    super(fm);
    this.mNumOfTabs=NumOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        LiveFragment one = new LiveFragment();
        return one;
      case 1:
        FragmentNotes two = new FragmentNotes();
        return two;

    }
    return null;
  }

  @Override
  public int getCount() {
    return mNumOfTabs;
  }
}
