package com.baarton.traininglog.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baarton.traininglog.R
import com.baarton.traininglog.model.Duration
import com.baarton.traininglog.model.SportRecord
import com.baarton.traininglog.ui.theme.TrainingLogTheme
import com.baarton.traininglog.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.viewModel
import kotlin.time.Duration.Companion.minutes


@Composable
fun ListScreen(paddingValues: PaddingValues) {
    val viewModel: HomeScreenViewModel by viewModel()

    val state = viewModel.homeScreenState.collectAsState().value

    HomeScreenContent(
        paddingValues,
        state,
        { viewModel.clearDatabase() }
    )
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    state: HomeScreenViewModel.HomeScreenViewState,
    onClearDatabaseClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Text(
            text = stringResource(id = R.string.home_screen_title),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(24.dp)
        )

        RecordList(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            records = state.list
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            enabled = state.list.isNotEmpty(), //TODO might not be true after API data combine
            onClick = { onClearDatabaseClicked() }
        ) {
            Text(
                text = stringResource(id = R.string.home_screen_clear_db_button),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun RecordList(modifier: Modifier, records: List<SportRecord>) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(records.size) { index ->
            RecordRow(records[index])
        }
    }
}

@Composable
fun RecordRow(record: SportRecord) {
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {

            //TODO Icon to display DB/cloud data source
            Text(
                text = record.sportName,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.background,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = record.sportLocation,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.background,
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                )
                Text(
                    text = record.sportDuration.duration.toString(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.background,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrainingLogTheme {
        HomeScreenContent(
            paddingValues = PaddingValues(12.dp),
            state = HomeScreenViewModel.HomeScreenViewState(
                false, listOf(
                    SportRecord(sportName = "Cycling", sportLocation = "Praha", sportDuration = Duration(45.minutes)),
                    SportRecord(sportName = "Running", sportLocation = "Brno", sportDuration = Duration(30.minutes)),
                    SportRecord(
                        sportName = "Swimming",
                        sportLocation = "Staré Město u Uherského Hradiště",
                        sportDuration = Duration(60.minutes)
                    )
                )
            ),
            onClearDatabaseClicked = { }
        )
    }
}