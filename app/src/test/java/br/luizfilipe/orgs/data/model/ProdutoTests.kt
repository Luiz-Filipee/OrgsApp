package br.luizfilipe.orgs.data.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class ProdutoTests {

    @Test
    fun `deve retornar verdadeiro quando o valor for valido`() {

        val produtoValido = Produto(
            nome = "melancia",
            descricao = "melancia da orta",
            valor = BigDecimal("30")
        )

        val valorValido = produtoValido.valorEhValido

        valorValido
        assertTrue(valorValido)
    }

    @Test
    fun `deve retornar falso quando o valor for maior que cem`() {
        // Arrange - arrumar
        val produto = Produto(
            nome = "melancia",
            descricao = "melancia da orta",
            valor = BigDecimal("110.99")
        )

        // Act - agir
        val valorValido = produto.valorEhValido

        // Asert - afirme
        assertFalse(valorValido)
    }

    @Test
    fun `deve retornar falso quando o valor for menor ou igual a zero`() {
        // Arrange - arrumar
        val produtoComValorIgualAZero = Produto(
            nome = "melancia",
            descricao = "melancia da orta",
            valor = BigDecimal("0.0")
        )
        val produtoComValorMenorQueZero = Produto(
            nome = "melancia",
            descricao = "melancia da orta",
            valor = BigDecimal("-10.0")
        )

        // Act - agir
        val valoIgualAZeroEhValido = produtoComValorIgualAZero.valorEhValido
        val valorMenorQueZeroEhValido = produtoComValorIgualAZero.valorEhValido

        // Asert - afirme
        assertFalse(valoIgualAZeroEhValido)
        assertFalse(valorMenorQueZeroEhValido)
    }

}