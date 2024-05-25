package br.luizfilipe.orgs.data.model

import org.junit.Assert.*

import org.junit.Test

class UserTests {

    @Test
    fun `deve retornar true se email e senha forem validos`() {
        val usuarioValido = User(
            nome = "luiz",
            email = "luizkato@gmail.com",
            senha = "Cimento789@",
            telefone = "6765756756"
        )

        val result = usuarioValido.ehValido

        assertTrue(result)
    }

    @Test
    fun `deve retornar false se email for invalido`(){
        val usuarioInvalido = User(
            nome = "luiz",
            email = "luizkatogmail.com",
            senha = "Cimento789@",
            telefone = "6765756756"
        )

        val result = usuarioInvalido.ehValido

        assertFalse(result)
    }

    @Test
    fun `deve retornar false se senha for invalido`(){
        val usuarioInvalido = User(
            nome = "luiz",
            email = "luizkatogmail.com",
            senha = "12345",
            telefone = "6765756756"
        )

        val result = usuarioInvalido.ehValido

        assertFalse(result)
    }
}