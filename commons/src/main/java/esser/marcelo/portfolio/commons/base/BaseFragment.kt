package esser.marcelo.portfolio.commons.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import esser.marcelo.portfolio.commons.base.widgets.LoaderDialog
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.qualifier
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.reflect.KClass

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes
    private val layoutId: Int
) : Fragment() {

    private val loader: LoaderDialog = LoaderDialog()
    private var isShowingLoader = false
    lateinit var viewBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()
    }

    fun requireCompatActivity(): AppCompatActivity {
        requireActivity()
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            return activity
        } else {
            throw TypeCastException("Main activity should extend from 'AppCompatActivity'")
        }
    }

    fun showSnackBar(message: String) {
        Snackbar.make(
            requireCompatActivity().findViewById(android.R.id.content), message,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.BLACK).setTextColor(Color.WHITE).show()
    }

    fun showLoader() {
        if (!loader.isAdded) {
            loader.show(requireCompatActivity().supportFragmentManager, "loaderTag")
        }
    }

    fun hideLoader() {
        if (loader.isAdded) {
            loader.dismiss()
        }
    }

    abstract fun onInitDataBinding()
}