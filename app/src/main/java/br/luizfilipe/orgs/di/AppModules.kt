package br.luizfilipe.orgs.di

import br.luizfilipe.orgs.di.modules.adaptersModule
import br.luizfilipe.orgs.di.modules.daoModule
import br.luizfilipe.orgs.di.modules.dataBaseModule
import br.luizfilipe.orgs.di.modules.viewModelModules

val appModules = listOf(
    dataBaseModule,
    daoModule,
    adaptersModule,
    viewModelModules
)