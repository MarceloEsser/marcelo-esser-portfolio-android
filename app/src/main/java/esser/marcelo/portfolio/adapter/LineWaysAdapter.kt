package esser.marcelo.portfolio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.portfolio.commons.base.BaseViewHolder
import esser.marcelo.portfolio.core.model.LineWay
import esser.marcelo.portfolio.databinding.RowLineWayBinding

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class LineWaysAdapter(
    private val context: Context,
    private val clickEvent: (lineWay: LineWay) -> Unit,
) : RecyclerView.Adapter<LineWaysAdapter.LineWaysViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineWaysViewHolder {
        return LineWaysViewHolder(LayoutInflater.from(context), parent)
    }

    override fun onBindViewHolder(holder: LineWaysViewHolder, position: Int) {
        val way = LineWay.availableWays()[position]
        holder.bind(way, clickEvent)
    }

    override fun getItemCount(): Int {
        return LineWay.availableWays().size
    }

    class LineWaysViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        BaseViewHolder<RowLineWayBinding>(
            binding = RowLineWayBinding.inflate(inflater, parent, false)
        ) {
        fun bind(lineWay: LineWay, clickEvent: (lineWay: LineWay) -> Unit) {
            binding.root.setOnClickListener {
                clickEvent.invoke(lineWay)
            }
            binding.lineWay = lineWay.description
            binding.executePendingBindings()
        }
    }
}