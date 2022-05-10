package io.github.sgpublic.reactiontime.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseActivity<VB: ViewBinding, VM: ViewModel>: AppCompatActivity() {
    private var _binding: VB? = null
    @Suppress("PropertyName")
    protected val ViewBinding: VB get() = _binding!!
    @Suppress("PropertyName")
    protected abstract val ViewModel: VM

    private var rootViewBottom: Int = 0

    final override fun onCreate(savedInstanceState: Bundle?) {
        beforeCreate()
        onViewModelSetup()
        super.onCreate(savedInstanceState)

        setupContentView()
        onViewSetup()
        onActivityCreated(savedInstanceState)
    }

    protected open fun beforeCreate() { }

    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    private fun setupContentView() {
        _binding = onCreateViewBinding()
        setContentView(ViewBinding.root)
    }

    protected abstract fun onCreateViewBinding(): VB

    protected open fun onViewSetup() { }

    protected open fun onViewModelSetup() { }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}