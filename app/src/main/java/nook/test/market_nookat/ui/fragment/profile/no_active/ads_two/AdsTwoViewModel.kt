package nook.test.market_nookat.ui.fragment.profile.no_active.ads_two

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel


class AdsTwoViewModel (private val repository: Repository): BaseViewModel() {

    fun getOneAds(int: Int) = repository.oneAds(int)

}