package com.example.food2forkmvvm.di


import android.content.Context
import com.example.food2forkmvvm.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * https://developer.android.com/training/dependency-injection/hilt-android#generated-components
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * provides the injection to BaseApplication
     */
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }


}