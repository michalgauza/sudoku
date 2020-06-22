package com.example.sudoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.sudoku.databinding.ActivityGameBinding
import com.example.sudoku.net.ResponseWrapper
import org.koin.android.ext.android.inject


class GameActivity : AppCompatActivity() {

    private val viewModel by inject<GameActivityViewModel>()

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.sudokuBoardModel = viewModel.sudokuBoard
        setContentView(binding.root)
        viewModel.getRestSudokuValues()

        viewModel.responseLiveData.observe(this, Observer { response ->
            setupDialog(response)
        })

        viewModel.sudokuBoard.cellsListLiveData.observe(this, Observer {
            binding.sudokuBoardViewGameActivity.updateCellsList(it)
        })

        binding.sudokuBoardViewGameActivity.cellTouchListener = {
            viewModel.sudokuBoard.setSelectedCell(it)
        }

    }

    private fun setupDialog(@StringRes title: Int) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(title))
            setCancelable(false)
            setPositiveButton(getString(R.string.try_again)) { _, _ ->
                viewModel.getRestSudokuValues()
            }
            setNegativeButton(getString(R.string.exit_sudoku)) { _, _ ->
                this@GameActivity.finish()
            }
            show()
        }
    }

}
