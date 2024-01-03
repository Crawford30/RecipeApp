package com.example.food2forkmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.food2forkmvvm.cache.RecipeDAO
import com.example.food2forkmvvm.cache.database.RecipeDatabase
import com.example.food2forkmvvm.cache.model.RecipeEntityMapper
import com.example.food2forkmvvm.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    /**
     * provides the injection to database
     */
    @Singleton
    @Provides
    fun provideDB(app: BaseApplication): RecipeDatabase {
        return Room
            .databaseBuilder(app, RecipeDatabase::class.java, RecipeDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration() //Will destroy a rebuild the migration
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDAO(app: RecipeDatabase): RecipeDAO {
        return app.recipeDAO()
    }

    @Singleton
    @Provides
    fun provideCacheRecipeMapper(): RecipeEntityMapper {
        return RecipeEntityMapper()
    }


}