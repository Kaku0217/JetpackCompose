package com.automationtest.composeuitest

import androidx.compose.ui.test.junit4.ComposeTestRule

// This function e2eTestDSL takes a ComposeTestRule and a block of E2ETestActions.
// It creates an instance of E2ETestActions and then applies the block to it.
// The block contains a sequence of actions to be performed in the E2E test, providing a DSL-like syntax for writing tests.
fun e2eTestDSL(composeTestRule: ComposeTestRule, block: E2ETestActions.() -> Unit) {
    E2ETestActions(composeTestRule).apply { block() }
}
