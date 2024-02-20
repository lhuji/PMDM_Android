package com.example.entrenamientocorrutinasretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.entrenamientocorrutinasretrofit.databinding.ActivityMainBinding

// Clase principal que representa la actividad principal de la aplicación
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    // Lista de URL de imágenes de cachorros
    private lateinit var imagesPuppies: List<String>
    // Adaptador para el RecyclerView
    private lateinit var dogsAdapter: DogsAdapter

    // Referencia al objeto de enlace para la actividad principal
    private lateinit var binding: ActivityMainBinding

    // Método llamado cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el diseño de la actividad utilizando el enlace generado
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Establecer el listener de texto de búsqueda en la vista de búsqueda
        binding.searchBreed.setOnQueryTextListener(this)
    }

    // Método para inicializar el RecyclerView con los datos de respuesta de la API
    private fun initCharacter(puppies: DogsResponse) {
        // Verificar si la respuesta de la API es exitosa
        if (puppies.status == "success") {
            imagesPuppies = puppies.images
        }
        // Crear el adaptador con la lista de URL de imágenes y configurar el RecyclerView
        dogsAdapter = DogsAdapter(imagesPuppies)
        binding.rvDogs.setHasFixedSize(true)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = dogsAdapter
    }

    // Método para obtener una instancia de Retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Método llamado cuando se envía un texto de búsqueda
    override fun onQueryTextSubmit(query: String): Boolean {
        // Convertir el texto de búsqueda a minúsculas y realizar la búsqueda
        searchByName(query.lowercase())
        return true
    }

    // Método para realizar la búsqueda por nombre de raza
    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Reemplazar espacios en blanco en la consulta por "/"
            val query2 = query.replace(" ", "/")
            // Crear una llamada a la API para obtener las imágenes de la raza de perro especificada
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query2/images")
            val puppies = call.execute().body()
            // Actualizar la interfaz de usuario en el subproceso principal con los resultados de la búsqueda
            runOnUiThread {
                if (puppies?.status == "success") {
                    initCharacter(puppies)
                } else {
                    showErrorDialog()
                }
                hideKeyboard()
            }
        }
    }

    // Método para mostrar un diálogo de error
    private fun showErrorDialog() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    // Método llamado cuando cambia el texto de búsqueda (no utilizado en este caso)
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    // Método para ocultar el teclado virtual
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchBreed.windowToken, 0)
    }
}
