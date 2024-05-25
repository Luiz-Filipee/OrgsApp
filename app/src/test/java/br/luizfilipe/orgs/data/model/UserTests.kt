package br.luizfilipe.orgs.data.model

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class UserTests {

    @Test
    fun `deve retornar true se email e senha forem validos`() {
        val userValid = User(
            nome = "luiz",
            email = "luizkato@gmail.com",
            senha = "Cimento789@",
            telefone = "6765756756"
        )

        val emailAndPasswordIsValid = userValid.ehValido

        emailAndPasswordIsValid.shouldBeTrue()
    }

    @Test
    fun `deve retornar false se email for invalido`() {
        val userInvalid = User(
            nome = "luiz",
            email = "luizkatogmail.com",
            senha = "Cimento789@",
            telefone = "6765756756"
        )

        val emailIsInvalid = userInvalid.ehValido

        emailIsInvalid.shouldBeFalse()
    }

    @Test
    fun `deve retornar false se senha for invalido`() {
        val userInvalid = User(
            nome = "luiz",
            email = "luizkatogmail.com",
            senha = "12345",
            telefone = "6765756756"
        )

        val passwordIsInvalid = userInvalid.ehValido

        passwordIsInvalid.shouldBeFalse()
    }
}