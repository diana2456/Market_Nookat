package nook.test.market_nookat.ui.fragment.settings.ads.notifi

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class AdsFireViewModel (private val repository: Repository): BaseViewModel() {

    fun getOneAds(int: Int) = repository.oneAds(int)

    fun searchAds(title: String) = repository.searchAds(title)

}