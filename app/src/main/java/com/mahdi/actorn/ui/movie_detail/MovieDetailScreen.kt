package com.mahdi.actorn.ui.movie_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahdi.actorn.R
import com.mahdi.actorn.model.MovieDetail
import com.mahdi.actorn.ui.component.LoadNetworkImage
import com.mahdi.actorn.utils.verticalGradientScrim


@Composable
fun MovieDetailScreen(
    selectedMovie: (Int) -> Unit,
    viewModel: MovieDetailViewModel,
    navigateUp: () -> Unit,
) {
    val uiState = viewModel.uiState
    Spacer(modifier = Modifier.height(15.dp))
    Scaffold(
        topBar = {
            MovieDetailAppBar(
                navigateUp = navigateUp,
                title = ""
            )
        }
    ) {
        MovieDetailsContent(uiState.movieData, viewModel)
    }
}

@Composable
fun Genres(viewModel: MovieDetailViewModel) {
    val uiState = viewModel.uiState
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_genres),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            uiState.movieData?.let { data ->
                data.genres.forEach { genres ->
                    TextAll(text = "Genres : ", color = MaterialTheme.colors.onSurface)

                    if (genres != null) {
                        TextAll2(text = "$genres", color = MaterialTheme.colors.onSurface)
                    } else {
                        ""
                    }

                }
            }
        }
    }
}

@Composable
fun ProductionCompanies(viewModel: MovieDetailViewModel) {
    val uiState = viewModel.uiState
    uiState.movieData?.let { data ->
        data.productionCompanies.forEach { productionCompanies ->
            TextDescription(
                query = "by $productionCompanies", paddingstart = 0.dp,
                paddingEnd = 0.dp
            )
        }
    }
}

@Composable
fun ProfileNAME(movieData: MovieDetail?, viewModel: MovieDetailViewModel) {
    val uiState = viewModel.uiState
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(20.dp))
        MovieBanner(movieData?.poster)
        Spacer(modifier = Modifier.width(20.dp))
        Column() {
            if (uiState.movieData?.movieTitle !=null){
                TextTitle(query = "${uiState.movieData?.movieTitle}", padding = 0.dp)
            }else{
                ""
            }
            ProductionCompanies(viewModel = viewModel)
        }
    }
}

@Composable
fun MovieDetailsContent(movieData: MovieDetail?, viewModel: MovieDetailViewModel) {
    val uiState = viewModel.uiState
     ActorBackgroundWithGradiantForeground(imageUrl = uiState.movieData?.poster)
    LazyColumn {
        item { ProfileNAME(movieData = movieData, viewModel = viewModel) }
        item { MovieBannercenter(bannerUrl = movieData?.banner) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { Overview(overview = movieData?.overview, query = movieData?.voteAverage) }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { Tagline(tag = movieData?.tagline) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { PopularAverageLanguage(movieData) }
        item { Genres(viewModel = viewModel) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { ReleaseData(Date = movieData?.releaseData) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { RunTime(runTime = movieData?.runtime) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { OriginalLanguage(language = movieData?.originalLanguage) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { MovieBudget(budget = movieData?.budget) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { Revenue(revenue = movieData?.revenue) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { Status(status = movieData?.status) }
        item { divider() }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

@Composable
fun PopularAverageLanguage(movieData: MovieDetail?) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.fillMaxWidth(0.5f), contentAlignment = Alignment.CenterStart) {
                Popularity(popularity = movieData?.popularity)
            }
            Box(Modifier.fillMaxWidth(1f), contentAlignment = Alignment.CenterStart) {
                VoteAverage(average = movieData?.voteAverage)
            }
        }
    }
}

@Composable
fun VoteAverage(average: Double?) {
    Column() {
        Text(text = "VoteAverage ", color = Color.White, fontSize = 15.sp)
        Row(verticalAlignment = Alignment.Bottom) {
            if (average!=null){
                TextPopularAverageLanguage(
                    text = "$average",
                    fontWeight = FontWeight.W400,
                )
            }else{
                ""
            }

            Text(text = "/10", fontSize = 15.sp, color = Color.Gray)
        }
    }
}

@Composable
fun Tagline(tag: String?) {
    Column() {
        TextTitle(query = "Tagline", padding = 20.dp)
        if (tag!=null){
            TextDescription(query = "$tag", paddingstart = 20.dp, paddingEnd = 20.dp)
        }else{
            ""
        }

    }
}

@Composable
fun Status(status: String?) {
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_replace),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextAll(
                text = "Status : ",
                color = MaterialTheme.colors.onSurface
            )
            if (status!=null){
                TextAll2(text = "$status", color = MaterialTheme.colors.onSurface)
            }else{
                ""
            }

        }
    }
}

@Composable
fun RunTime(runTime: Int?) {
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextAll(
                text = "Running Time : ",
                color = MaterialTheme.colors.onSurface
            )
            if (runTime!=null){
                TextAll2(text = "$runTime", color = MaterialTheme.colors.onSurface)
            }else{
                ""
            }

        }
    }
}

@Composable
fun Revenue(revenue: Long?) {
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_money),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextAll(
                text = "Movie Income : ",
                color = MaterialTheme.colors.onSurface
            )
            if (revenue!=null){
                TextAll2(text = "$revenue", color = MaterialTheme.colors.onSurface)
            }else{
                ""
            }

        }
    }
}

