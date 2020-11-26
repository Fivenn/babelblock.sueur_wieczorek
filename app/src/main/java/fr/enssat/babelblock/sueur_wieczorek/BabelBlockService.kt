package fr.enssat.babelblock.sueur_wieczorek

import android.content.Context
import java.util.*

interface TranslationTool {
    fun translate(text: String, callback: (String) -> Unit)
    fun close()
}

class BabelBlockService(val context: Context) {
    fun translator(from: Locale, to: Locale): TranslationTool = TranslatorHandler(context.applicationContext, from, to)
}