package esser.marcelo.portfolio.core

import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.repository.database.AppDatabase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class AppDatabaseTest {

    @MockK
    lateinit var appDatabase: AppDatabase

    @MockK
    lateinit var appDao: AppDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun obtainCharacterFavoriteDao() {
        every { appDatabase.getAppDao() } returns appDao

        MatcherAssert.assertThat(
            appDatabase.getAppDao(),
            CoreMatchers.instanceOf(AppDao::class.java)
        )
    }
}