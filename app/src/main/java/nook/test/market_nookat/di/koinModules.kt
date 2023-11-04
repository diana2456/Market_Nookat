package nook.test.market_nookat.di

import nook.test.market_nookat.data.network.networkModule
import nook.test.market_nookat.data.repository.remoteDataSourceModule


val koinModules = listOf(
    repoModules,
    viewModules,
    networkModule,
    remoteDataSourceModule
)