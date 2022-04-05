package com.example.glmobileexerciceapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.entitie.ItemEntity
import com.example.domain.service.ItemsService
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.usecase.implementations.GetItemsUseCaseImpl
import com.example.domain.util.Result
import com.example.glmobileexerciceapp.viewmodel.ItemsData
import com.example.glmobileexerciceapp.viewmodel.ItemsStatus
import com.example.glmobileexerciceapp.viewmodel.ItemsViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ItemsViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var itemsViewModel: ItemsViewModel
    private lateinit var getItemssUseCase: GetItemsUseCase
    private val service: ItemsService = mock()

    private val listOfItems: List<ItemEntity> = mock()

    private val successResponseList = listOf(
        ItemsData(status = ItemsStatus.LOADING),
        ItemsData(status = ItemsStatus.SUCCESS_DATA, data = listOfItems)
    )

    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher)
        getItemssUseCase = GetItemsUseCaseImpl(service)
        itemsViewModel = ItemsViewModel(getItemssUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchCharacters return success result with empty list`() {

        whenever(service.getItems()).thenReturn(Result.Success(emptyList()))

        runBlocking {
            itemsViewModel.fetchItems().join()
        }

        verify(service).getItems()
    }

    @Test
    fun `when fetchCharacters return success result`() {
        val itemsLiveData = itemsViewModel.itemsLiveData.testObserver()

        whenever(service.getItems()).thenReturn(Result.Success(listOfItems))

        runBlocking {
            itemsViewModel.fetchItems().join()
        }

        verify(service).getItems()

        assertEquals(successResponseList[1].status, itemsLiveData.observedValues[1]?.peekContent()?.status)
        assertEquals(successResponseList[0].status, itemsLiveData.observedValues[0]?.peekContent()?.status)
        assertEquals(successResponseList[0].data, itemsLiveData.observedValues[0]?.peekContent()?.data)
    }

    @Test
    fun `on Save Button Pressed, when barcode is missing`() {

        val exception: Exception = Exception(ERROR_MESSAGE)
        val failureResult: Result.Failure = mock()
        whenever(failureResult.exception).thenReturn(exception)
        whenever(service.getItems()).thenReturn(failureResult)

        runBlocking {

            itemsViewModel.fetchItems()
            verify(service).getItems()
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Error on test service"
    }
}