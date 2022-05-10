@file:OptIn(ExperimentalCoroutinesApi::class)

package esser.marcelo.portfolio.schedules.viewModel

import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.model.LineWay
import esser.marcelo.portfolio.core.model.Schedule
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.repository.service.ISogalAPI
import esser.marcelo.portfolio.core.repository.service.SogalService
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import esser.marcelo.portfolio.core.wrapper.Resource
import esser.marcelo.portfolio.schedules.BaseUnitTest
import esser.marcelo.portfolio.schedules.R
import esser.marcelo.portfolio.schedules.scenes.schedule.SchedulesViewModel
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

class SchedulesViewModelTest : BaseUnitTest() {

    @RelaxedMockK
    lateinit var appDao: AppDao

    @RelaxedMockK
    lateinit var sogalApi: ISogalAPI

    lateinit var viewModel: SchedulesViewModel
    lateinit var service: SogalServiceDelegate

    val nothing = null
    val mockList by lazy {
        LineSchedules(
            0,
            0,
            workingDays = listOf(
                Schedule("06:50", accessible = "N"),
                Schedule("07:50", accessible = "N"),
                Schedule("08:50", accessible = "S"),
                Schedule("09:50", accessible = "N"),
            ),
            saturdays = listOf(
                Schedule("06:50", accessible = "N"),
                Schedule("08:50", accessible = "S"),
                Schedule("09:50", accessible = "N"),
            ),
            sundays = listOf(
                Schedule("06:50", accessible = "N"),
                Schedule("09:50", accessible = "N"),
            ),

            )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        service = SogalService(dao = appDao, _mApi = sogalApi)
        viewModel = SchedulesViewModel(service, coroutinesTestRule.testDispatchers)
        val line = BusLine(0, "lineName", "lineCode")
        line.way = LineWay("wayDescription", "wayCode")
        viewModel.line = line
    }

    @Test
    fun whenObserveSchedulesLiveData_ShouldFetchLineSchedules() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            coEvery {
                sogalApi.postSogalSchedules(
                    lineWay = "wayCode",
                    lineCode = "lineCode"
                )
            } returns Resource.success(mockList)

            coEvery { appDao.getLineSchedule(0) } returns mockList

            val observer = { lineSchedules: LineSchedules ->
                assert(lineSchedules.id == 0L)
            }

            viewModel.schedule.observeForever(observer)

            coVerify {
                sogalApi.postSogalSchedules(
                    lineWay = "wayCode",
                    lineCode = "lineCode"
                )
            }
            coVerify { appDao.getLineSchedule(0) }
        }

    }

    @Test
    fun whenObserveSchedulesLiveDataWithoutViewModelLine_ShouldNotFetchLineSchedules() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            viewModel.line = null

            viewModel.schedule.observeForever {}
            viewModel.error.observeForever {
                assert(it == "you need a line to get the schedules")
            }
        }

    }

    @Test
    fun wheAErrorIsCaughtOnRepository_ShouldNotifyTheErrorLiveData() {
        runBlocking(coroutinesTestRule.testDispatchers) {

            coEvery {
                sogalApi.postSogalSchedules(
                    lineWay = "wayCode",
                    lineCode = "lineCode"
                )
            } returns Resource.error("generic error message")

            coEvery { appDao.getLineSchedule(0) } returns nothing

            viewModel.schedule.observeForever {}

            viewModel.error.observeForever { message ->
                assert(message == "generic error message")
            }

            coVerify {
                sogalApi.postSogalSchedules(
                    lineWay = "wayCode",
                    lineCode = "lineCode"
                )
            }
            coVerify { appDao.getLineSchedule(0) }
        }
    }

    @Test
    fun whenTheFetchIsFinished_ShouldHaveDataOnListMap() {
        runBlocking(coroutinesTestRule.testDispatchers) {
            coEvery { appDao.getLineSchedule(0) } returns mockList

            val observer = { _: LineSchedules ->
                val workingDay = viewModel.listMap[R.id.action_workingdays]
                assert(!workingDay.isNullOrEmpty())

                val saturday = viewModel.listMap[R.id.action_saturday]
                assert(!saturday.isNullOrEmpty())

                val sunday = viewModel.listMap[R.id.action_sunday]
                assert(!sunday.isNullOrEmpty())
            }

            viewModel.schedule.observeForever(observer)

            coVerify { appDao.getLineSchedule(0) }
        }
    }

}