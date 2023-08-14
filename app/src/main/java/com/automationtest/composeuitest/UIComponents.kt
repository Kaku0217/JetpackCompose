
package com.automationtest.composeuitest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.automationtest.composeuitest.testdata.*

@Composable
fun CartIconWithBadge(itemCount: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.testTag(TestTags.CART_ICON_TAG)) {
        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Shopping Cart")
        if (itemCount > 0) {
            Surface(
                color = Color.Red,
                shape = CircleShape,
                modifier = Modifier
                    .size(20.dp),
                content = {
                    Text(
                        text = itemCount.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(TestTags.CART_BADGE_TAG), // add test tag to badge
                    )
                }
            )
        }
    }
}







