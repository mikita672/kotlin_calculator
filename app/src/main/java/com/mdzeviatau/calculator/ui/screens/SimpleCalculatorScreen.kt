package com.mdzeviatau.calculator.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mdzeviatau.calculator.ui.components.Display
import com.mdzeviatau.calculator.ui.components.Numpad
import com.mdzeviatau.calculator.ui.components.RemoveButton
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorAction
import com.mdzeviatau.calculator.ui.viewmodel.CalculatorViewModel

@Composable
fun SimpleCalculatorScreen(
    modifier: Modifier = Modifier, viewModel: CalculatorViewModel = viewModel<CalculatorViewModel>()
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(state.expression) {
        if (state.expression == "Error" || state.expression == "NaN") {
            Toast.makeText(
                context,
                "Cannot divide by 0 or incorrect expression",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = modifier) {
            Display(modifier = Modifier.weight(1f), text = state.expression)
            RemoveButton(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .padding(end = 16.dp),
                onClick = { viewModel.onAction(CalculatorAction.RemoveOneSymbol) })
            Numpad(modifier = Modifier.weight(1.5f), onAction = viewModel::onAction)
        }
    } else {
        Column(modifier = modifier) {
            Display(modifier = Modifier.weight(1.2f), text = state.expression)
            RemoveButton(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.End),
                onClick = { viewModel.onAction(CalculatorAction.RemoveOneSymbol) }
            )
            Numpad(modifier = Modifier.weight(2f), onAction = viewModel::onAction)
        }
    }
}
