package com.automationtest.composeuitest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.automationtest.composeuitest.testdata.*

class ConfirmPurchaseActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val initialItemCount = cartViewModel.getInitialCartItemCount(applicationContext)
            cartViewModel.updateCartItemCount(applicationContext, initialItemCount)

            val navController = rememberNavController()

            NavHost(navController, startDestination = "confirm") {
                composable("confirm") { ConfirmPurchaseScreen(navController) }
                composable("thank-you") { ThankYouScreen() }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfirmPurchaseScreen(navController: NavHostController) {
    val cartViewModel: CartViewModel = viewModel()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Address Screen") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to AddressScreenActivity when the back button is clicked
                        context.startActivity(Intent(context, AddressScreenActivity::class.java))
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    // Cart icon and badge
                    val initialItemCount = cartViewModel.getInitialCartItemCount(context)
                    CartIconWithBadge(initialItemCount) {
                        val intent = Intent(context, CartScreenActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            )
        },
        content = {
            // Wrap the content inside a Box and apply the test tag
            Box(modifier = Modifier.testTag(TestTags.CONFIRM_SCREEN_TAG)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(100.dp))

                    LazyColumn {
                        item {
                            Text("Checkout: Overview\n")
                        }
                        item {
                            Text("QTY Description")
                        }

                        item {
                            Text("Payment Information")
                        }

                        item {
                            Text("Shipping Information")
                        }

                        item {
                            Text("Price Total")
                            Text("Item total: $0")
                            Text("Tax: $0.00")
                            Text("Total: $0.00")
                        }


                        item {
                            Spacer(modifier = Modifier.height(48.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                AddressScreenActivity::class.java
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .testTag(TestTags.CHECKOUT_CANCEL_TAG)
                                ) {
                                    Text("Cancel")
                                }

                                Button(
                                    onClick =
                                    {
                                        cartViewModel.clearCart(context)
                                        navController.navigate("thank-you")
                                    },
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .testTag(TestTags.CHECKOUT_FINISH_TAG)
                                ) {
                                    Text("Finish")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ThankYouScreen() {
    val cartViewModel: CartViewModel = viewModel()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Address Screen") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to AddressScreenActivity when the back button is clicked
                        context.startActivity(Intent(context, AddressScreenActivity::class.java))
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    // Cart icon and
                    val initialItemCount = cartViewModel.getInitialCartItemCount(context)
                    CartIconWithBadge(initialItemCount) {
                        val intent = Intent(context, CartScreenActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            )
        },
        content = {
            // Wrap the content inside a Box and apply the test tag
            Box(modifier = Modifier.testTag(TestTags.THANK_YOU_SCREEN_TAG)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Thank you for your order!",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        //style = MaterialTheme.typography
                    )

                    Text(
                        text = "Your order has been dispatched, and will arrive just as fast as the pony can get there!",
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            val intent = Intent(context, ShowcaseActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .testTag(TestTags.BACK_HOME_TAG)
                    ) {
                        Text("Back Home")
                    }
                }
            }
        }
    )
}

