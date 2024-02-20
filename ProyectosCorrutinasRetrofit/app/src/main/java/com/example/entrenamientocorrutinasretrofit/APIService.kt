package com.example.entrenamientocorrutinasretrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

// Interfaz que define los métodos de la API
interface APIService {
    // Método GET que obtiene la respuesta de la API utilizando una URL dinámica
    // La URL completa se especificará en el parámetro de la función
    @GET
    fun getDogsByBreeds(@Url url: String): Call<DogsResponse>
}