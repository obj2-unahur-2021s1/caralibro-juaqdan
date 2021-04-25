package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>() //Ver tema del null de los videos
  val listaDeAmigos = mutableSetOf<Usuario>() //añado una lista de amigos para continuar lo siguiente (Joaquin)

  val listaDePermitidos = mutableSetOf<Usuario>() //Lista de usuarios que pueden ver las publicaciones.(Dany)
  val listaDeExcluidos = mutableSetOf<Usuario>()  //Lista de usuarios que no pueden ver las publicaciones.(Dany)

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

  /*Agrega una nueva publicación a la lista "publicaciones" y a su vez, asigna a este usuario como dueño de la
    publicación. (Dany)*/
  fun agregarPublicacion(publicacion: Publicacion) {
    publicacion.publicarsePor(this) // Ahora esta publicación es de este usuario.
    publicaciones.add(publicacion) //Se agrega a la lista de "publicaciones"
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  /*puse "aniadir" por conversion al ingles (porque no usan ñ y en algunos lenguajes estalla)
    es una lista y pregunta si el usuario ya esta agregado como amigo (Joaquin)*/
  fun aniadirAmigo(nuevoAmigo : Usuario) { if ( this.esAmigoDe(nuevoAmigo) ) { listaDeAmigos.add(nuevoAmigo) } }

  /*agregue este metodo que nos ayudara para los proximos pasos (Joaquin)*/
  fun esAmigoDe(amigo : Usuario) = listaDeAmigos.contains(amigo)



  /*Agrega a "unUsuario" (objeto de tipo usuario) a la colección "listaDeAmigos" solo si este no se
    encuentra en dicha lista y a su vez, dicho usuario acepta la solicitud de amistad agregándolo a su propia
    colección "listaDeAmigos" haciendo así que se ambos usuarios se agreguen de forma recíproca. (Dany)*/
  fun agregarAmigo(unUsusario: Usuario) {
    if (!listaDeAmigos.contains(unUsusario)) {
      listaDeAmigos.add(unUsusario)
      aceptarSolicitudDeAmistadDe(this)
    }
  }
  /*Este método sirve como complemento de "agregarAmigo", solo agrega a un objeto de tipo "Usuario" a su colección
    "listaDeAmigos". Este no requiere una condición ya que solo se usará cuando se ejecute el método anterior. (Dany)*/
  fun aceptarSolicitudDeAmistadDe(unUsusario: Usuario) { listaDeAmigos.add(unUsusario) }

   /*Compara la cantidad total de amigos de otro usuario con la cantidad propia, de esta manara define si es
     o no mas amistoso que el otro usuario. (Dany) */
  fun esMasAmistosoQue( otroUsuario: Usuario) = this.amigosTotales() > otroUsuario.amigosTotales()

  /*Devuelve la cantidad total de amigos que tiene un usuario
    Este método está para complemtentar al método "esMasAmistosoQue". (Dany)*/
  fun amigosTotales() = listaDeAmigos.size


  /*Verifica si este usuario puede ver una publicación dada, llamando al método "puedeSeVistaPor(this)"
    de las publicaciones. (Dany) */
  fun puedeVer(unaPublicacion: Publicacion) = unaPublicacion.puedeSeVistaPor(this)

  /*Si la publicación dada se encuentra en l lista "publicaciones", se podrá cambiar el permiso de esta
    por otro dado. Agregué este método porque debe ser responsabilidad del usuario modificar los permisos
    de sus publicaciones. (Dany) */
  fun cambiarPermisoDe(unaPublicaion: Publicacion, nuevoPermiso: Permiso) {
    if (publicaciones.contains(unaPublicaion)) {
      unaPublicaion.cambiarAcceso(nuevoPermiso)
    }
  }
  /*Agrega a un usuario a la lista "listaDePermitidos".(Dany)*/
  fun agregarAListaDePermitidos(unUsuario: Usuario) { listaDePermitidos.add(unUsuario) }

  /*Agrega a un usuario a la lista "listaDeExcluidos".(Dany)*/
  fun agregarAListaDeExcluidos(unUsuario: Usuario) { listaDeExcluidos.add(unUsuario) }

  /*Define una lista de mejores amigos teniendo en cuenta solo a aquellos amigos de la lista "listaDeAmigos"
    que tengan la capacidad de ver todas la publicaciones de este usuario. (Dany)*/
  fun mejoresAmigos() = listaDeAmigos.filter { puedeVerTodasMisPublicaciones(it) }

  /*Nos indica si un usuario dado puede ver todas la publicaciones de este usuario. Este método es para complementar al
  * anterior "mejoresAmigos()". (Dany)*/
  fun puedeVerTodasMisPublicaciones(unUsuario: Usuario) = publicaciones.all { it.puedeSeVistaPor(unUsuario) }

  /*Describe cual de todos los amigos de "listaDeAmigos" tiene mas "cantidadDeMeGusta" sumando todos los de sus
    publicaciones. (Dany)*/
  fun amigoMasPopular() = listaDeAmigos.maxByOrNull { it.totalDeMeGustaEnPublicaciones() }

  /*Suma todos los me gusta de cada publicación del usuario y nos devuelve el número total. Este método es complementario
    al método anterior (amigoMasPopular()). (Dany) */
  fun totalDeMeGustaEnPublicaciones() = publicaciones.sumBy { it.cantidadDeMeGusta }

  /*Sabiendo el total de me gusta recibido en sus publicaciones recibidos por un usuario dado, verifica si este
    es mayor al 90% de todos los me gusta recibidos en total. (Dany)*/
  fun stalkeaA(unUsuario: Usuario) =
    unUsuario.totalDeMeGustaRecibidosPor(this) > unUsuario.totalDeMeGustaEnPublicaciones() * 0.9

  /*Describe el número total de me gusta recibidos por un usuario dado. Este método complementa al anterior(stakeaA())
    (Dany)*/
  fun totalDeMeGustaRecibidosPor(unUsuario : Usuario) = publicaciones.count { it.recibioMegustaDe(unUsuario) }
}
