package hu.ait.stayhard.ui.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ait.stayhard.ui.data.HabitAppDatabase
import hu.ait.stayhard.ui.data.HabitDAO
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideHabitDao(appDatabase: HabitAppDatabase): HabitDAO {
        return appDatabase.habitDao()
    }

    @Provides
    @Singleton
    fun provideHabitAppDatabase(
        @ApplicationContext appContext: Context): HabitAppDatabase {
        return HabitAppDatabase.getDatabase(appContext)
    }
}