package dev.kevalkanpariya.educo.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import dev.kevalkanpariya.educo.R
import dev.kevalkanpariya.educo.domain.model.CourseCategory
import dev.kevalkanpariya.educo.navigation.Routes
import dev.kevalkanpariya.educo.presentation.components.BottomNavigationBar
import dev.kevalkanpariya.educo.presentation.components.CourseCard
import dev.kevalkanpariya.educo.presentation.components.SearchBar
import dev.kevalkanpariya.educo.ui.theme.Grey600
import timber.log.Timber

@Preview
@Composable
fun SearchScreen(

) {

    val topSearches= listOf(
        "photography",
        "craft",
        "art",
        "procreate",
        "marketing",
        "UX design"
    )

    val courseCategories = listOf(
        CourseCategory(
            title = "Hello everyone 1",
            id = "1"
        ),
        CourseCategory(
            title = "Hello everyone 2",
            id = "2"
        ),
        CourseCategory(
            title = "Hello everyone 3",
            id = "3"
        ),
        CourseCategory(
            title = "Hello everyone 4",
            id = "4"
        ),
        CourseCategory(
            title = "Hello everyone 5",
            id = "5"
        ),
        CourseCategory(
            title = "Hello everyone 6",
            id = "6"
        )
    )

    val scrollState = rememberScrollState()



    ScaffoldWithBottomBar(
        bottomBar = {
            BottomNavigationBar(
                route =Routes.SearchScreen.route,
                onItemSelected = {
                    Log.d("SearchScreen", "item $it is selected")
                }
            )
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val constraints = if (minWidth < 600.dp) {
                decoupledConstraints(16.dp) // Portrait constraints
            } else {
                decoupledConstraints(margin = 32.dp) // Landscape constraints
            }



            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState)
                ,
                constraintSet = constraints
            ) {

                SearchBar(modifier = Modifier.layoutId(searchBarRef))

                Text(modifier = Modifier.layoutId(topSearchTitleRef), text = "Top searches", color = Grey600)

                TopSearchList(
                    modifier = Modifier.layoutId(topSearchListRef),
                    topSearches = topSearches
                )

                Text(modifier = Modifier.layoutId(categoryTextRef), text = "Categories", color = Grey600)

                CategoryList(
                    modifier = Modifier.layoutId(courseCatListRef),
                    courseCategories = courseCategories
                )



            }

        }
    }


}


@Composable
fun TopSearchList(
    modifier: Modifier = Modifier,
    topSearches: List<String>
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        mainAxisAlignment = MainAxisAlignment.Start,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 10.dp,
        mainAxisSpacing = 10.dp
    ) {
        topSearches.forEach { topSearch ->
            Text(
                text = topSearch,
                modifier = Modifier
                    .background(
                        color = Color(0xFFEDEEF0),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(10.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = Color(0xFF282F3E),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CategoryList(
    modifier: Modifier = Modifier,
    courseCategories: List<CourseCategory>
) {

    //whenever use lazyGrid always define its height
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        items(courseCategories, key = { it.id }) { courseCategory ->
            CourseCard(
                title = courseCategory.title,
                imagePainter = painterResource(id = R.drawable.interiordesign)
            )
        }
    }
}


private fun decoupledConstraints(
    margin: Dp
): ConstraintSet {
    return ConstraintSet {

        val searchBar = createRefFor(searchBarRef)
        val topSearchBarTitle = createRefFor(topSearchTitleRef)
        val topSearchList = createRefFor(topSearchListRef)
        val categoryText = createRefFor(categoryTextRef)
        val courseCatList = createRefFor(courseCatListRef)
        val bottomBar = createRefFor(bottomBarRef)

        constrain(searchBar) {
            top.linkTo(parent.top, 30.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(topSearchBarTitle) {
            top.linkTo(searchBar.bottom, 30.dp)
            start.linkTo(parent.start)
        }

        constrain(topSearchList) {
            top.linkTo(topSearchBarTitle.bottom, 28.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(categoryText) {
            top.linkTo(topSearchList.bottom, 40.dp)
            start.linkTo(parent.start)
        }

        constrain(courseCatList) {
            top.linkTo(categoryText.bottom, 28.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(bottomBar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, 0.dp)
        }
    }
}

@Composable
@UiComposable
fun ScaffoldWithBottomBar(
    bottomBar: @Composable @UiComposable () -> Unit ={},
    content: @Composable @UiComposable (PaddingValues) -> Unit ={},

) {

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        Log.d("EducoMMMM","$layoutHeight  $layoutWidth")

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val bottomBarPlaceables = subcompose("bottombar") {
                CompositionLocalProvider(
                    content = bottomBar
                )
            }.fastMap { it.measure(looseConstraints) }

            val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height ?: 0

            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.fastForEach {
                it.place(0, layoutHeight - bottomBarHeight)
            }

            val bodyContentHeight = layoutHeight

            val bodyContentPlaceables = subcompose("maincontent") {
                val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
                val innerPadding = PaddingValues(
                    top = insets.calculateTopPadding(),
                    bottom =
                    if (bottomBarPlaceables.isEmpty()) {
                        insets.calculateBottomPadding()
                    } else {
                        bottomBarHeight.toDp()
                    },
                    start = insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection),
                    end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
                )
                content(innerPadding)
            }.fastMap { it.measure(looseConstraints.copy(maxHeight = bodyContentHeight)) }

            // Placing to control drawing order to match default elevation of each placeable
            bodyContentPlaceables.fastForEach {
                it.place(0, 0)
            }
        }
    }
}

private const val searchBarRef = "searchbar"
private const val topSearchTitleRef = "top_search_title"
private const val categoryTextRef = "category_text"
private const val topSearchListRef = "top_search_list"
private const val courseCatListRef = "course_cat_list"
private const val bottomBarRef = "bottom_bar"