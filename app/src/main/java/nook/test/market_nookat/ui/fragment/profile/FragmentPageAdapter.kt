package nook.test.market_nookat.ui.fragment.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import nook.test.market_nookat.ui.fragment.profile.active.ActiveFragment
import nook.test.market_nookat.ui.fragment.profile.no_active.NoActiveFragment


class FragmentPageAdapter(fragment: FragmentActivity, ) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            ActiveFragment()
        } else {
            NoActiveFragment()
        }
    }
}