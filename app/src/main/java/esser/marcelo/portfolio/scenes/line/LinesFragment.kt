package esser.marcelo.portfolio.scenes.line

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import esser.marcelo.portfolio.R
import esser.marcelo.portfolio.adapter.LineWaysAdapter
import esser.marcelo.portfolio.adapter.LinesAdapter
import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.core.model.busLine.BaseLine
import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.databinding.LinesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LinesFragment : BaseFragment<LinesFragmentBinding>(
    R.layout.lines_fragment
) {
    private val viewModel: LinesViewModel by viewModel()
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lines.observe(requireActivity()) {
            linesAdapterConfig(it)
        }
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

    }

    private fun linesAdapterConfig(it: List<BusLine>) {
        viewBinding.linesActivityRvLines.adapter = LinesAdapter(
            it, requireContext(),
            lineClickEvent()
        )
    }

    private fun lineClickEvent() = { line: BaseLine ->
        viewModel.line = line

        bottomSheetBehavior = BottomSheetBehavior.from<View>(viewBinding.bottomSheet)

        viewBinding.bottomSheetBg.visibility = VISIBLE

        if (bottomSheetBehavior != null) {
            configureBottomSheet(bottomSheetBehavior!!)

            viewBinding.rvWays.adapter = LineWaysAdapter(requireContext()) { lineWay ->
                val bundle = bundleOf(
                    "lineWay" to lineWay.code,
                    "lineName" to line.name,
                    "lineCode" to line.code
                )
                findNavController().navigate(
                    R.id.action_lines_fragment_to_schedules_fragment,
                    bundle
                )
            }

            bottomSheetBehavior?.peekHeight =
                viewBinding.bottomSheet.height - viewBinding.bottomSheetContent.height + 100
        }

    }

    private fun configureBottomSheet(bottomSheetBehavior: BottomSheetBehavior<*>) {
        viewBinding.bottomSheetBg.setOnClickListener {
            hideBottomSheet()
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    hideBottomSheet()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset == 0f) {
                    hideBottomSheet()
                }
            }
        })

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        if (bottomSheetBehavior == null) return
        bottomSheetBehavior?.peekHeight = 0
        viewBinding.bottomSheetBg.visibility = View.GONE
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onPause() {
        super.onPause()
        hideBottomSheet()
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }
}