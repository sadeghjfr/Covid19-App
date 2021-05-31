package com.sadeghjfr22.covid19.view.activity

import android.animation.Animator
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import com.sadeghjfr22.covid19.base.BaseActivity
import com.sadeghjfr22.covid19.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var font = Typeface.createFromAsset(assets, "fonts/font_lalezar.ttf")
        binding.txtSplash.setTypeface(font)

        var intent:Intent
        intent = Intent(this, MainActivity::class.java)

        binding.lottieAnimationView.addAnimatorListener(object:Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {

                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        })



    }


}