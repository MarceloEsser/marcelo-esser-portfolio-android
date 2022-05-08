package esser.marcelo.portfolio.line

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import esser.marcelo.portfolio.R
import esser.marcelo.portfolio.adapter.LinesAdapter
import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.databinding.LinesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LinesFragment : BaseFragment<LinesFragmentBinding>(
    R.layout.lines_fragment
) {
    private val viewModel: LinesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lines.observe(requireActivity()) {
            viewBinding.linesActivityRvLines.visibility = VISIBLE
            viewBinding.linesActivityRvLines.adapter = LinesAdapter(it, requireContext())
        }
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

}