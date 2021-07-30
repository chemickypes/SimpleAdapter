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

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Injector(private val itemView: View) {

    @Suppress("UNCHECKED_CAST")
    fun <V : View> with(id: Int, action: (V) -> Unit): Injector {
        action(findViewById<View>(id) as V)
        return this
    }

    fun <T : View> findViewById(id: Int): T? {
        return itemView.findViewById(id)
    }

    fun tag(id: Int, `object`: Any): Injector {
        findViewById<View>(id)?.tag = `object`
        return this
    }

    fun text(id: Int, res: Int): Injector {
        val view = findViewById<TextView>(id)
        view?.setText(res)
        return this
    }

    fun text(id: Int, charSequence: CharSequence): Injector {
        val view = findViewById<TextView>(id)
        view?.text = charSequence
        return this
    }

    fun typeface(id: Int, typeface: Typeface, style: Int): Injector {
        val view = findViewById<TextView>(id)
        view?.setTypeface(typeface, style)
        return this
    }

    fun typeface(id: Int, typeface: Typeface): Injector {
        val view = findViewById<TextView>(id)
        view?.typeface = typeface
        return this
    }

    fun textColor(id: Int, color: Int): Injector {
        val view = findViewById<TextView>(id)
        view?.setTextColor(color)
        return this
    }

    fun textSize(id: Int, sp: Int): Injector {
        val view = findViewById<TextView>(id)
        view?.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp.toFloat())
        return this
    }

    fun alpha(id: Int, alpha: Float): Injector {
        val view = findViewById<View>(id)
        view?.alpha = alpha
        return this
    }

    fun image(id: Int, res: Int): Injector {
        val view = findViewById<ImageView>(id)
        view?.setImageResource(res)
        return this
    }

    fun image(id: Int, drawable: Drawable): Injector {
        val view = findViewById<ImageView>(id)
        view?.setImageDrawable(drawable)
        return this
    }

    fun background(id: Int, res: Int): Injector {
        val view = findViewById<View>(id)
        view?.setBackgroundResource(res)
        return this
    }

    fun background(id: Int, drawable: Drawable): Injector {
        val view = findViewById<View>(id)
        view?.background = drawable
        return this
    }

    fun visible(id: Int): Injector {
        findViewById<View>(id)?.visibility = View.VISIBLE
        return this
    }


    fun invisible(id: Int): Injector {
        findViewById<View>(id)?.visibility = View.INVISIBLE
        return this
    }

    fun gone(id: Int): Injector {
        findViewById<View>(id)?.visibility = View.GONE
        return this
    }

    fun visibility(id: Int, visibility: Int): Injector {
        findViewById<View>(id)?.visibility = visibility
        return this
    }


    fun clicked(id: Int, listener: View.OnClickListener): Injector {
        findViewById<View>(id)?.setOnClickListener(listener)
        return this
    }

    fun clicked(id: Int, listenr: (View) -> Unit): Injector {
        findViewById<View>(id)?.setOnClickListener {
            listenr(it)
        }
        return this
    }

    fun longClicked(id: Int, listener: View.OnLongClickListener): Injector {
        findViewById<View>(id)?.setOnLongClickListener(listener)
        return this
    }

    fun enable(id: Int, enable: Boolean): Injector {
        findViewById<View>(id)?.isEnabled = enable
        return this
    }

    fun enable(id: Int): Injector {
        findViewById<View>(id)?.isEnabled = true
        return this
    }

    fun disable(id: Int): Injector {
        findViewById<View>(id)?.isEnabled = false
        return this
    }


    fun selected(id: Int, selected: Boolean): Injector {
        findViewById<View>(id)?.isSelected = selected
        return this
    }

    fun pressed(id: Int, pressed: Boolean): Injector {
        findViewById<View>(id)?.isPressed = pressed
        return this
    }

    fun adapter(id: Int, adapter: RecyclerView.Adapter<*>): Injector {
        val view = findViewById<RecyclerView>(id)
        view?.adapter = adapter
        return this
    }


    fun layoutManager(id: Int, layoutManager: RecyclerView.LayoutManager): Injector {
        val view = findViewById<RecyclerView>(id)
        view?.layoutManager = layoutManager
        return this
    }

    fun addView(id: Int, vararg views: View): Injector {
        val viewGroup = findViewById<ViewGroup>(id)
        for (view in views) {
            viewGroup?.addView(view)
        }
        return this
    }

    fun addView(id: Int, view: View, params: ViewGroup.LayoutParams): Injector {
        val viewGroup = findViewById<ViewGroup>(id)
        viewGroup?.addView(view, params)
        return this
    }

    fun removeAllViews(id: Int): Injector {
        val viewGroup = findViewById<ViewGroup>(id)
        viewGroup?.removeAllViews()
        return this
    }

    fun removeView(id: Int, view: View): Injector {
        val viewGroup = findViewById<ViewGroup>(id)
        viewGroup?.removeView(view)
        return this
    }
}
