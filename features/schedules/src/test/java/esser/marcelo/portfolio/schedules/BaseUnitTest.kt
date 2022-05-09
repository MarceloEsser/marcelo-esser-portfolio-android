package esser.marcelo.portfolio.schedules
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

abstract class BaseUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

}