package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil


/*Se creó este objeto para que represente un valor que pude cambiar y que al hacerlo afecte
  a todas las instancias de la clase "Foto" que se hayan construido. Investigar si hay alguna manera mas
  simplificada como hacer una variable estática por ejemplo (Dany)*/
object factorDeCompresion {
  var valor: Double = 0.7

  fun cambiarValorDeFactor(nuevoValor: Double) {
    valor = nuevoValor
  }
}


//*****************************************Permisos********************************************


/*Implementé los permisos como objetos e hice que sea su responsabilidad determinar si un usuario
  puede ver una publicación o no, otorgándole un método que, mediante dos parámetros los cuales el primero es
  una publicación y el otro es un usuario, define sie es verdadero o falso dependiendo de los requisitos de
  cada objeto. Este se relacionará con el método "puedeSeVistaPor(unUsuario: Usuario)"
  de la clase "Publicacion" (ver en esa clase). (Dany)*/
abstract class Permiso {
    abstract fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) : Boolean
}

object publico : Permiso() {
  /*Todos las publicaciones publicas pueden se vistas por cualquiera, por lo tanto siempre sevuelve "true". (Dany)*/
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) = true
}
object soloAmigos : Permiso() {
  /*En este permiso, solo aquellos que sean amigos de quien hizo la publicación podrán verla, entonces, se necesita
    identificar quien la publicó. Para esto las publicaciones ahora tienen un nuevo atributo "usuariosQueLaPublico"
    el cual es el usuario que las publicó (ver en la clase "Publicacion"). Así con el uso del método
    "esAmigoDe(unUsuario)" de los usuarios podemos verificar si el usuario qu publicó la publicación
    es amigo de un usuario dado. (Dany)*/
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) =
    unapublicacion.usuariosQueLaPublico.esAmigoDe(unUsuario)
}
object privadoConPermitidos : Permiso() {
  /*Se agrega a los Usuarios una lista "listaDePermitidos" que entre sus amigos elige cuales pueden ver ciertas
    publicaciones. Solo aquellos usuarios que estén dentro de la lista pueden ver la publicación.(Dany)*/
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) =
    (unapublicacion.usuariosQueLaPublico).listaDePermitidos.contains(unUsuario)
}
object publicoConExcepciones : Permiso() {
  /*Se agrega a los Usuarios una lista "listaDeExcluidos" que entre sus amigos elige cuales no pueden ver ciertas
    publicaciones. Solo aquellos usuarios que no estén dentro de esta lista podrán ver la publicación.(Dany)*/
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) =
    !(unapublicacion.usuariosQueLaPublico).listaDeExcluidos.contains(unUsuario)
}
//*******************************************************************************

abstract class Publicacion {

  abstract var usuariosQueLaPublico: Usuario //Indica el usuario que publicó esta publicación.(Dany)

  var cantidadDeMeGusta : Int = 0 /*Es la cantidad de MG que recibe una publicación,
                                    se inicia siempre en 0 y es una variable porque puede aumentar (Dany)*/

  val usuariosQueLeGusto = mutableSetOf<Usuario>() /*Son los usuarios que le dieron MG, se juntan en una colección
                                                     porque no se pueden repetir, un usuario que dió MG
                                                     no puede dar otra vez a la misma.
                                                     La colección es de objetos de tipo "Usuario"(Dany)*/


  var permisoDeAcceso : Permiso = publico /*la variable que define los permisos y se pueden cambiar cuando quieran
  esta inicializa en publico porque ese seria el predeterminado segun yo. aunque esto lo podemos cambiar a "lateinit" para iniciarla posteriormente.
   Al igual que la calidad de video, los permisos de acceso son objetos independientes con la clase "Permiso" heredada (Joaquin)*/



  abstract fun espacioQueOcupa(): Int

