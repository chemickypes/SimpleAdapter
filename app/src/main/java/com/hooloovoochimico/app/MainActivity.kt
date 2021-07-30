package com.hooloovoochimico.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.hooloovoochimico.app.databinding.SimpleRowBinding
import com.hooloovoochimico.simpleadapter.binding.SimpleBindingAdapter

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*findViewById<RecyclerView>(R.id.list)?.apply {
            adapter = SimpleAdapter.create()
                .register(R.layout.simple_row, String::class) { s, injector ->
                    injector.text(R.id.simple_text, s)
                }.register(R.layout.simple_row, Int::class) { i, injector ->
                    injector.text(R.id.simple_text, "Intero: $i")
                }
                .register(R.layout.simple_row, Class1::class) { classs, injector ->
                    injector.text(R.id.simple_text, "class: $classs")
                }
                .apply {
                    updateData(listOf("ciao", 1, 5, "sono",Class1("prova Class1",1), "stato", "bravo"))
                }
        }*/

        findViewById<RecyclerView>(R.id.list)?.apply {
            adapter = SimpleBindingAdapter.create()
                .register(String::class, SimpleRowBinding::inflate) { s, binding ->
                    binding.simpleText.text = s
                }
                .register(Int::class, SimpleRowBinding::inflate) { s, binding ->
                    binding.simpleText.text = "Intero: $s"
                }
                .register(Class1::class, SimpleRowBinding::inflate) { classs, binding ->
                    binding.simpleText.text = "class: $classs"
                }
                .apply {
                    updateData(listOf("ciao", 1, 5, "sono",Class1("prova Class1",1), "stato", "bravo"))
                }
        }
    }
}

data class Class1(val s: String, val i: Int)