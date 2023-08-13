package com.automationtest.composeuitest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.automationtest.composeuitest.testdata.*


class ItemScreenActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val initialItemCount = cartViewModel.getInitialCartItemCount(applicationContext)
            cartViewModel.updateCartItemCount(applicationContext, initialItemCount)
            val product = intent.getStringExtra("product")
            ItemScreen(product)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(product: String?) {
    val context = LocalContext.current
    val cartViewModel: CartViewModel = viewModel()
    val cartItemCount = cartViewModel.cartItemCount.value
    // Use a unique key that changes whenever cartItemCount changes
    val topAppBarKey = "topAppBar_$cartItemCount"


    Scaffold(
        topBar = {
            LaunchedEffect(topAppBarKey) {
            }
            TopAppBar(
                title = { Text(text = "Showcase Screen") },

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
                        val intent = Intent(context, CartScreenActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            )
        }
    ) {
        // Wrap the content inside a Box and apply the test tag
        Box(modifier = Modifier.testTag(TestTags.ITEM_SCREEN_TAG)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = "Product Details:",
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                     text = product ?: "Unknown Product",
                    fontWeight = FontWeight.Medium,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(48.dp)
                        .testTag(product ?: "unknownProductTag")
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        //val initialItemCount = cartViewModel.cartItemCount.value + 1
                        //cartViewModel.updateCartItemCount(context, initialItemCount)
                        product?.let {
                            cartViewModel.addItemToCart(context, it)
                        }
                        cartViewModel.loadCartItemsFromPrefs(context)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag(TestTags.ADD_TO_CART_TAG)
                ) {
                    Text("Add to Cart")
                }
            }
        }
    }
}
