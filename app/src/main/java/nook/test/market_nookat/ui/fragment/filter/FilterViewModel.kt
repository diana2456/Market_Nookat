package nook.test.market_nookat.ui.fragment.filter

import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class FilterViewModel (private val repository: Repository) : BaseViewModel() {

    fun getCategory() = repository.getCategory()

}