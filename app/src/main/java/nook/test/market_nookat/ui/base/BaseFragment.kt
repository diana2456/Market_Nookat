package nook.test.market_nookat.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel, DB : ViewBinding> : Fragment() {
    protected abstract val viewModel: VM
    protected lateinit var binding: DB

    protected abstract fun inflateViewBinding(inflater: LayoutInflater,container: ViewGroup?,boolean: Boolean): DB


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflateViewBinding(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    abstract fun initFragment()
    abstract fun initResume()
    abstract fun initPause()
    abstract fun initDestroy()
}