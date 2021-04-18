package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>() //Ver tema del null de los videos

  /*Ejecuta el método "recibirUnMeGustaDe(unUsuario)" del objeto de tipo "Publicacion" que se pasa por parámetro
    que a su vez, dicho método espera recibir un objeto de tipo "Usuario" en su parámetro, el cual será el
    el propio objeto que está ejecutando este método, esto se logra con el "this" que hace referencia a sí mismo.
    Algo enredado lo se jeje espero que se entienda (Dany) */
  fun darMegustaEnPublicacion(unaPublicacion: Publicacion) { unaPublicacion.recibirUnMeGustaDe(this) }

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

}
