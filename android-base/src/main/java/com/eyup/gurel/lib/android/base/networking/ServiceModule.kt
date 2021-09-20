package com.eyup.gurel.lib.android.base.networking

import com.eyup.gurel.lib.android.base.adapter.moshi.DateTimeAdapter
import com.eyup.gurel.lib.android.base.adapter.moshi.DoubleAdapter
import com.eyup.gurel.lib.android.base.adapter.moshi.UUIDAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class ServiceModule {
    companion object {
        // Dagger will only look for methods annotated with @Provides
        @Provides
        @Singleton
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .add(DoubleAdapter())
                .add(DateTimeAdapter())
                .add(UUIDAdapter())
                .build()
        }

        @Provides
        @Singleton
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .build()
        }

        @Provides
        @Singleton
        @Named("base")
        fun provideRESTRetrofit(moshi: Moshi,
                                                            okHttpClient: OkHttpClient,
                                                            @Named("base_rest_api_url") baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .callFactory(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }

        @Provides
        @Named("network_scheduler")
        fun provideNetworkScheduler(): Scheduler {
            return Schedulers.io()
        }

    }
}