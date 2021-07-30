package com.hooloovoochimico.simpleadapter.binding


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
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class SimpleBindingAdapter private constructor() :
    RecyclerView.Adapter<SimpleBindingViewHolder<*>>() {

    val data = mutableListOf<Any>()

    private val constructors = mutableMapOf<KClass<*>, BindingConstructor<*, out ViewBinding>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleBindingViewHolder<*> {

        return if (viewType >= 0) {
            val constructor = constructors[constructors.keys.elementAt(viewType)]
            SimpleBindingViewHolder(
                constructor!!.binderBlock.invoke(LayoutInflater.from(parent.context)),
                constructors.keys.elementAt(viewType)
            )
        } else {
            SimpleBindingViewHolder(
                View(parent.context),
                constructors.keys.elementAt(viewType)
            )
        }

    }

    override fun onBindViewHolder(holder: SimpleBindingViewHolder<*>, position: Int) {
        holder.viewBinding?.let {
            (constructors[holder.clazz] as BindingConstructor<Any, ViewBinding>).block.invoke(
                data[position], it
            )
        }
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

    fun <D : Any, T : ViewBinding> register(
        clazz: KClass<D>,
        binderBlock: (LayoutInflater) -> T,
        block: (D, T) -> Unit
    ): SimpleBindingAdapter {

        constructors[clazz] = BindingConstructor(binderBlock, block)

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
        fun create() = SimpleBindingAdapter()
    }

}

class SimpleBindingViewHolder<D : Any> : RecyclerView.ViewHolder {

    var clazz: KClass<D>
    var viewBinding: ViewBinding? = null

    constructor(
        view: ViewBinding,
        clazz: KClass<D>
    ) : super(view.root) {
        this.clazz = clazz
        this.viewBinding = view
    }

    constructor(view: View, clazz: KClass<D>) : super(view) {
        this.clazz = clazz
    }


}

internal class BindingConstructor<D : Any, T : ViewBinding>(
    val binderBlock: (LayoutInflater) -> T,
    val block: (D, T) -> Unit
)