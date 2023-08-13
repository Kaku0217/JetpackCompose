package com.automationtest.composeuitest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
        // Initialize testData
        testData = TestData(
            testAccount,
            testAddress,
            testProducts,
            testCartQuantity
        )
    }
    @Test
    fun testScenarioSuccess() {
        // Start the app
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
        // Start the app
        composeTestRule.setContent {
            ComposeUITestTheme {
                LoginScreen()
            }
        }
        testData.testCartQuantity = 3
        testScenario()
    }


    private fun testScenario() {
        e2eTestDSL(composeTestRule) {
            // Verify login flow
            login(testData.testAccount)
            // Select first item
            selectItem(0)
            // Add to cart
            addToCart("1")
            // Go back to Showcase Screen
            backToShowcaseScreen()
            // Select second item
            selectItem(1)
            // Add to cart
            addToCart("2")
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
        // Reset

    }
}
