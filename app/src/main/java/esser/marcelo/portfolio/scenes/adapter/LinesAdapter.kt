package esser.marcelo.portfolio.scenes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.portfolio.commons.base.BaseViewHolder
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.databinding.RowLineBinding

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class LinesAdapter(
    private val lines: List<BusLine>,
    private val context: Context,
    private val clickEvent: (line: BusLine) -> Unit
) : RecyclerView.Adapter<LinesAdapter.LinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesViewHolder {
        return LinesViewHolder(LayoutInflater.from(context), parent)
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    override fun onBindViewHolder(viewHolder: LinesViewHolder, position: Int) {
        val line = lines[position]
        viewHolder.bind(line, clickEvent)
    }

    class LinesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        BaseViewHolder<RowLineBinding>(
            binding = RowLineBinding.inflate(inflater, parent, false)
        ) {
        fun bind(line: BusLine, clickEvent: (line: BusLine) -> Unit) {
            binding.root.setOnClickListener {
                clickEvent.invoke(line)
            }

            binding.line = line
            binding.executePendingBindings()
        }
    }
}
