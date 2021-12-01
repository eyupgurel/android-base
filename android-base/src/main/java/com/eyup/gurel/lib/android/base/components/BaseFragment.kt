package com.eyup.gurel.lib.android.base.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.eyup.gurel.lib.android.base.lifecycle.ScreenLifecycleTask
import com.eyup.gurel.lib.dagger2.di.ForScreen
import com.eyup.gurel.lib.dagger2.di.Injector
import com.eyup.gurel.lib.dagger2.lifecycle.DisposableManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    protected var  screenLifecycleTasks:  Set<@JvmSuppressWildcards ScreenLifecycleTask>? = null
        @Inject set(value) {
            field = value
        }
    private val disposables = CompositeDisposable()
    @Inject
    @ForScreen  protected lateinit var disposableManager: DisposableManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutRes(), container, false)
        if (arguments == null || !requireArguments().containsKey("instance_id")) {
            val bundle = Bundle()
            bundle.putString("instance_id", UUID.randomUUID().toString())
            if (arguments != null)
                bundle.putAll(arguments)
            arguments = bundle
        }
        Injector.inject(this)
        screenLifecycleTasks?.forEach{it.onEnterScope(view)}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposables.addAll(*subscriptions())
    }

    override fun onDestroyView() {
        disposables.clear()
        screenLifecycleTasks?.forEach{it.onExitScope()}
        super.onDestroyView()
    }

    override fun onDestroy() {
        screenLifecycleTasks?.forEach{
            it.onDestroy()
        }
        if (!requireActivity().isChangingConfigurations) {
            Injector.clearComponent(this)
        }
        super.onDestroy()
    }

    protected abstract fun subscriptions(): Array<Disposable>

    @LayoutRes
    protected abstract fun layoutRes(): Int

    companion object {
        fun<T: Fragment> newInstance(factory: () -> T): Fragment {
            val bundle = Bundle()
            bundle.putString("instance_id", UUID.randomUUID().toString())
            val fragment: T = factory()
            fragment.arguments = bundle
            return fragment
        }
    }
}