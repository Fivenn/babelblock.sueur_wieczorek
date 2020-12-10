package fr.enssat.babelblock.sueur_wieczorek.screens.translator

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.enssat.babelblock.sueur_wieczorek.R
import fr.enssat.babelblock.sueur_wieczorek.SpeechToTextTool
import fr.enssat.babelblock.sueur_wieczorek.databinding.TranslatorFragmentBinding
import fr.enssat.babelblock.sueur_wieczorek.tools.Language
import kotlinx.android.synthetic.main.translator_fragment.*
import java.util.*

class TranslatorFragment : Fragment() {

    private lateinit var viewModel: TranslatorViewModel
    private lateinit var binding: TranslatorFragmentBinding
    private val recordAudioRequestCode = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TranslatorViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.translator_fragment,
            container,
            false
        )

        // Translate button
        binding.translateButton.setOnClickListener {
            viewModel.translate(binding.sourceText.text.toString()) {
                binding.translatedText.setText(it)
            }
        }

        // Volume and mic button
        binding.volumeButton.setOnClickListener { onTextToSpeech() }
        binding.micButton.setOnClickListener { onSpeechToText() }

        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }

        // Swap language button
        binding.swapLanguageButton.setOnClickListener {
            val sourceLanguagePosition = binding.sourceLanguage.selectedItemPosition
            binding.sourceLanguage.setSelection(binding.targetLanguage.selectedItemPosition)
            binding.targetLanguage.setSelection(sourceLanguagePosition)
        }

        // Spinner language adapter
        val adapater = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.availableLanguages.sorted()
        )

        // Source language spinner
        binding.sourceLanguage.adapter = adapater
        binding.sourceLanguage.setSelection(adapater.getPosition(Language("fr")))
        binding.sourceLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.from = adapater.getItem(position)?.code.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.translatedText.setText("")
                }
            }

        // Targeted language spinner
        binding.targetLanguage.adapter = adapater
        binding.targetLanguage.setSelection(adapater.getPosition(Language("en")))
        binding.targetLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.to = adapater.getItem(position)?.code.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.translatedText.setText("")
                }

            }

        return binding.root
    }

    /**
     *
     */
    private fun onTextToSpeech() {
        viewModel.textToSpeech.speak(binding.translatedText.text.toString(), Locale(viewModel.to))
    }

    private fun onSpeechToText() {
        micButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.d("Reco UI", "Button pressed")
                v.performClick()
                viewModel.speechToText.start(object : SpeechToTextTool.Listener {
                    override fun onResult(text: String, isFinal: Boolean) {
                        Log.d("DebugReco", Boolean.toString())
                        if (isFinal) {
                            binding.sourceText.setText(text)
                        }
                    }
                })
            } else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Reco UI", "Button releases")
                viewModel.speechToText.stop()
            }
            false
        }
    }

    /**
     *
     */
    private fun checkPermission() {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(Manifest.permission.RECORD_AUDIO),
            recordAudioRequestCode
        )
    }

    /**
     *
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == recordAudioRequestCode && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this.requireContext(), "Permission Granted", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}