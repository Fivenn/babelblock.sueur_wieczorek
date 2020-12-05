package fr.enssat.babelblock.sueur_wieczorek.screens.blocktranslator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import fr.enssat.babelblock.sueur_wieczorek.BabelBlockService
import fr.enssat.babelblock.sueur_wieczorek.TranslationTool
import fr.enssat.babelblock.sueur_wieczorek.tools.Language

class BlockTranslatorViewModel(application: Application) : AndroidViewModel(application) {

    var service: BabelBlockService = BabelBlockService(getApplication())
    val availableLanguages: List<Language> = TranslateLanguage.getAllLanguages()
        .map { Language(it) }
}