package nook.test.market_nookat.ui.fragment.photo

import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class PhotoViewModel(private val repository: Repository) : BaseViewModel() {
    fun getOneAds(int: Int) = repository.oneAds(int)
}