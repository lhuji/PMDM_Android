package com.example.entrenamientocorrutinasretrofit

import android.widget.ImageView
import com.squareup.picasso.Picasso


// Extensión de la clase ImageView para cargar una imagen desde una URL utilizando Picasso
fun ImageView.fromUrl(url: String) {
    // Utiliza Picasso para cargar la imagen desde la URL y establecerla en esta ImageView
    Picasso.get().load(url).into(this)
}