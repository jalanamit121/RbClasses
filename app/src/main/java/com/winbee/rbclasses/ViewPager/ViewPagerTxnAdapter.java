package com.winbee.rbclasses.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.winbee.rbclasses.FragmentNotesPurchased;
import com.winbee.rbclasses.LivePurchasedFragment;
import com.winbee.rbclasses.MyClassFragment;
import com.winbee.rbclasses.TxnFragment;
import com.winbee.rbclasses.YouTubeVideoList;

public class ViewPagerTxnAdapter extends FragmentStatePagerAdapter {
  //    String[] tabarray = new String[]{"Home","Live Classes"};
//    Integer tabnumber = 2;
  int mNumOfTabs;

  public ViewPagerTxnAdapter(FragmentManager fm, int NumOfTabs) {
    super(fm);
    this.mNumOfTabs=NumOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        TxnFragment one = new TxnFragment();
        return one;
//            case 1:
//                TxnFragment two = new TxnFragment();
//                return two;

    }
    return null;
  }

  @Override
  public int getCount() {
    return mNumOfTabs;
  }
}

