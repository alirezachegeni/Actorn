package com.mahdi.actorn.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsHeight
import com.mahdi.actorn.model.ActorDetail
import com.mahdi.actorn.model.Movie
import com.mahdi.actorn.ui.component.CategoryTitle
import com.mahdi.actorn.ui.component.LoadNetworkImage
import com.mahdi.actorn.ui.component.ShowProgressIndicator
import com.mahdi.actorn.utils.*
import timber.log.Timber
import com.mahdi.actorn.*
import com.mahdi.actorn.R

@Composable
fun DetailsScreen(
    selectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: DetailsViewModel,
) {
    val uiState = viewModel.uiState
    val actorProfile = "${uiState.actorData?.profileUrl}"

    Surface(color = MaterialTheme.colors.background) {

        Box {
            ActorBackgroundWithGradiantForeground(imageUrl = actorProfile)
            Column {
                ContentDetail(
                    navigateUp = navigateUp,
                    uiState = uiState,
                    selectedMovie = selectedMovie
                )
            }
            ShowProgressIndicator(isLoadingData = uiState.isFetchingDetail)
        }
    }
}

@Composable
private fun ActorBackgroundWithGradiantForeground(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box {
        LoadNetworkImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(id = R.string.cd_actor_banner),
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            shape = RectangleShape
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(0.5f),
                    startYPercentage = 0f,
                    endYPercentage = 1f
                )
        )
    }
}

@Composable
private fun ContentDetail(
    navigateUp: () -> Unit,
    uiState: DetailsViewState,
    selectedMovie: (Int) -> Unit,
) {
    val actorData = uiState.actorData
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsHeight()
    )
    if (actorData?.actorName != null){
        DetailAppBar(navigateUp = navigateUp, title = "${actorData?.actorName}")
    }else{
        ""
    }
    Spacer(modifier = Modifier.padding(top = 20.dp))
    ActorRoundProfile("${actorData?.profileUrl}")
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            ActorInfoHeader(actorData = actorData)
        }
        item {
            ActorCastMovie(cast = uiState.castList, selectedMovie = selectedMovie)
        }
        item {
            ActorBioGraphi(bioGraphi = actorData?.bioGraphi)
        }
    }
}

@Composable
private fun ActorRoundProfile(
    profileUrl: String,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        LoadNetworkImage(
            imageUrl = profileUrl,
            contentDescription = stringResource(id =  com.mahdi.actorn.R.string.cd_actor_image),
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                ),
            shape = CircleShape
        )
    }
}

@Composable
private fun ActorInfoHeader(
    actorData: ActorDetail?,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            AgeInfo(actorAge = actorData?.dateOfBirth)

        }

        item {
            PopularityInfo(popularity = actorData?.popularity)

        }
        item {
            CountryInfo(placeOfBirth = actorData?.placeOfBirth)
        }
    }
}

@Composable
private fun ActorCastMovie(
    cast: List<Movie>,
    selectedMovie: (Int) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id =  com.mahdi.actorn.R.drawable.ic_movies_cast),
            contentDescription = stringResource(
                id =  com.mahdi.actorn.R.string.cd_cast_icon
            ),
            colorFilter = ColorFilter.tint(color = Color.White),
            alpha = 0.8f,
            modifier = Modifier.padding(start = 20.dp)
                .size(40.dp)
        )
        CategoryTitle(title = stringResource(id = com.mahdi.actorn. R.string.cast_movie_title))
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        items(cast) { movie ->
            LoadNetworkImage(imageUrl = movie.posterPathUrl,
                contentDescription = stringResource(id =  com.mahdi.actorn.R.string.cd_movie_poster),
                modifier = Modifier
                    .size(100.dp, 150.dp)
                    .clickable {
                        Timber.e("id is${movie.movieId}")
                        selectedMovie(movie.movieId)
                    },
                shape = MaterialTheme.shapes.medium)
        }
    }
}

@Composable
private fun ActorBioGraphi(
    bioGraphi: String?,
) {
    Column(
        modifier = Modifier
            .verticalGradientScrim(
                color = MaterialTheme.colors.surface.copy(
                    0.75f
                ), startYPercentage = 0f, endYPercentage = 1f
            )
            .padding(bottom = 60.dp, top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id =  com.mahdi.actorn.R.drawable.ic_biography),
                contentDescription = stringResource(
                    id =  com.mahdi.actorn.R.string.cast_biography_title
                ),
                colorFilter = ColorFilter.tint(color = Color.White),
                alpha = 0.8f, modifier = Modifier.size(40.dp)
            )
            CategoryTitle(title = stringResource(id =  com.mahdi.actorn.R.string.cast_biography_title))
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        if (bioGraphi != null) {
            Text(
                text = bioGraphi,
                style = MaterialTheme.typography.h1, fontSize = 18.sp, color = Color.White
            )
        }
    }
}

@Composable
private fun AgeInfo(
    actorAge: String?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp, 45.dp)) {
            Text(
                text = "${calculateAge(actorAge)}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.size(80.dp, 80.dp), contentAlignment = Alignment.TopCenter) {
            ActorInfoHeaderSubtitle(subtitle = stringResource(id =  com.mahdi.actorn.R.string.actor_age_subtitle))
        }
    }
}

@Composable
private fun CountryInfo(
    placeOfBirth: String?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Column {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp, 45.dp)) {
                    Image(
                        painter = painterResource(id =  com.mahdi.actorn.R.drawable.ic_globe),
                        contentDescription = stringResource(
                            id =  com.mahdi.actorn.R.string.cd_place_of_birth_icon
                        ),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                    )
                }
                Box(
                    modifier = Modifier.size(80.dp, 80.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(text = placeOfBirth ?: "", style = MaterialTheme.typography.h1 , textAlign = TextAlign.Center)
                }


            }

        }
    }
}

@Composable
private fun PopularityInfo(
    popularity: Double?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp, 45.dp)) {
            Text(
                text = getPopularity(popularity),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.size(80.dp, 80.dp), contentAlignment = Alignment.TopCenter) {
            ActorInfoHeaderSubtitle(subtitle = stringResource(id = com.mahdi.actorn.R.string.actor_popularity_subtitle))
        }
    }
}

@Composable
private fun ActorInfoHeaderSubtitle(
    subtitle: String,
) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(vertical = 10.dp),
        color = MaterialTheme.colors.onBackground, textAlign = TextAlign.Center
    )
}

