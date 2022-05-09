package esser.marcelo.portfolio.schedules.scenes.schedule

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.core.model.busSchedule.BaseSchedule
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
        viewModel.line = args.line

        viewModel.schedule.observe(requireActivity()) { lineSchedules ->
            val workingDaySchedule = lineSchedules.workingDays ?: listOf()
            adapter = SchedulesAdapter(requireCompatActivity(), workingDaySchedule)
            viewBinding.schedulesActivityRvSchedules.adapter = adapter
            bottomNavigationBarListener()
        }

        viewModel.error.observe(
            requireCompatActivity()
        ) { error -> Toast.makeText(requireCompatActivity(), error, Toast.LENGTH_LONG).show() }

    }

    private fun bottomNavigationBarListener() {
        viewModel.schedule.value?.let { lineSchedules ->
            viewBinding.schedulesBottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_workingdays -> {
                        configureList(lineSchedules.workingDays)
                        true
                    }
                    R.id.action_saturday -> {
                        configureList(lineSchedules.saturdays)
                        true
                    }
                    R.id.action_sunday -> {
                        configureList(lineSchedules.sundays)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun configureList(schedule: List<BaseSchedule>?) {
        if (schedule.isNullOrEmpty()) {
            viewBinding.tvWithoutItems.visibility = View.VISIBLE
            viewBinding.schedulesActivityRvSchedules.visibility = GONE
            return
        }
        viewBinding.tvWithoutItems.visibility = GONE
        viewBinding.schedulesActivityRvSchedules.visibility = View.VISIBLE
        schedule.let {
            if (it.isNotEmpty()) {
                adapter = SchedulesAdapter(requireCompatActivity(), schedule)
                adapter.notifyDataSetChanged()
                viewBinding.schedulesActivityRvSchedules.adapter = adapter
                viewBinding.schedulesActivityRvSchedules.visibility = View.VISIBLE
            } else {
                viewBinding.schedulesActivityRvSchedules.visibility = View.INVISIBLE
            }
        }
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

}