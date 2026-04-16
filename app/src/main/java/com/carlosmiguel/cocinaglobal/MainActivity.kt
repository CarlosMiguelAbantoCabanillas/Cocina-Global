package com.carlosmiguel.cocinaglobal


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.carlosmiguel.cocinaglobal.ui.theme.CategoryActivity
import com.carlosmiguel.cocinaglobal.ui.theme.CocinaGlobalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by remember { mutableStateOf(false) }

            CocinaGlobalTheme(darkTheme = darkMode) {
                FondoConContenido(
                    darkMode = darkMode,
                    onToggleDarkMode = {
                        darkMode = !darkMode
                    }
                )
            }
        }
    }
}

@Composable
fun FondoConContenido(
    darkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FondoOndas()
        ContenidoPrincipal()

        IconButton(
            onClick = onToggleDarkMode,
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (darkMode)
                    Icons.Default.Brightness4
                else
                    Icons.Default.Brightness7,
                contentDescription = "Cambiar tema"
            )
        }
    }
}

@Composable
fun FondoOndas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Colores y Degradados
        drawRect(
            brush = Brush.verticalGradient(
                listOf(
                    Color(0xFFFFD4BD), // Melocotón claro
                    Color(0xFFFFB894)  // Melocotón más oscuro
                )
            ),
            size = size
        )

        // Capa 1 de ondas
        drawPath(
            Path().apply {
                moveTo(0f, h * 0.15f)
                cubicTo(w * 0.25f, h * 0.05f, w * 0.75f, h * 0.25f, w, h * 0.15f)
                lineTo(w, 0f)
                lineTo(0f, 0f)
                close()
            },
            Color(0xFFFFC4A3)
        )

        // Capa 2 de ondas
        drawPath(
            Path().apply {
                moveTo(0f, h * 0.45f)
                cubicTo(w * 0.25f, h * 0.35f, w * 0.75f, h * 0.55f, w, h * 0.45f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            Color(0xFFFFD9C4)
        )
    }
}

@Composable
fun ContenidoPrincipal() {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        // Espera 3 segundos (3000ms)
        delay(3000L)

        val intent = Intent(context, CategoryActivity::class.java)
        context.startActivity(intent)


        (context as? ComponentActivity)?.finish()
    }
    // *************************************************************

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // --- IMÁGENES ALREDEDOR (VISUAL) ---
        ImageAround(R.drawable.cubiertos, 75.dp, 100.dp)
        ImageAround(R.drawable.sushi, 190.dp, 120.dp)
        ImageAround(R.drawable.bandera_espana, 240.dp, 200.dp)
        ImageAround(R.drawable.bandera_peru, 50.dp, 200.dp)
        ImageAround(R.drawable.bol_de_arroz, 40.dp, 530.dp)
        ImageAround(R.drawable.ramen, 100.dp, 630.dp)
        ImageAround(R.drawable.corazon, 280.dp, 580.dp)
        ImageAround(R.drawable.bandera_mexico, 225.dp, 650.dp)
        ImageAround(R.drawable.taco, 330.dp, 480.dp)

        // --- TEXTO CENTRAL (VISUAL) ---
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cocina",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8D2A20)
            )
            Text(
                text = "Global",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8D2A20)
            )
        }
    }
}

@Composable
fun ImageAround(id: Int, x: Dp, y: Dp) {
    Image(
        painter = painterResource(id),
        contentDescription = null,
        modifier = Modifier
            .size(90.dp)
            .absoluteOffset(x, y)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CocinaGlobalTheme {
        FondoConContenido(
            darkMode = false,
            onToggleDarkMode = {}
        )
    }
}

