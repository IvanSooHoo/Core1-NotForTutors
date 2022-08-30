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
    lateinit var mediaPlayer: MediaPlayer
    lateinit var clicks: MediaPlayer

    // no need to call prepare(); create() does that for you
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val score = findViewById<Button>(R.id.Score)
        val steal = findViewById<Button>(R.id.Steal)
        val Reset = findViewById<Button>(R.id.Reset)
        val result = findViewById<TextView>(R.id.Result)
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.battle_crowd_celebration)
        clicks = MediaPlayer.create(applicationContext, R.raw.click)
        // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.i(
            "LIFECYCLE",
            "MediaPlayer Successfully Created $mediaPlayer"
        )//check for successful creation
        Log.i(
            "LIFECYCLE",
            "MediaPlayer Successfully Created $clicks"
        )//check for successful creation

        savedInstanceState?.let {
            _score = it.getInt("SCORE")
            result.text = _score.toString()
        }

        score.setOnClickListener() {
            clicks.start()
            if (_score in 0..14) {
                _score = Add()

                Log.i(
                    "LIFECYCLE",
                    "is CLICKS on score button playing? ${clicks.isPlaying}"
                )//checks to see if sound is playing upon score click
                Log.i(
                    "LIFECYCLE",
                    "MediaPlayer is still $mediaPlayer"
                )//this is to make sure that everytime click it will point to the same media player


                if (_score in 0..4) {
                    result.setTextColor(Color.BLACK)
                }
                if (_score in 5..9) {
                    result.setTextColor(Color.GREEN)
                }
                if (_score in 10..15) {
                    result.setTextColor(Color.BLUE)
                }
                if (_score == 15) {
                    mediaPlayer.seekTo(0)
                    mediaPlayer.setVolume(10F, 20F)
                    mediaPlayer.start()
                    Log.i("LIFECYCLE", " IS IT PLAYING? ${mediaPlayer.isPlaying}")
                    Log.i("LIFECYCLE", "The media has played successfully")
                }

            }
            result.text = _score.toString()

        }
        steal.setOnClickListener() {
            clicks.start()
            Log.i(
                "LIFECYCLE",
                "is CLICKS on steal button playing? ${clicks.isPlaying}"
            )//checks to see if CLICKS is playing upon button click
            if (_score in 1..15) {
                _score = Steal()
                if (_score in 0..4) {
                    result.setTextColor(Color.BLACK)
                }
                if (_score in 5..9) {
                    result.setTextColor(Color.GREEN)
                }
                if (_score in 10..15) {
                    result.setTextColor(Color.BLUE)
                }

            }
            result.text = _score.toString()

        }
        Reset.setOnClickListener()
        {
            _score = reset()
            mediaPlayer.stop()//stops the media player
            mediaPlayer.prepare()//prepares media player for next play.
            clicks.start()
            Log.i(
                "LIFECYCLE",
                "is CLICKS on reset button playing? ${clicks.isPlaying}"
            )//test whether sound is playing upon reset click
            Log.i(
                "LIFECYCLE",
                "IS MEDIAPLAYER PLAYING? : ${mediaPlayer.isPlaying}"
            )//test to see if MEDIAplayer is still playing

            if (_score in 0..4) {
                result.setTextColor(Color.BLACK)
            }
            if (_score in 5..9) {
                result.setTextColor(Color.GREEN)
            }
            if (_score in 10..15) {
                result.setTextColor(Color.BLUE)
            }
            result.text = _score.toString()
        }


    }


    private fun Add() = _score + 1
    private fun Steal() = _score - 1
    private fun reset() = _score - _score

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer.release()
            clicks.release()
            Log.i("LIFECYCLE", "$mediaPlayer is successfully destroyed")
            Log.i("LIFECYCLE", "$clicks is successfully destroyed")

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCORE", _score)
        Log.i("LIFECYCLE", "Saved Score: $_score")
    }

}