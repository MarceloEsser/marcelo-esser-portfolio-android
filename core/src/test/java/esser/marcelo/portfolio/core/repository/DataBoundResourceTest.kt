@file:OptIn(ExperimentalCoroutinesApi::class)

package esser.marcelo.portfolio.core.repository

import esser.marcelo.portfolio.core.BaseUnitTest
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.model.LineWay
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.repository.service.ISogalAPI
import esser.marcelo.portfolio.core.repository.service.SogalService
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import esser.marcelo.portfolio.core.wrapper.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DataBoundResourceTest : BaseUnitTest() {

    @RelaxedMockK
    lateinit var appDao: AppDao

    @RelaxedMockK
    lateinit var sogalApi: ISogalAPI

    lateinit var sogalServiceDelegate: SogalServiceDelegate

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sogalServiceDelegate = SogalService(dao = appDao, _mApi = sogalApi)
    }

    @Test
    fun serviceDelegate_getLines_ShouldCallApi_And_Return_ResourceBusLineList() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "lineName", "lineCode")
            var response: Resource<List<BusLine>>? = null

            coEvery { sogalApi.postSogalLines("buscaLinhas") } returns Resource.success(
                listOf(line)
            )

            sogalServiceDelegate.getLines().collect {
                response = it
            }

            coVerify { sogalApi.postSogalLines("buscaLinhas") }

            assert(response != null)
            response?.let { safetyResponse ->
                assert(safetyResponse.requestStatus == Status.success)
                assert(safetyResponse.data != null)
            }

        }
    }

    @Test
    fun serviceDelegate_getLines_ShouldFetchFromDatabase_And_Return_BusLineList() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "name", "code")
            var response: Resource<List<BusLine>>? = null

            coEvery { appDao.getLines() } returns listOf(line)

            sogalServiceDelegate.getLines(shouldCreateCall = false).collect {
                response = it
            }

            coVerify { appDao.getLines() }

            assert(response != null)
            response?.let { safetyResponse ->
                assert(safetyResponse.requestStatus == Status.success)
                assert(safetyResponse.data != null)
            }
        }
    }

    @Test
    fun serviceDelegate_linesServiceFetch_ShouldInsertBusLines() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "name", "code")

            var response: Resource<List<BusLine>>? = null

            coEvery { sogalApi.postSogalLines("buscaLinhas") } returns Resource.success(listOf(line))
            sogalServiceDelegate.getLines().collect {
                response = it
            }

            assert(response != null)
            assert(response?.requestStatus == Status.success)

            response?.data?.let {
                coVerify { appDao.insertLines(it) }
            }
        }
    }

    @Test
    fun serviceDelegate_getSchedulesLineWay_ShouldNotBeNull() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "name", "code")
            line.way = null

            var response: Resource<LineSchedules>? = null
            sogalServiceDelegate.getSchedules(line).collect {
                response = it
            }

            assert(response != null)
            assert(response?.requestStatus == Status.error)
            assert(response?.message == "Line way must not be null")
        }
    }

    @Test
    fun serviceDelegate_getSchedules_ShouldCallApi_And_Return_ResourceLineSchedules() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "lineName", "lineCode")
            line.way = LineWay("wayDescription", "wayCode")

            var response: Resource<LineSchedules>? = null

            coEvery { sogalApi.postSogalSchedules("wayCode", "lineCode") } returns Resource.success(
                data = LineSchedules(0, lineId = line.id, null, null, null)
            )

            sogalServiceDelegate.getSchedules(line).collect {
                response = it
            }

            coVerify { sogalApi.postSogalSchedules("wayCode", "lineCode") }

            assert(response != null)
            response?.let { safetyResponse ->
                assert(safetyResponse.requestStatus == Status.success)
                assert(safetyResponse.data != null)
            }

        }
    }

    @Test
    fun serviceDelegate_getSchedules_ShouldFetchFromDatabase_And_Return_LineSchedules() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "lineName", "lineCode")
            line.way = LineWay("wayDescription", "wayCode")
            var response: Resource<LineSchedules>? = null

            coEvery { appDao.getLineSchedule(line.id) } returns LineSchedules(
                0,
                lineId = line.id,
                null,
                null,
                null
            )
            sogalServiceDelegate.getSchedules(line, shouldCreateCall = false).collect {
                response = it
            }

            coVerify { appDao.getLineSchedule(line.id) }

            assert(response != null)
            response?.let { safetyResponse ->
                assert(safetyResponse.requestStatus == Status.success)
                assert(safetyResponse.data != null)
            }
        }
    }

    @Test
    fun serviceDelegate_schedulesServiceFetch_ShouldInsertLineSchedules() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "lineName", "lineCode")
            line.way = LineWay("wayDescription", "wayCode")

            var response: Resource<LineSchedules>? = null

            coEvery { sogalApi.postSogalSchedules("wayCode", "lineCode") } returns Resource.success(
                LineSchedules(
                    0,
                    -1,
                    null,
                    null,
                    null
                )
            )

            sogalServiceDelegate.getSchedules(line).collect {
                response = it
            }

            assert(response != null)
            assert(response?.requestStatus == Status.success)

            response?.data?.let {
                it.lineId = line.id
                coVerify { appDao.insertSchedule(it) }
            }
        }
    }


}