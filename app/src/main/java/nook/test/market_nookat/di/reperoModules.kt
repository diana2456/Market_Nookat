package nook.test.market_nookat.di

import nook.test.market_nookat.data.repository.Repository
import org.koin.dsl.module

val repoModules = module {
    single { Repository(get()) }
}