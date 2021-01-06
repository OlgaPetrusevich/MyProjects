package com.gmail.petrusevich.volha.fitnessapp.common.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer


class CombinedLiveData<R>(
    vararg liveDatas: LiveData<*>,
    private val combine: (data: List<Any?>) -> R
) : MediatorLiveData<R>() {

    private val data: MutableList<Any?> = MutableList(liveDatas.size) { null }

    init {
        for (i in liveDatas.indices) {
            super.addSource(liveDatas[i]) { r ->
                data[i] = r

                if (data.all { it != null }) {
                    value = combine(data)
                }
            }
        }
    }
}

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner, Observer { it?.let { observer.invoke(it) } })
}

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    observe(owner, Observer { it.let { observer.invoke(it) } })
}
