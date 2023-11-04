package nook.test.market_nookat.ui.fragment.settings.ads.photo

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class PhotoNotifiViewModel(private val repository: Repository) : BaseViewModel() {
    fun getOneAds(int: Int) = repository.oneAds(int)
}