package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil





//*******************************************************Publicación******************************************
abstract class Publicacion {

  abstract var usuariosQueLaPublico: Usuario

  var cantidadDeMeGusta : Int = 0

  val usuariosQueLeGusto = mutableSetOf<Usuario>()

  var permisoDeAcceso : Permiso = publico


  abstract fun espacioQueOcupa(): Int

  fun recibirUnMeGustaDe(unUsuario : Usuario) {
    if(!this.recibioMegustaDe(unUsuario)){
      cantidadDeMeGusta += 1
      usuariosQueLeGusto.add(unUsuario)
    }
  }

  fun recibioMegustaDe(unUsuario: Usuario) = usuariosQueLeGusto.contains(unUsuario)

  fun cambiarAcceso(accesoNuevo : Permiso) { this.permisoDeAcceso = accesoNuevo }

  fun publicarsePor(unUsuario: Usuario) { usuariosQueLaPublico = unUsuario }

  fun puedeSeVistaPor(unUsuario: Usuario) = permisoDeAcceso.publicacion_puedeSeVistaPor(this, unUsuario)

}

//*****************************************Permisos********************************************

abstract class Permiso {
  abstract fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) : Boolean
}

object publico : Permiso() {
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) = true
}

object soloAmigos : Permiso() {
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) =
    unapublicacion.usuariosQueLaPublico.esAmigoDe(unUsuario)
}

object privadoConPermitidos : Permiso() {
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) =
    (unapublicacion.usuariosQueLaPublico).listaDePermitidos.contains(unUsuario)
}

object publicoConExcepciones : Permiso() {
  override fun publicacion_puedeSeVistaPor(unapublicacion: Publicacion,unUsuario: Usuario) =
    !(unapublicacion.usuariosQueLaPublico).listaDeExcluidos.contains(unUsuario)
}



//*****************************************************Texto**************************************************

class Texto(val contenido: String, override var usuariosQueLaPublico : Usuario) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}


//**********************************************Foto***********************************************************

class Foto(val alto: Int, val ancho: Int, override var usuariosQueLaPublico : Usuario) : Publicacion() {

  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion.valor).toInt()

  fun cambiarFactorDeCompresion(nuevoValor : Double) { factorDeCompresion.cambiarValorDeFactor(nuevoValor) }
}

object factorDeCompresion {
  var valor: Double = 0.7

  fun cambiarValorDeFactor(nuevoValor: Double) {
    valor = nuevoValor
  }
}



//**********************************************Video************************************************************

class Video(val duracion : Int, val calidad : Calidad, override var usuariosQueLaPublico : Usuario) : Publicacion() {
  override fun espacioQueOcupa() = calidad.calcularEspacio(duracion)
}

//**************************Calidades*****************************************************************************

/*Son las calidades posibles de un objeto de tipo "Video"*/

abstract class Calidad(){
  abstract fun calcularEspacio(duracion: Int) : Int
}

object sd : Calidad(){
  override fun calcularEspacio(duracion: Int) = duracion
}
object hd720p : Calidad(){
  override fun calcularEspacio(duracion: Int) = duracion * 3
}
object hd1080p : Calidad() {
  override fun calcularEspacio(duracion: Int) = hd720p.calcularEspacio(duracion) * 2
}
//*****************************************************************



