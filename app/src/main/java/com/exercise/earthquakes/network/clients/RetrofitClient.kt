package com.exercise.earthquakes.network.clients

import android.support.annotation.NonNull
import com.exercise.earthquakes.model.Earthquakes
import com.exercise.earthquakes.network.EarthquakeRequestParams
import com.exercise.earthquakes.network.EarthquakeService
import com.exercise.earthquakes.network.di.DaggerRetrofitClientDataComponent
import com.exercise.earthquakes.ui.interfaces.OnEarthquakeRetrieved
import io.github.adamshurwitz.retrorecycler.dependencyinjection.RetrofitClientDataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by nativlevy on 2/11/18.
 */
class RetrofitClient : BaseClient() {

    /**
     * We will query earthquake endpoint with this service
     */
    @Inject
    lateinit var mEarthquakeService: EarthquakeService

    /**
     * Collects all subscriptions to unsubscribe later
     */
    @NonNull
    private val mCompositeDisposable = CompositeDisposable()


    init {
        DaggerRetrofitClientDataComponent.builder().retrofitClientDataModule(RetrofitClientDataModule()).build().inject(this)
    }

    override fun queryEarthquakes(params: EarthquakeRequestParams, onEarthquakeReceived: OnEarthquakeRetrieved) {
        mCompositeDisposable.add(mEarthquakeService.invokeEarthquakes(params.north, params.south, params.east, params.west, params.username)
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribeWith(object : DisposableObserver<Earthquakes>() {
                    override fun onNext(response: Earthquakes) {
                        onEarthquakeReceived.onRetrieved(response)
                    }

                    override fun onError(e: Throwable) {
                        onEarthquakeReceived.onError(e.message)
                    }

                    override fun onComplete() {
                    }
                }))
    }

    override fun release() {
        mCompositeDisposable.dispose()
    }

}
