package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val listaDeAmigos = mutableSetOf<Usuario>()

  val listaDePermitidos = mutableSetOf<Usuario>()
  val listaDeExcluidos = mutableSetOf<Usuario>()


  fun darMegustaEnPublicacion(unaPublicacion: Publicacion) {

    if(!unaPublicacion.recibioMegustaDe(this) && this.puedeVer(unaPublicacion)) {
      unaPublicacion.recibirUnMeGustaDe(this)
    }
  }


  fun agregarPublicacion(publicacion: Publicacion) {
    publicacion.publicarsePor(this)
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun esAmigoDe(amigo : Usuario) = listaDeAmigos.contains(amigo)

  fun agregarAmigo(unUsusario: Usuario) {
    if (!listaDeAmigos.contains(unUsusario)) {
      listaDeAmigos.add(unUsusario)
      aceptarSolicitudDeAmistadDe(this)
    }
  }

  fun aceptarSolicitudDeAmistadDe(unUsusario: Usuario) { listaDeAmigos.add(unUsusario) }

  fun esMasAmistosoQue( otroUsuario: Usuario) = this.amigosTotales() > otroUsuario.amigosTotales()

  fun amigosTotales() = listaDeAmigos.size

  fun puedeVer(unaPublicacion: Publicacion) = unaPublicacion.puedeSeVistaPor(this)

  fun cambiarPermisoDe(unaPublicaion: Publicacion, nuevoPermiso: Permiso) {
    if (publicaciones.contains(unaPublicaion)) {
      unaPublicaion.cambiarAcceso(nuevoPermiso)
    }
  }

  fun agregarAListaDePermitidos(unUsuario: Usuario) { listaDePermitidos.add(unUsuario) }

  fun agregarAListaDeExcluidos(unUsuario: Usuario) { listaDeExcluidos.add(unUsuario) }

  fun mejoresAmigos() = listaDeAmigos.filter { puedeVerTodasMisPublicaciones(it) }

  fun puedeVerTodasMisPublicaciones(unUsuario: Usuario) = publicaciones.all { it.puedeSeVistaPor(unUsuario) }

  fun amigoMasPopular() = listaDeAmigos.maxByOrNull { it.totalDeMeGustaEnPublicaciones() }

  fun totalDeMeGustaEnPublicaciones() = publicaciones.sumBy { it.cantidadDeMeGusta }

  fun stalkeaA(unUsuario: Usuario) =
    unUsuario.totalDeMeGustaRecibidosPor(this) > unUsuario.totalDeMeGustaEnPublicaciones() * 0.9

  fun totalDeMeGustaRecibidosPor(unUsuario : Usuario) = publicaciones.count { it.recibioMegustaDe(unUsuario) }
}
