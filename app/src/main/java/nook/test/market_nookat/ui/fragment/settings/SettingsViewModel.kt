package nook.test.market_nookat.ui.fragment.settings

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel


class SettingsViewModel (private val repository: Repository) : BaseViewModel() {

    fun getAds() = repository.getAds()
}