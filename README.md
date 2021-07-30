# SimpleAdapter
RecyclerView.Adapter with no boilerplate

## 📢📢📢 This project is still under development

### Examples
Use Simple adapter is Simple:
``` kotlin
findViewById<RecyclerView>(R.id.list)?.apply {
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
                    updateData(listOf("ciao", 1, 5, "sono",
                        Class1("prova Class1",1), "stato", "bravo"))
                }
        }
```

SimpleAdapter loves ViewBinding:
``` kotlin
binding.list.apply {
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
                    updateData(listOf("ciao", 1, 5, "sono",
                        Class1("prova Class1",1), "stato", "bravo"))
                }
        }
```

SimpleAdapter is ***pure Kotlin***.

The idea on which it is based oh the [SlimAdapter](https://github.com/linisme/SlimAdapter) by linisme.

### TODO
* Infinite scroll
* Testing
* The sky is limit


