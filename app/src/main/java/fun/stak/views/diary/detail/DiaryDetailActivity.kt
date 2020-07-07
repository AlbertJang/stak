package `fun`.stak.views.diary.detail

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.model.diary.Diary
import `fun`.stak.model.diary.DiaryDetailData
import `fun`.stak.viewmodels.DiaryViewModel
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_diary_detail.*
import org.koin.android.ext.android.inject

class DiaryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
    }
}