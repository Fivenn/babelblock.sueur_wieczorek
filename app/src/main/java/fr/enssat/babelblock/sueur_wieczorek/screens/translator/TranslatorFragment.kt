package fr.enssat.babelblock.sueur_wieczorek.screens.translator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.enssat.babelblock.sueur_wieczorek.R
import fr.enssat.babelblock.sueur_wieczorek.databinding.TranslatorFragmentBinding

class TranslatorFragment : Fragment() {

    private lateinit var viewModel: TranslatorViewModel
    private lateinit var binding: TranslatorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TranslatorViewModel::class.java)

        val adapater = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.availableLanguages
        )

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.translator_fragment,
            container,
            false
        )
        binding.translateButton.setOnClickListener { onTranslate() }
        binding.volumeButton.setOnClickListener { onTextToSpeech() }
        binding.sourceLanguage.adapter = adapater
        binding.targetLanguage.adapter = adapater

        return binding.root
    }

    /** Methods for buttons presses **/

    private fun onTranslate() {
        viewModel.translator.translate(binding.sourceText.text.toString()) { translatedText ->
            binding.translatedText.setText(translatedText)
        }
    }

    private fun onTextToSpeech() {
        viewModel.textToSpeech.speak(binding.translatedText.text.toString())
    }
}