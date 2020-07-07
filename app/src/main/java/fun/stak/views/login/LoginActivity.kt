package `fun`.stak.views.login

import `fun`.stak.R
import `fun`.stak.data.local.Resource
import `fun`.stak.viewmodels.UserViewModel
import `fun`.stak.views.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.securepreferences.SecurePreferences
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private val securePreferences: SecurePreferences by inject()
    private val viewModel: UserViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        configureObservables()

        val token = securePreferences.getEncryptedString("token", "")

        if(token.isNullOrEmpty().not()) {
            viewModel.tokenRefresh()
        }
    }

    private fun configureObservables() {
        viewModel.tokenStatus.observe(this, Observer { resources ->
            when(resources.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    val loginToken = resources.data?.data?.loginToken
                    securePreferences.edit().putUnencryptedString("token", loginToken).commit()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                Resource.Status.ERROR -> {}
                Resource.Status.OFFLINE_ERROR -> {
                    Toast.makeText(this, "off line error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}