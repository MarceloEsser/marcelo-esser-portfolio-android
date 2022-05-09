package esser.marcelo.portfolio.schedules.scenes.schedule

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.core.model.Schedule
import esser.marcelo.portfolio.schedules.R
import esser.marcelo.portfolio.schedules.adapter.SchedulesAdapter
import esser.marcelo.portfolio.schedules.databinding.SchedulesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SchedulesFragment : BaseFragment<SchedulesFragmentBinding>(R.layout.schedules_fragment) {
    private val viewModel: SchedulesViewModel by viewModel()
    private val args: SchedulesFragmentArgs by navArgs()
    private lateinit var adapter: SchedulesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.line = args.line
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.schedule.observe(requireActivity()) { configureShowingData(it.workingDays) }

        viewModel.error.observe(requireCompatActivity()) { message ->
            Toast.makeText(requireCompatActivity(), message, Toast.LENGTH_LONG).show()
        }

        configureNavigationListener()
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.hasSchedule = true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun configureShowingData(schedule: List<Schedule>?) {
        viewBinding.hasSchedule = !schedule.isNullOrEmpty()

        schedule?.let {
            adapter = SchedulesAdapter(requireCompatActivity(), schedule)
            viewBinding.schedulesActivityRvSchedules.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun configureNavigationListener() {
        viewBinding.schedulesBottomNavigation.setOnItemSelectedListener { menuItem ->
            val mSchedule: List<Schedule>? = viewModel.listMap[menuItem.itemId]
            configureShowingData(mSchedule)
            return@setOnItemSelectedListener true
        }
    }

}