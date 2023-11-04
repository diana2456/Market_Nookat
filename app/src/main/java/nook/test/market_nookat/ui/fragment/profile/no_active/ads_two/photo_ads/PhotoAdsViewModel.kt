package nook.test.market_nookat.ui.fragment.profile.no_active.ads_two.photo_ads

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel


class PhotoAdsViewModel(private val repository: Repository) : BaseViewModel() {
    fun getOneAds(int: Int) = repository.oneAds(int)
}