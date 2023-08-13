package com.automationtest.composeuitest

import androidx.compose.ui.test.junit4.ComposeTestRule

fun e2eTestDSL(composeTestRule: ComposeTestRule, block: E2ETestActions.() -> Unit) {
    E2ETestActions(composeTestRule).apply { block() }
}
