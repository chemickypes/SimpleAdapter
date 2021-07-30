package com.hooloovoochimico.simpleadapter.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class SlimBindingAdapter private constructor() :
    RecyclerView.Adapter<SlimBindingViewHolder<*>>() {

    val data = mutableListOf<Any>()

    private val constructors = mutableMapOf<KClass<*>, BindingConstructor<*, out ViewBinding>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlimBindingViewHolder<*> {

        return if (viewType >= 0) {
            val constructor = constructors[constructors.keys.elementAt(viewType)]
            SlimBindingViewHolder(
                constructor!!.binderBlock.invoke(LayoutInflater.from(parent.context)),
                constructors.keys.elementAt(viewType)
            )
        } else {
            SlimBindingViewHolder(
                View(parent.context),
                constructors.keys.elementAt(viewType)
            )
        }

    }

    override fun onBindViewHolder(holder: SlimBindingViewHolder<*>, position: Int) {
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
    ): SlimBindingAdapter {

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
        fun create() = SlimBindingAdapter()
    }

}

class SlimBindingViewHolder<D : Any> : RecyclerView.ViewHolder {

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