@Composable
fun ReleaseData(
    Date: String?,
) {
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_date_range_24),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextAll(
                text = "ReleaseDate : ",

                color = MaterialTheme.colors.onSurface
            )
            if (Date!=null){
                TextAll2(text = "$Date", color = MaterialTheme.colors.onSurface)
            }else{
                ""
            }

        }
    }
}

@Composable
private fun ActorBackgroundWithGradiantForeground(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Box {
        LoadNetworkImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(id = R.string.cd_actor_banner),
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f),
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
private fun MoviePosterDetail(
    posterUrl: String?,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadNetworkImage(
            imageUrl = "$posterUrl",
            contentDescription = stringResource(id = R.string.cd_actor_image),
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
        )
    }
}

@Composable
fun OriginalLanguage(
    language: String?,
) {
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_replace),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextAll(
                text = "Language : ",

                color = MaterialTheme.colors.onSurface
            )
            if (language!=null){
                TextAll2(text = "$language", color = MaterialTheme.colors.onSurface)
            }else{
                ""
            }

        }

    }
}

@Composable
fun Overview(
    overview: String?,
    query: Double?,
) {
    Column(verticalArrangement = Arrangement.Center) {
        Box(Modifier.fillMaxWidth()) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextTitle(query = "Overview", padding = 20.dp)
            }
            Box(
                contentAlignment = Alignment.CenterEnd, modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_gold),
                        contentDescription = ""
                    )
                    if (query != null) {
                        TextDescription(
                            query = "$query", paddingstart = 0.dp,
                            paddingEnd = 20.dp
                        )
                    } else {
                        ""
                    }


                }
            }
        }
        if (overview != null) {
            TextDescription(query = "$overview", paddingstart = 20.dp, paddingEnd = 20.dp)
        } else {
            ""
        }

    }

}


@Composable
fun Popularity(
    popularity: Double?,
) {
    Column() {

        Text(text = "Grade Of Fame", color = Color.White, fontSize = 15.sp)

        Row(verticalAlignment = Alignment.Bottom) {
            if (popularity!=null){
                TextPopularAverageLanguage(
                    text = "$popularity",
                    fontWeight = FontWeight.W400,

                    )
            }else{
                ""
            }

            Text(text = "/100", fontSize = 15.sp, color = Color.Gray)
        }


    }


}


@Composable
fun MovieBudget(
    budget: String?,
) {
    Column() {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                painter = painterResource(id = R.drawable.ic_money),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextAll(
                text = "Movie Production Budget : ",

                color = MaterialTheme.colors.onSurface
            )
            if (budget!=null){
                TextAll2(text = "$budget", color = MaterialTheme.colors.onSurface)
            }else{
                ""
            }

        }


    }

}


@Composable
private fun MovieBanner(
    bannerUrl: String?,
) {
    Box(
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            backgroundColor = MaterialTheme.colors.onSurface,
            shape = RoundedCornerShape(40.dp)
        ) {
            LoadNetworkImage(
                imageUrl = "$bannerUrl",
                contentDescription = stringResource(id = R.string.cd_actor_image),
                shape = RectangleShape,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)


            )
        }


    }
}

@Composable
fun TextAll(
    text: String,
    color: Color,
) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontWeight = SemiBold,
            fontSize = 18.sp,
            textAlign = TextAlign.Justify
        ),
        modifier = Modifier.padding(start = 20.dp)
    )
}

@Composable
fun TextAll2(
    text: String,
    color: Color,
) {

    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontWeight = W300,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        ),

        modifier = Modifier.padding(end = 20.dp)
    )


}


@Composable
fun TextPopularAverageLanguage(
    text: String,
    fontWeight: FontWeight,


    ) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = fontWeight,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            ),

            modifier = Modifier.padding(top = 5.dp)
        )

    }

}


@Composable
fun divider(

) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(1.dp)
            .alpha(0.7f), color = Color.Gray
    )
}

@Composable
fun dividerPopularAverageLanguage() {
    Divider(
        modifier = Modifier
            .width(100.dp)
            .padding(start = 5.dp, end = 5.dp)
            .height(1.5.dp)
            .alpha(1f), color = Color.Black
    )
}


@Composable
fun TextTitle(query: String, padding: Dp) {

    Text(
        modifier = Modifier.padding(start = padding),
        text = query,
        style = TextStyle(
            lineHeight = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            textAlign = TextAlign.Justify
        )
    )
}

@Composable
fun TextDescription(query: String, paddingstart: Dp, paddingEnd: Dp) {
    Text(
        text = query, fontWeight = W300, fontSize = 16.sp,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier.padding(start = paddingstart, end = paddingEnd),

    )

}


@Composable
private fun MovieBannercenter(
    bannerUrl: String?,

    ) {
    Box(
        modifier = Modifier.padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            backgroundColor = MaterialTheme.colors.onSurface,
            shape = RoundedCornerShape(30.dp)
        ) {
            LoadNetworkImage(
                imageUrl = "$bannerUrl",
                contentDescription = stringResource(id = R.string.cd_actor_image),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(200.dp)


            )
        }


    }
}

