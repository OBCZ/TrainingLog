package com.baarton.traininglog.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.baarton.traininglog.ui.theme.Purple700
import com.baarton.traininglog.viewmodel.AddRecordViewModel
import org.koin.androidx.compose.viewModel


@Composable
fun AddRecordScreen(paddingValues: PaddingValues) {
    val viewModel: AddRecordViewModel by viewModel()

    val state = viewModel.addRecordState.collectAsState().value

    AddRecordScreenContent(
        paddingValues,
        state,
        { viewModel.onSportValueChanged(it) },
        { viewModel.onSportLocationChanged(it) },
        { viewModel.onSportDurationChanged(it) }
    )

}

@Composable
fun AddRecordScreenContent(
    paddingValues: PaddingValues,
    state: AddRecordViewModel.AddRecordViewState,
    onSportValueChanged: (String) -> Unit,
    onSportLocationChanged: (String) -> Unit,
    onSportDurationChanged: (String) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Purple700)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Add Record Screen",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(CenterHorizontally),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        TextField(
            label = { Text(color = Color.White, text = "Add sport") },
            value = state.sportName,
            onValueChange = {
                onSportValueChanged(it)
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = (50.sp)
            )

        )
        TextField(
            label = { Text(color = Color.White, text = "Add location") },
            value = state.sportLocation,
            onValueChange = {
                onSportLocationChanged(it)
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = (50.sp)
            )
        )
        TextField(
            label = { Text(color = Color.White, text = "Add duration") },
            value = state.sportDuration.toIsoString(),
            onValueChange = {
                onSportDurationChanged(it)
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = (50.sp)
            )
        )
    }

}
