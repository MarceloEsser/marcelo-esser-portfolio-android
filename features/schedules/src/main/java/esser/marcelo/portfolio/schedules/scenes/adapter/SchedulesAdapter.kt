package esser.marcelo.portfolio.schedules.scenes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.portfolio.commons.base.BaseViewHolder
import esser.marcelo.portfolio.schedules.databinding.RowScheduleBinding

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class SchedulesAdapter(
    private val context: Context,
    private val schedules: List<BaseSchedule>
) : RecyclerView.Adapter<SchedulesAdapter.SchedulesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SchedulesViewHolder {
        return SchedulesViewHolder(LayoutInflater.from(context), parent)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(viewHolder: SchedulesViewHolder, position: Int) {
        val schedule = schedules[position]
        viewHolder.bind(schedule)
    }

    override fun getItemViewType(position: Int): Int {
        if (schedules[position].isAccessible())
            return 1
        return 0
    }


    class SchedulesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        BaseViewHolder<RowScheduleBinding>(
            binding = RowScheduleBinding.inflate(inflater, parent, false)
        ) {
        fun bind(schedule: BaseSchedule) {
            binding.hour = schedule.hour
            binding.isAccessible = schedule.isAccessible()
            binding.executePendingBindings()
        }
    }
}