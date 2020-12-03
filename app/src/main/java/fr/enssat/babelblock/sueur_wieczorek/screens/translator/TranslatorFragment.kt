package fr.enssat.babelblock.sueur_wieczorek.screens.translator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.enssat.babelblock.sueur_wieczorek.R
import fr.enssat.babelblock.sueur_wieczorek.databinding.TranslatorFragmentBinding
import fr.enssat.babelblock.sueur_wieczorek.tools.Language

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
            viewModel.availableLanguages.sorted()
        )

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.translator_fragment,
            container,
            false
        )
        binding.translateButton.setOnClickListener {
            viewModel.onTranslate(binding.sourceText.text.toString()) {
                binding.translatedText.setText(it)
            }
        }
        binding.volumeButton.setOnClickListener { onTextToSpeech() }

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

    /** Methods for buttons presses **/

    private fun onTextToSpeech() {
        viewModel.textToSpeech.speak(binding.translatedText.text.toString())
    }
}