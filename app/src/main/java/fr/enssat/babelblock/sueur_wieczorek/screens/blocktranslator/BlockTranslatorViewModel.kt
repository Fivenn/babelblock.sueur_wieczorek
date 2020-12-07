package fr.enssat.babelblock.sueur_wieczorek.screens.blocktranslator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import fr.enssat.babelblock.sueur_wieczorek.tools.Language
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslator
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslatorChain
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslatorDisplay

class BlockTranslatorViewModel(application: Application) : AndroidViewModel(application) {

    val availableLanguages: List<Language> = TranslateLanguage.getAllLanguages()
        .map { Language(it) }
    val blockTranslatorChain = BlockTranslatorChain()

    fun createBlockTranslatorAt(index: Int) = object : BlockTranslatorDisplay {
        override var blockLanguage = availableLanguages.sorted()[index]
        override var blockText = ""
        override val blockTranslator = object : BlockTranslator {
            override fun close() {
                Log.d("Block $blockLanguage", "closed")
            }
        }
    }
}