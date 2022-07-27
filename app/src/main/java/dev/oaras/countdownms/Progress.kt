package dev.oaras.countdownms

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun Progress(
    modifier: Modifier = Modifier,
    data: CountdownData,
    timeFlow: Flow<Long>,
    strokeWidth: Dp = 5.dp,
) {
    val time by timeFlow.collectAsState(initial = System.currentTimeMillis())
    val progress = remember(time){
        data.progress(time)
    }
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            strokeWidth = strokeWidth,
            progress = progress
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${time - data.start} ms")
            Divider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth(0.5f)
            )
            Text(text = "${data.max} ms")
            Spacer(Modifier.height(2.dp))
            Text(text = "${data.max - time + data.start} ms left")
        }
    }
}

//@Preview
//@Composable
//fun ProgressPreview() {
//    Progress(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(64.dp),
//
//        data = CountdownData(509056093505, 999999999999999)
//    )
//}