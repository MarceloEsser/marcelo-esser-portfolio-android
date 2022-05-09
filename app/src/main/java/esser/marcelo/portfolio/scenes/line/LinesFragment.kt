package esser.marcelo.portfolio.scenes.line

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import esser.marcelo.portfolio.R
import esser.marcelo.portfolio.adapter.LineWaysAdapter
import esser.marcelo.portfolio.adapter.LinesAdapter
import esser.marcelo.portfolio.commons.base.BaseFragment
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.databinding.LinesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LinesFragment : BaseFragment<LinesFragmentBinding>(
    R.layout.lines_fragment
) {

    private val viewModel: LinesViewModel by viewModel()
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    private val wayAdapter by lazy {
        LineWaysAdapter(requireContext()) { lineWay ->
            viewModel.line?.let {
                it.way = lineWay
                findNavController().navigate(
                    LinesFragmentDirections.actionLinesFragmentToSchedulesFragment(it)
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lines.observe(requireActivity()) {
            viewBinding.linesActivityRvLines.adapter = LinesAdapter(
                it, requireContext(), lineClickEvent()
            )
        }

        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

    }

    private fun lineClickEvent(): (BusLine) -> Unit = { line: BusLine ->
        viewModel.line = line

        bottomSheetBehavior = BottomSheetBehavior.from<View>(viewBinding.bottomSheet)

        viewBinding.bottomSheetBg.visibility = VISIBLE

        bottomSheetBehavior?.let { bottomSheet ->
            configureBottomSheetCallback(bottomSheet)

            bottomSheet.peekHeight =
                viewBinding.bottomSheet.height - viewBinding.bottomSheetContent.height + 100
        }

    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.activityLinesEtSearch.addTextChangedListener(searchWatcher())

        viewBinding.rvWays.adapter = wayAdapter

    }

    override fun onPause() {
        super.onPause()
        hideBottomSheet()
    }

    override fun onResume() {
        super.onResume()
        hideBottomSheet()
    }

    fun hideBottomSheet() {
        if (bottomSheetBehavior == null) return
        bottomSheetBehavior?.peekHeight = 0
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun configureBottomSheetCallback(bottomSheetBehavior: BottomSheetBehavior<*>) {
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


    private fun searchWatcher() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            viewModel.filterBy(
                viewBinding.activityLinesEtSearch.text.toString()
            )
            searchAnimationControl()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    private fun searchAnimationControl() {
        if (viewBinding.activityLinesEtSearch.text.isNotEmpty() && viewBinding.lavCancelSearchAction.progress == 0f) {
            val animate = ValueAnimator.ofFloat(0f, 0.5f)

            animate.addUpdateListener {
                viewBinding.lavCancelSearchAction.progress = it.animatedValue as Float
            }

            animate.duration = 700
            animate.start()
        } else if (viewBinding.activityLinesEtSearch.text.isNullOrEmpty() && viewBinding.lavCancelSearchAction.progress == 0.5f) {
            val animate = ValueAnimator.ofFloat(0.5f, 0f)

            animate.addUpdateListener {
                viewBinding.lavCancelSearchAction.progress = it.animatedValue as Float
            }
            animate.duration = 500
            animate.start()
        }
    }
}