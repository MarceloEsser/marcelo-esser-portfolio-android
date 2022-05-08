package esser.marcelo.portfolio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.portfolio.commons.base.BaseViewHolder
import esser.marcelo.portfolio.core.model.busLine.BaseLine
import esser.marcelo.portfolio.databinding.RowLineBinding

class LinesAdapter(
    private val lines: List<BaseLine>,
    private val context: Context,
) : RecyclerView.Adapter<LinesAdapter.LinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesViewHolder {
        return LinesViewHolder(LayoutInflater.from(context), parent)
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    override fun onBindViewHolder(viewHolder: LinesViewHolder, position: Int) {
        val line = lines[position]
        viewHolder.bind(line)
    }

    class LinesViewHolder(inflater: LayoutInflater, parent: ViewGroup) : BaseViewHolder<RowLineBinding>(
        binding = RowLineBinding.inflate(inflater, parent, false)
    ) {
        fun bind(line: BaseLine) {
            binding.line = line
            binding.executePendingBindings()
        }
    }
}
