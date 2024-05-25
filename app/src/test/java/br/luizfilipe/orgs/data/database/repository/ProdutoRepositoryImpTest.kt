package br.luizfilipe.orgs.data.database.repository

import br.luizfilipe.orgs.data.database.dao.LocalProdutoDataSource
import br.luizfilipe.orgs.data.model.Produto
import br.luizfilipe.orgs.di.modules.repositoriesTestModules
import junit.framework.Assert.assertNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.math.BigDecimal
import kotlin.test.assertEquals
import org.amshove.kluent.shouldBeTrue
import kotlin.test.assertTrue


class ProdutoRepositoryImpTest : KoinTest {

    private val localProdutoDataSource: LocalProdutoDataSource by inject()

    @Before
    fun setUp() {

        startKoin {

            modules(repositoriesTestModules)

        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun deveRetornarUmProdutoCasoSejaSalvoCorretamente() {

        runBlocking {
            val produto = Produto(
                nome = "Produto teste",
                descricao = "descricao do produto teste",
                valor = BigDecimal(10.0),
                imagem = null,
                usuarioId = 1L
            )

            CoroutineScope(Dispatchers.IO).launch {
                localProdutoDataSource.salva(produto)
            }

            val produtoSalvo = localProdutoDataSource.buscaPorId(produto.id).firstOrNull()

            assertEquals(produto, produtoSalvo)

        }
    }

    @Test
    fun deveRetornarTrueCasoSejaRemovidoCorretamente() {

        runBlocking {

            // Arrange
            val produto = Produto(
                nome = "Produto teste",
                descricao = "descricao do produto teste",
                valor = BigDecimal(10.0),
                imagem = null,
                usuarioId = 1L
            )

            // Act
            CoroutineScope(Dispatchers.IO).launch {
                localProdutoDataSource.salva(produto)
            }

            CoroutineScope(Dispatchers.IO).launch {
                localProdutoDataSource.remove(produto)
            }

            delay(100)

            val produtoRemovido = localProdutoDataSource.buscaPorId(produto.id).firstOrNull()

            // Asert
            assertNull(produtoRemovido)

        }
    }

    @Test
    fun deveRetornarTrueCasoSeUmProdutoForEncontrado() {

        runBlocking {

            // Arrange
            val produto = Produto(
                nome = "ProdutoBuscado",
                descricao = "descricao do produto teste",
                valor = BigDecimal(10.0),
                imagem = null,
                usuarioId = 1L
            )

            // Act
            CoroutineScope(Dispatchers.IO).launch {
                localProdutoDataSource.salva(produto)
            }

            delay(100)

            val produtoEncontrado = localProdutoDataSource.buscaPorNome(produto.nome).firstOrNull()

            // Asert
            assertTrue(produtoEncontrado != null)

        }
    }


}