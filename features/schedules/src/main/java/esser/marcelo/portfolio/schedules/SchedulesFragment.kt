package esser.marcelo.portfolio.schedules

import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.schedules.databinding.SchedulesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SchedulesFragment : BaseFragment<SchedulesFragmentBinding>(R.layout.schedules_fragment) {
    private val viewModel: SchedulesViewModel by viewModel()

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

}