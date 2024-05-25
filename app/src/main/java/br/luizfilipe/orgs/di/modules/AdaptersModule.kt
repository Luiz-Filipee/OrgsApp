package br.luizfilipe.orgs.di.modules

import android.content.Context
import br.luizfilipe.orgs.view.adapter.ListaProdutosAdapter
import org.koin.dsl.module

val adaptersModule = module {

    single { providerAdapter(get())  }

}
fun providerAdapter(context: Context): ListaProdutosAdapter{
    return ListaProdutosAdapter(
        context = context
    )
}