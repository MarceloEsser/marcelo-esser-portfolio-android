package esser.marcelo.portfolio.schedules.scenes.schedule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.schedules.R
import esser.marcelo.portfolio.schedules.adapter.SchedulesAdapter
import esser.marcelo.portfolio.schedules.databinding.SchedulesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SchedulesFragment : BaseFragment<SchedulesFragmentBinding>(R.layout.schedules_fragment) {
    private val viewModel: SchedulesViewModel by viewModel()
    val args: SchedulesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lineCode = args.lineCode
        viewModel.lineName = args.lineName
        viewModel.lineWay = args.lineWay

        viewModel.schedule.observe(requireActivity()) { lineSchedules ->
            val workingDaySchedule = lineSchedules.workingDays ?: listOf()
            viewBinding.schedulesActivityRvSchedules.adapter =
                SchedulesAdapter(requireCompatActivity(), workingDaySchedule)
        }

        viewModel.error.observe(
            requireCompatActivity()
        ) { error -> Toast.makeText(requireCompatActivity(), error, Toast.LENGTH_LONG).show() }

    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

}