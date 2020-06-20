package com.example.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.sudoku.databinding.ActivityGameBinding
import org.koin.android.ext.android.inject


class GameActivity : AppCompatActivity() {

    val viewModel by inject<GameActivityViewModel>()

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)

        viewModel.cellsListLiveData.observe(this, Observer {
            binding.sudokuBoardViewGameActivity.updateCellsList(it)
        })

        binding.sudokuBoardViewGameActivity.cellTouchListener = {
            viewModel.setSelectedCell(it)
        }

    }

}
