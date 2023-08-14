package com.automationtest.composeuitest

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.automationtest.composeuitest.data.Account
import com.automationtest.composeuitest.data.Address
import com.automationtest.composeuitest.testdata.*

// Function to perform login action using the provided account details
fun E2ETestActions.login(account: Account): E2ETestActions {
    // Enter the username
    enterTextInNodeWithTestTag(TestTags.USERNAME_TAG, account.username)
    // Enter the password
    enterTextInNodeWithTestTag(TestTags.PASSWORD_TAG, account.password)
    // Click the login button
    clickNodeWithTestTag(TestTags.LOGIN_BUTTON_TAG)
    // Verify if navigation to the home screen occurs
    verifyTagExists(TestTags.SHOWCASE_SCREEN_TAG)

    return this
}

// Function to enter text into a node identified by the given test tag
fun E2ETestActions.enterTextInNodeWithTestTag(testTag: String, text: String): E2ETestActions {
    // Perform text input on a node with the specified test tag
    composeTestRule.onNode(hasTestTag(testTag)).performTextInput(text)
    composeTestRule.waitForIdle()

    return this
}

// Function to perform a click action on a node identified by the given test tag
fun E2ETestActions.clickNodeWithTestTag(testTag: String): E2ETestActions {
    // Perform a click action on a node with the specified test tag
    composeTestRule.onNode(hasTestTag(testTag)).performClick()
    composeTestRule.waitForIdle()

    return this
}

// Function to verify the existence of a node identified by the given test tag
fun E2ETestActions.verifyTagExists(expectedTag: String): E2ETestActions {
    composeTestRule.waitForIdle()
    // Verify the existence of a node with the expected test tag
    composeTestRule.onNode(hasTestTag(expectedTag)).assertExists()

    return this
}

// Function to verify the non-existence of a node identified by the given test tag
fun E2ETestActions.verifyTagDoesNotExist(expectedTag: String): E2ETestActions {
    composeTestRule.waitForIdle()
    // Verify the absence of a node with the expected test tag
    composeTestRule.onNode(hasTestTag(expectedTag)).assertDoesNotExist()

    return this
}

// Function to select an item from a list at the given index
fun E2ETestActions.selectItem(index: Int): E2ETestActions {
    // Get all nodes that match the specified test tag
    val nodes = composeTestRule.onAllNodes(hasTestTag(TestTags.SHOWCASE_PRODUCT_TAG))
    // Select the node at the specified index
    val targetNode = nodes[index]
    // Perform a click action on the selected node
    targetNode.performClick()
    // Verify the existence of the selected test item
    verifyTagExists(ProductItems.PRODUCT_LIST[index])

    return this
}

// Function to add an item to the cart and verify the expected quantity
fun E2ETestActions.addToCart(expectedQuantity: String): E2ETestActions {
    // Click the "Add to Cart" button
    clickNodeWithTestTag(TestTags.ADD_TO_CART_TAG)
    composeTestRule.waitForIdle()
    // Verify the cart badge shows the expected quantity
    val badgeNode = composeTestRule.onNodeWithTag(TestTags.CART_BADGE_TAG, useUnmergedTree = true)
    badgeNode.assertTextEquals(expectedQuantity)

    return this
}

// Function to navigate to the cart screen and verify the expected quantity of items
fun E2ETestActions.goToCart(expectedQuantity: Int): E2ETestActions {
    // Click the cart icon to navigate to the cart screen
    clickNodeWithTestTag(TestTags.CART_ICON_TAG)
    composeTestRule.waitForIdle()
    // Verify the existence of the cart screen
    verifyTagExists(TestTags.CART_SCREEN_TAG)
    // Verify the number of items in the cart matches the expected quantity
    val cartScreenNodes = composeTestRule.onAllNodes(hasTestTag(TestTags.CART_SCREEN_ITEM_TAG))
    cartScreenNodes.assertCountEquals(expectedQuantity)

    return this
}

// Function to navigate to the checkout process
fun E2ETestActions.goCheckout(): E2ETestActions {
    // Click the "Checkout" button
    clickNodeWithTestTag(TestTags.CART_CHECKOUT_TAG)
    composeTestRule.waitForIdle()
    // Verify the existence of the address screen
    verifyTagExists(TestTags.ADDRESS_SCREEN_TAG)

    return this
}

// Function to input the address during the checkout process
fun E2ETestActions.inputAddress(address: Address): E2ETestActions {
    // Enter the first name
    enterTextInNodeWithTestTag(TestTags.FIRST_NAME_TAG, address.firstName)
    // Enter the last name
    enterTextInNodeWithTestTag(TestTags.LAST_NAME_TAG, address.lastName)
    // Enter the zip code
    enterTextInNodeWithTestTag(TestTags.ZIP_CODE_TAG, address.zipCode)
    // Click the "Continue" button
    clickNodeWithTestTag(TestTags.ADDRESS_CONTINUE_TAG)
    composeTestRule.waitForIdle()
    // Verify the existence of the confirmation screen
    verifyTagExists(TestTags.CONFIRM_SCREEN_TAG)

    return this
}

// Function to finalize the checkout process
fun E2ETestActions.finishCheckout(): E2ETestActions {
    // Click the "Finish Checkout" button
    clickNodeWithTestTag(TestTags.CHECKOUT_FINISH_TAG)
    composeTestRule.waitForIdle()
    // Verify the existence of the "Thank You" screen
    verifyTagExists(TestTags.THANK_YOU_SCREEN_TAG)

    return this
}

// Function to navigate back to the home screen after completing the checkout process
fun E2ETestActions.backHome(): E2ETestActions {
    // Click the "Back to Home" button
    clickNodeWithTestTag(TestTags.BACK_HOME_TAG)
    composeTestRule.waitForIdle()
    // Verify the existence of the showcase screen
    verifyTagExists(TestTags.SHOWCASE_SCREEN_TAG)
    // Verify the absence of the cart badge
    verifyTagDoesNotExist(TestTags.CART_BADGE_TAG)
    // Verify navigation back to the home screen
    verifyTagExists(TestTags.SHOWCASE_SCREEN_TAG)

    return this
}

// Function to navigate back to the showcase screen from any other screen
fun E2ETestActions.backToShowcaseScreen(): E2ETestActions {
    // Click the "Back" button to navigate to the showcase screen
    clickNodeWithTestTag(TestTags.BACK_TO_HOME_TAG)
    composeTestRule.waitForIdle()
    // Optional: Verify the existence of the showcase screen after navigation
    verifyTagExists(TestTags.SHOWCASE_SCREEN_TAG)

    return this
}