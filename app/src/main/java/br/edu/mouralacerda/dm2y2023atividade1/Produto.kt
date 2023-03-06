package br.edu.mouralacerda.dm2y2023atividade1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Produto(
    val nome: String,
    val valor: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
){
    override fun toString(): String {
        return "Produto = $nome / Valor = $valor"
    }
}