  /*Toma al objeto de tipo "Usuario" que se pone en el parámetro y lo agrega a la colección "usuariosQueLeGusto".
    Aumenta en 1 la variable "cantidadDeMeGusta". Este método complementa al método
    "darMegustaEnPublicacion(unaPublicacion)" de la clase "Usuario" (Dany)*/

  /*En este metodo le agrege la corroboracion (puede sustiturse con un check) de que no este el usuario porque
   por mas que sea una lista, el atributo "cantidadDeMeGusta" estaba aumentando por mas de que no se sume el
   usuario que da me gusta (Joaquin)*/
  fun recibirUnMeGustaDe(unUsuario : Usuario) {
    if(!this.recibioMegustaDe(unUsuario)){
      cantidadDeMeGusta += 1
      usuariosQueLeGusto.add(unUsuario)
    }
  }
  /*puse este metodo para poder usarlo en la clase "usuario" y asi preguntar antes de llamar el metodo
  "recibirUnMegustaDe" y tirar el error de que el usuario ya le dio me gusta.
  posterior a eso, el metodo "recibirUnMeGustaDe" vuelve a preguntar si este usuario ya le dio me gusta(Joaquin)*/
  fun recibioMegustaDe(unUsuario: Usuario) = usuariosQueLeGusto.contains(unUsuario)

  fun cambiarAcceso(accesoNuevo : Permiso) { this.permisoDeAcceso = accesoNuevo }

  /*Este método modifica quien publicó esta publicación.(Dany)*/
  fun publicarsePor(unUsuario: Usuario) {
    usuariosQueLaPublico = unUsuario
  }

  /*Dependiendo de que tipo de permiso tenga la publicación, se definirá con distintas condiciones (ver permisos)
   si el usuario dado puede verla o no. (Dany)*/
  fun puedeSeVistaPor(unUsuario: Usuario) = permisoDeAcceso.publicacion_puedeSeVistaPor(this, unUsuario)

}

class Texto(val contenido: String, override var usuariosQueLaPublico : Usuario) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Foto(val alto: Int, val ancho: Int, override var usuariosQueLaPublico : Usuario) : Publicacion() {

  /*Quité la variable "factorDeCompresion" porque creé el objeto "factorDeCompresion"
    con un atributo que define su valor y un método para modificarlo en cualquier momento.
    También modifiqué el método "espacioQueOcupa()" par que se adapte al uso del objeto. (Dany)*/
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion.valor).toInt()

  /* Agregue la funcion para cambiar el factor de compresion desde la foto. y asi cuando se decida cambiar
    se realice directamente desde la instancia y que el valor de compresion (hasta ahora) aplica solo a las
    fotos, cualquier cosa lo cambiamos (Joaquin) */
  fun cambiarFactorDeCompresion(nuevoValor : Double) { factorDeCompresion.cambiarValorDeFactor(nuevoValor) }
}


//**************************Calidades************************

/*Son las calidades posibles de un objeto de tipo "Video"*/

//Clase superior que engloba a todos los objetos que representan la calidad de un "Video"
abstract class Calidad(){
  abstract fun calcularEspacio(duracion: Int) : Int
}


object sd : Calidad(){
  override fun calcularEspacio(duracion: Int) = duracion //Los segundos de duración
}
object hd720p : Calidad(){
  override fun calcularEspacio(duracion: Int) = duracion * 3 //El triple de los segundos de duración
}
object hd1080p : Calidad() {
  /*decidi cambiarlo porque el enunciado dice "Hace lo mismo que el hd720 y lo multiplica por 2".
    puede ser que la calidad 720p cambie la manera de calcular, si eso pasara tendriamos que
    cambiar la manera de calcular aca tambien*/
  override fun calcularEspacio(duracion: Int) = hd720p.calcularEspacio(duracion) * 2
}
//*****************************************************************

class Video(val duracion : Int, val calidad : Calidad, override var usuariosQueLaPublico : Usuario) : Publicacion() {
  override fun espacioQueOcupa() = calidad.calcularEspacio(duracion)
}

