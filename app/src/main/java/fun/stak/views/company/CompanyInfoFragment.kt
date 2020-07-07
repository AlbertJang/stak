package `fun`.stak.views.company

import `fun`.stak.R
import `fun`.stak.viewmodels.CompanyViewModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_company_info.*
import org.koin.android.ext.android.inject

class CompanyInfoFragment : Fragment() {
    private val viewModel: CompanyViewModel by inject()
    private var companyAdapter: CompanyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureObservables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        companyAdapter = CompanyAdapter { company ->
            if(company != null) {
                val bundle = Bundle()
                bundle.putLong("id", company.id)
                findNavController().navigate(R.id.action_companyInfo_to_companyDetail, bundle)
            }
        }

        companyRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = companyAdapter
        }
    }

    private fun configureListener() {
        companySearchEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.fetchSearchTxt(s.toString())
            }
        })
    }

    private fun configureObservables() {
        viewModel.companyList.observe(this, Observer {
            companyAdapter?.submitList(it)
        })
    }
}