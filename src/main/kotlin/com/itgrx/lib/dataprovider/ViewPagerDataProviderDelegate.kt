package com.itgrx.lib.dataprovider

import android.support.v4.view.PagerAdapter
import io.reactivex.disposables.Disposable

class ViewPagerDataProviderDelegate(
        private val adapter: PagerAdapter,
        private val provider: DataProvider
) {
    private var subscription: Disposable? = null

    fun create() {
        subscription = provider.updates().subscribe({ adapter.notifyDataSetChanged() })
    }

    fun destroy() {
        subscription!!.dispose()
    }

}