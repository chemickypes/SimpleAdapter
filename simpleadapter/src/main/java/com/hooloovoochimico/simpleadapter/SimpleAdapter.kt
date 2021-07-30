package com.hooloovoochimico.simpleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

class SimpleAdapter private constructor() :
    RecyclerView.Adapter<SimpleViewHolder<*>>() {

    val data = mutableListOf<Any>()

    private val constructors = mutableMapOf<KClass<*>, SimpleHolderConstructor<*>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<*> {
        val constructor =
            if (viewType >= 0) constructors[constructors.keys.elementAt(viewType)] else null
        return SimpleViewHolder(
            LayoutInflater.from(parent.context).inflate(constructor?.layout ?: 0, parent, false),
            constructors.keys.elementAt(viewType)
        )
    }


    override fun onBindViewHolder(holder: SimpleViewHolder<*>, position: Int) {
        (constructors[holder.clazz] as SimpleHolderConstructor<Any>).injectionBlock.invoke(
            data[position],
            Injector(holder.itemView)
        )
    }

    override fun getItemCount(): Int = data.size

    private fun getDataAt(position: Int): Any = data[position]

    override fun getItemViewType(position: Int): Int {
        val data = try {
            getDataAt(position)::class
        } catch (e: NullPointerException) {
            Any::class
        }

        val type = constructors.keys.indexOf(data)
        return if (type < 0) constructors.keys.indexOf(Any::class) else type
    }

    fun <T : Any> register(
        layout: Int,
        clazz: KClass<T>,
        injector: (T, Injector) -> Unit
    ): SimpleAdapter {
        SimpleHolderConstructor(layout, injector).also { constructors[clazz] = it }

        return this
    }

    fun registerDefault(layout: Int, injector: (Any, Injector) -> Unit): SimpleAdapter {
        SimpleHolderConstructor(layout, injector).also { constructors[Any::class] = it }

        return this
    }

    fun updateData(datas: List<*>) {
        data.clear()
        datas.forEach { el ->
            el?.let {
                data.add(it)
            }
        }
        notifyDataSetChanged()
    }

    companion object {
        fun create() = SimpleAdapter()
    }
}