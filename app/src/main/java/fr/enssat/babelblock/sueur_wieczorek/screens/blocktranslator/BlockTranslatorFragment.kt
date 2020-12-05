package fr.enssat.babelblock.sueur_wieczorek.screens.blocktranslator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.enssat.babelblock.sueur_wieczorek.R
import fr.enssat.babelblock.sueur_wieczorek.databinding.BlockTranslatorFragmentBinding
import fr.enssat.babelblock.sueur_wieczorek.tools.Language
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.*

class BlockTranslatorFragment : Fragment() {

    private lateinit var viewModel: BlockTranslatorViewModel
    private lateinit var binding: BlockTranslatorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(BlockTranslatorViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.block_translator_fragment,
            container,
            false
        )

        // Block translator
        val blockTranslatorChain = BlockTranslatorChain()
        val adapter = BlockTranslatorChainAdapter(blockTranslatorChain)

        val moveHelper = BlockTranslatorChainMoveHelper.create(adapter)
        moveHelper.attachToRecyclerView(binding.blockTranslator)

        binding.blockTranslator.adapter = adapter

        // Languages list
        binding.languagesList.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.availableLanguages.sorted()
        )
        binding.languagesList.setOnItemClickListener { _, _, position, _ ->
            blockTranslatorChain.add(getBlockTranslator(position))
        }

        return binding.root
    }

    private fun getBlockTranslator(index: Int) = object : BlockTranslatorDisplay {
        override var blockSourceLanguage = Language("fr")
        override var blockTargetLanguage = Language("")
        override var blockSourceText = ""
        override var blockTranslatedText = ""
        override val blockTranslator = object : BlockTranslator {
            override fun run() {
            }

            override fun close() {
            }
        }
    }
}