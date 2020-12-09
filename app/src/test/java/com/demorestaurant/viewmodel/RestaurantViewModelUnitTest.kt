package com.demorestaurant.viewmodel

import androidx.lifecycle.Observer
import com.demorestaurant.InstantTaskExecutor
import com.demorestaurant.TrampolineSchedulerProvider
import com.demorestaurant.data.config.NetworkConnectionInterceptor
import com.demorestaurant.data.remote.RestaurantResponse
import com.demorestaurant.repository.RestaurantRepository
import com.demorestaurant.ui.base.Command
import com.demorestaurant.ui.listrestaurant.ListRestaurantViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, InstantTaskExecutor::class)
class RestaurantViewModelUnitTest {

    @RelaxedMockK
    lateinit var restaurantRepository: RestaurantRepository

    private lateinit var viewModel: ListRestaurantViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ListRestaurantViewModel(restaurantRepository)

        // Given
        with(viewModel) {
            mCommand = mockk()
            mCompositeDisposable = CompositeDisposable()
            mSchedulerProvider = TrampolineSchedulerProvider()
            every { setCommand(any()) } just Runs
        }
    }


    @Test
    fun `get list restaurant case no internet connection`() {
        val spyViewModel = spyk(viewModel, recordPrivateCalls = true)
        with(spyViewModel) {
            // Given
            val noConnectionException: NetworkConnectionInterceptor.NoConnectivityException =
                mockk(relaxed = true)
            every {
                restaurantRepository.getListRestaurantRemote()
            } returns Single.error(noConnectionException)

            // When
            getListRestaurant()

            // Then
            verify { setCommand(ofType(Command.ShowLoadingDialog::class)) }
            verify { restaurantRepository.getListRestaurantRemote() }
            verify { setCommand(ofType(Command.HideLoadingDialog::class)) }
            verify { setCommand(ofType(Command.OfflineDialog::class)) }
        }
    }

    @Test
    fun `get list restaurant case error`() {
        val spyViewModel = spyk(viewModel, recordPrivateCalls = true)
        with(spyViewModel) {
            // Given
            val exception: NumberFormatException =
                mockk(relaxed = true)
            every {
                restaurantRepository.getListRestaurantRemote()
            } returns Single.error(exception)

            every {
                handleError(exception)
            } just runs
            // When
            getListRestaurant()

            // Then
            verify { setCommand(ofType(Command.ShowLoadingDialog::class)) }
            verify { restaurantRepository.getListRestaurantRemote() }
            verify { setCommand(ofType(Command.HideLoadingDialog::class)) }
            verify { handleError(any()) }
        }
    }

    @Test
    fun `get list restaurant case get success`() {
        val spyViewModel = spyk(viewModel, recordPrivateCalls = true)
        with(spyViewModel) {
            // Given
            val listRestaurantResponse: List<RestaurantResponse> = listOf()
            every {
                restaurantRepository.getListRestaurantRemote()
            } returns Single.just(listRestaurantResponse)
            val observer: Observer<List<RestaurantResponse>> = mockk(relaxed = true)
            mListRestaurant.observeForever(observer)

            // When
            getListRestaurant()
            // Delay 2s
            Thread.sleep(2000)
            // Then
            verify { setCommand(ofType(Command.ShowLoadingDialog::class)) }
            verify { restaurantRepository.getListRestaurantRemote() }
            verify { setCommand(ofType(Command.HideLoadingDialog::class)) }
            verify { observer.onChanged(listRestaurantResponse) }
        }
    }
}