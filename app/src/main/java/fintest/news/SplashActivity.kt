package fintest.news

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import fintest.news.App


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        App.setCurrentActivity(this)

        Handler().postDelayed({
        val intentNoLogin = Intent(this, MainActivity::class.java)
        startActivity(intentNoLogin)
        finish()
        }, 2000)
    }
}
