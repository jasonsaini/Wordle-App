package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (wordToGuess.contains(guess[i], ignoreCase = true)) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var word_list = FourLetterWordList();
        var answer = word_list.getRandomFourLetterWord();

        val entry_button = findViewById<Button>(R.id.guessSubmitBtn)
        var reset_button = findViewById<Button>(R.id.resetBtn);
        reset_button.isVisible = false;

        var guessCount = 0
        var guessEntry = findViewById<EditText>(R.id.guessEntry)


        entry_button.setOnClickListener{
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            var userGuess = guessEntry.text.toString()
            if(userGuess.length != 4)
            {
                return@setOnClickListener
            }
            else
            {
                guessCount += 1
                if(guessCount == 1)
                {
                    println("guess count = 1")
                    findViewById<TextView>(R.id.guess1label).text = "Guess #1"
                    findViewById<TextView>(R.id.userguess1).text  = userGuess

                    findViewById<TextView>(R.id.guess1checklabel).text = "Guess #1 Check"
                    findViewById<TextView>(R.id.usercheck1).text = checkGuess(userGuess, answer)
                }
                else if(guessCount == 2)
                {
                    println("guess count = 2")
                    findViewById<TextView>(R.id.guess2label).text = "Guess #2"
                    findViewById<TextView>(R.id.userguess2).text = userGuess

                    findViewById<TextView>(R.id.guess2checklabel).text = "Guess #2 Check"
                    findViewById<TextView>(R.id.usercheck2).text = checkGuess(userGuess, answer)

                }
                else if(guessCount == 3)
                {
                    println("guess count = 3")
                    findViewById<TextView>(R.id.guess3label).text = "Guess #3"
                    findViewById<TextView>(R.id.userguess3).text= userGuess

                    findViewById<TextView>(R.id.guess3checklabel).text = "Guess #3 Check"
                    findViewById<TextView>(R.id.usercheck3).text = checkGuess(userGuess, answer)

                    findViewById<TextView>(R.id.answerReveal).text = "\t\t\t"  + answer
                    entry_button.isVisible = false
                    reset_button.isVisible = true
                }
                if(answer == userGuess)
                {

                    guessCount = 0
                    findViewById<TextView>(R.id.answerReveal).text = "You got it! The answer was\t\t\t" + answer
                    entry_button.isVisible = false
                    reset_button.isVisible = true
                    return@setOnClickListener
                }

            }
        }


        reset_button.setOnClickListener{
            var root = findViewById<ConstraintLayout>(R.id.constraintLayout)
            answer = word_list.getRandomFourLetterWord();
            guessCount = 0
            root.children.forEach{
                if(it is TextView && (it.text.toString() != "Wordle" ) && (it.text.toString() != "GUESS!") && (it.text.toString() != "RESET"))
                {
                    it.text = ""
                }

            }
            reset_button.isVisible = false
            entry_button.isVisible = true
        }


    }
}

