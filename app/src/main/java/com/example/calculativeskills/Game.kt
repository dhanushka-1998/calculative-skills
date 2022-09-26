package com.example.calculativeskills

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class Game : AppCompatActivity() {

    // Define ui components
    private lateinit var greaterButton: Button
    private lateinit var lessButton: Button
    private lateinit var equalButton: Button
    private lateinit var leftStatement: TextView
    private lateinit var rightStatement: TextView
    private lateinit var correctView: TextView
    private lateinit var wrongView: TextView
    private lateinit var resultView: TextView
    private lateinit var timerView: TextView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var gameOverImage: ImageView
    private lateinit var countDownTimer: CountDownTimer

    private var isStop = arrayOf(false)
    private var gameTime = arrayListOf<Long>(50000)
    private var numbers = arrayListOf<Int>()
    private var operations = arrayListOf<String>()
    private var values = arrayOf(0,0)
    private var points = arrayOf(0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.supportActionBar?.hide() // Hide Action Bar when application startup

        // All UI Components of Game Screen
        greaterButton = findViewById(R.id.greaterButton)
        lessButton = findViewById(R.id.lessButton)
        equalButton = findViewById(R.id.equalButton)
        leftStatement = findViewById(R.id.leftExpression)
        rightStatement = findViewById(R.id.rightExpression)
        correctView = findViewById(R.id.correct)
        wrongView = findViewById(R.id.wrong)
        resultView = findViewById(R.id.result)
        timerView = findViewById(R.id.timer)
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        gameOverImage = findViewById(R.id.gameOver)

        gameOverImage.visibility = View.INVISIBLE     // Make invisible Game Over Image when application startup

        // Run the timer function when application startup
        countDownTimer = timer(gameTime)

        // Run the setData function when application startup
        setData()

        val checkedPoints = arrayListOf<Int>()

        // Set click listener of Greater Button
        greaterButton.setOnClickListener {
            if(!isStop[0]) {
                checkAnswerSelection("Greater")
                setData()
            }
            if((0 == (points[0] % 5)) && (points[0]!=0) && !checkedPoints.contains(points[0]) && !isStop[0]) {
                checkedPoints.add(points[0])
                gameTime[0] = gameTime[0] + 10000
                countDownTimer.cancel()
                countDownTimer = timer(gameTime)
            }
        }

        // Set click listener of Equal Button
        equalButton.setOnClickListener {
            if(!isStop[0]) {
                checkAnswerSelection("Equal")
                setData()
            }
            if((0 == (points[0] % 5)) && (points[0]!=0) && !checkedPoints.contains(points[0]) && !isStop[0]) {
                checkedPoints.add(points[0])
                gameTime[0] = gameTime[0] + 10000
                countDownTimer.cancel()
                countDownTimer = timer(gameTime)
            }
        }

        // Set click listener of Lesser Button
        lessButton.setOnClickListener {
            if(!isStop[0]) {
                checkAnswerSelection("Less")
                setData()
            }
            if((0 == (points[0] % 5)) && (points[0]!=0) && !checkedPoints.contains(points[0]) && !isStop[0]) {
                checkedPoints.add(points[0])
                gameTime[0] = gameTime[0] + 10000
                countDownTimer.cancel()
                countDownTimer = timer(gameTime)
            }
        }
    }

    /**
     * This Method [valuesGenerator] Generate Values
     **/
    private fun valuesGenerator(operationDone:Boolean){
        val availableOperations = arrayListOf("+","-","/","*")
        val random = Random

        val termsCount: Int = if(!operationDone) {
            1 + random.nextInt(4)
        }else{
            operations.size+1
        }
        var count = 0
        while (count!=termsCount){
            if (count==0) {
                numbers.add(1 + random.nextInt(20))
            }
            else{
                if (!operationDone){
                    availableOperations.shuffle()
                    operations.add(availableOperations[count])
                }
                numbers.add(1+random.nextInt(20))
            }
            count++
        }
    }

    /**
     * This [expressionGenerator] this method Generate expressions
     **/
    private fun expressionGenerator():String{
        val expression: Any
        val count = numbers.size

        expression = when (count) {
            1 -> {
                "${numbers[0]}"
            }
            2 -> {
                " ${numbers[0]} ${operations[0]} ${numbers[1]}"
            }
            3 -> {
                "( ${numbers[0]} ${operations[0]} ${numbers[1]} ) ${operations[1]} ${numbers[2]}"
            }
            else -> {
                "(( ${numbers[0]} ${operations[0]} ${numbers[1]} ) ${operations[1]} ${numbers[2]} ) ${operations[2]} ${numbers[3]}"
            }
        }
        return expression
    }

    /**
     * This [calculateValues] this Method calculate values
     **/
    private fun calculateValues():Int{
        var total = 0
        var count = 0
        var generating = true

        while(generating) {
            generating = false
            for (i in numbers) {
                if (count == 0 && total == 0) {
                    total += i
                    count--
                } else {
                    if (operations[count] == "+") {
                        total += i
                    } else if (operations[count] == "-") {
                        total -= i
                    } else if (operations[count] == "/") {
                        if (total % i == 0) {
                            total /= i
                        }
                        else {
                            generating = true
                            break
                        }
                    } else if (operations[count] == "*") {
                        total *= i
                    }

                    if (total > 100){
                        generating = true
                        break
                    }
                }
                count++
            }
            if(generating){
                numbers.clear()
                valuesGenerator(true)
                count=0
                total=0
            }
        }
        return total
    }

    /**
     * This [checkAnswerSelection] this Method check the expression values with button
     **/
    @SuppressLint("SetTextI18n")
    private fun checkAnswerSelection(buttonValue: String){
        if(buttonValue == "Greater" && values[0] > values[1]){
            resultView.text = "CORRECT!"
            resultView.setTextColor(Color.rgb(0,200,0))
            points[0]++
        }
        else if(buttonValue == "Equal" && values[0] == values[1]){
            resultView.text = "CORRECT!"
            resultView.setTextColor(Color.rgb(0,200,0))
            points[0]++
        }
        else if(buttonValue == "Less" && values[0] < values[1]){
            resultView.text = "CORRECT!"
            resultView.setTextColor(Color.rgb(0,200,0))
            points[0]++
        }
        else{
            resultView.text = "WRONG!"
            resultView.setTextColor(Color.rgb(200,0,0))
            points[1]++
        }
    }

    /**
     * This [timer] this Method for timer function
     * Reference : https://camposha.info/android-examples/android-timer-libraries/#gsc.tab=0
     *             https://developer.android.com/reference/kotlin/android/os/CountDownTimer
     *             https://stackoverflow.com/questions/54095875/how-to-create-a-simple-countdown-timer-in-kotlin
     **/
    private fun timer(gameTime: ArrayList<Long>):CountDownTimer{
        val timer = object: CountDownTimer(gameTime[0], 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min = (millisUntilFinished / 1000) / 60
                val sec = (millisUntilFinished / 1000) % 60
                val format = String.format("%02d : %02d", min, sec)
                timerView.text = format

                // Set timer colors by count of gameTime
                if(sec>10 || min>0){
                    timerView.setTextColor(Color.rgb(25,0,37))
                }
                else{
                    timerView.setTextColor(Color.rgb(255,0,0))
                }
                gameTime[0] = gameTime[0] - 1000
            }

            /**
             * This [onFinish] method for finish the game
             **/
            @SuppressLint("SetTextI18n")
            override fun onFinish() {

                // Making visible and invisible ui components when operation done
                gameOverImage.visibility = View.VISIBLE
                leftStatement.visibility = View.INVISIBLE
                rightStatement.visibility = View.INVISIBLE
                lessButton.visibility = View.INVISIBLE
                greaterButton.visibility = View.INVISIBLE
                equalButton.visibility = View.INVISIBLE
                textView1.visibility = View.INVISIBLE
                textView2.visibility = View.INVISIBLE
                textView3.visibility = View.INVISIBLE
                timerView.visibility = View.INVISIBLE

                isStop[0] = true
                correctView.text = "CORRECT : "+points[0].toString()
                correctView.setTextColor(Color.rgb(0,180,0))
                wrongView.text = "WRONG : "+points[1].toString()
                wrongView.setTextColor(Color.rgb(180,55,0))
                resultView.text = " "
            }
        }
        timer.start()
        return timer
    }

    /**
     * This [setData] this Method set calculated values and generated expressions
     **/
    private fun setData(){
        valuesGenerator(false)
        values[0] = calculateValues()
        leftStatement.text = expressionGenerator()
        numbers.clear()
        operations.clear()
        valuesGenerator(false)
        values[1] = calculateValues()
        rightStatement.text = expressionGenerator()
        numbers.clear()
        operations.clear()
    }

    /**
     * This Method [onSaveInstanceState] save the necessary data on activity before the rotation
     **/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("operation", operations)
        outState.putIntegerArrayList("number", numbers)
    }

    /**
     * This [onRestoreInstanceState] method Restore necessary data to a bundle after the rotation
     **/
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operations = savedInstanceState.getStringArrayList("operation") as ArrayList<String>
        numbers = savedInstanceState.getIntegerArrayList("number") as ArrayList<Int>
    }
}