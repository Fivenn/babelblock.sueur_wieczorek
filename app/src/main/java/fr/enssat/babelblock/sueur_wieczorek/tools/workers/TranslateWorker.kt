package fr.enssat.babelblock.sueur_wieczorek.tools.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.android.gms.tasks.Tasks
import fr.enssat.babelblock.sueur_wieczorek.BabelBlockService
import fr.enssat.babelblock.sueur_wieczorek.TranslationTool
import fr.enssat.babelblock.sueur_wieczorek.tools.*
import java.util.*

class TranslateWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val service = BabelBlockService(applicationContext)
        val previousBlockText = inputData.getString(KEY_PREVIOUS_BLOCK_TEXT)
        val previousBlockLanguage = inputData.getString(KEY_PREVIOUS_BLOCK_LANGUAGE)
        var blockText = ""
        val blockLanguage = inputData.getString(KEY_BLOCK_LANGUAGE)
        val blockIndex = inputData.getInt(KEY_BLOCK_INDEX, -1)
        lateinit var translator: TranslationTool
        lateinit var outputData: Data

        return try {
            if (previousBlockLanguage != null && blockLanguage != null && previousBlockText != null) {
                translator =
                    service.translator(Locale(previousBlockLanguage), Locale(blockLanguage))
                blockText = Tasks.await(
                    translator.translate(previousBlockText) {}
                )
            }
            outputData = workDataOf(KEY_BLOCK_TEXT to blockText, KEY_BLOCK_INDEX to blockIndex)

            return Result.success(outputData)
        } catch (error: Throwable) {
            Result.failure()
        } finally {
            translator.close()
        }
    }
}