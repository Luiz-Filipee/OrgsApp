package br.luizfilipe.orgs.di.modules

import br.luizfilipe.orgs.data.database.dao.LocalProdutoDataSource
import br.luizfilipe.orgs.data.database.dao.LocalUserDataSource
import br.luizfilipe.orgs.data.database.repository.ProdutoRepository
import br.luizfilipe.orgs.data.database.repository.ProdutoRepositoryImp
import br.luizfilipe.orgs.data.database.repository.UserRepository
import br.luizfilipe.orgs.data.database.repository.UserRepositoryImp
import org.koin.dsl.module

val daoModule = module {

    single<UserRepository> { providerUserRepository(get()) }
    single<ProdutoRepository> { providerProdutoRepository(get()) }
}

fun providerUserRepository(localUserDataSource: LocalUserDataSource): UserRepository {
    return UserRepositoryImp(
        localUserDataSource
    )
}

fun providerProdutoRepository(localProdutoDataSource: LocalProdutoDataSource): ProdutoRepository {
    return ProdutoRepositoryImp(
        localProdutoDataSource
    )
}
