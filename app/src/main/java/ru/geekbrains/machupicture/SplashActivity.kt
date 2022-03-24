package ru.geekbrains.machupicture

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val angle_sky = 2500f
    private val angle_earth = -2500f
    private val angle_water = 1500f
    private val angle_moon = 180f
    private val time_animation: Long = 7000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        image_view_sky.animate().rotationBy(angle_sky)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(time_animation)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationEnd(p0: Animator?) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    // Nothing to do
                }

            })

        image_view_earth.animate().rotationBy(angle_earth)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(time_animation)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationEnd(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    // Nothing to do
                }

            })

        image_view_water.animate().rotationBy(angle_water)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(time_animation)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationEnd(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    // Nothing to do
                }

            })
        image_view_moon.animate().rotationBy(angle_moon)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(time_animation)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationEnd(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    // Nothing to do
                }

            })

        image_view_meteor.animate().scaleX(0.8f).scaleY(0.8f)
            .translationY(1000f).translationX(500f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(3000)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationEnd(p0: Animator?) {

                    image_view_logo.animate().scaleX(200f).scaleY(200f)
                        .setInterpolator(DecelerateInterpolator())
                        .setDuration(300)
                        .setListener(object : Animator.AnimatorListener{
                            override fun onAnimationStart(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationEnd(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationCancel(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationRepeat(p0: Animator?) {
                                // Nothing to do
                            }

                        })

                    image_view_boom_1.animate()
                        .scaleX(50f).scaleY(50f)
                        .translationY(2000f).translationX(1000f)
                        .setInterpolator(DecelerateInterpolator())
                        .setDuration(2000)
                        .setListener(object : Animator.AnimatorListener{
                            override fun onAnimationStart(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationEnd(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationCancel(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationRepeat(p0: Animator?) {
                                // Nothing to do
                            }

                        })
                    image_view_boom_2.animate()
                        .scaleX(50f).scaleY(50f)
                        .translationY(-500f).translationX(-2000f)
                        .setInterpolator(DecelerateInterpolator())
                        .setDuration(2000)
                        .setListener(object : Animator.AnimatorListener{
                            override fun onAnimationStart(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationEnd(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationCancel(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationRepeat(p0: Animator?) {
                                // Nothing to do
                            }

                        })
                    image_view_boom_3.animate()
                        .scaleX(50f).scaleY(50f)
                        .translationY(-2000f).translationX(800f)
                        .setInterpolator(DecelerateInterpolator())
                        .setDuration(2000)
                        .setListener(object : Animator.AnimatorListener{
                            override fun onAnimationStart(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationEnd(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationCancel(p0: Animator?) {
                                // Nothing to do
                            }

                            override fun onAnimationRepeat(p0: Animator?) {
                                // Nothing to do
                            }

                        })
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Nothing to do
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    // Nothing to do
                }

            })




    }

    override fun onDestroy() {
        super.onDestroy()
    }


}