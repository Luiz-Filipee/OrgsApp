package br.luizfilipe.orgs.ui.helpercallback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.ui.adapter.ListaProdutosAdapter


class ProdutoItemTouchHelperCallback(private val adapterItemTouchHelper: ListaProdutosAdapter) :
    ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val movimentRemove = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val movimentReposition =
            ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(movimentReposition, movimentRemove)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val positionInitial = viewHolder.adapterPosition
        val positionFinal = target.adapterPosition
        trocaProdutos(positionInitial, positionFinal)
        return true
    }

    private fun trocaProdutos(positionInitial: Int, positionFinal: Int) {
        adapterItemTouchHelper.troca(positionInitial, positionFinal)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val posicaoDaNotaDeslizada = viewHolder.adapterPosition
        adapterItemTouchHelper.remove(posicaoDaNotaDeslizada)
    }


}
