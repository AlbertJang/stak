package `fun`.stak.views.login

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.viewmodels.UserViewModel
import `fun`.stak.views.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.securepreferences.SecurePreferences
import kotlinx.android.synthetic.main.fragment_sign_in.*
import okhttp3.MultipartBody
import org.koin.android.ext.android.inject


class SignInFragment : Fragment() {
    private val securePreferences: SecurePreferences by inject()
    private val viewModel: UserViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureObservables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureListener()
    }

    private fun configureListener() {
        signinButton?.setOnClickListener {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

            builder.addFormDataPart("email", emailEditText.text.toString())
                .addFormDataPart("password", passwordEditText.text.toString())

            viewModel.signIn(builder.build())
        }

        signupButton?.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun configureObservables() {
        viewModel.performFetchStatsStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    val loginToken = resources.data?.data?.loginToken
                    securePreferences.edit().putUnencryptedString("token", loginToken).commit()
                    startActivity(Intent(context, MainActivity::class.java))
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {
                    Toast.makeText(context, "off line error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}