package io.github.adamshurwitz.retrorecycler.dependencyinjection

import com.exercise.earthquakes.network.EarthquakeService
import com.exercise.earthquakes.network.EarthquakeService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by nativlevy on 2/11/18.
 */
@Module
class RetrofitClientDataModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideService(retrofit: Retrofit): EarthquakeService {
        return retrofit.create(EarthquakeService::class.java)
    }
}