package com.automationtest.composeuitest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.automationtest.composeuitest.testdata.*


class ShowcaseActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val initialItemCount = cartViewModel.getInitialCartItemCount(applicationContext)
            cartViewModel.updateCartItemCount(applicationContext, initialItemCount)
            ShowcaseScreen()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseScreen() {
    val products = ProductItems.PRODUCT_LIST
    val cartViewModel: CartViewModel = viewModel()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Login Screen") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to MainActivity when the back button is clicked
                        context.startActivity(Intent(context, MainActivity::class.java))
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
            Box(modifier = Modifier.testTag(TestTags.SHOWCASE_SCREEN_TAG)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    val rows = products.chunked(2)

                    LazyColumn(modifier = Modifier.padding(30.dp)) {
                        items(rows) { rowProducts ->
                            LazyRow(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                itemsIndexed(rowProducts) { _, product ->
                                    val productTag = TestTags.SHOWCASE_PRODUCT_TAG
                                    Box(

                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(8.dp)
                                            .background(Color.Yellow)
                                            .weight(1f)
                                            .clickable {
                                                val intent =
                                                    Intent(context, ItemScreenActivity::class.java)
                                                intent.putExtra("product", product)
                                                context.startActivity(intent)
                                            }
                                            .testTag(productTag)
                                    ) {
                                        Text(
                                            text = "$product",
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 24.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}