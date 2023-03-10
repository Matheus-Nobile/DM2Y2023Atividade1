package br.edu.mouralacerda.dm2y2023atividade1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.mouralacerda.dm2y2023atividade1.databinding.ActivityNovoProdutoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NovoProduto : AppCompatActivity() {

    private val b by lazy {
        ActivityNovoProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnSalvar.setOnClickListener {
            val produto = Produto(
                b.edtNome.text.toString(),
                b.edtValor.text.toString().toDouble()
            )
            CoroutineScope(Dispatchers.IO).launch {
                ProdutoDatabase.getInstance(this@NovoProduto).produtoDao().salvar(produto)
                withContext(Dispatchers.Main){
                    finish()
                }
            }
        }
    }
}