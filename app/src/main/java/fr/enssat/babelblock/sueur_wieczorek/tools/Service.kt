package fr.enssat.babelblock.sueur_wieczorek

import android.content.Context
import fr.enssat.babelblock.tools.impl.TextToSpeechHandler
import java.util.Locale

interface TextToSpeechTool {
    fun speak(text: String)
    fun stop()
    fun close()
}

class BlockService(val context: Context) {
    fun textToSpeech():TextToSpeechTool {
        val locale = Locale.getDefault()
        return TextToSpeechHandler(context.applicationContext, locale)}

}

