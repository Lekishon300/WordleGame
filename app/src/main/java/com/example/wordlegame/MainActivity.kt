package com.example.wordlegame

import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.jinatonic.confetti.CommonConfetti
import com.github.jinatonic.confetti.ConfettiSource

class MainActivity : AppCompatActivity() {
    private var count=0
    private val wordToGuess=FourLetterWordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeGame()

    }
    private fun initializeGame(){
        Log.d(wordToGuess,"Answer Debug message!")
        val inputEditText=findViewById<EditText>(R.id.user_input)
        val submitButton= findViewById<Button>(R.id.submit_button)

        val guess1Answer= findViewById<TextView>(R.id.guess1Answer)
        val guesscheck1check = findViewById<TextView>(R.id.guesscheck1check)

        val guess2Answer=findViewById<TextView>(R.id.guess2Answer)
        val guesscheck2check = findViewById<TextView>(R.id.guesscheck2check)

        val guess3Answer=findViewById<TextView>(R.id.guess3Answer)
        val guesscheck3check = findViewById<TextView>(R.id.guesscheck3check)

        val correctAnswer= findViewById<TextView>(R.id.correctanswer)
        correctAnswer.visibility= View.GONE
        val container=findViewById<ViewGroup>(R.id.confettiContainer)


        submitButton.setOnClickListener{
            val userInput=inputEditText.text.toString().uppercase()
            if(userInput.length==4 && userInput.all { it.isLetter() }){//only contains alphabets
                count++
                when(count){
                    1->{
                        guess1Answer.text=userInput
                        guesscheck1check.text= checkGuess(userInput)

                        if(guesscheck1check.text.equals("OOOO")) {
                            submitButton.text="RESTART"
                            correctAnswer.text=wordToGuess
                            correctAnswer.visibility= View.VISIBLE
                            generateConfetti(container)
                            submitButton.setOnClickListener{
                                restartGame()
                            }
                        }
                    }
                    2->{
                        guess2Answer.text=userInput
                        guesscheck2check.text= checkGuess(userInput)

                        if(guesscheck2check.text.equals("OOOO")) {
                            submitButton.text="RESTART"
                            correctAnswer.text=wordToGuess
                            correctAnswer.visibility= View.VISIBLE
                            generateConfetti(container)
                            submitButton.setOnClickListener{
                                restartGame()
                            }
                        }
                    }
                    3->{
                        guess3Answer.text=userInput
                        guesscheck3check.text= checkGuess(userInput)

                        correctAnswer.text=wordToGuess
                        submitButton.text="RESTART"
                        correctAnswer.visibility= View.VISIBLE

                        if(guesscheck3check.text.equals("OOOO")) {
                            generateConfetti(container)
                        }
                        submitButton.setOnClickListener{
                                restartGame()
                        }
                    }
                }
            }
            else{
                Toast.makeText(this,"Please enter a 4 letter word.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun restartGame(){
       val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(0,0)
    }
    private fun generateConfetti(container:ViewGroup){
        CommonConfetti.rainingConfetti(container, intArrayOf(Color.MAGENTA))
            .infinite().setEmissionDuration(3000L)
            .setEmissionRate(100F)
            .setAccelerationY(600F)
            .setAccelerationX(20F, 10F)
            .setRotationalVelocity(180F, 180F)
            .animate();
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {

            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}