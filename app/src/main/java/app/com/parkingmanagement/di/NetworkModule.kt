package app.com.parkingmanagement.di


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import app.com.parkingmanagement.data.repository.*
import app.com.parkingmanagement.data.source.RetrofitService
import app.com.parkingmanagement.domain.repository.*
import app.com.parkingmanagement.util.Constants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val mCache = Cache(context.cacheDir, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .cache(mCache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                request =
                    if (true) request.newBuilder() //make default to true till i figure out how to inject network status
                        .header("Cache-Control", "public, max-age=" + 5).build()
                    else request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
        return client.build()
    }


    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideIsNetworkAvailable(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideFloorRepository(
        retrofitService: RetrofitService
    ): FloorRepository {
        return FloorRepositoryImpl(retrofitService)
    }

    @Singleton
    @Provides
    fun provideCouponRepository(
        retrofitService: RetrofitService
    ): CouponRepository {
        return CouponRepositoryImpl(retrofitService)
    }


    @Singleton
    @Provides
    fun provideParkingChargesRepository(
        retrofitService: RetrofitService
    ): ParkingChargesRepository {
        return ParkingChargesRepositoryImpl(retrofitService)
    }

    @Singleton
    @Provides
    fun provideParkingTicketRepository(
        retrofitService: RetrofitService
    ): ParkingTicketRepository {
        return ParkingTicketRepositoryImpl(retrofitService)
    }

    @Singleton
    @Provides
    fun provideTransactionRepository(
        retrofitService: RetrofitService
    ): TransactionRepository {
        return TransactionRepositoryImpl(retrofitService)
    }


}