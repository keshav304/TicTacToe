package com.example.tictactoe


import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class GameActivity() : AppCompatActivity(), View.OnClickListener, Parcelable {

    var player = true
    var turnCount = 0
    var boardStatus = Array(3) { IntArray(3) }

    //declaring a 2-d array of buttons
    lateinit var board: Array<Array<Button>>

    //Initialising outside so that it is globally accessible
    private lateinit var binding: ActivityMainBinding

    constructor(parcel: Parcel) : this() {
        player = parcel.readByte() != 0.toByte()
        turnCount = parcel.readInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //view binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Inflating the activity with root view
        val view = binding.root
        setContentView(view)

        board = arrayOf(
            arrayOf(binding.button1, binding.button2, binding.button3),
            arrayOf(binding.button4, binding.button5, binding.button6),
            arrayOf(binding.button7, binding.button8, binding.button9)
        )
        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
            }
        }

        initialiseBoardStatus()

        binding.resetbtn.setOnClickListener {
            player = true
            turnCount = 0
            initialiseBoardStatus()
        }

    }


    private fun initialiseBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1

                //enable buttons on initialisation
                board[i][j].isEnabled = true
                board[i][j].text = ""
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.button1 -> {
                    updateValue(row=0, column=0, currentPlayer=player)
                }
                R.id.button2 -> {
                    updateValue(row=0, column=1, currentPlayer=player)
                }
                R.id.button3 -> {
                    updateValue(row=0, column=2, currentPlayer=player)
                }
                R.id.button4 -> {
                    updateValue(row=1, column=0, currentPlayer=player)
                }
                R.id.button5 -> {
                    updateValue(row=1, column=1, currentPlayer=player)
                }
                R.id.button6 -> {
                    updateValue(row=1, column=2, currentPlayer=player)
                }
                R.id.button7 -> {
                    updateValue(row=2, column=0, currentPlayer=player)
                }
                R.id.button8 -> {
                    updateValue(row=2, column=1, currentPlayer=player)
                }
                R.id.button9 -> {
                    updateValue(row=2, column=2, currentPlayer=player)
                }
            }
        }
        //change the current player and turn count on each click
        turnCount++
        player = !player

        //Changing the text according to the player's turn
        if (player){
            updateDisplay("Player X turn")
        }
        else{
            updateDisplay("Player O turn")
        }

        if(turnCount==9){
            updateDisplay("Game Over")
        }

        checkWinner()
    }

    private fun checkWinner() {
        // checking winning combination in rows
        for (i in 0..2){
            if (boardStatus[i][0] ==boardStatus[i][1] && boardStatus[i][0]==boardStatus[i][2]){
                if (boardStatus[i][0] == 1){
                    updateDisplay(getString(R.string.winnerx))
                    break
                }
                else if(boardStatus[i][0] == 0){
                    updateDisplay(getString(R.string.winnero))
                    break
                }
            }
        }

        //checking combination for vertical rows
        for (i in 0..2){
            if(boardStatus[0][i] ==boardStatus[1][i] && boardStatus[0][i]==boardStatus[2][i]){
                if (boardStatus[0][i] == 1){
                    updateDisplay(getString(R.string.winnerx))
                    break
                }
                else if(boardStatus[0][i] == 0){
                    updateDisplay(getString(R.string.winnero))
                    break
                }
            }
        }

        //checking combinations for diagonals

        //first diagonal
        if(boardStatus[0][0] ==boardStatus[1][1] && boardStatus[0][0]==boardStatus[2][2]){
            if (boardStatus[0][0] == 1){
                updateDisplay(getString(R.string.winnerx))
            }
            else if(boardStatus[0][0] == 0){
                updateDisplay(getString(R.string.winnero))
            }
        }

        //second diagonal
        if(boardStatus[0][2] ==boardStatus[1][1] && boardStatus[0][2]==boardStatus[2][0]){
            if (boardStatus[0][2] == 1){
                updateDisplay(getString(R.string.winnerx))
            }
            else if(boardStatus[0][2] == 0){
                updateDisplay(getString(R.string.winnero))
            }
        }


    }

    private fun updateDisplay(displayText: String) {
        binding.playerx.text=displayText
        if(binding.playerx.text.contains("Winner")){
            disableButtons()
        }
    }

    private fun disableButtons() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
            }
        }
    }

    private fun updateValue(row: Int, column: Int, currentPlayer: Boolean) {
        val text = if (currentPlayer) "X" else "O"
        val value = if (currentPlayer) 1 else 0
        board[row][column].apply {
            isEnabled = false
            setText(text)
        }
        boardStatus[row][column] = value
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (player) 1 else 0)
        parcel.writeInt(turnCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameActivity> {
        override fun createFromParcel(parcel: Parcel): GameActivity {
            return GameActivity(parcel)
        }

        override fun newArray(size: Int): Array<GameActivity?> {
            return arrayOfNulls(size)
        }
    }
}

