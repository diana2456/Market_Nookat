package nook.test.market_nookat.ui.fragment.profile.active.ads_active
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class AdsActiveViewModel  (private val repository: Repository): BaseViewModel() {

    fun getOneAds(int: Int) = repository.oneAds(int)

}