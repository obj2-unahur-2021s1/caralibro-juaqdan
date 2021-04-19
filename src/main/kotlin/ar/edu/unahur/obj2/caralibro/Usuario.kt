package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>() //Ver tema del null de los videos
  val listaDeAmigos = mutableSetOf<Usuario>() //añado una lista de amigos para continuar lo siguiente (Joaquin)

  /*Ejecuta el método "recibirUnMeGustaDe(unUsuario)" del objeto de tipo "Publicacion" que se pasa por parámetro
    que a su vez, dicho método espera recibir un objeto de tipo "Usuario" en su parámetro, el cual será el
    el propio objeto que está ejecutando este método, esto se logra con el "this" que hace referencia a sí mismo.
    Algo enredado lo se jeje espero que se entienda (Dany) */
  fun darMegustaEnPublicacion(unaPublicacion: Publicacion) {
    /*aca consultamos si ya le dio me gusta antes, y asi no arrastramos el error. el simbolo ! es para dar vuelta
    el valor(si es falso, pasa a verdadero y viceversa)... el profe comento que la funcion (o metodo, no sé)
    llamada "check" puede sustituir el "if" y asi hacer el codigo mas sencillo y legible
    pero esta funcion no la se implementar todavia (Joaquin)*/
    if(!unaPublicacion.recibioMegustaDe(this)) {
      unaPublicacion.recibirUnMeGustaDe(this)
    }
    /*este fragmento (else) lo dejo por las dudas. el enunciado dice que no puede dar like a
      una misma publicacion pero tampoco aclara que tire un error o que no haga nada en caso de ya
      tener el like. si queres sacalo porque no lo use (Joaquin)*/
//    else{
//      error("el usuario ya le dio me gusta a la publicacion")
//    }
  }

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  /*puse "aniadir" por conversion al ingles (porque no usan ñ y en algunos lenguajes estalla)
    es una lista y pregunta si el usuario ya esta agregado como amigo (Joaquin)*/
  fun aniadirAmigo(nuevoAmigo : Usuario) { if ( this.esAmigoDe(nuevoAmigo) ) { listaDeAmigos.add(nuevoAmigo) } }

  /*agregue este metodo que nos ayudara para los proximos pasos (Joaquin)*/
  fun esAmigoDe(amigo : Usuario) = listaDeAmigos.contains(amigo)

}
