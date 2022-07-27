package dev.oaras.countdownms

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Picker(
    modifier: Modifier = Modifier,
    vm: CountdownViewModel,
) {
    val context = LocalContext.current
    val dialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            vm.setTarget(context, year, month, dayOfMonth)
        },
        vm.year, vm.month-1, vm.day
    )
    OutlinedTextField(
        modifier = modifier.clickable { dialog.show() },
        placeholder = { Text(text = "Pick date") },
        value = if (vm.data == null) "" else "Date: ${vm.data!!.string}",
        readOnly = true,
        onValueChange = {},
        enabled = false,
    )
}

@Preview
@Composable
fun PickerPreview() {
    Picker(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), CountdownViewModel(LocalContext.current))
}