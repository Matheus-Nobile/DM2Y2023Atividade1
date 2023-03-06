package br.edu.mouralacerda.dm2y2023atividade1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProdutoDAO {

    @Insert
    fun salvar(produto: Produto)

    @Query("SELECT * FROM Produto")
    fun listar(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.valor")
    fun listarPorPrecoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.valor DESC")
    fun listarPorPrecoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.nome")
    fun listarPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY Produto.nome DESC")
    fun listarPorNomeDesc(): List<Produto>

    @Delete
    fun deleteProduto(produto: Produto)
}