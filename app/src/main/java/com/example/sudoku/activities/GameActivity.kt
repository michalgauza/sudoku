package com.example.sudoku.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityGameBinding
import com.example.sudoku.models.DifficultLevel
import com.example.sudoku.models.SudokuBoard
import org.koin.android.ext.android.inject

val DEFAULT_DIFFICULT_LEVEL = DifficultLevel.EASY
const val DEFAULT_IS_CONTINUE = false

class GameActivity : AppCompatActivity() {

    private val viewModel by inject<GameActivityViewModel>()

    private val errorStringResObserver = Observer<Int> { response -> setupDialog(response) }

    private val winLObserver = Observer<Boolean> { win -> if (win) setupDialog(R.string.win) }

    private val boardObserver = Observer<SudokuBoard> { board ->
        binding.sudokuBoardViewGameActivity.updateCellsList(board.cellsList)
    }

    private val localGameSaveObserver = Observer<@StringRes Int> { message ->
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private lateinit var binding: ActivityGameBinding

    private var difficultLevel =
        DEFAULT_DIFFICULT_LEVEL
    private var isContinue = DEFAULT_IS_CONTINUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let { bundle ->
            bundle.getParcelable<DifficultLevel>(DIFFICULT_LEVEL_KEY)?.let {
                difficultLevel = it
            }
            isContinue = bundle.getBoolean(IS_CONTINUE_KEY)
        }

        binding = ActivityGameBinding.inflate(layoutInflater)
        setupBinding()
        setupViewModel()
    }

    private fun setupBinding() {
        with(binding) {
            lifecycleOwner = this@GameActivity
            viewModel = this@GameActivity.viewModel
            sudokuBoardViewGameActivity.cellTouchListener = { cell ->
                this@GameActivity.viewModel.setSelectedCell(cell)
            }
            setContentView(root)
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            setupSudokuBoard(isContinue, difficultLevel)
            errorStringResLiveData.observe(this@GameActivity, errorStringResObserver)
            winLiveData.observe(this@GameActivity, winLObserver)
            boardLiveData.observe(this@GameActivity, boardObserver)
            localGameMessageSingleLiveEvent.observe(this@GameActivity, localGameSaveObserver)
        }
    }

    private fun setupDialog(@StringRes title: Int) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(title))
            setCancelable(false)
            setPositiveButton(getString(R.string.try_again)) { _, _ ->
                viewModel.setupSudokuBoard(false, difficultLevel)
            }
            setNegativeButton(getString(R.string.exit_sudoku)) { _, _ ->
                this@GameActivity.finish()
            }
            show()
        }
    }

    companion object {
        const val DIFFICULT_LEVEL_KEY = "difficultLevelKey"
        const val IS_CONTINUE_KEY = "isContinueKey"
        fun getIntent(
            context: Context,
            difficultLevel: DifficultLevel = DifficultLevel.EASY,
            isContinue: Boolean
        ) = Intent(context, GameActivity::class.java).apply {
            putExtra(DIFFICULT_LEVEL_KEY, difficultLevel as Parcelable)
            putExtra(IS_CONTINUE_KEY, isContinue)
        }
    }
}
