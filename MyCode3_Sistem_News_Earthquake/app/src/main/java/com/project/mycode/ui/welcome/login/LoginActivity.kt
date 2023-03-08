package com.project.mycode.ui.welcome.login

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
import com.project.mycode.ui.TabActivity
import com.project.mycode.ui.welcome.CustomDialog
import com.project.mycode.ui.welcome.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        setupView()
        setupAction()
        initFirebaseAuth()
    }


    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtLock.text.toString().trim()

            if (checkValidation(email, password)) {
                loginServer(email, password)
            }
        }

    }

    private fun loginServer(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        firebaseAuth(credential)
    }

    private fun firebaseAuth(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                CustomDialog.hideLoading()
                if (it.isSuccessful) {
                    startActivity(Intent(this, TabActivity::class.java))
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
        if (email.isEmpty()) {
            binding.edtEmail.error = "Please field your email"
            binding.edtEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
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