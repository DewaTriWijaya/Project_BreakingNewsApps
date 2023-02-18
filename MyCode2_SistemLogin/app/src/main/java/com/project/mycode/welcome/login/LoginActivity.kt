package com.project.mycode.welcome.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.mycode.databinding.ActivityLoginBinding
import com.project.mycode.home.HomeActivity
import com.project.mycode.welcome.CustomDialog

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    //private lateinit var viewModel: LoginViewModel
    //private lateinit var pref: UserPreference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //pref = UserPreference(this)

        setupView()
        setupAction()
        initFirebaseAuth()
    }


    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtLock.text.toString().trim()

            //pref = UserPreference(this)
            //val loginResponse = LoginResponse(email, password)
            //loginUser(loginResponse)

            if (checkValidation(email, password)) {
                loginServer(email, password)
            }
        }

    }

    private fun loginServer(email: String, password: String){
        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth(credential)
    }

    private fun firebaseAuth(credential: AuthCredential){
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                CustomDialog.hideLoading()
                if (it.isSuccessful){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(this, "Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                CustomDialog.hideLoading()
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkValidation(email: String, password: String): Boolean {
        if (email.isEmpty()){
            binding.edtEmail.error = "Please field your email"
            binding.edtEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtEmail.error = "Please use valid email"
            binding.edtEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.edtLock.error = "Please field your password"
            binding.edtLock.requestFocus()
        } else {
            return true
        }
        CustomDialog.hideLoading()
        return false
    }

    private fun initFirebaseAuth(){
        auth = FirebaseAuth.getInstance()
    }

//    private fun loginUser(loginResponse: LoginResponse) {
//        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[LoginViewModel::class.java]
//
//        viewModel.getLoginUser(loginResponse).observe(this) {
//            when (it) {
//                is ResultResponse.Loading -> {}
//                is ResultResponse.Success -> {
//                    try {
//                        val userData = it.data.users
//                        pref.apply {
//                            setStringPreference(USER_ID_KEY, userData.email)
//                            setStringPreference(TOKEN_KEY, userData.password)
//                            setBooleanPreference(IS_LOGIN, true)
//                        }
//                    } finally {
//                        AlertDialog.Builder(this).apply {
//                            setTitle("Selamat")
//                            setMessage("Selamat Kamu Berhasil Login")
//                            setPositiveButton("Next") { _, _ ->
//                                val intent = Intent(context, HomeActivity::class.java)
//                                intent.flags =
//                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                                startActivity(intent)
//                                finish()
//                            }
//                            create()
//                            show()
//                        }
//                    }
//                }
//                is ResultResponse.Error -> {
//                    AlertDialog.Builder(this).apply {
//                        setTitle("Hmmm!")
//                        setMessage("Kamu Gagal Masuk.Coba Ulangi Lagi!")
//                        setNegativeButton("OK", null)
//                        create()
//                        show()
//                    }
//                }
//                else -> {
//                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//    }

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