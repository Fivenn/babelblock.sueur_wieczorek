package fr.enssat.babelblock.sueur_wieczorek

import android.content.Context
import fr.enssat.babelblock.sueur_wieczorek.tools.impl.TranslatorHandler
import fr.enssat.babelblock.tools.impl.TextToSpeechHandler
import java.util.*

interface TranslationTool {
    fun translate(text: String, callback: (String) -> Unit)
    fun close()
}

interface TextToSpeechTool {
    fun speak(text: String)
    fun stop()
    fun close()
}

class BabelBlockService(val context: Context) {
    fun translator(from: Locale, to: Locale): TranslationTool =
        TranslatorHandler(context.applicationContext, from, to)

    fun textToSpeech(): TextToSpeechTool {
        val locale = Locale.getDefault()
        return TextToSpeechHandler(context.applicationContext, locale)
    }
}