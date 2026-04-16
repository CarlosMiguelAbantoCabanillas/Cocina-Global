package com.carlosmiguel.cocinaglobal.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosmiguel.cocinaglobal.FoodListActivity
import com.carlosmiguel.cocinaglobal.R
import com.carlosmiguel.cocinaglobal.ui.theme.RankingActivity

class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by remember { mutableStateOf(false) }

            CocinaGlobalTheme(darkTheme = darkMode) {
                CategoryList(
                    darkMode = darkMode,
                    onToggleDarkMode = {
                        darkMode = !darkMode
                    }
                )
            }
        }
    }
}

data class CategoryItem(
    val name: String,
    val icon: Int
)

@Composable
fun CategoryList(
    darkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    val context = LocalContext.current

    val categories = listOf(
        CategoryItem("Italiana", R.drawable.pizza_icon),
        CategoryItem("Japonesa", R.drawable.sushi),
        CategoryItem("Mexicana", R.drawable.taco),
        CategoryItem("Francesa", R.drawable.bol_de_arroz),
        CategoryItem("Brasileña", R.drawable.brasil_icon),
        CategoryItem("Peruana", R.drawable.ceviche_icon),
        CategoryItem("Argentina", R.drawable.asado_icon),
        CategoryItem("Española", R.drawable.paella_icon),
        CategoryItem("Turca", R.drawable.kebad_icon),
        CategoryItem("Coreana", R.drawable.kimchi_icon)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        //FondoOndasCategory()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(top = 85.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Cocina Global",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = {
                        val intent = Intent(context, FavoritesActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "Ver Favoritos",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Button(
                    onClick = {
                        val intent = Intent(context, RankingActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB35A3C)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Text(text = "Ver Ranking ⭐", color = Color.White)
                }
            }

            items(categories) { category ->
                CategoryCard(
                    categoryName = category.name,
                    icon = category.icon,
                    onClick = {
                        val intent = Intent(context, FoodListActivity::class.java).apply {
                            putExtra("category", category.name)
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }
        IconButton(
            onClick = onToggleDarkMode,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(38.dp)
        ) {
            Text(
                text = if (darkMode) "🌙" else "☀️",
                fontSize = 28.sp
            )
        }
    }
}

@Composable
fun FondoOndasCategory() {
    val peachLight = Color(0xFFFFD4BD)
    val peachDark = Color(0xFFFFB894)
    val wave1Color = Color(0xFFFFC4A3)
    val wave2Color = Color(0xFFFFD9C4)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(peachLight, peachDark)
            ),
            size = size
        )

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.15f)
                cubicTo(w * 0.25f, h * 0.05f, w * 0.75f, h * 0.25f, w, h * 0.15f)
                lineTo(w, 0f)
                lineTo(0f, 0f)
                close()
            },
            color = wave1Color
        )

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.45f)
                cubicTo(w * 0.25f, h * 0.35f, w * 0.75f, h * 0.55f, w, h * 0.45f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = wave2Color
        )
    }
}

@Composable
fun CategoryCard(
    categoryName: String,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = categoryName,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )

            Text(
                text = categoryName,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun PreviewCategoryScreen() {
    CocinaGlobalTheme {
        CategoryList(
            darkMode = false,
            onToggleDarkMode = {}
        )
    }
}