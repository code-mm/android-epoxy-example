package com.ms.app.ui.base

import android.os.Bundle
import android.os.Parcelable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.sample.core.simpleController
import com.ms.app.view.dialog.widget.progress.UIProgressDialog
import com.bdlbsc.doper.utils.thread.ThreadPoolUtils.runOnMainThread
import com.bdlbsc.doper.utils.toast.ToastUtils.show

abstract class BaseFragment : BaseMvRxFragment() {

    protected val epoxyController by lazy { epoxyController() }

    var myView: View? = null
    protected var baseDialog: UIProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        baseDialog = (activity as BaseAppCompatActivity?)!!.baseDialog
        if (getLayoutId() != 0) {
            // 实例化View
            this.myView = inflater.inflate(getLayoutId(), container, false)
        }
        // 返回视图
        return myView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    fun <T> findViewById(viewID: Int): T {
        return myView!!.findViewById<View>(viewID) as T
    }


    fun showToast(text: String?) {
        runOnMainThread(Runnable { show(text) })
    }

    protected var lengthfilter20 = InputFilter { source, start, end, dest, dstart, dend ->
        val dValue = dest.toString()
        if (dValue != null) {
            if (dValue.length > 20) {
                return@InputFilter dValue.substring(0, 20)
            }
        }
        null
    }
    protected var lengthfilter11 = InputFilter { source, start, end, dest, dstart, dend ->
        val dValue = dest.toString()
        if (dValue != null) {
            if (dValue.length > 11) {
                return@InputFilter dValue.substring(0, 11)
            }
        }
        null
    }
    protected var lengthfilter18 = InputFilter { source, start, end, dest, dstart, dend ->
        val dValue = dest.toString()
        if (dValue != null) {
            if (dValue.length > 18) {
                return@InputFilter dValue.substring(0, 18)
            }
        }
        null
    }

    fun showDialog() {
        runOnMainThread(Runnable {
            if (!baseDialog!!.isShowing) {
                baseDialog!!.show()
            }
        })
    }

    fun hideDialog() {
        runOnMainThread(Runnable {
            if (baseDialog!!.isShowing) {
                baseDialog!!.hide()
            }
        })
    }

    protected var userNameAndPasswordInputFilter = arrayOf(lengthfilter20, LengthFilter(20))
    protected var phoneNumberInputFilter = arrayOf(lengthfilter11, LengthFilter(11))
    protected var iDcardInputFilter = arrayOf(lengthfilter18, LengthFilter(18))

    /**
     * Provide the EpoxyController to use when building models for this Fragment.
     * Basic usages can simply use [simpleController]
     */
    abstract fun epoxyController(): MvRxEpoxyController

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    protected fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        /**
         * If we put a parcelable arg in [MvRx.KEY_ARG] then MvRx will attempt to call a secondary
         * constructor on any MvRxState objects and pass in this arg directly.
         * @see [com.airbnb.mvrx.sample.features.dadjoke.DadJokeDetailState]
         */
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }

    protected open fun getLayoutId(): Int {
        return 0
    }
}
