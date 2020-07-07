package `fun`.stak.views.diary.detail

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.model.diary.BuyData
import `fun`.stak.model.diary.Diary
import `fun`.stak.model.diary.SellData
import `fun`.stak.viewmodels.DiaryPayViewModel
import `fun`.stak.viewmodels.DiaryViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_diary_detail.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {
    private val diaryViewModel: DiaryViewModel by inject()
    private val diaryPayViewModel: DiaryPayViewModel by inject()

    private var detailPayAdapter: DetailPayAdapter? = null

    private var diaryId: Long? = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureObservables()

        detailPayAdapter = DetailPayAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureListener()

        diaryId = requireActivity().intent?.extras?.getLong("id", 0)
        diaryViewModel.getDiaryDetail(diaryId)
    }

    private fun configureListener() {
        addPayButton?.setOnClickListener {
            if(diaryId != null) {
                val bundle = Bundle()
                bundle.putLong("id", diaryId!!)
                findNavController().navigate(
                    R.id.action_diaryDetailFragment_to_diaryPayFragment,
                    bundle
                )
            }
        }

        deleteButton?.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("매매일지 삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("삭제") { dialog, which ->
                    diaryViewModel.deleteDiary(diaryId)
                }
                .setNegativeButton("취소") { dialog, which ->

                }.show()
        }
    }

    private fun configureObservables() {
        diaryViewModel.detailDiaryStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    initDetail(resources.data?.data?.diary)
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {}
            }
        })

        diaryViewModel.delPayDiaryStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    diaryViewModel.getDiaryDetail(diaryId)
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {}
            }
        })

        diaryViewModel.delDiaryStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    requireActivity().finish()
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {}
            }
        })

        diaryPayViewModel.payList.observe(this, Observer {

        })
    }

    private fun initDetail(diary: Diary?) {
        companyNameTextView?.text = diary?.company?.companyName

        // trade
        val tradePrice = diary?.company?.tradeData?.tradePrice
        val changePrice = diary?.company?.tradeData?.changePrice
        val per = diary?.company?.tradeData?.per

        if (per != null) {
            when {
                per > 0 -> {
                    tradePriceTextView?.setTextColor(Color.RED)
                    changePriceTextView?.setTextColor(Color.RED)
                    tradePerTextView?.setTextColor(Color.RED)

                    changePriceTextView?.text = "▲${changePrice} / "
                }
                per < 0 -> {
                    tradePriceTextView?.setTextColor(Color.BLUE)
                    changePriceTextView?.setTextColor(Color.BLUE)
                    tradePerTextView?.setTextColor(Color.BLUE)

                    changePriceTextView?.text = "▼${changePrice} / "
                }
                else -> {
                    tradePriceTextView?.setTextColor(Color.BLACK)
                    changePriceTextView?.setTextColor(Color.BLACK)
                    tradePerTextView?.setTextColor(Color.BLACK)

                    changePriceTextView?.text = "${changePrice} / "
                }
            }
        }

        tradePriceTextView?.text = "${tradePrice}원 / "
        tradePerTextView?.text = "${per}%"

        // current
        currentAmountTextView?.text = "보유수량 : ${(diary?.buyAmount ?: 0) - (diary?.sellAmount ?: 0)}주 / "
        getDateTextView?.text = "보유일 : ${calculateDate(diary?.firstBuyDate, diary?.lastSellDate) ?: 0}일"

        // price
        rePriceTextView?.text = "재매수 : ${diary?.rePrice ?: "-"} | "
        goalPriceTextView?.text = "목표가 : ${diary?.goalPrice ?: "-"} | "
        cutPriceTextView?.text = "손절가 : ${diary?.cutPrice ?: "-"}"

        totalBuyAmountTextView?.text = "${diary?.buyAmount ?: 0}주"
        totalBuyPriceTextView?.text = "${diary?.avgBuyPrice ?: 0}원"

        totalSellAmountTextView?.text = "${diary?.sellAmount ?: 0}주"
        totalSellPriceTextView?.text = "${diary?.avgSellPrice ?: 0}원"

        payRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = detailPayAdapter
        }

        /*buyRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = DetailPayAdapter(diary?.buySet) { buyData ->
                if(buyData is BuyData) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제") { dialog, which ->
                            viewModel.deletePay(diaryId, "buy", buyData.id)
                        }
                        .setNegativeButton("취소") { dialog, which ->

                        }.show()
                }
            }
        }

        sellRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = DetailPayAdapter(diary?.sellSet) { sellData ->
                if(sellData is SellData) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제") { dialog, which ->
                            viewModel.deletePay(diaryId, "sell", sellData.id)
                        }
                        .setNegativeButton("취소") { dialog, which ->

                        }.show()
                }
            }
        }*/
    }

    private fun calculateDate(firstBuyDate: String?, lastSellDate: String?): Long? {
        if(firstBuyDate == null) return null

        val fm = SimpleDateFormat("yyyy-MM-dd")

        val buyDate = fm.parse(firstBuyDate)

        val sellDate = if(lastSellDate == null) {
            val calendar = Calendar.getInstance()
            val today = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.DATE)}"
            fm.parse(today)
        } else {
            fm.parse(lastSellDate)
        }

        val time = sellDate.time - buyDate.time

        return time / 1000 / 3600 * 24
    }
}