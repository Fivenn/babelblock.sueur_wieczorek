package fr.enssat.babelblock.sueur_wieczorek.tools.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface ItemMoveAdapater {
    fun onRowMoved(from: Int, to: Int)
    fun onRowSelected(viewHolder: RecyclerView.ViewHolder)
    fun onRowReleased(viewHolder: RecyclerView.ViewHolder)
}

object BlockTranslatorChainMoveHelper {
    fun create(adapter: ItemMoveAdapater) = ItemTouchHelper(ItemMoveCallback(adapter))
}

private class ItemMoveCallback(private val adapter: ItemMoveAdapater) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = true
    override fun isItemViewSwipeEnabled() = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int = makeMovementFlags(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.run { adapter.onRowSelected(this) }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        adapter.onRowReleased(viewHolder)
    }
}