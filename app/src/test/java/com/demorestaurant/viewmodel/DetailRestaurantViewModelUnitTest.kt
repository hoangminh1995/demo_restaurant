package com.demorestaurant.viewmodel

import androidx.lifecycle.Observer
import com.demorestaurant.InstantTaskExecutor
import com.demorestaurant.TrampolineSchedulerProvider
import com.demorestaurant.data.config.NetworkConnectionInterceptor
import com.demorestaurant.data.remote.DetailRestaurantResponse
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.repository.RestaurantRepository
import com.demorestaurant.ui.base.Command
import com.demorestaurant.ui.detailrestaurant.DetailRestaurantViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, InstantTaskExecutor::class)
class DetailRestaurantViewModelUnitTest {

    @RelaxedMockK
    lateinit var restaurantRepository: RestaurantRepository

    private lateinit var viewModel: DetailRestaurantViewModel

    @BeforeEach
    fun setUp() {
        viewModel = DetailRestaurantViewModel(restaurantRepository)

        // Given
        with(viewModel) {
            mCommand = mockk()
            mCompositeDisposable = CompositeDisposable()
            mSchedulerProvider = TrampolineSchedulerProvider()
            every { setCommand(any()) } just Runs
        }
    }

    @Test
    fun `get detail restaurant case no internet connection`() {
        val spyViewModel = spyk(viewModel, recordPrivateCalls = true)
        with(spyViewModel) {
            // Given
            mRestaurantResponse= RestaurantResponse(1,"","", mockk())
            val noConnectionException: NetworkConnectionInterceptor.NoConnectivityException =
                mockk(relaxed = true)
            every {
                restaurantRepository.getDetailRestaurantRemote(1)
            } returns Single.error(noConnectionException)

            // When
            getDetailRestaurant()

            // Then
            verify { setCommand(ofType(Command.ShowLoadingDialog::class)) }
            verify { restaurantRepository.getDetailRestaurantRemote(1) }
            verify { setCommand(ofType(Command.HideLoadingDialog::class)) }
            verify { setCommand(ofType(Command.OfflineDialog::class)) }
        }
    }

    @Test
    fun `get detail restaurant case error`() {
        val spyViewModel = spyk(viewModel, recordPrivateCalls = true)
        with(spyViewModel) {
            // Given
            mRestaurantResponse= RestaurantResponse(1,"","", mockk())
            val exception: NumberFormatException =
                mockk(relaxed = true)
            every {
                restaurantRepository.getDetailRestaurantRemote(1)
            } returns Single.error(exception)

            every {
                handleError(exception)
            } just runs

            // When
            getDetailRestaurant()

            // Then
            verify { setCommand(ofType(Command.ShowLoadingDialog::class)) }
            verify { restaurantRepository.getDetailRestaurantRemote(1) }
            verify { setCommand(ofType(Command.HideLoadingDialog::class)) }
            verify { handleError(any()) }
        }
    }

    @Test
    fun `get detail restaurant case get success`() {
        val spyViewModel = spyk(viewModel, recordPrivateCalls = true)
        with(spyViewModel) {
            // Given
            mRestaurantResponse= RestaurantResponse(1,"","", mockk())
            val listDetailRestaurantResponse: List<DetailRestaurantResponse> = listOf()
            every {
                restaurantRepository.getDetailRestaurantRemote(1)
            } returns Single.just(listDetailRestaurantResponse)

            val observer: Observer<List<DetailRestaurantResponse>> = mockk(relaxed = true)
            mListDetailRestaurant.observeForever(observer)

            // When
            getDetailRestaurant()
            // Delay 2s
            Thread.sleep(2000)
            // Then
            verify { setCommand(ofType(Command.ShowLoadingDialog::class)) }
            verify { restaurantRepository.getDetailRestaurantRemote(1) }
            verify { setCommand(ofType(Command.HideLoadingDialog::class)) }
            verify { observer.onChanged(listDetailRestaurantResponse) }
        }
    }
}