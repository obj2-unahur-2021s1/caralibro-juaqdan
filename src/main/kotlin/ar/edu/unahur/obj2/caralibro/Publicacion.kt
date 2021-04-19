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

abstract class Permiso {

}

object publico : Permiso() {

}
object soloAmigos : Permiso() {

}
object privadoConPermitidos : Permiso() {

}
object publicoConExcepciones : Permiso() {

}

abstract class Publicacion {

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

}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {

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

class Video(val duracion : Int, val calidad : Calidad) : Publicacion() {
  override fun espacioQueOcupa() = calidad.calcularEspacio(duracion)
}

