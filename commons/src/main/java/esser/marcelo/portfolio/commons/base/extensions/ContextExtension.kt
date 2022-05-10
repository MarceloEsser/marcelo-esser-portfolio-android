package esser.marcelo.portfolio.commons.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

/**
 * Hide Keyboard from layer
 * @param activity -> Activity
 */
fun Activity.hideKeyboard() {
    try {
        val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View? = this.currentFocus

        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    } catch (e: java.lang.Exception) {

    }
}