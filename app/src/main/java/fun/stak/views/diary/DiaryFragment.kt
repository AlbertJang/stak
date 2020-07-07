package `fun`.stak.views.diary

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.viewmodels.DiaryViewModel
import `fun`.stak.views.diary.adddiary.AddDiaryActivity
import `fun`.stak.views.diary.adddiary.fragment.addcompany.adapter.DiaryAdapter
import `fun`.stak.views.diary.detail.DiaryDetailActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_diary.*
import org.koin.android.ext.android.inject

class DiaryFragment : Fragment() {
    private val viewModel: DiaryViewModel by inject()
    private var diaryAdapter: DiaryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureObservables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        configureListener()
    }

    override fun onStart() {
        super.onStart()

        viewModel.refreshAllList()
    }

    private fun configureRecyclerView() {
        diaryAdapter = DiaryAdapter { diary ->
            // diary click
            val diaryIntent = Intent(requireContext(), DiaryDetailActivity::class.java)
            diaryIntent.putExtra("id", diary?.id)
            startActivity(diaryIntent)
        }

        diaryRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = diaryAdapter
        }
    }

    private fun configureListener() {
        addDiaryButton?.setOnClickListener {
            val addDiaryIntent = Intent(context, AddDiaryActivity::class.java)
            startActivity(addDiaryIntent)
        }
    }

    private fun configureObservables() {
        viewModel.diaryList.observe(this, Observer {
            diaryAdapter?.submitList(it)
        })

        viewModel.delDiaryStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show()
                    viewModel.refreshAllList()
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {}
            }
        })
    }
}