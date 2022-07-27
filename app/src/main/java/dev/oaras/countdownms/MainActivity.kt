package dev.oaras.countdownms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.oaras.countdownms.ui.theme.CountdownInMillisecondTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val vm: CountdownViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownInMillisecondTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Countdown in Millisecond")
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            elevation = 0.dp
                        )
                    },
                ) {
                    Picker(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        vm
                    )
                    if(vm.data != null){
                        Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Progress(modifier = Modifier.size(200.dp), vm.data!!, vm.currentTimeMs)
                        }
                    }
                }
            }
        }
    }
}