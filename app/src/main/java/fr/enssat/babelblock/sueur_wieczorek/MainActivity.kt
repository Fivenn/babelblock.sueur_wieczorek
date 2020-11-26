package fr.enssat.babelblock.sueur_wieczorek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var translator: TranslationTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val service = BabelBlockService(this)
        translator = service.translator(Locale.FRENCH, Locale.ENGLISH)

        translateButton.setOnClickListener {
            translator.translate(sourceText.text.toString()) { translatedText ->
                this.translatedText.setText(translatedText)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        translator.close()
    }
}