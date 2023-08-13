package com.automationtest.composeuitest

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.automationtest.composeuitest.testdata.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.unit.sp


class CartScreenActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CartScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel()
    val cartItemCount = cartViewModel.cartItemCount.value
    val cartItems = cartViewModel.cartItems
    // Use a unique key that changes whenever cartItemCount changes
    val topAppBarKey = "topAppBar_$cartItemCount"

    Scaffold(
        topBar = {
            // Use the unique key for the LaunchedEffect
            LaunchedEffect(topAppBarKey) {
                // No-op effect, just here to trigger recomposition
            }
            LaunchedEffect(Unit) {
                cartViewModel.loadCartItemsFromPrefs(context)
            }
            TopAppBar(
                title = { Text(text = "Item Screen") },

                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to ShowcaseActivity when the back button is clicked
                        context.startActivity(Intent(context, ShowcaseActivity::class.java))
                    },
                        modifier = Modifier.testTag(TestTags.BACK_TO_HOME_TAG)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    // Cart icon and badge
                    val initialItemCount = cartViewModel.getInitialCartItemCount(context)
                    CartIconWithBadge(initialItemCount) {
                        //val intent = Intent(context, CartScreenActivity::class.java)
                        //context.startActivity(intent)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp)
                .testTag(TestTags.CART_SCREEN_TAG)
        ) {
            Column {
                Text(
                    text = "Your Cart",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(48.dp)
                )
                LazyColumn {
                    items(cartItems) { item ->
                        Text(
                            text = item,
                            fontWeight = FontWeight.Medium,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .testTag(TestTags.CART_SCREEN_ITEM_TAG)
                        )
                        Divider()

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            context.startActivity(Intent(context,ShowcaseActivity::class.java))
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag(TestTags.CART_CONTINUE_TAG)
                    ) {
                        Text("Continue Shopping")
                    }

                    Button(
                        onClick = {
                            context.startActivity(
                                Intent(context, AddressScreenActivity::class.java))
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .testTag(TestTags.CART_CHECKOUT_TAG)
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    }
}
