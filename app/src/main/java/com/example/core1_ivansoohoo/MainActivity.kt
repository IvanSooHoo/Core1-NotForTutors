package com.example.core1_ivansoohoo

import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var _score: Int = 0
    var mediaPlayer:MediaPlayer? = null


    // no need to call prepare(); create() does that for you
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val score = findViewById<Button>(R.id.Score)
        val steal = findViewById<Button>(R.id.Steal)
        val Reset = findViewById<Button>(R.id.Reset)
        val result = findViewById<TextView>(R.id.Result)

        savedInstanceState?.let {
            _score = it.getInt("SCORE")
            result.text = _score.toString()
        }


        score.setOnClickListener(){

            if(_score in 0 .. 14){
                _score = Add()
                click()

                if(_score in 0..4){
                    result.setTextColor(Color.BLACK)
                }
                if(_score in 5..9){
                    result.setTextColor(Color.GREEN)
                }
                if(_score in 10..15){
                    result.setTextColor(Color.BLUE)
                }

            }
            result.text = _score.toString()

        }
        steal.setOnClickListener(){

            if(_score in 1 .. 15){
                _score = Steal()
                if(_score in 0..4){
                    result.setTextColor(Color.BLACK)
                }
                if(_score in 5..9){
                    result.setTextColor(Color.GREEN)
                }
                if(_score in 10..15){
                    result.setTextColor(Color.BLUE)
                }

            }
            result.text = _score.toString()

        }
        Reset.setOnClickListener()
        {
            _score = reset()
            result.text = _score.toString()
            if(_score in 0..4){
                result.setTextColor(Color.BLACK)
            }
            if(_score in 5..9){
                result.setTextColor(Color.GREEN)
            }
            if(_score in 10..15){
                result.setTextColor(Color.BLUE)
            }

        }



    }



    private fun Add() = _score + 1
    private fun Steal() = _score - 1
    private fun reset() = _score - _score

    private fun click()
    {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.click)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }else{
            mediaPlayer!!.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if(mediaPlayer != null){
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCORE",_score)
        Log.i("LIFECYCLE","Saved Score: $_score")
    }

}