package `fun`.stak.views.diary.adddiary.fragment.diaryPay

import `fun`.stak.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_diary_pay.*

class DiaryPayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureListener()
    }

    private fun configureListener() {
        cancelButton?.setOnClickListener {
            requireActivity().finish()
        }
    }
}