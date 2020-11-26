package fr.enssat.babelblock.sueur_wieczorek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }*/


    lateinit var speaker: TextToSpeechTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tts
        val service = BlockService(this)
        speaker = service.textToSpeech()

        volumeButton.setOnClickListener {
            val text = translatedText.text.toString()
            speaker.speak(text)
        }
    }

    override fun onDestroy() {
        //tts
        speaker.close()
        super.onDestroy()
    }

}