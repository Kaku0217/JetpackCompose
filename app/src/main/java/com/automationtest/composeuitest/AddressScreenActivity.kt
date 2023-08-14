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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.automationtest.composeuitest.testdata.TestTags

class AddressScreenActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
        val initialItemCount = cartViewModel.getInitialCartItemCount(applicationContext)
            cartViewModel.updateCartItemCount(applicationContext, initialItemCount)
            AddressScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen() {
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    //val navController = rememberNavController()

    val cartViewModel: CartViewModel = viewModel()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Item Screen") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to ShowcaseActivity when the back button is clicked
                        context.startActivity(Intent(context, CartScreenActivity::class.java))
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
            Box(modifier = Modifier.testTag(TestTags.ADDRESS_SCREEN_TAG)) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    OutlinedTextField(
                        value = firstname,
                        onValueChange = { newName -> firstname = newName },
                        label = { Text("First Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .testTag(TestTags.FIRST_NAME_TAG)
                    )

                    OutlinedTextField(
                        value = lastname,
                        onValueChange = { newName -> lastname = newName },
                        label = { Text("Last Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .testTag(TestTags.LAST_NAME_TAG)
                    )

                    OutlinedTextField(
                        value = zipCode,
                        onValueChange = { newZipCode -> zipCode = newZipCode },
                        label = { Text("Zip/Postal Code") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .testTag(TestTags.ZIP_CODE_TAG)

                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, CartScreenActivity::class.java))
                            },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .testTag(TestTags.ADDRESS_CANCEL_TAG)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                context.startActivity(Intent(context, ConfirmPurchaseActivity::class.java))
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .testTag(TestTags.ADDRESS_CONTINUE_TAG)
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        }
    )
}
