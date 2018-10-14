package com.ss.android.learn.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.ss.android.glide.GlideClient
import com.ss.android.learn.R
import com.ss.android.learn.icontext.IEditPhotiDetailContext
import com.ss.android.learn.view.FingerView
import com.ss.android.util.UIUtils
import kotlinx.android.synthetic.main.edit_photo_fragment.*

class EditPhotoDetailFragment : Fragment(), IEditPhotiDetailContext {

    override fun addTextView() {
        val view = FingerView(this.context!!)
        val params = LinearLayout.LayoutParams(UIUtils.dip2Px(context, 100).toInt(), UIUtils.dip2Px(context, 100).toInt())
        view.setCustomTextColor(resources.getColor(R.color.white))
        view.setSlideable(false)
        params.gravity = Gravity.CENTER
        container.addView(view, params)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        view.postDelayed({
            showSoftKeyBoard()
        }, 1000)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_photo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage()
    }

    private fun loadImage() {
        GlideClient.getInstantce().get().loadImage(context, UIUtils.getScreenWidth(context), UIUtils.getScreenHeight(context), image, "http://p1.ipstatp.com/origin/tos-maliva-p-0000/77198b75aaf044d0a36bcb0134b114f2.webp")
    }

    private fun showSoftKeyBoard() {
        var imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }
}