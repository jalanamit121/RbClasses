package com.winbee.rbclasses.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.winbee.rbclasses.FragmentNotes;
import com.winbee.rbclasses.FragmentNotesPurchased;
import com.winbee.rbclasses.LiveFragment;
import com.winbee.rbclasses.LivePurchasedFragment;

public class ViewPagerLivePurchasedAdapter extends FragmentStatePagerAdapter {
  //    String[] tabarray = new String[]{"Home","Live Classes"};
//    Integer tabnumber = 2;
  int mNumOfTabs;

  public ViewPagerLivePurchasedAdapter(FragmentManager fm, int NumOfTabs) {
    super(fm);
    this.mNumOfTabs=NumOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        LivePurchasedFragment one = new LivePurchasedFragment();
        return one;
      case 1:
        FragmentNotesPurchased two = new FragmentNotesPurchased();
        return two;

    }
    return null;
  }

  @Override
  public int getCount() {
    return mNumOfTabs;
  }
}
