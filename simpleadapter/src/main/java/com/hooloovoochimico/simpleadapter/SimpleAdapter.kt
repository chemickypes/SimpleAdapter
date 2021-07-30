package com.hooloovoochimico.simpleadapter

/*

Copyright 2021 Angelo Moroni

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

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