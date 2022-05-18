@file:OptIn(ExperimentalCoroutinesApi::class)

package esser.marcelo.portfolio.viewModel

import esser.marcelo.portfolio.BaseUnitTest
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineWay
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.repository.service.ISogalAPI
import esser.marcelo.portfolio.core.repository.service.SogalServiceImpl
import esser.marcelo.portfolio.core.repository.service.ISogalService
import esser.marcelo.portfolio.core.wrapper.Resource
import esser.marcelo.portfolio.scenes.lines.LinesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class LinesViewModelTest : BaseUnitTest() {

    @RelaxedMockK
    lateinit var appDao: AppDao

    @RelaxedMockK
    lateinit var sogalApi: ISogalAPI

    lateinit var viewModel: LinesViewModel
    lateinit var service: ISogalService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        service = SogalServiceImpl(dao = appDao, _mApi = sogalApi)
        viewModel = LinesViewModel(service, coroutinesTestRule.testDispatchers)
    }

    @Test
    fun whenObserveLinesLiveData_ShouldFetchLines() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(0, "lineName", "lineCode")
            line.way = LineWay("wayDescription", "wayCode")
            val lineList = listOf(line)

            coEvery { sogalApi.postSogalLines("buscaLinhas") } returns Resource.success(lineList)
            coEvery { appDao.getLines() } returns lineList

            val observer = { lines: List<BusLine> ->
                assert(!lines.isNullOrEmpty())
                assert(lines[0].name == "lineName")
            }

            viewModel.lines.observeForever(observer)

            coVerify { sogalApi.postSogalLines("buscaLinhas") }
            coVerify { appDao.getLines() }
        }

    }

    @Test
    fun wheAErrorIsCaughtOnRepository_ShouldNotifyTheErrorLiveData() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val nothing = null
            coEvery { sogalApi.postSogalLines("buscaLinhas") } returns Resource.error("generic error message")
            coEvery { appDao.getLines() } returns nothing

            val observer = { lines: List<BusLine> ->
                print("\nwheAErrorIsCaughtOnRepository_ShouldNotifyTheErrorLiveData ${lines.size}")
            }

            viewModel.lines.observeForever(observer)

            viewModel.error.observeForever { message ->
                assert(message == "generic error message")
            }

            coVerify { sogalApi.postSogalLines("buscaLinhas") }
            coVerify { appDao.getLines() }

        }

    }

    @Test
    fun whenFilterActionCall_ShouldReturnFilteredLinesList() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            coEvery { appDao.getLines() } returns listOf(
                BusLine(0, "Mathias", "5001"),
                BusLine(1, "Floripa", "5002"),
                BusLine(2, "Niteroi", "5003"),
            )

            val observer = { lines: List<BusLine> ->
                print("\nwhenFilterActionCall_ShouldReturnFilteredLinesList ${lines.size}")
            }
            viewModel.lines.observeForever(observer)

            viewModel.filterBy("5001")
            assert(viewModel.lines.value?.size == 1)

            coVerify { appDao.getLines() }

        }
    }

    @Test
    fun whenFilterActionCalledWithEmptyString_ShouldResetLinesList() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            coEvery { appDao.getLines() } returns listOf(
                BusLine(0, "Mathias", "5001"),
                BusLine(1, "Floripa", "5002"),
                BusLine(2, "Niteroi", "5003"),
            )

            val observer = { lines: List<BusLine> ->
                print("\nwhenFilterActionCalledWithEmptyString_ShouldResetLinesList ${lines.size}")
            }

            viewModel.lines.observeForever(observer)
            assert(viewModel.lines.value?.size == 3)

            viewModel.filterBy("5001")
            assert(viewModel.lines.value?.size == 1)

            viewModel.filterBy("")
            assert(viewModel.lines.value?.size == 3)

            coVerify { appDao.getLines() }
        }
    }

    @Test
    fun whenFilterActionCalledWithLineName_ShouldReturnFilteredLinesList() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            coEvery { appDao.getLines() } returns listOf(
                BusLine(0, "Mathias", "5001"),
                BusLine(1, "Floripa", "5002"),
                BusLine(2, "Niteroi", "5003"),
            )

            val observer = { lines: List<BusLine> ->
                print("\nwhenFilterActionCalledWithLineName_ShouldReturnFilteredLinesList ${lines.size}")
            }

            viewModel.lines.observeForever(observer)
            assert(viewModel.lines.value?.size == 3)

            viewModel.filterBy("Mat")
            assert(viewModel.lines.value?.size == 1)

            coVerify { appDao.getLines() }

        }
    }

}