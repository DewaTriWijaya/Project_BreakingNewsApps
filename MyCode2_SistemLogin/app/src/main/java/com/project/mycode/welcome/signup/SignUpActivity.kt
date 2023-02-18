package com.project.mycode.welcome.signup

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.mycode.databinding.ActivitySignUpBinding
import com.project.mycode.home.HomeActivity
import com.project.mycode.welcome.CustomDialog

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtLock.text.toString().trim()
            val confirm = binding.edtConfirm.text.toString().trim()

            CustomDialog.showLoading(this)
            if (checkValidation(email, password, confirm)) {
                signUpToServer(email, password)
            }
        }
    }

    private fun signUpToServer(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                CustomDialog.hideLoading()
                if (it.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                }
            }
            .addOnFailureListener {
                CustomDialog.hideLoading()
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkValidation(email: String, password: String, confirm: String): Boolean {
        if (email.isEmpty()) {
            binding.edtEmail.let {
                it.error = "Please field your email"
                it.requestFocus()
            }
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.let {
                it.error = "Please use valid email"
                it.requestFocus()
            }
        } else if (password.isEmpty()) {
            binding.edtLock.let {
                it.error = "Please field your password"
                it.requestFocus()
            }
        } else if (confirm.isEmpty()) {
            binding.edtConfirm.let {
                it.error = "Please field your confirm password"
                it.requestFocus()
            }
        } else if (password != confirm) {
            binding.edtLock.let {
                it.error = "Your pass didnt match"
                it.requestFocus()
            }
            binding.edtConfirm.let {
                it.error = "Your confirm pass didnt match"
                it.requestFocus()
            }
        } else {
            binding.edtLock.error = null
            binding.edtConfirm.error = null
            return true
        }
        CustomDialog.hideLoading()
        return false
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