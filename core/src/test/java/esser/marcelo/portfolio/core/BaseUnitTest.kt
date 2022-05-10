package esser.marcelo.portfolio.core
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

abstract class BaseUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

}