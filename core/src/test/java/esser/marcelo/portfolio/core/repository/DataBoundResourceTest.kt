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
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DataBoundResourceTest : BaseUnitTest() {

    @MockK
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
    fun serviceDelegate_getLines_ShouldInvokeCorrectDaoMethod() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            sogalServiceDelegate.getLines()
            coEvery { appDao.getLines() } returns listOf()
        }
    }

    @Test
    fun serviceDelegate_linesServiceFetch_ShouldInsertLines() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            sogalServiceDelegate.getLines()
            coEvery { appDao.insertLines(mockk()) }
        }
    }

    @Test
    fun serviceDelegate_getSchedulesLineWay_ShouldNotBeNull() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "name", "code")
            line.way = null

            var response: esser.marcelo.portfolio.core.wrapper.Resource<LineSchedules>? = null
            sogalServiceDelegate.getSchedules(line, shouldFetch = false).collect {
                response = it
            }

            assert(response != null)
            assert(response?.requestStatus == Status.error)
            assert(response?.message == "Line way must not be null")
        }
    }

    @Test
    fun serviceDelegate_getSchedules_ShouldInvokeCorrectDaoMethod() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "name", "code")
            sogalServiceDelegate.getSchedules(line)
            coEvery { appDao.getLineSchedule(line.id) } returns LineSchedules(
                0,
                line.id,
                null,
                null,
                null
            )
        }
    }

    @Test
    fun serviceDelegate_schedulesServiceFetch_ShouldInsertLineSchedules() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            val line = BusLine(1, "name", "code")
            line.way = LineWay("description", "code")

            var response: esser.marcelo.portfolio.core.wrapper.Resource<LineSchedules>? = null

            sogalServiceDelegate.getSchedules(line, false).collect {
                response = it
            }

            assert(response != null)
            assert(response?.requestStatus == Status.success)

            response?.data?.let {

                it.lineId = line.id
                coEvery { appDao.insertSchedule(it) }
            }
        }
    }


}