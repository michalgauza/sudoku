package com.example.sudoku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sudoku.R
import com.example.sudoku.models.DifficultLevel
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        continue_button_menu_activity.setOnClickListener {
            GameActivity.getIntent(context = this, isContinue = true).run { startActivity(this) }
        }

        easy_button_menu_activity.setOnClickListener {
            GameActivity.getIntent(this, DifficultLevel.EASY, false).run { startActivity(this) }
        }

        medium_button_menu_activity.setOnClickListener {
            GameActivity.getIntent(this, DifficultLevel.MEDIUM, false).run { startActivity(this) }
        }

        hard_button_menu_activity.setOnClickListener {
            GameActivity.getIntent(this, DifficultLevel.HARD, false).run { startActivity(this) }
        }
    }
}