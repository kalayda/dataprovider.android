package com.itgrx.lib.dataprovider

import android.support.v7.widget.RecyclerView
import io.reactivex.disposables.Disposable

class RecyclerViewDataProviderDelegate(
        private val adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
        private val provider: DataProvider
) {
    private var subscription: Disposable? = null

    fun create() {
        subscription = provider.updates().subscribe({ event ->
            if (event is DataProvider.Inserted) {
                if (event.count == 1)
                    adapter.notifyItemInserted(event.position)
                else
                    adapter.notifyItemRangeInserted(event.position, event.count)
            }
            if (event is DataProvider.Changed) {
                if (event.count == 1)
                    adapter.notifyItemChanged(event.position)
                else
                    adapter.notifyItemRangeChanged(event.position, event.count)
            }
            if (event is DataProvider.AllChanged) {
                adapter.notifyDataSetChanged()
            }
            if (event is DataProvider.Removed) {
                if (event.count == 1)
                    adapter.notifyItemRemoved(event.position)
                else
                    adapter.notifyItemRangeRemoved(event.position, event.count)
            }
            if (event is DataProvider.Moved) {
                adapter.notifyItemMoved(event.from, event.to)
            }
        })
    }

    fun destroy() {
        subscription!!.dispose()
    }

}