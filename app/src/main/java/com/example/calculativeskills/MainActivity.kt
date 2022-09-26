package com.example.calculativeskills

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    // Define ui components
    private lateinit var mainLogo: ImageView
    private lateinit var aboutButton: Button
    private lateinit var newGameButton: Button
    private lateinit var okButtonPopup: Button
    private lateinit var inflater: LayoutInflater
    private lateinit var popupWindow: PopupWindow

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide() // Hide Action Bar when application startup

        // All UI Components of MainActivity Screen
        mainLogo = findViewById(R.id.mainLogo)
        aboutButton = findViewById(R.id.aboutButton)
        newGameButton = findViewById(R.id.newGameButton)

        /**
         * PopUp Window
         * Reference : https://www.android--code.com/2018/02/android-kotlin-popup-window-example.html
         **/
        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_view,null)
        popupWindow = PopupWindow(
            popupView,  // Custom view to show in popup window
            ConstraintLayout.LayoutParams.WRAP_CONTENT,     // Width of popup window
            ConstraintLayout.LayoutParams.WRAP_CONTENT      // Height of popup window
        )
        okButtonPopup = popupView.findViewById(R.id.okButton)

        // Set click listener of About Button
        aboutButton.setOnClickListener {

            mainLogo.visibility = View.INVISIBLE        // Make invisible app mainLogo when clicking the about button
            aboutButton.visibility = View.INVISIBLE     // Make invisible about button when clicking the about button
            newGameButton.visibility = View.INVISIBLE   // Make invisible new game button when clicking the about button

            val rootLayout = findViewById<ConstraintLayout>(R.id.constraint_layout)
            popupWindow.showAtLocation(
                rootLayout,     // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0,  // X offset
                0   // Y offset
            )
        }

        // Set click listener of Ok Button
        okButtonPopup.setOnClickListener{
            popupWindow.dismiss()

            mainLogo.visibility = View.VISIBLE          // Make visible again app mainLogo when clicking the ok button
            aboutButton.visibility = View.VISIBLE       // Make visible again about button when clicking the ok button
            newGameButton.visibility = View.VISIBLE     // Make visible again new game button when clicking the ok button
        }

        // Set click listener of new game Button
        newGameButton.setOnClickListener{
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}