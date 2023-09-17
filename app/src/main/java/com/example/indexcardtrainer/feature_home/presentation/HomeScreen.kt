package com.example.indexcardtrainer.feature_home.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.util.UserRankImageMap
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.feature_home.presentation.components.TrainingsConfigBottomSheet
import com.example.indexcardtrainer.feature_home.presentation.components.IndexCardPreview
import com.example.indexcardtrainer.feature_home.presentation.components.MenuTile
import com.example.indexcardtrainer.feature_home.presentation.components.UserInfoBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    /*************************** Dialog **************************************/
    if (viewModel.shouldShowUserRankUpDialog.value) {
        Dialog(onDismissRequest = { viewModel.shouldShowUserRankUpDialog.value = false }) {
            val imageId = UserRankImageMap.map[viewModel.userData.currentRank]
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.large)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.promotion),
                        style = MaterialTheme.typography.displaySmall
                    )
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "close",
                        modifier = Modifier
                            .clickable {
                                viewModel.shouldShowUserRankUpDialog.value = false
                            }
                            .size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.promotion_text),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = viewModel.getUserRankName(
                        viewModel.userState.value.rank,
                        LocalContext.current
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                imageId?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = "new rank",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
    /******************************************************************************************/

    val recentlyFailedCards = viewModel.getRecentlyFailedCards()
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val userRank = viewModel.userState.value.rank
        UserInfoBar(
            userRank = viewModel.getUserRankName(userRank, LocalContext.current),
            userRubberDots = viewModel.userState.value.rubberDots,
            imageResId = UserRankImageMap.map[userRank]
        )
        if (recentlyFailedCards.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    pageCount = recentlyFailedCards.size,
                    state = pagerState,
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large)

                ) { index ->
                    IndexCardPreview(recentlyFailedCards[index])
                }
                Row(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(recentlyFailedCards.size) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
            ) {
                Text(
                    text = stringResource(id = R.string.no_cards_recently_failed),
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuTile(
                text = stringResource(id = R.string.show_cards),
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                onClick = { viewModel.onNavigationEvent(NavigationEvent.OnAllCardsClick) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                backgroundResId = R.drawable.cards_tile_background
            )
            MenuTile(
                text = stringResource(id = R.string.start_training),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                isIconLeft = false,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                backgroundResId = R.drawable.training_tile_background,
                onClick = { viewModel.shouldShowTrainingsConfig.value = true }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            MenuTile(
                text = stringResource(id = R.string.view_statistics),
                imageVector = Icons.Rounded.BarChart,
                hasIcon = false,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                backgroundResId = R.drawable.statistics_tile_background,
                isEnabled = false
            )
            MenuTile(
                text = stringResource(id = R.string.trainings_history),
                imageVector = Icons.Rounded.Settings,
                hasIcon = false,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                backgroundResId = R.drawable.trainingshistory_background,
                onClick = { viewModel.onNavigationEvent(NavigationEvent.OnTrainingsHistoryClick) },
                isEnabled = true
            )
        }
    }
    TrainingsConfigBottomSheet(
        shouldShowBottomSheet = viewModel.shouldShowTrainingsConfig,
        loadAllCards = viewModel::loadAllCards,
        onHomeEvent = viewModel::onHomeEvent,
        cardSelectionStateList = viewModel.cardSelectionStateList,
        categorySelectionStateList = viewModel.categorySelectionStateList,
        refresh = viewModel::refreshAnything
    )
}