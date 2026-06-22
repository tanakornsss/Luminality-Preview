package dev.tanakornsss.luminality.module

import androidx.room.Room
import dev.tanakornsss.luminality.data.backup.BackupRepository
import dev.tanakornsss.luminality.data.backup.BackupRepositoryImpl
import dev.tanakornsss.luminality.data.backup.BackupViewModel
import dev.tanakornsss.luminality.data.journal.JournalDao
import dev.tanakornsss.luminality.data.journal.JournalRepository
import dev.tanakornsss.luminality.data.journal.JournalRepositoryImpl
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.data.streak.StreakDao
import dev.tanakornsss.luminality.data.streak.StreakRepository
import dev.tanakornsss.luminality.data.streak.StreakRepositoryImpl
import dev.tanakornsss.luminality.db.AppDatabase
import dev.tanakornsss.luminality.launch.LaunchRepository
import dev.tanakornsss.luminality.launch.LaunchRepositoryImpl
import dev.tanakornsss.luminality.launch.LaunchViewModel
import dev.tanakornsss.luminality.setting.SettingRepository
import dev.tanakornsss.luminality.setting.SettingRepositoryImpl
import dev.tanakornsss.luminality.setting.SettingViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::JournalRepositoryImpl) { bind<JournalRepository>() }
    singleOf(::StreakRepositoryImpl) { bind<StreakRepository>() }
    singleOf(::BackupRepositoryImpl) { bind<BackupRepository>() }
    singleOf(::LaunchRepositoryImpl) { bind<LaunchRepository>() }
    singleOf(::SettingRepositoryImpl) { bind<SettingRepository>() }
}

val viewModelModule = module {
    viewModelOf(::JournalViewModel)
    viewModelOf(::BackupViewModel)
    viewModelOf(::LaunchViewModel)
    viewModelOf(::SettingViewModel)
}

val dbModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_db"
        )
        .fallbackToDestructiveMigration(true)
        .build()
    }

    single<JournalDao> {
        get<AppDatabase>().journalDao()
    }

    single<StreakDao> {
        get<AppDatabase>().streakDao()
    }
}