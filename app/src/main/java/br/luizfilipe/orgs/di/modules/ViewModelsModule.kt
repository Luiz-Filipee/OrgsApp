package br.luizfilipe.orgs.di.modules

import br.luizfilipe.orgs.data.database.repository.ProdutoRepository
import br.luizfilipe.orgs.data.database.repository.ProdutoRepositoryImp
import br.luizfilipe.orgs.data.database.repository.UserRepository
import br.luizfilipe.orgs.data.database.repository.UserRepositoryImp
import br.luizfilipe.orgs.viewmodel.ProdutoViewModel
import br.luizfilipe.orgs.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    viewModel { providerUserViewModel(get()) }
    viewModel { providerProdutoViewModel(get()) }

}

fun providerUserViewModel(userRepository: UserRepository): UserViewModel {
    return UserViewModel(
        userRepository = userRepository
    )
}

fun providerProdutoViewModel(produtoRepository: ProdutoRepository): ProdutoViewModel {
    return ProdutoViewModel(
        produtoRepository = produtoRepository
    )
}
