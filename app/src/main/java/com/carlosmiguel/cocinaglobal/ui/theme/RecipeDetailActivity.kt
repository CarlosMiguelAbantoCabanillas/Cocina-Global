package com.carlosmiguel.cocinaglobal.ui.theme


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.carlosmiguel.cocinaglobal.FavoritesManager
import kotlinx.coroutines.launch

class RecipeDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val foodName = intent.getStringExtra(EXTRA_FOOD_NAME) ?: "Receta"
        val imageRes = intent.getIntExtra(EXTRA_IMAGE_RES, 0)

        setContent {
            CocinaGlobalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFD4BD)
                ) {
                    val recipe = RecipeRepository.getRecipe(foodName)

                    RecipeDetailScreen(
                        title = foodName,
                        imageRes = imageRes,
                        recipe = recipe
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_FOOD_NAME = "extra_food_name"
        const val EXTRA_IMAGE_RES = "extra_image_res"
    }
}

data class Recipe(
    val time: String,
    val servings: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val tips: String? = null
)

object RecipeRepository {

    private val recipes = mapOf(
        "Ceviche" to Recipe(
            time = "20 min",
            servings = "2 porciones",
            ingredients = listOf(
                "300 g pescado blanco fresco (en cubos)",
                "8–10 limones (jugo)",
                "1/2 cebolla roja en pluma",
                "Ají limo o rocoto (opcional)",
                "Cilantro picado",
                "Sal y pimienta",
                "Choclo y camote (para acompañar)"
            ),
            steps = listOf(
                "Sazona el pescado con sal y pimienta.",
                "Agrega jugo de limón y mezcla 1–2 min.",
                "Incorpora cebolla, ají y cilantro.",
                "Prueba sal. Sirve de inmediato con camote y choclo."
            ),
            tips = "Usa pescado muy fresco; no dejes el limón demasiado tiempo para que no se pase."
        ),
        "Lomo Saltado" to Recipe(
            time = "25–30 min",
            servings = "2 porciones",
            ingredients = listOf(
                "300 g lomo o bistec en tiras",
                "1 cebolla roja en pluma",
                "2 tomates en gajos",
                "1 cda sillao (soya)",
                "1 cda vinagre",
                "Papas fritas",
                "Sal y pimienta",
                "Cilantro o perejil (opcional)"
            ),
            steps = listOf(
                "Dora la carne a fuego fuerte con sal y pimienta. Reserva.",
                "Saltea cebolla 1–2 min, agrega tomate 1 min.",
                "Añade sillao y vinagre, regresa la carne y mezcla.",
                "Sirve con papas fritas y arroz."
            ),
            tips = "Fuego fuerte para que la carne se selle y no suelte agua."
        ),
        "Causa Rellena" to Recipe(
            time = "35–45 min",
            servings = "4 porciones",
            ingredients = listOf(
                "1 kg papa amarilla (hervida y prensada)",
                "2–3 cdas pasta de ají amarillo",
                "2 cdas aceite",
                "Jugo de 2 limones",
                "Sal",
                "Relleno: pollo deshilachado o atún",
                "Mayonesa",
                "Palta (opcional)"
            ),
            steps = listOf(
                "Mezcla la papa con ají amarillo, aceite, limón y sal hasta formar una masa.",
                "Mezcla el relleno con mayonesa (y cebolla si quieres).",
                "Arma: capa de papa, relleno y otra capa de papa.",
                "Refrigera 15 min y sirve."
            ),
            tips = "Si queda seca, agrega un chorrito de aceite o limón."
        ),
        "Ají de Gallina" to Recipe(
            time = "35–45 min",
            servings = "3 porciones",
            ingredients = listOf(
                "2 pechugas cocidas y deshilachadas",
                "3–4 panes remojados en leche (o galletas)",
                "2–3 cdas pasta de ají amarillo",
                "1 cebolla picada",
                "2 dientes de ajo",
                "Queso (parmesano o fresco)",
                "Nueces (opcional)",
                "Sal y pimienta",
                "Arroz y papa sancochada (para servir)"
            ),
            steps = listOf(
                "Sofríe cebolla y ajo, agrega ají amarillo.",
                "Licúa pan con leche y añade a la olla.",
                "Agrega queso, sazona y cocina hasta espesar.",
                "Incorpora el pollo y calienta 3–5 min. Sirve con arroz y papa."
            ),
            tips = "Cocina a fuego bajo para que la salsa quede suave."
        ),
        "Anticuchos" to Recipe(
            time = "25 min + marinado 1 h",
            servings = "4 brochetas",
            ingredients = listOf(
                "500 g corazón de res (o carne en cubos)",
                "2 cdas pasta de ají panca",
                "2 cdas vinagre",
                "2 cdas sillao (soya)",
                "Ajo, comino, sal y pimienta",
                "Palitos para brocheta"
            ),
            steps = listOf(
                "Mezcla ají panca, vinagre, sillao, ajo y especias.",
                "Marina la carne 1 hora.",
                "Ensarta y cocina a la parrilla o sartén, pintando con la marinada.",
                "Sirve con papa o choclo."
            ),
            tips = "Remoja los palitos 10 min para que no se quemen."
        ),
        "Tacos al Pastor" to Recipe(
            time = "30 min + marinado 1 h",
            servings = "6 tacos",
            ingredients = listOf(
                "400 g cerdo en tiras",
                "2 cdas achiote",
                "Jugo de 1 naranja",
                "Jugo de 1 limón",
                "1 cdita comino + orégano",
                "Sal",
                "Tortillas",
                "Piña, cebolla y cilantro"
            ),
            steps = listOf(
                "Mezcla achiote, cítricos, sal y especias. Marina el cerdo.",
                "Cocina en sartén hasta dorar.",
                "Calienta tortillas.",
                "Arma con piña, cebolla y cilantro."
            ),
            tips = "Si quieres más sabor, agrega un chorrito de vinagre al marinado."
        ),
        "Enchiladas" to Recipe(
            time = "35 min",
            servings = "4 porciones",
            ingredients = listOf(
                "8 tortillas",
                "2 tazas pollo deshilachado",
                "Salsa roja o verde (2 tazas)",
                "Queso rallado",
                "Crema (opcional)",
                "Cebolla (opcional)"
            ),
            steps = listOf(
                "Calienta la salsa.",
                "Rellena tortillas con pollo y enrolla.",
                "Baña con salsa y agrega queso.",
                "Hornea 10–12 min o hasta derretir. Sirve con crema."
            ),
            tips = "Pasa la tortilla 5–10 seg por aceite para que no se rompa."
        ),
        "Pozole" to Recipe(
            time = "1 h 10 min",
            servings = "4 porciones",
            ingredients = listOf(
                "Maíz pozolero (cocido o en lata)",
                "400 g pollo o cerdo",
                "1 cebolla + ajo",
                "Caldo/agua",
                "Sal",
                "Orégano",
                "Toppings: lechuga, rábano, limón"
            ),
            steps = listOf(
                "Cocina carne con cebolla/ajo para hacer caldo.",
                "Agrega maíz y cocina hasta suave (si es lata, 15–20 min).",
                "Sazona con sal y orégano.",
                "Sirve con toppings al gusto."
            ),
            tips = "El limón y el orégano al servir son clave."
        ),
        "Chilaquiles" to Recipe(
            time = "20 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Totopos (tortilla frita) o nachos sin sabor",
                "Salsa roja o verde",
                "Queso",
                "Crema",
                "Cebolla",
                "Huevo o pollo (opcional)"
            ),
            steps = listOf(
                "Calienta la salsa en sartén.",
                "Agrega totopos y mezcla 1–2 min (según lo crujiente que lo quieras).",
                "Sirve con queso, crema y cebolla.",
                "Añade huevo o pollo si quieres."
            ),
            tips = "Para crujientes: mezcla poco tiempo. Para suaves: cocina 3–4 min."
        ),
        "Guacamole" to Recipe(
            time = "10 min",
            servings = "2–3 porciones",
            ingredients = listOf(
                "2 aguacates",
                "1/4 cebolla picada",
                "1 tomate picado",
                "Cilantro",
                "Jugo de 1 limón",
                "Sal",
                "Jalapeño/ají (opcional)"
            ),
            steps = listOf(
                "Machaca el aguacate.",
                "Agrega cebolla, tomate y cilantro.",
                "Añade limón y sal.",
                "Mezcla y sirve."
            ),
            tips = "Deja el hueso dentro si lo guardarás para que se oxide menos."
        ),
        "Pizza" to Recipe(
            time = "1 h 20 min",
            servings = "1 pizza grande",
            ingredients = listOf(
                "Masa (comprada o casera)",
                "Salsa de tomate",
                "Queso mozzarella",
                "Orégano",
                "Toppings: jamón, pepperoni, champiñón, etc."
            ),
            steps = listOf(
                "Precalienta horno fuerte (220–250°C).",
                "Estira la masa y coloca salsa.",
                "Agrega mozzarella y toppings.",
                "Hornea 10–15 min hasta dorar."
            ),
            tips = "Horno bien caliente = base más crujiente."
        ),
        "Pasta Carbonara" to Recipe(
            time = "20 min",
            servings = "2 porciones",
            ingredients = listOf(
                "200 g pasta",
                "2 huevos",
                "Queso parmesano",
                "100 g panceta/bacon",
                "Pimienta negra",
                "Sal"
            ),
            steps = listOf(
                "Cocina la pasta al dente.",
                "Dora panceta y apaga el fuego.",
                "Mezcla huevo + parmesano + pimienta.",
                "Une pasta caliente con panceta y luego agrega mezcla de huevo (fuera del fuego), mezclando rápido."
            ),
            tips = "No lo cocines con fuego o se vuelve huevo revuelto."
        ),
        "Lasagna" to Recipe(
            time = "1 h 10 min",
            servings = "6 porciones",
            ingredients = listOf(
                "Láminas de lasagna",
                "Carne molida",
                "Salsa de tomate",
                "Cebolla y ajo",
                "Queso mozzarella",
                "Queso parmesano",
                "Sal y pimienta"
            ),
            steps = listOf(
                "Haz salsa: sofríe cebolla/ajo, agrega carne, luego tomate.",
                "Cocina pasta (si no es precocida).",
                "Arma capas: salsa, pasta, queso.",
                "Hornea 25–35 min hasta gratinar."
            ),
            tips = "Reposar 10 min antes de cortar para que no se desarme."
        ),
        "Risotto" to Recipe(
            time = "35–40 min",
            servings = "2 porciones",
            ingredients = listOf(
                "1 taza arroz arborio",
                "4 tazas caldo caliente",
                "1/2 cebolla picada",
                "Mantequilla",
                "Queso parmesano",
                "Sal y pimienta"
            ),
            steps = listOf(
                "Sofríe cebolla con mantequilla.",
                "Agrega arroz y tuesta 1 min.",
                "Añade caldo poco a poco, moviendo, hasta cremoso (18–22 min).",
                "Apaga y agrega parmesano + un toque de mantequilla."
            ),
            tips = "El caldo debe estar caliente para mantener la cocción constante."
        ),
        "Tiramisu" to Recipe(
            time = "25 min + frío 4 h",
            servings = "6 porciones",
            ingredients = listOf(
                "Queso mascarpone (o mezcla crema + queso crema)",
                "Bizcochos (soletillas)",
                "Café fuerte frío",
                "Cacao en polvo",
                "Azúcar",
                "Vainilla (opcional)"
            ),
            steps = listOf(
                "Mezcla mascarpone con azúcar y vainilla.",
                "Remoja soletillas rápido en café.",
                "Arma capas: crema y galletas.",
                "Refrigera 4 h y espolvorea cacao."
            ),
            tips = "No remojes demasiado las galletas o se deshacen."
        ),
        "Sushi" to Recipe(
            time = "45–60 min",
            servings = "3 rollos",
            ingredients = listOf(
                "Arroz para sushi",
                "Vinagre de arroz + azúcar + sal",
                "Nori (alga)",
                "Rellenos: pepino, palta, surimi o pescado cocido",
                "Soya para servir"
            ),
            steps = listOf(
                "Cocina arroz y enfría mezclando con vinagre + azúcar + sal.",
                "En nori, coloca arroz y relleno.",
                "Enrolla y corta en piezas.",
                "Sirve con soya."
            ),
            tips = "Para empezar, usa rellenos cocidos (más fácil y seguro)."
        ),
        "Ramen" to Recipe(
            time = "35–45 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Fideos ramen",
                "Caldo (pollo o miso)",
                "Soya",
                "Ajo y jengibre",
                "Huevo",
                "Cebollita",
                "Toppings: pollo, champiñón, etc."
            ),
            steps = listOf(
                "Hierve caldo con ajo/jengibre y un toque de soya.",
                "Cocina fideos aparte.",
                "Cocina huevo (6–7 min para yema cremosa).",
                "Sirve fideos, agrega caldo y toppings."
            ),
            tips = "Cocina los fideos aparte para que el caldo quede más limpio."
        ),
        "Tempura" to Recipe(
            time = "25–30 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Camarón o verduras",
                "1 taza harina",
                "Agua muy fría",
                "1 huevo (opcional)",
                "Aceite para freír",
                "Sal"
            ),
            steps = listOf(
                "Mezcla harina con agua fría (y huevo si quieres) sin batir mucho.",
                "Pasa ingredientes por la mezcla.",
                "Fríe 2–3 min hasta dorar.",
                "Escurre y sirve."
            ),
            tips = "Mezcla poco: grumos = tempura más crujiente."
        ),
        "Onigiri" to Recipe(
            time = "20–25 min",
            servings = "4 onigiri",
            ingredients = listOf(
                "Arroz cocido",
                "Sal",
                "Relleno: atún con mayo o salmón cocido",
                "Nori en tiras"
            ),
            steps = listOf(
                "Con manos húmedas, toma arroz y pon relleno al centro.",
                "Forma triángulo o bola.",
                "Pon una tira de nori.",
                "Sirve."
            ),
            tips = "Moja manos con agua + sal para que no se pegue."
        ),
        "Takoyaki" to Recipe(
            time = "35–45 min",
            servings = "20 bolitas aprox",
            ingredients = listOf(
                "1 taza harina + 2 tazas agua",
                "1 huevo",
                "Pulpo cocido en cubitos (o salchicha si no tienes)",
                "Cebollita",
                "Salsa (tipo okonomiyaki o mayo+soya)",
                "Aceite"
            ),
            steps = listOf(
                "Mezcla harina, agua y huevo.",
                "Calienta molde (o sartén para bocaditos).",
                "Agrega mezcla, pulpo y cebollita.",
                "Cocina girando hasta dorar."
            ),
            tips = "Sin molde, haz bocaditos en sartén y quedan parecidos."
        ),
        "Croissant" to Recipe(
            time = "2 h + reposos",
            servings = "8 unidades",
            ingredients = listOf(
                "Harina",
                "Levadura",
                "Leche",
                "Azúcar",
                "Sal",
                "Mantequilla (laminado)"
            ),
            steps = listOf(
                "Haz masa y deja reposar.",
                "Encierra mantequilla y lamina (doblar/estirar) con frío varias veces.",
                "Corta triángulos y enrolla.",
                "Leuda y hornea 15–18 min."
            ),
            tips = "Mantén la mantequilla fría para lograr capas."
        ),
        "Ratatouille" to Recipe(
            time = "50–60 min",
            servings = "4 porciones",
            ingredients = listOf(
                "Berenjena",
                "Zucchini",
                "Tomate",
                "Pimiento",
                "Cebolla",
                "Ajo",
                "Aceite de oliva, sal, hierbas"
            ),
            steps = listOf(
                "Corta verduras.",
                "Sofríe cebolla y ajo, agrega pimiento.",
                "Añade berenjena y zucchini, luego tomate.",
                "Cocina lento 25–30 min."
            ),
            tips = "Al día siguiente sabe aún mejor."
        ),
        "Crêpes" to Recipe(
            time = "20–25 min",
            servings = "8 crepes",
            ingredients = listOf(
                "1 taza harina",
                "2 huevos",
                "1 taza leche",
                "1 cda mantequilla derretida",
                "Pizca de sal",
                "Azúcar (si son dulces)"
            ),
            steps = listOf(
                "Mezcla todo y deja reposar 5–10 min.",
                "Sartén caliente con poquito aceite/mantequilla.",
                "Vierte mezcla fina, cocina 40–60 seg por lado.",
                "Rellena dulce o salado."
            ),
            tips = "Si salen gruesas, agrega un poquito más de leche."
        ),
        "Quiche" to Recipe(
            time = "55–65 min",
            servings = "6 porciones",
            ingredients = listOf(
                "Masa de tarta",
                "3 huevos",
                "1 taza leche o crema",
                "Queso",
                "Relleno: jamón/espinaca/champiñón",
                "Sal y pimienta"
            ),
            steps = listOf(
                "Prehornea la masa 8 min.",
                "Mezcla huevos, leche, queso y condimentos.",
                "Agrega relleno y vierte mezcla.",
                "Hornea 35–40 min hasta cuajar."
            ),
            tips = "No cortes caliente: espera 10 min."
        ),
        "Macarons" to Recipe(
            time = "1 h 30 min",
            servings = "18 unidades",
            ingredients = listOf(
                "Claras de huevo",
                "Azúcar",
                "Almendra molida",
                "Azúcar impalpable",
                "Colorante (opcional)",
                "Relleno: ganache o crema"
            ),
            steps = listOf(
                "Haz merengue con claras + azúcar.",
                "Incorpora secos con movimientos suaves.",
                "Manga en bandeja, reposa 20–30 min.",
                "Hornea 12–15 min. Rellena al enfriar."
            ),
            tips = "Si empiezas, hazlos sin colorante para practicar."
        ),
        "Feijoada" to Recipe(
            time = "1 h 30 min",
            servings = "4 porciones",
            ingredients = listOf(
                "Frijoles negros (cocidos o en lata)",
                "Chorizo/bacon/carne (opcional)",
                "Cebolla y ajo",
                "Laurel",
                "Sal y pimienta"
            ),
            steps = listOf(
                "Sofríe cebolla/ajo y dora carnes si usas.",
                "Agrega frijoles, laurel y un poco de agua si hace falta.",
                "Cocina 30–40 min hasta espesar.",
                "Sirve con arroz."
            ),
            tips = "Machaca un poco de frijol para más cremosidad."
        ),
        "Pão de Queijo" to Recipe(
            time = "30–35 min",
            servings = "15 bolitas",
            ingredients = listOf(
                "1 taza fécula de yuca/tapioca",
                "1/2 taza leche",
                "1/4 taza aceite",
                "1 huevo",
                "Queso rallado",
                "Sal"
            ),
            steps = listOf(
                "Hierve leche+aceite y vierte sobre la fécula.",
                "Mezcla y deja entibiar.",
                "Agrega huevo, queso y sal. Forma bolitas.",
                "Hornea 18–22 min hasta dorar."
            ),
            tips = "Si queda seca la masa, agrega un chorrito de leche."
        ),
        "Moqueca" to Recipe(
            time = "40–50 min",
            servings = "3 porciones",
            ingredients = listOf(
                "400 g pescado en trozos",
                "1 cebolla en pluma",
                "1 pimiento en tiras",
                "2 tomates en rodajas",
                "1 taza leche de coco",
                "Ajo, sal, limón",
                "Cilantro"
            ),
            steps = listOf(
                "Sazona pescado con limón, sal y ajo.",
                "En olla: cebolla, pimiento, tomate; encima el pescado.",
                "Agrega leche de coco y cocina tapado 15–20 min.",
                "Termina con cilantro."
            ),
            tips = "No muevas demasiado para no romper el pescado."
        ),
        "Brigadeiro" to Recipe(
            time = "20 min",
            servings = "12 unidades",
            ingredients = listOf(
                "1 lata leche condensada",
                "2 cdas cacao/chocolate en polvo",
                "1 cda mantequilla",
                "Granas de chocolate"
            ),
            steps = listOf(
                "En olla: condensada + cacao + mantequilla.",
                "Cocina moviendo hasta que se despegue del fondo.",
                "Enfría, forma bolitas.",
                "Pasa por granas."
            ),
            tips = "Fuego bajo para que no se queme."
        ),
        "Coxinha" to Recipe(
            time = "1 h",
            servings = "10 unidades",
            ingredients = listOf(
                "Pollo deshilachado",
                "Caldo de pollo",
                "Harina",
                "Mantequilla",
                "Sal",
                "Pan rallado",
                "Aceite para freír"
            ),
            steps = listOf(
                "Haz masa: caldo + mantequilla, agrega harina y mezcla hasta bola.",
                "Rellena con pollo sazonado.",
                "Forma gotita, pasa por pan rallado.",
                "Fríe hasta dorar."
            ),
            tips = "Si es tu primera vez, hazlas un poco más grandes (más fácil)."
        ),
        "Asado" to Recipe(
            time = "1 h (según cortes)",
            servings = "3–4 porciones",
            ingredients = listOf(
                "Cortes de carne (asado, vacío, chorizo, etc.)",
                "Sal gruesa",
                "Carbón o parrilla",
                "Chimichurri (opcional)"
            ),
            steps = listOf(
                "Prepara brasas (sin llama fuerte).",
                "Sala la carne y cocina a fuego medio-bajo.",
                "Da vuelta cuando selle y suelte fácil.",
                "Sirve con chimichurri."
            ),
            tips = "No pinches la carne para que no pierda jugos."
        ),
        "Empanadas" to Recipe(
            time = "45–60 min",
            servings = "10 empanadas",
            ingredients = listOf(
                "Tapas de empanada",
                "Carne molida",
                "Cebolla",
                "Huevo duro (opcional)",
                "Aceitunas (opcional)",
                "Sal, pimienta, pimentón"
            ),
            steps = listOf(
                "Sofríe cebolla, agrega carne y condimentos.",
                "Deja enfriar el relleno.",
                "Rellena tapas y cierra.",
                "Hornea 15–20 min o fríe hasta dorar."
            ),
            tips = "Relleno frío = empanada mejor cerrada."
        ),
        "Milanesa" to Recipe(
            time = "25–30 min",
            servings = "2 porciones",
            ingredients = listOf(
                "2 filetes finos",
                "Huevo",
                "Pan rallado",
                "Ajo/perejil (opcional)",
                "Sal y pimienta",
                "Aceite"
            ),
            steps = listOf(
                "Sazona la carne.",
                "Pasa por huevo batido y luego por pan rallado.",
                "Fríe u hornea hasta dorar.",
                "Sirve con limón o ensalada."
            ),
            tips = "Presiona el pan rallado para que no se desprenda."
        ),
        "Choripán" to Recipe(
            time = "15–20 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Chorizos",
                "Pan tipo baguette",
                "Chimichurri",
                "Salsa criolla (opcional)"
            ),
            steps = listOf(
                "Cocina chorizos a la parrilla o sartén.",
                "Abre el pan y tuéstalo.",
                "Coloca chorizo y agrega chimichurri.",
                "Sirve caliente."
            ),
            tips = "Haz cortes superficiales al chorizo para que se cocine parejo."
        ),
        "Dulce de Leche" to Recipe(
            time = "1 h 30 min",
            servings = "1 frasco",
            ingredients = listOf(
                "1 litro leche",
                "300 g azúcar",
                "1 cdita vainilla",
                "Pizca de bicarbonato (opcional)"
            ),
            steps = listOf(
                "Cocina leche + azúcar a fuego bajo, moviendo.",
                "Cuando espese y tome color caramelo, agrega vainilla.",
                "Enfría y guarda en frasco."
            ),
            tips = "Paciencia y fuego bajo: si subes fuego se puede pegar."
        ),
        "Paella" to Recipe(
            time = "45–60 min",
            servings = "4 porciones",
            ingredients = listOf(
                "2 tazas arroz",
                "Caldo (4–5 tazas)",
                "Azafrán o colorante",
                "Pollo o mariscos (lo que tengas)",
                "Ajo, sal, aceite de oliva",
                "Pimiento/arvejas (opcional)"
            ),
            steps = listOf(
                "Sofríe ajo y proteína, agrega verduras.",
                "Añade arroz, azafrán y mezcla 1 min.",
                "Agrega caldo y cocina sin mover demasiado.",
                "Reposo 5 min y sirve."
            ),
            tips = "No la revuelvas como arroz normal."
        ),
        "Tortilla Española" to Recipe(
            time = "35–45 min",
            servings = "4 porciones",
            ingredients = listOf(
                "4 papas",
                "1 cebolla (opcional)",
                "5 huevos",
                "Sal",
                "Aceite"
            ),
            steps = listOf(
                "Fríe papas en rodajas (y cebolla) hasta suaves.",
                "Escurre aceite.",
                "Mezcla con huevos batidos y sal.",
                "Cocina en sartén y voltea para dorar ambos lados."
            ),
            tips = "Fuego medio-bajo para que no se queme por fuera."
        ),
        "Gazpacho" to Recipe(
            time = "15 min + frío",
            servings = "3 porciones",
            ingredients = listOf(
                "4 tomates",
                "1/2 pepino",
                "1/2 pimiento",
                "1 diente de ajo",
                "Aceite de oliva",
                "Vinagre",
                "Sal"
            ),
            steps = listOf(
                "Licúa tomates, pepino, pimiento y ajo.",
                "Agrega aceite, vinagre y sal.",
                "Cuela (opcional) y enfría.",
                "Sirve bien frío."
            ),
            tips = "Bien frío sabe mucho mejor."
        ),
        "Croquetas" to Recipe(
            time = "45–60 min",
            servings = "12 unidades",
            ingredients = listOf(
                "2 cdas mantequilla",
                "2 cdas harina",
                "2 tazas leche",
                "Jamón o pollo picado",
                "Sal, pimienta, nuez moscada",
                "Huevo + pan rallado",
                "Aceite"
            ),
            steps = listOf(
                "Haz bechamel: mantequilla+harina, luego leche hasta espesar.",
                "Agrega jamón/pollo y condimentos. Enfría.",
                "Forma croquetas, pasa por huevo y pan rallado.",
                "Fríe hasta dorar."
            ),
            tips = "Enfriar bien la masa es clave para que no se rompan."
        ),
        "Churros" to Recipe(
            time = "25–30 min",
            servings = "15 churros",
            ingredients = listOf(
                "1 taza agua",
                "2 cdas mantequilla",
                "1 taza harina",
                "Pizca de sal",
                "Azúcar",
                "Aceite para freír"
            ),
            steps = listOf(
                "Hierve agua con mantequilla y sal.",
                "Agrega harina de golpe y mezcla hasta masa.",
                "Forma churros y fríe.",
                "Espolvorea azúcar."
            ),
            tips = "Si no tienes manga, haz palitos con cuchara (igual quedan ricos)."
        ),
        "Kebab" to Recipe(
            time = "25–35 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Carne (pollo o res) en tiras",
                "Yogur natural",
                "Ajo",
                "Pimentón, comino, sal",
                "Pan pita o tortilla",
                "Verduras: tomate, lechuga, cebolla"
            ),
            steps = listOf(
                "Marina carne con yogur, ajo y especias 20 min.",
                "Cocina en sartén hasta dorar.",
                "Arma en pan con verduras.",
                "Agrega salsa de yogur si quieres."
            ),
            tips = "El yogur ayuda a que la carne quede más tierna."
        ),
        "Baklava" to Recipe(
            time = "1 h",
            servings = "12 porciones",
            ingredients = listOf(
                "Masa filo",
                "Nueces o pistachos picados",
                "Mantequilla derretida",
                "Almíbar: azúcar + agua + miel + limón"
            ),
            steps = listOf(
                "Arma capas de filo con mantequilla entre capas.",
                "Pon nueces al centro y más capas arriba.",
                "Corta y hornea 30–35 min.",
                "Vierte almíbar frío sobre baklava caliente."
            ),
            tips = "Almíbar frío + baklava caliente = textura perfecta."
        ),
        "Lahmacun" to Recipe(
            time = "35–45 min",
            servings = "4 unidades",
            ingredients = listOf(
                "Masa fina (o tortillas grandes como atajo)",
                "Carne molida",
                "Tomate picado",
                "Cebolla",
                "Pimentón/paprika",
                "Sal y pimienta",
                "Limón y perejil"
            ),
            steps = listOf(
                "Mezcla carne con tomate, cebolla y condimentos.",
                "Unta capa fina sobre la masa.",
                "Hornea 10–12 min fuerte.",
                "Sirve con limón y perejil, enrollado."
            ),
            tips = "Si usas tortilla, hornea menos tiempo (se seca rápido)."
        ),
        "Menemen" to Recipe(
            time = "15–20 min",
            servings = "2 porciones",
            ingredients = listOf(
                "3 huevos",
                "2 tomates",
                "1/2 pimiento",
                "Cebolla (opcional)",
                "Sal, pimienta",
                "Aceite"
            ),
            steps = listOf(
                "Sofríe pimiento (y cebolla si usas).",
                "Agrega tomate picado y cocina hasta salsa.",
                "Añade huevos y mezcla suave.",
                "Sazona y sirve con pan."
            ),
            tips = "No cocines demasiado el huevo para que quede jugoso."
        ),
        "Börek" to Recipe(
            time = "45–55 min",
            servings = "6 porciones",
            ingredients = listOf(
                "Masa filo",
                "Queso (feta o fresco) o carne",
                "Perejil (si es queso)",
                "Mantequilla/aceite",
                "Huevo (opcional para pintar)"
            ),
            steps = listOf(
                "Rellena la filo con queso+perejil o carne.",
                "Enrolla o arma capas en bandeja.",
                "Pinta con mantequilla y huevo.",
                "Hornea 25–30 min hasta dorar."
            ),
            tips = "Tapa la filo con paño húmedo para que no se seque."
        ),
        "Kimchi" to Recipe(
            time = "2 h + fermentación 2–5 días",
            servings = "1 frasco grande",
            ingredients = listOf(
                "1 col china",
                "Sal",
                "3 cdas gochugaru (o ají en hojuelas)",
                "Ajo y jengibre",
                "Salsa de pescado o soya",
                "Azúcar",
                "Cebollita y zanahoria"
            ),
            steps = listOf(
                "Sala la col en agua 1–2 h, enjuaga y escurre.",
                "Mezcla pasta picante con ajo/jengibre, soya y azúcar.",
                "Unta la col y agrega verduras.",
                "Fermenta 2–5 días y luego refrigera."
            ),
            tips = "Prueba desde el día 2: depende del calor."
        ),
        "Bibimbap" to Recipe(
            time = "40–50 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Arroz cocido",
                "Carne en tiras (opcional)",
                "Zanahoria, espinaca, zucchini",
                "Huevo",
                "Salsa: gochujang/soya/azúcar/sésamo"
            ),
            steps = listOf(
                "Saltea verduras por separado con sal.",
                "Cocina carne (si usas).",
                "Sirve arroz, acomoda toppings y huevo arriba.",
                "Agrega salsa y mezcla al comer."
            ),
            tips = "Coloca todo en secciones para que se vea más pro."
        ),
        "Tteokbokki" to Recipe(
            time = "25–30 min",
            servings = "2–3 porciones",
            ingredients = listOf(
                "Tteok (pastelitos de arroz) o gnocchi como alternativa",
                "Agua/caldo",
                "Gochujang",
                "Azúcar",
                "Soya",
                "Ajo",
                "Cebollita"
            ),
            steps = listOf(
                "Hierve agua/caldo y disuelve gochujang, azúcar y soya.",
                "Agrega tteok y cocina hasta espesar.",
                "Mueve para que no se pegue.",
                "Termina con cebollita."
            ),
            tips = "Si no tienes tteok, con gnocchi queda sorprendentemente bien."
        ),
        "Bulgogi" to Recipe(
            time = "20 min + marinado 30 min",
            servings = "2 porciones",
            ingredients = listOf(
                "Carne de res en láminas",
                "Soya",
                "Azúcar/miel",
                "Ajo",
                "Aceite de sésamo",
                "Cebolla",
                "Pimienta"
            ),
            steps = listOf(
                "Mezcla soya, azúcar, ajo, sésamo y pimienta.",
                "Marina la carne 30 min.",
                "Saltea a fuego alto 5–8 min.",
                "Sirve con arroz."
            ),
            tips = "Corte fino = más jugoso."
        ),
        "Japchae" to Recipe(
            time = "35–45 min",
            servings = "2–3 porciones",
            ingredients = listOf(
                "Fideos de camote (o fideos de arroz)",
                "Zanahoria, cebolla, pimiento",
                "Espinaca",
                "Soya",
                "Azúcar",
                "Aceite de sésamo"
            ),
            steps = listOf(
                "Cocina fideos y escurre.",
                "Saltea verduras y espinaca por separado.",
                "Mezcla soya+azúcar+sésamo y combina todo.",
                "Cocina 2–3 min y sirve."
            ),
            tips = "Corta fideos con tijera para que sea más fácil comer."
        )
    )

    fun getRecipe(foodName: String): Recipe {
        fun getAllRecipeNames(): List<String> {
            return recipes.keys.toList()
        }
        return recipes[foodName] ?: Recipe(
            time = "—",
            servings = "—",
            ingredients = listOf("Receta no encontrada para: $foodName"),
            steps = listOf("Revisa que el nombre sea EXACTO (tildes y mayúsculas)."),
            tips = "Si el plato tiene caracteres raros (Crêpes / Pão), debe coincidir igualito."
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    title: String,
    imageRes: Int,
    recipe: Recipe
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(FavoritesManager.isFavorite(context, title)) }
    var rating by remember { mutableStateOf(RatingManager.getRating(context, title)) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = Color(0xFF6D2E1F)
                    )
                },

                // 🔙 BOTÓN DE REGRESO
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? ComponentActivity)?.finish()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },

                // ❤️ BOTÓN DE FAVORITO
                actions = {
                    IconButton(
                        onClick = {
                            val wasFavorite = isFavorite

                            FavoritesManager.toggleFavorite(context, title)

                            isFavorite = FavoritesManager.isFavorite(context, title)

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    if (!wasFavorite) "Añadido a favoritos ❤️"
                                    else "Eliminado de favoritos 💔"
                                )
                            }
                        }
                    ) {
                        val scale by animateFloatAsState(
                            targetValue = if (isFavorite) 1.3f else 1f,
                            label = ""
                        )

                        Icon(
                            imageVector = if (isFavorite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) Color(0xFFE53935) else Color.Gray,
                            modifier = Modifier.scale(scale)
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                if (imageRes != 0) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .clip(RoundedCornerShape(18.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sin imagen", color = Color(0xFF6D2E1F))
                    }
                }
            }

            item {
                Text(
                    text = "⏱ ${recipe.time}   |   🍽 ${recipe.servings}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6D2E1F)
                )
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5ECE6)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Tu valoración",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3A2A20)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            for (i in 1..5) {
                                Icon(
                                    imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Estrella $i",
                                    tint = Color(0xFFFFB300),
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .clickable {
                                            if (rating == i) {
                                                rating = 0
                                                RatingManager.setRating(context, title, 0)

                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Valoración eliminada")
                                                }

                                            } else {
                                                rating = i
                                                RatingManager.setRating(context, title, i)

                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Valoraste con $i ⭐")
                                                }
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }

            item {
                SectionCard(title = "Ingredientes") {
                    recipe.ingredients.forEach { ing ->
                        Text(
                            text = "• $ing",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                SectionCard(title = "Preparación") {
                    recipe.steps.forEachIndexed { index, step ->
                        Text(
                            text = "${index + 1}. $step",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }

            if (!recipe.tips.isNullOrBlank()) {
                item {
                    SectionCard(title = "Tip") {
                        Text(
                            text = recipe.tips,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5ECE6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A2A20)
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}