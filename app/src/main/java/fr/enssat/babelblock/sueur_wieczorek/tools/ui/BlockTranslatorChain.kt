package fr.enssat.babelblock.sueur_wieczorek.tools.ui

import fr.enssat.babelblock.sueur_wieczorek.tools.Language

interface BlockTranslator {
    fun close()
}

interface BlockTranslatorDisplay {
    val blockTranslator: BlockTranslator
    var blockLanguage: Language
    var blockText: String
}

class BlockTranslatorChain(list: List<BlockTranslatorDisplay> = emptyList()) {

    private val list = list.toMutableList()
    val size get() = list.size
    private var onChangeListener: (() -> Unit)? = null

    fun setOnChangeListener(callback: () -> Unit) {
        onChangeListener = callback
    }

    fun get(index: Int) = list[index]

    fun move(from: Int, to: Int) {
        val dragged = list.removeAt(from)
        list.add(to, dragged)
    }

    fun add(blockTranslator: BlockTranslatorDisplay) {
        list.add(blockTranslator)
        onChangeListener?.invoke()
    }
}