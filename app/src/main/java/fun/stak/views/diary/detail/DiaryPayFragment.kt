package `fun`.stak.views.diary.detail

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.viewmodels.DiaryViewModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_add_diary_pay.*
import okhttp3.MultipartBody
import org.koin.android.ext.android.inject
import java.util.*

class DiaryPayFragment : Fragment() {
    private val viewModel: DiaryViewModel by inject()

    private var diaryId: Long? = 0
    private var payType = "buy"

    lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureObservables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_diary_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryId = arguments?.getLong("id")

        configureListener()

        // method init
        buyButton?.isChecked = true

        // calendar init
        val calendar = Calendar.getInstance()
        payDateEditText?.setText(
            "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(
                Calendar.DATE
            )}"
        )

        datePickerDialog = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                payDateEditText?.setText("${year}-${month}-${dayOfMonth}")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))
    }

    private fun configureListener() {
        methodGroup?.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                payType = if (checkedId == R.id.buyButton) {
                    "buy"
                } else {
                    "sell"
                }
            }
        }

        payDateEditText?.setOnTouchListener { v, event ->
            datePickerDialog.show()
            return@setOnTouchListener true
        }

        submitButton?.setOnClickListener {
            if (invalidPayCheck()) {
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

                builder.addFormDataPart("pay_type", payType)
                    .addFormDataPart("price", priceEditText?.text.toString())
                    .addFormDataPart("amount", amountEditText?.text.toString())
                    .addFormDataPart("pay_date", payDateEditText?.text.toString())
                    .addFormDataPart("reason", reasonEditText?.text.toString())

                viewModel.payDiary(diaryId, builder.build())
            }
        }
    }

    private fun configureObservables() {
        viewModel.payDiaryStatus.observe(this, Observer { resources ->
            when (resources.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    requireActivity().finish()
                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.OFFLINE_ERROR -> {
                }
            }
        })
    }

    private fun invalidPayCheck(): Boolean {
        if(priceEditText.text.isNullOrEmpty()) {
            priceLayout?.error = "필수값입니다."
        } else {
            priceLayout?.error = null
        }
        if(amountEditText?.text.isNullOrEmpty()) {
            amountLayout?.error = "필수값입니다."
        } else {
            amountLayout?.error = null
        }
        if(payDateEditText?.text.isNullOrEmpty()) {
            payDateLayout?.error = "필수값입니다."
        } else {
            payDateLayout?.error = null
        }

        return !(priceEditText.text.isNullOrEmpty() || amountEditText?.text.isNullOrEmpty() || payDateEditText?.text.isNullOrEmpty())
    }
}