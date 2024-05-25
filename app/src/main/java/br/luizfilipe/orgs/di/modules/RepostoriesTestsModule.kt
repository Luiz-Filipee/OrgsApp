package br.luizfilipe.orgs.di.modules

import br.luizfilipe.orgs.data.database.dao.LocalProdutoDataSource
import br.luizfilipe.orgs.data.database.dao.LocalUserDataSource
import br.luizfilipe.orgs.data.database.dao.memories.InMemoryProdutoDataSource
import br.luizfilipe.orgs.data.database.dao.memories.InMemoryUserDataSource
import br.luizfilipe.orgs.data.database.repository.ProdutoRepository
import br.luizfilipe.orgs.data.database.repository.ProdutoRepositoryImp
import br.luizfilipe.orgs.data.database.repository.UserRepository
import br.luizfilipe.orgs.data.database.repository.UserRepositoryImp
import org.koin.dsl.module

val repositoriesTestModules = module {

    single<ProdutoRepository> { ProdutoRepositoryImp(get()) }
    single<UserRepository> { UserRepositoryImp(get()) }
    single<LocalProdutoDataSource> { InMemoryProdutoDataSource() }
    single<LocalUserDataSource> { InMemoryUserDataSource() }

}