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



abstract class Publicacion {

  var cantidadDeMeGusta : Int = 0 /*Es la cantidad de MG que recibe una publicación,
                                    se inicia siempre en 0 y es una variable porque puede aumentar (Dany)*/

  val usuariosQueLeGusto = mutableSetOf<Usuario>() /*Son los usuarios que le dieron MG, se juntan en una colección
                                                     porque no se pueden repetir, un usuario que dió MG
                                                     no puede dar otra vez a la misma.
                                                     La colección es de objetos de tipo "Usuario"(Dany)*/

  abstract fun espacioQueOcupa(): Int

  /*Toma al objeto de tipo "Usuario" que se pone en el parámetro y lo agrega a la colección "usuariosQueLeGusto".
    Aumenta en 1 la variable "cantidadDeMeGusta". Este método complementa al método
    "darMegustaEnPublicacion(unaPublicacion)" de la clase "Usuario" (Dany)*/
  fun recibirUnMeGustaDe(unUsusario : Usuario) { cantidadDeMeGusta += 1; usuariosQueLeGusto.add(unUsusario) }

}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {

  /*Quité la variable "factorDeCompresion" porque creé el objeto "factorDeCompresion"
    con un atributo que define su valor y un método para modificarlo en cualquier momento.
    También modifiqué el método "espacioQueOcupa()" par que se adapte al uso del objeto. (Dany)*/
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion.valor).toInt()
}



class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
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
object fullHd1080p : Calidad() {
  override fun calcularEspacio(duracion: Int) = (duracion * 3) * 2 //El doble del triple de los segundos de duración
}
//*****************************************************************

class Video(val duracion : Int, val calidad : Calidad) : Publicacion() {
  override fun espacioQueOcupa() = calidad.calcularEspacio(duracion)
}

