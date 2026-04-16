package com.carlosmiguel.cocinaglobal

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carlosmiguel.cocinaglobal.ui.theme.CocinaGlobalTheme
import com.carlosmiguel.cocinaglobal.ui.theme.RecipeDetailActivity
import kotlinx.coroutines.launch

data class FoodItem(
    val name: String,
    val description: String,
    val imageRes: Int
)

private val flagByCategory = mapOf(
    "Italiana" to R.drawable.bandera_italia,
    "Japonesa" to R.drawable.bandera_japon,
    "Mexicana" to R.drawable.bandera_mexico,
    "Francesa" to R.drawable.bandera_francia,
    "Brasileña" to R.drawable.bandera_brasil,
    "Peruana" to R.drawable.bandera_peru,
    "Argentina" to R.drawable.bandera_argentina,
    "Española" to R.drawable.bandera_espana,
    "Turca" to R.drawable.bandera_turquia,
    "Coreana" to R.drawable.bandera_corea
)

private val foodByCategory = mapOf(

    "Peruana" to listOf(
        FoodItem("Ceviche", "Pescado fresco marinado en limón, con cebolla, ají y cilantro.", R.drawable.ceviche),
        FoodItem("Lomo Saltado", "Salteado de carne con cebolla, tomate y papas fritas, estilo criollo.", R.drawable.lomo_saltado),
        FoodItem("Causa Rellena", "Capas de papa amarilla con relleno cremoso de pollo o atún.", R.drawable.causa_rellena),
        FoodItem("Ají de Gallina", "Pollo desmenuzado en salsa cremosa de ají amarillo, queso y nueces.", R.drawable.aji_de_gallina),
        FoodItem("Anticuchos", "Brochetas marinadas a la parrilla, intensas y súper sabrosas.", R.drawable.anticuchos)
    ),

    "Mexicana" to listOf(
        FoodItem("Tacos al Pastor", "Tortilla con cerdo sazonado, piña, cilantro y cebolla.", R.drawable.tacos_al_pastor),
        FoodItem("Enchiladas", "Tortillas rellenas bañadas en salsa, con queso y crema.", R.drawable.enchiladas),
        FoodItem("Pozole", "Sopa tradicional de maíz y carne, con limón y toppings al gusto.", R.drawable.pozole),
        FoodItem("Chilaquiles", "Totopos crujientes con salsa roja o verde, queso y crema.", R.drawable.chilaquiles),
        FoodItem("Guacamole", "Aguacate cremoso con limón, cebolla y toque de cilantro.", R.drawable.guacamole)
    ),

    "Italiana" to listOf(
        FoodItem("Pizza", "Masa crujiente con salsa de tomate, queso y tus toppings favoritos.", R.drawable.pizza),
        FoodItem("Pasta Carbonara", "Pasta cremosa con huevo, queso y panceta (sin crema).", R.drawable.pasta_carbonara),
        FoodItem("Lasagna", "Capas de pasta con carne, salsa y queso gratinado.", R.drawable.lasagna),
        FoodItem("Risotto", "Arroz cremoso cocido lento, con mantequilla y parmesano.", R.drawable.risotto),
        FoodItem("Tiramisu", "Postre suave de café, cacao y crema, clásico italiano.", R.drawable.tiramisu)
    ),

    "Japonesa" to listOf(
        FoodItem("Sushi", "Arroz sazonado con pescado o rellenos, fresco y ligero.", R.drawable.sushi_2),
        FoodItem("Ramen", "Sopa con fideos, caldo intenso y toppings deliciosos.", R.drawable.ramen_2),
        FoodItem("Tempura", "Fritura ligera y crujiente de mariscos o vegetales.", R.drawable.tempura),
        FoodItem("Onigiri", "Bola de arroz rellena, práctica y súper rica.", R.drawable.onigiri),
        FoodItem("Takoyaki", "Bolitas calientes con pulpo y salsa, típicas de street food.", R.drawable.takoyaki)
    ),

    "Francesa" to listOf(
        FoodItem("Croissant", "Hojaldre mantequilloso, doradito y suave por dentro.", R.drawable.croissant),
        FoodItem("Ratatouille", "Guiso de verduras cocidas lentamente, lleno de sabor.", R.drawable.ratatouille),
        FoodItem("Crêpes", "Panqueques finos dulces o salados, súper versátiles.", R.drawable.crepes),
        FoodItem("Quiche", "Tarta salada con huevo, queso y relleno a elección.", R.drawable.quiche),
        FoodItem("Macarons", "Galletitas delicadas rellenas, coloridas y elegantes.", R.drawable.macarons)
    ),

    "Brasileña" to listOf(
        FoodItem("Feijoada", "Guiso de frijoles negros con carnes, potente y tradicional.", R.drawable.feijoada),
        FoodItem("Pão de Queijo", "Panecillos de queso, suaves por dentro y adictivos.", R.drawable.pao_de_queijo),
        FoodItem("Moqueca", "Estofado de pescado con leche de coco y especias.", R.drawable.moqueca),
        FoodItem("Brigadeiro", "Dulce de chocolate cremoso, clásico de fiestas.", R.drawable.brigadeiro),
        FoodItem("Coxinha", "Croqueta de pollo en forma de gota, crujiente y rica.", R.drawable.coxinha)
    ),

    "Argentina" to listOf(
        FoodItem("Asado", "Parrillada argentina con cortes jugosos y sabor ahumado.", R.drawable.asado),
        FoodItem("Empanadas", "Masa rellena de carne o pollo, horneada o frita.", R.drawable.empanadas),
        FoodItem("Milanesa", "Carne empanizada dorada, clásica y contundente.", R.drawable.milanesa),
        FoodItem("Choripán", "Chorizo a la parrilla en pan, con chimichurri.", R.drawable.choripan),
        FoodItem("Dulce de Leche", "Crema dulce caramelizada, perfecta para postres.", R.drawable.dulce_de_leche)
    ),

    "Española" to listOf(
        FoodItem("Paella", "Arroz con mariscos o carne, con azafrán y mucho sabor.", R.drawable.paella),
        FoodItem("Tortilla Española", "Tortilla de papa y huevo, simple pero brutal.", R.drawable.tortilla_espanola),
        FoodItem("Gazpacho", "Sopa fría de tomate y verduras, fresca y ligera.", R.drawable.gazpacho),
        FoodItem("Croquetas", "Bocaditos cremosos por dentro y crujientes por fuera.", R.drawable.croquetas),
        FoodItem("Churros", "Masa frita crujiente, perfecta con chocolate caliente.", R.drawable.churros)
    ),

    "Turca" to listOf(
        FoodItem("Kebab", "Carne sazonada servida en pan o plato, súper popular.", R.drawable.kebab),
        FoodItem("Baklava", "Postre de hojaldre con nueces y miel, bien dulce.", R.drawable.baklava),
        FoodItem("Lahmacun", "Pan finito con carne especiada, tipo pizza turca.", R.drawable.lahmacun),
        FoodItem("Menemen", "Huevos con tomate y especias, perfecto para desayuno.", R.drawable.menemen),
        FoodItem("Börek", "Hojaldre relleno de queso o carne, súper rico.", R.drawable.borek)
    ),

    "Coreana" to listOf(
        FoodItem("Kimchi", "Fermentado picante de col, básico en Corea.", R.drawable.kimchi),
        FoodItem("Bibimbap", "Arroz con vegetales, carne y huevo, mezclado al final.", R.drawable.bibimbap),
        FoodItem("Tteokbokki", "Pastelitos de arroz en salsa picante y dulce.", R.drawable.tteokbokki),
        FoodItem("Bulgogi", "Carne marinada dulce-salada, jugosa y famosa.", R.drawable.bulgogi),
        FoodItem("Japchae", "Fideos de camote salteados con vegetales y salsa.", R.drawable.japchae)
    )
)

class FoodListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getStringExtra("category") ?: "Categoría"

        setContent {
            CocinaGlobalTheme {
                FoodListScreen(category = category)
            }
        }
    }
}

@Composable
fun FondoOndasNaranja() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFFFD4BD), Color(0xFFFFB894))
            ),
            size = size
        )

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.18f)
                cubicTo(w * 0.25f, h * 0.10f, w * 0.75f, h * 0.26f, w, h * 0.18f)
                lineTo(w, 0f)
                lineTo(0f, 0f)
                close()
            },
            color = Color(0xFFFFC4A3)
        )

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.55f)
                cubicTo(w * 0.25f, h * 0.45f, w * 0.75f, h * 0.65f, w, h * 0.55f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = Color(0xFFFFE0CC)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(category: String) {
    val context = LocalContext.current
    val foods = foodByCategory[category] ?: emptyList()
    var searchText by remember { mutableStateOf("") }

    val filteredFoods = foods.filter {
        it.name.contains(searchText, ignoreCase = true)
    }
    val flagRes = flagByCategory[category]

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    val context = LocalContext.current

                    IconButton(onClick = {
                        (context as? ComponentActivity)?.finish()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF6D2E1F)
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (flagRes != null) {
                            Image(
                                painter = painterResource(id = flagRes),
                                contentDescription = "Bandera de $category",
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        Text(
                            text = category,
                            color = Color(0xFF6D2E1F),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFC4A3),
                    scrolledContainerColor = Color(0xFFFFC4A3)
                )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            FondoOndasNaranja()

            if (filteredFoods.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay platos cargados para $category 😅",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF6D2E1F)
                    )
                }
            }
            Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                    ) {

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = {
                        Text(
                            "Buscar receta",
                            color = Color(0xFF8A5A44)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color(0xFF6D2E1F)
                        )
                    },
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { searchText = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Limpiar",
                                    tint = Color(0xFF6D2E1F)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFB35A3C),
                        unfocusedBorderColor = Color(0xFFCC8A6A),
                        focusedContainerColor = Color(0xFFFFF7F2),
                        unfocusedContainerColor = Color(0xFFFFF7F2),
                        cursorColor = Color(0xFF6D2E1F),
                        focusedTextColor = Color(0xFF3A2A20),
                        unfocusedTextColor = Color(0xFF3A2A20)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(filteredFoods) { food ->
                        FoodCard(food = food) {
                            val intent = Intent(context, RecipeDetailActivity::class.java).apply {
                                putExtra(RecipeDetailActivity.EXTRA_FOOD_NAME, food.name)
                                putExtra(RecipeDetailActivity.EXTRA_IMAGE_RES, food.imageRes)
                            }
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FoodCard(food: FoodItem, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = ""
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                scope.launch {
                    pressed = true
                    kotlinx.coroutines.delay(120)
                    pressed = false
                    onClick()
                }
            },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5ECE6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = food.imageRes),
                contentDescription = food.name,
                modifier = Modifier
                    .size(82.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF3A2A20)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = food.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B5A4E)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FoodListScreenPreview() {
    CocinaGlobalTheme {
        FoodListScreen(category = "Coreana")
    }
}