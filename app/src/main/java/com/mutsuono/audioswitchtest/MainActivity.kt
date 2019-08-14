package com.mutsuono.audioswitchtest

import android.media.AudioAttributes
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sp: SoundPool
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        sp = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(2)
            .build()

        val sound = sp.load(this, R.raw.high_sample, 1)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            sp.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)
        }

        val switch = findViewById<Switch>(R.id.switch1)
        switch.setOnCheckedChangeListener{ _, isChecked ->

                var toastTxt = ""

                if (isChecked) {
                    toastTxt = "speaker"
                    forceSpeaker(true)
                } else {
                    toastTxt = "headphone"
                    forceSpeaker(false)
                }

                val toast = Toast.makeText(this@MainActivity, toastTxt, Toast.LENGTH_SHORT)
                toast.show()

                sp.play(sound, 0.0f, 0.0f, 0, 0, 1.0f)
            }
    }

    fun forceSpeaker(on: Boolean) {

        val FOR_MEDIA = 1
        val FORCE_SPEAKER = if (on) 1 else 0

        val audioSystemClass = Class.forName("android.media.AudioSystem")
        val setForceUse =
            audioSystemClass.getMethod("setForceUse", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
        setForceUse.invoke(null, FOR_MEDIA, FORCE_SPEAKER)
    }
}
