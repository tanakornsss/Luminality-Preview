package dev.tanakornsss.luminality.module

import dev.tanakornsss.luminality.launch.LaunchRepository
import dev.tanakornsss.luminality.launch.LaunchRepositoryImpl
import dev.tanakornsss.luminality.launch.LaunchViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::LaunchRepositoryImpl) { bind<LaunchRepository>() }
    viewModelOf(::LaunchViewModel)


}