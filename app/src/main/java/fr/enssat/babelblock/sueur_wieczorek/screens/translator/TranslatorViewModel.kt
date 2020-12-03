package fr.enssat.babelblock.sueur_wieczorek.screens.translator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import fr.enssat.babelblock.sueur_wieczorek.BabelBlockService
import fr.enssat.babelblock.sueur_wieczorek.TextToSpeechTool
import fr.enssat.babelblock.sueur_wieczorek.TranslationTool
import fr.enssat.babelblock.sueur_wieczorek.tools.Language
import java.util.*

class TranslatorViewModel(application: Application) : AndroidViewModel(application) {

    var service: BabelBlockService = BabelBlockService(getApplication())
    private lateinit var translator: TranslationTool
    var textToSpeech: TextToSpeechTool
    val availableLanguages: List<Language> = TranslateLanguage.getAllLanguages()
        .map { Language(it) }
    var from: String = ""
    var to: String = ""

    init {
        textToSpeech = service.textToSpeech()
    }

    fun onTranslate(sourceText: String, callback: (String) -> Unit) {
        translator = service.translator(Locale(from), Locale(to))
        translator.translate(sourceText) { translatedText ->
            callback(translatedText)
            translator.close()
        }
    }

    override fun onCleared() {
        super.onCleared()

        translator.close()
        textToSpeech.close()
    }
}