package com.hooloovoochimico.simpleadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

class SimpleViewHolder<T: Any>(
    view: View,
    val clazz: KClass<T>
) : RecyclerView.ViewHolder(view)

class SimpleHolderConstructor< T : Any>
    (val layout: Int, val injectionBlock: (data: T, injector: Injector) -> Unit)