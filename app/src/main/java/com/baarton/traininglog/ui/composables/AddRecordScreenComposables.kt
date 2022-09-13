package com.baarton.traininglog.ui.composables

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.baarton.traininglog.ui.theme.Typography
import com.baarton.traininglog.R
import com.baarton.traininglog.viewmodel.AddRecordViewModel
import org.koin.androidx.compose.viewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes


@Composable
fun AddRecordScreen(paddingValues: PaddingValues) {
    val viewModel: AddRecordViewModel by viewModel()

    val state = viewModel.addRecordState.collectAsState().value

    AddRecordScreenContent(
        paddingValues,
        state,
        { viewModel.onSportValueChanged(it) },
        { viewModel.onSportLocationChanged(it) },
        { viewModel.onSportDurationChanged(it) },
        { viewModel.onSportRecordSaveClick() }
    )
}

@Composable
fun AddRecordScreenContent(
    paddingValues: PaddingValues,
    state: AddRecordViewModel.AddRecordViewState,
    onSportValueChanged: (String) -> Unit,
    onSportLocationChanged: (String) -> Unit,
    onSportDurationChanged: (Duration) -> Unit,
    onSportRecordSaveClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val context = LocalContext.current

        Text(
            text = stringResource(id = R.string.add_screen_title),
            style = Typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(24.dp)
        )

        TextField(
            label = { Text(style = Typography.caption, text = stringResource(id = R.string.add_screen_sport_name_field_caption)) },
            value = state.sportName.value,
            isError = !state.sportName.isValid(),
            onValueChange = { onSportValueChanged(it) },
            textStyle = Typography.body1,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(12.dp),
        )

        TextField(
            label = { Text(style = Typography.caption, text = stringResource(id = R.string.add_screen_sport_location_field_caption)) },
            value = state.sportLocation.value,
            isError = !state.sportLocation.isValid(),
            onValueChange = { onSportLocationChanged(it) },
            textStyle = Typography.body1,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(12.dp)
        )

        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(CenterHorizontally)
                .padding(12.dp)
        ) {
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 12.dp),
                onClick = { selectTime(context, onSportDurationChanged) },
            ) {
                Text(
                    text = stringResource(id = R.string.add_screen_select_duration_button),
                    style = Typography.button
                )
            }

            Text(
                text = state.sportDuration.value.toString(),
                style = Typography.body1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentSize()
                    .padding(horizontal = 12.dp)
            )

            if (!state.sportDuration.isValid()) {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    tint = Color.Red,
                    contentDescription = stringResource(id = R.string.add_screen_content_description_duration_warning),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .wrapContentSize()
                        .padding(horizontal = 12.dp)
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = CenterHorizontally
        ) {

            if (!state.formIsValid) {
                Text(
                    text = stringResource(id = R.string.add_screen_invalid_form_text_warning),
                    color = Color.Red,
                    style = Typography.caption
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                enabled = state.formIsValid,
                onClick = { onSportRecordSaveClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.add_screen_add_record_button),
                    style = Typography.button
                )
            }
            //TODO switch with cloud/local storage option
        }

    }

}

fun selectTime(context: Context, onSportDurationChanged: (Duration) -> Unit) {
    TimePickerDialog(context, { _, hour, minute ->
        val duration = hour.hours + minute.minutes
        onSportDurationChanged(duration)
    }, 0, 0, true).show()
}