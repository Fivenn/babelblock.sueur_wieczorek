package fr.enssat.babelblock.sueur_wieczorek.tools.impl

import android.content.Context
import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import fr.enssat.babelblock.sueur_wieczorek.TranslationTool
import java.util.*

class TranslatorHandler(context: Context, from: Locale, to: Locale) : TranslationTool {

    private val options = TranslatorOptions.Builder()
            .setSourceLanguage(from.language)
            .setTargetLanguage(to.language)
            .build()

    private val translator = Translation.getClient(options)

    private val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

    init {
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener { Log.d("Translation", "download completed") }
                .addOnFailureListener { e -> Log.e("Translation", "Download failed", e) }
    }

    override fun translate(text: String, callback: (String) -> Unit) {
        translator.translate(text)
                .addOnSuccessListener(callback)
                .addOnFailureListener { e -> Log.e("Translation", "Translation failed", e) }
    }

    override fun close() {
        translator.close()
    }

}