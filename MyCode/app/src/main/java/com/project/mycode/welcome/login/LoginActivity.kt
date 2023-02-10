package com.project.mycode.welcome.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.project.mycode.data.ResultResponse
import com.project.mycode.databinding.ActivityLoginBinding
import com.project.mycode.home.HomeActivity
import com.project.mycode.welcome.UserPreference
import com.project.mycode.welcome.UserPreference.Companion.IS_LOGIN
import com.project.mycode.welcome.UserPreference.Companion.TOKEN_KEY
import com.project.mycode.welcome.UserPreference.Companion.USER_ID_KEY

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: UserPreference
    //private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = UserPreference(this)

        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtLock.text.toString()

            pref = UserPreference(this)


            val loginResponse = LoginResponse(email, password)
            loginUser(loginResponse)
        }
    }

    private fun loginUser(loginResponse: LoginResponse) {
        val viewModel1 = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel1.getLoginUser(loginResponse).observe(this) {
            when (it) {
                is ResultResponse.Loading -> {

                }
                is ResultResponse.Success -> {
                    try {
                        val userData = it.data.users
                        pref.apply {
                            setStringPreference(USER_ID_KEY, userData.email)
                            setStringPreference(TOKEN_KEY, userData.password)
                            setBooleanPreference(IS_LOGIN, true)
                        }
                    } finally {
                        AlertDialog.Builder(this).apply {
                            setTitle("Selamat")
                            setMessage("Selamat Kamu Berhasil Login")
                            setPositiveButton("Next") { _, _ ->
                                val intent = Intent(context, HomeActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
                is ResultResponse.Error -> {
                    AlertDialog.Builder(this).apply {
                        setTitle("Hmmm!")
                        setMessage("Kamu Gagal Masuk.Coba Ulangi Lagi!")
                        setNegativeButton("OK", null)
                        create()
                        show()
                    }
                }
                else -> {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}