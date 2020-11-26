package fr.enssat.babelblock.sueur_wieczorek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var translator: TranslationTool
    lateinit var speaker: TextToSpeechTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val service = BabelBlockService(this)

        // Translator
        translator = service.translator(Locale.FRENCH, Locale.ENGLISH)
        translateButton.setOnClickListener {
            translator.translate(sourceText.text.toString()) { translatedText ->
                this.translatedText.setText(translatedText)
            }
        }

        // Text To Speech
        speaker = service.textToSpeech()
        volumeButton.setOnClickListener {
            val text = translatedText.text.toString()
            speaker.speak(text)
        }
    }

        override fun onDestroy() {
            super.onDestroy()

            translator.close()
            speaker.close()
        }
    }