package com.example.entrenamientocorrutinasretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.entrenamientocorrutinasretrofit.databinding.ItemDogBinding

// Adaptador para el RecyclerView que mostrará las imágenes de los perros
class DogsAdapter(private val images: List<String>) : RecyclerView.Adapter<DogsAdapter.ViewHolder>() {

    // Método llamado cuando se enlaza una vista del elemento de la lista con sus datos
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    // Método llamado cuando se necesita crear una nueva vista de elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflar el diseño del elemento de perro utilizando el LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_dog, parent, false)
        // Devolver una instancia de ViewHolder que contiene la vista del elemento
        return ViewHolder(itemView)
    }

    // Método llamado para obtener la cantidad total de elementos en el conjunto de datos
    override fun getItemCount(): Int {
        return images.size
    }

    // ViewHolder que contiene una vista de elemento de perro
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Acceder a los elementos de la vista utilizando el enlace generado por ViewBinding
        private val binding = ItemDogBinding.bind(view)

        // Método para enlazar los datos del perro con la vista del elemento
        fun bind(image: String) {
            // Utilizar una extensión de la ImageView para cargar la imagen desde una URL
            binding.ivDog.fromUrl(image)
        }
    }
}
