package dev.tanakornsss.luminality

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.google.firebase.analytics.FirebaseAnalytics

lateinit var firebaseAnalytics: FirebaseAnalytics

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val apiLevel = Build.VERSION.SDK_INT

        firebaseAnalytics.setUserProperty(
            "api_level",
            apiLevel.toString()
        )

        enableEdgeToEdge()
        setContent {
            LuminalityApp(this)
        }
    }
}
