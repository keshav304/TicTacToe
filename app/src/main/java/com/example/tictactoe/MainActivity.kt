package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.util.LogWriter
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.databinding.LandingPageBinding
import kotlinx.android.synthetic.main.landing_page.*

const val key1="player1"
const val key2 ="player2"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: LandingPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)
        binding =LandingPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val player1 = binding.player1.text

        val player2 = binding.player2.text
        binding.startbtn.setOnClickListener {
            val game_activity = Intent(this, GameActivity::class.java)
            game_activity.putExtra(key1, player1)
            game_activity.putExtra(key2, player2)
            startActivity(game_activity)
        }
    }

}