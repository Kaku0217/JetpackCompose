package com.automationtest.composeuitest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.automationtest.composeuitest.data.Product
import com.automationtest.composeuitest.data.TestData
import com.automationtest.composeuitest.data.testAccount
import com.automationtest.composeuitest.data.testAddress
import com.automationtest.composeuitest.data.testCartQuantity
import com.automationtest.composeuitest.data.testProducts
import com.automationtest.composeuitest.testdata.*
import com.automationtest.composeuitest.ui.theme.ComposeUITestTheme
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class E2EScenarioTest {
    // Variables for holding test data
    private lateinit var testData: TestData

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        // Initialize testData before running the test
        testData = TestData(
            testAccount,
            testAddress,
            testProducts,
            testCartQuantity
        )
    }
    @Test
    fun testScenarioSuccess() {
        // Test the positive scenario with 2 items in the cart
        composeTestRule.setContent {
            ComposeUITestTheme {
                LoginScreen()
            }
        }
        testData.testCartQuantity = 2
        testScenario()
    }

    @Test
    fun testScenarioFailure() {
        // Test the negative scenario with 3 items in the cart
        composeTestRule.setContent {
            ComposeUITestTheme {
                LoginScreen()
            }
        }
        testData.testProducts = listOf(
            Product(name = ProductItems.PRODUCT_LIST[0], index = 0),
            Product(name = ProductItems.PRODUCT_LIST[1], index = 1),
            Product(name = ProductItems.PRODUCT_LIST[0], index = 0)
        )
        testData.testCartQuantity = 3
        testScenario()
    }

    private fun testScenario() {
        e2eTestDSL(composeTestRule) {
            // Test login, product selection, adding to cart, checking out, and verifying the process
            login(testData.testAccount)

            // Iterate through each product in testProducts along with its index
            testData.testProducts.forEachIndexed { index, product ->
                // Select item based on the index
                selectItem(product.index)

                // Add to cart based on the index (adding 1 to the index as quantity)
                addToCart((index + 1).toString())

                // Go back to Showcase Screen
                backToShowcaseScreen()
            }

            // Go to cart
            goToCart(testData.testCartQuantity)
            // Go to checkout
            goCheckout()
            // Verify address input flow
            inputAddress(testData.testAddress)
            // Verify finish checkout flow
            finishCheckout()
            // Verify back to home flow
            backHome()
        }
    }

    @After
    fun tearDown() {
        // Code to reset any global state if needed
    }
}
