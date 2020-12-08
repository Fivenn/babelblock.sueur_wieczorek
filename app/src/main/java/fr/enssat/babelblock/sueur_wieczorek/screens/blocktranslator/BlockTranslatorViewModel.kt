package fr.enssat.babelblock.sueur_wieczorek.screens.blocktranslator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.mlkit.nl.translate.TranslateLanguage
import fr.enssat.babelblock.sueur_wieczorek.tools.*
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslator
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslatorChain
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslatorDisplay
import fr.enssat.babelblock.sueur_wieczorek.tools.workers.TranslateWorker

class BlockTranslatorViewModel(application: Application) : AndroidViewModel(application) {

    val availableLanguages: List<Language> = TranslateLanguage.getAllLanguages()
        .map { Language(it) }
    val blockTranslatorChain = BlockTranslatorChain()
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        workManager.pruneWork()
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    fun createBlockTranslatorAt(index: Int) = object : BlockTranslatorDisplay {
        override var blockLanguage = availableLanguages.sorted()[index]
        override var blockText = ""
        override val blockTranslator = object : BlockTranslator {
            override fun close() {
                Log.d("Block $blockLanguage", "closed")
            }
        }
    }

    fun translateBlockTranslatorChain() {
        for (index in 1 until blockTranslatorChain.size) {
            val translateRequest = OneTimeWorkRequestBuilder<TranslateWorker>()
                .setInputData(createInputDataForTranslateBuilder(index))
                .addTag(TAG_OUTPUT)
                .build()

            workManager.enqueue(translateRequest)
        }
    }

    private fun createInputDataForTranslateBuilder(index: Int): Data {
        val builder = Data.Builder()
        builder.putString(KEY_PREVIOUS_BLOCK_TEXT, blockTranslatorChain.get(index - 1).blockText)
        builder.putString(
            KEY_PREVIOUS_BLOCK_LANGUAGE,
            blockTranslatorChain.get(index - 1).blockLanguage.code
        )
        builder.putString(KEY_BLOCK_LANGUAGE, blockTranslatorChain.get(index).blockLanguage.code)
        builder.putInt(KEY_BLOCK_INDEX, index)

        return builder.build()
    }

    fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            val workInfo = listOfWorkInfo.last()

            if (workInfo.state.isFinished) {
                val blockIndex = workInfo.outputData.getInt(KEY_BLOCK_INDEX, -1)
                val blockText = workInfo.outputData.getString(KEY_BLOCK_TEXT)

                if (blockIndex != -1 && !blockText.isNullOrEmpty()) {
                    blockTranslatorChain.get(blockIndex).blockText = blockText
                }
            }
        }
    }
}