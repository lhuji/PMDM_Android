package com.example.entrenamientocorrutinasretrofit

import com.google.gson.annotations.SerializedName

// Clase de datos que representa la respuesta de la API de perros
data class DogsResponse(
    // Anotación que especifica el nombre del campo en la respuesta JSON correspondiente a esta propiedad
    @SerializedName("status") var status: String,
    // Anotación que especifica el nombre del campo en la respuesta JSON correspondiente a esta propiedad
    @SerializedName("message") var images: List<String>
)