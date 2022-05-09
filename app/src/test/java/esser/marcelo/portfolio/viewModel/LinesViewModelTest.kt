@file:OptIn(ExperimentalCoroutinesApi::class)

package esser.marcelo.portfolio.viewModel

import esser.marcelo.portfolio.BaseUnitTest
import esser.marcelo.portfolio.architeture_components.getOrAwaitValue
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.repository.service.ISogalAPI
import esser.marcelo.portfolio.core.repository.service.SogalService
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import esser.marcelo.portfolio.core.wrapper.Resource
import esser.marcelo.portfolio.scenes.line.LinesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LinesViewModelTest : BaseUnitTest() {

    @MockK
    lateinit var appDao: AppDao

    @RelaxedMockK
    lateinit var sogalApi: ISogalAPI

    lateinit var viewModel: LinesViewModel
    lateinit var service: SogalServiceDelegate

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        service = SogalService(dao = appDao, _mApi = sogalApi)
        viewModel = LinesViewModel(service, coroutinesTestRule.testDispatchers)
    }

    @Test
    fun whenObserverLinesLiveData_ShouldFetchLines() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val lineList = listOf<BusLine>(BusLine(0, "code", "code"))
            Resource.success(lineList)
            viewModel.lines.observeForever {
                coVerify { service.getSchedules(lineList[0]) }
//                coEvery { appDao.insertLines(lineList) }
            }

        }

    }

}