package `fun`.stak.views.diary.adddiary.fragment.addcompany

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.viewmodels.CompanyViewModel
import `fun`.stak.viewmodels.DiaryViewModel
import `fun`.stak.views.diary.adddiary.fragment.addcompany.adapter.CompanyListAdapter
import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_add_compnay.*
import okhttp3.MultipartBody
import org.koin.android.ext.android.inject


class AddCompanyFragment : Fragment() {
    private val companyViewModel: CompanyViewModel by inject()
    private val diaryViewModel: DiaryViewModel by inject()
    private lateinit var companyListAdapter: CompanyListAdapter

    // request
    private var companyId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureObservables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_compnay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        configureListener()
    }

    private fun configureRecyclerView() {
        companyListAdapter = CompanyListAdapter{ companySimple ->
            // company click
            companyNameEditText?.setText(companySimple?.companyName)

            requireActivity().runOnUiThread {
                companyRecyclerView?.visibility = View.GONE
            }

            companyId = companySimple?.id
        }

        companyRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = companyListAdapter
        }
    }

    private fun configureListener() {
        confirmButton?.setOnClickListener {
            if(isValidNext()) {
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

                builder.addFormDataPart("company_id", companyId.toString())
                    .addFormDataPart("upstream", getUpstreamString())
                    .addFormDataPart("theme", getThemeString())
                    .addFormDataPart("re_buy_price", reBuyPriceEditText?.text.toString())
                    .addFormDataPart("goal_price", goalPriceEditText?.text.toString())
                    .addFormDataPart("cut_price", cutPriceEditText?.text.toString())
                    .addFormDataPart("etc_memo", etcEditText?.text.toString())

                diaryViewModel.addDiary(builder.build())
            }
        }

        // text changed
        companyNameEditText?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // init
                companyId = null

                companyViewModel.fetchSearchTxt(s.toString())

                // 텍스트를 변경하면 다시 목록이 보인다.
                companyRecyclerView?.visibility = View.VISIBLE
            }
        })


        // key listener
        upstreamEditText?.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_COMMA) {
                val upstreamString = upstreamEditText?.text.toString().trim()

                if(upstreamString.isEmpty().not()) {
                    val chip = Chip(requireContext())
                    chip.text = upstreamString
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        upstreamGroup?.removeView(chip)
                    }

                    upstreamGroup?.addView(chip)
                    upstreamEditText?.text?.clear()
                }

                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }

        themeEditText?.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_COMMA) {
                val themeString = themeEditText?.text.toString().trim()

                if(themeString.isEmpty().not()) {
                    val chip = Chip(requireContext())
                    chip.text = themeString
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        upstreamGroup?.removeView(chip)
                    }

                    themeGroup?.addView(chip)
                    themeEditText?.text?.clear()
                }

                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }
    }

    private fun configureObservables() {
        companyViewModel.companyList.observe(this, Observer {
            companyListAdapter.submitList(it)
        })

        diaryViewModel.addDiaryStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    requireActivity().finish()
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {}
            }
        })
    }

    private fun isValidNext(): Boolean {
        return if(companyId == null) {
            companyNameLayout.error = "종목이 선택되지 않았습니다."
            false
        } else {
            true
        }
    }

    private fun getUpstreamString(): String {
        val size = upstreamGroup.childCount
        var result = ""

        if(size > 0) {
            for(i in 0 until size) {
                if(i == 0) {
                    result += ((upstreamGroup[i] as Chip).text)
                } else {
                    result += (",").plus((upstreamGroup[i] as Chip).text)
                }
            }
        }

        return result
    }

    private fun getThemeString(): String {
        val size = themeGroup.childCount
        var result = ""

        if(size > 0) {
            for(i in 0 until size) {
                if(i == 0) {
                    result += ((themeGroup[i] as Chip).text)
                } else {
                    result += (",").plus((themeGroup[i] as Chip).text)
                }
            }
        }

        return result
    }
}