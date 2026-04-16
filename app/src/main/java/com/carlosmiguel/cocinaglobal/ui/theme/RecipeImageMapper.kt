package com.carlosmiguel.cocinaglobal.ui.theme

import com.carlosmiguel.cocinaglobal.R

fun getImageResByRecipeName(recipeName: String): Int {
    return when (recipeName) {
        "Ceviche" -> R.drawable.ceviche
        "Lomo Saltado" -> R.drawable.lomo_saltado
        "Causa Rellena" -> R.drawable.causa_rellena
        "Ají de Gallina" -> R.drawable.aji_de_gallina
        "Anticuchos" -> R.drawable.anticuchos

        "Tacos al Pastor" -> R.drawable.tacos_al_pastor
        "Enchiladas" -> R.drawable.enchiladas
        "Pozole" -> R.drawable.pozole
        "Chilaquiles" -> R.drawable.chilaquiles
        "Guacamole" -> R.drawable.guacamole

        "Pizza" -> R.drawable.pizza
        "Pasta Carbonara" -> R.drawable.pasta_carbonara
        "Lasagna" -> R.drawable.lasagna
        "Risotto" -> R.drawable.risotto
        "Tiramisu" -> R.drawable.tiramisu

        "Sushi" -> R.drawable.sushi_2
        "Ramen" -> R.drawable.ramen_2
        "Tempura" -> R.drawable.tempura
        "Onigiri" -> R.drawable.onigiri
        "Takoyaki" -> R.drawable.takoyaki

        "Croissant" -> R.drawable.croissant
        "Ratatouille" -> R.drawable.ratatouille
        "Crêpes" -> R.drawable.crepes
        "Quiche" -> R.drawable.quiche
        "Macarons" -> R.drawable.macarons

        "Feijoada" -> R.drawable.feijoada
        "Pão de Queijo" -> R.drawable.pao_de_queijo
        "Moqueca" -> R.drawable.moqueca
        "Brigadeiro" -> R.drawable.brigadeiro
        "Coxinha" -> R.drawable.coxinha

        "Asado" -> R.drawable.asado
        "Empanadas" -> R.drawable.empanadas
        "Milanesa" -> R.drawable.milanesa
        "Choripán" -> R.drawable.choripan
        "Dulce de Leche" -> R.drawable.dulce_de_leche

        "Paella" -> R.drawable.paella
        "Tortilla Española" -> R.drawable.tortilla_espanola
        "Gazpacho" -> R.drawable.gazpacho
        "Croquetas" -> R.drawable.croquetas
        "Churros" -> R.drawable.churros

        "Kebab" -> R.drawable.kebab
        "Baklava" -> R.drawable.baklava
        "Lahmacun" -> R.drawable.lahmacun
        "Menemen" -> R.drawable.menemen
        "Börek" -> R.drawable.borek

        "Kimchi" -> R.drawable.kimchi
        "Bibimbap" -> R.drawable.bibimbap
        "Tteokbokki" -> R.drawable.tteokbokki
        "Bulgogi" -> R.drawable.bulgogi
        "Japchae" -> R.drawable.japchae

        else -> 0
    }
}