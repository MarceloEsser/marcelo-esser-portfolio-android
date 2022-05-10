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
import esser.marcelo.portfolio.commons.base.hideKeyboard
import esser.marcelo.portfolio.core.Status
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
            viewBinding.linesActivityRvLines.adapter =
                LinesAdapter(it, requireContext()) { line: BusLine ->
                    viewModel.line = line
                    showLineWaysBottomSheet()
                }
        }

        viewModel.error.observe(requireCompatActivity()) { message ->
            showSnackBar(message)
        }

        viewModel.status.observe(requireCompatActivity()) { status ->
            if (status == Status.loading) {
                showLoader()
                return@observe
            }
            hideLoader()
        }

    }

    private fun showLineWaysBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from<View>(viewBinding.bottomSheet)

        viewBinding.isShowingBottomSheet = true

        bottomSheetBehavior?.let { bottomSheet ->
            configureBottomSheetCallback(bottomSheet)

            bottomSheet.peekHeight =
                viewBinding.bottomSheet.height - viewBinding.bottomSheetContent.height + 100
        }
    }


    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.activityLinesEtSearch.addTextChangedListener(searchWatcher())
        viewBinding.bottomSheetBg.setOnClickListener {
            hideBottomSheet()
        }
        viewBinding.lavCancelSearchAction.setOnClickListener {
            if (viewBinding.activityLinesEtSearch.text.isNotEmpty()) {
                viewBinding.activityLinesEtSearch.setText("")
                requireCompatActivity().hideKeyboard()
            }
        }
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
        viewBinding.isShowingBottomSheet = false
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

        viewBinding.isShowingBottomSheet = true
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
            searchAnimationControl()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchAnimationControl()
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