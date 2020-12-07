package fr.enssat.babelblock.sueur_wieczorek.tools.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import fr.enssat.babelblock.sueur_wieczorek.R
import kotlinx.android.synthetic.main.block_translator_list.view.*

class BlockTranslatorChainAdapter(private val blockTranslatorChain: BlockTranslatorChain) :
    RecyclerView.Adapter<BlockTranslatorChainAdapter.BlockTranslatorViewHolder>(),
    ItemMoveAdapater {

    init {
        blockTranslatorChain.setOnChangeListener { notifyDataSetChanged() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockTranslatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.block_translator_list, parent, false)
        return BlockTranslatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlockTranslatorViewHolder, position: Int) {
        holder.bind(blockTranslatorChain, position)
    }

    override fun getItemCount(): Int = blockTranslatorChain.size

    override fun onRowMoved(from: Int, to: Int) {
        blockTranslatorChain.move(from, to)
        notifyItemMoved(from, to)
    }

    override fun onRowSelected(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.GRAY)
    }

    override fun onRowReleased(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
        notifyDataSetChanged()
    }

    class BlockTranslatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(blockTranslatorChain: BlockTranslatorChain, index: Int) {
            val blockTranslator = blockTranslatorChain.get(index)
            itemView.blockSourceLanguage.text = blockTranslator.blockLanguage.toString()
            itemView.blockSourceText.setText(blockTranslator.blockText)
            itemView.blockSourceText.doOnTextChanged { text, start, before, count ->
                blockTranslatorChain.get(index).blockText = text.toString()
            }
        }
    }
}