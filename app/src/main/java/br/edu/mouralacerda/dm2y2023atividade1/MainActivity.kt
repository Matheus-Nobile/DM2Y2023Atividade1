package br.edu.mouralacerda.dm2y2023atividade1

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.mouralacerda.dm2y2023atividade1.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var i = 1

    private val b by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(b.root)
        atualizarLista()

        b.btnNovoProduto.setOnClickListener {

            startActivity(Intent(this, NovoProduto::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        atualizarLista()
        
        b.lstProduto.setOnItemLongClickListener { adapterView, view, i, l ->
            val c : Produto = adapterView.adapter.getItem(i) as Produto
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Apagar Produto")
                .setMessage("Deseja realmente apagar o produto selecionado?")
                .setPositiveButton("Sim") { dialog, which ->
                    CoroutineScope(Dispatchers.IO).launch {
                        //Executa pararelo à UI thread
                        ProdutoDatabase.getInstance(this@MainActivity).produtoDao().deleteProduto(c)
                        withContext(Dispatchers.Main){
                            //Executa na UI thread
                            atualizarLista()
                        }
                    }
                    Toast.makeText(this, "Produto removido com sucesso!",
                        Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Não") { dialog, which ->
                    Toast.makeText(this, "Produto não removido!",
                        Toast.LENGTH_SHORT).show()
                }
                .show()
            true

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menuOrdenarValor) {

            var lista: List<Produto>? = null

            if(Math.floorMod(i,2) == 0) {
                lista = ProdutoDatabase.getInstance(this).produtoDao().listarPorPrecoDesc()

            } else {
                lista = ProdutoDatabase.getInstance(this).produtoDao().listarPorPrecoAsc()
            }

            b.lstProduto.adapter = ArrayAdapter (this,
                android.R.layout.simple_list_item_1,
                lista
            )
            i++

        }

        if(item.itemId == R.id.menuOrdenarNome) {

            var lista: List<Produto>? = null

            if(Math.floorMod(i,2) == 0) {
                lista = ProdutoDatabase.getInstance(this).produtoDao().listarPorNomeDesc()

            } else {
                lista = ProdutoDatabase.getInstance(this).produtoDao().listarPorNomeAsc()
            }

            b.lstProduto.adapter = ArrayAdapter (this,
                android.R.layout.simple_list_item_1,
                lista
            )
            i++

        }

        if(item.itemId == R.id.menuFechar) {

            finishAffinity()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun atualizarLista() {
        var produtos: List<Produto>

        CoroutineScope(Dispatchers.IO).launch {
            //Executa pararelo à UI thread
            produtos = ProdutoDatabase.getInstance(this@MainActivity).produtoDao().listar()

            withContext(Dispatchers.Main){
                //Executa na UI thread
                b.lstProduto.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    produtos)
            }
        }
    }

}