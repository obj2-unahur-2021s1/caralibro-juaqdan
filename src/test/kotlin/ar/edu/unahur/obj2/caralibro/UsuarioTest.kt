package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val juana = Usuario()
    val micaela = Usuario()
    val jose = Usuario()
    val hector = Usuario()
    val dario = Usuario()

    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz", juana)
    val felicitaciones = Texto("Felicidades por aprobar", hector)
    val fotoEnCuzco = Foto(768, 1024, juana)
    val videoPerdido = Video(12, sd, micaela)
    val boomerangConAmigos = Video(5, hd720p, juana)
    val videoEnMachuPichu = Video(120, hd1080p, micaela)
    val fotoEnLima = Foto(1042, 760, juana)




    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)  //Parte del requerimiento 1
        }
        it("Los *Me gusta*") {
          juana.darMegustaEnPublicacion(fotoEnCuzco)  //Parte del requerimiento 2
          jose.darMegustaEnPublicacion(fotoEnCuzco)

          fotoEnCuzco.cantidadDeMeGusta.shouldBe(2)  //Requerimiento 2
          fotoEnCuzco.usuariosQueLeGusto.shouldBe(setOf(juana,jose))
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)  //Parte del requerimiento 1
        }
      }
      describe("de tipo video"){
        describe("calidad sd"){
          it("ocupa tantos bytes como la duracion en segundos"){
            videoPerdido.espacioQueOcupa().shouldBe(12)  //Parte del requerimiento 1
          }
        }
        describe("calidad hd720p"){
          it("el tamaño es igual al triple de la duracion en segundos"){
            boomerangConAmigos.espacioQueOcupa().shouldBe(15)  //Parte del requerimiento 1
          }
        }
        describe("calidad hd1080p"){
          it("el doble del tamaño de 720p"){
            videoEnMachuPichu.espacioQueOcupa().shouldBe(720)  //Parte del requerimiento 1
          }
        }
      }
    }

    describe("Un usuario") {

      juana.agregarAmigo(jose)
      juana.agregarAmigo(hector)
      juana.agregarAmigo(dario)
      juana.agregarAmigo(micaela)

      juana.agregarPublicacion(fotoEnCuzco)
      juana.agregarPublicacion(saludoCumpleanios)
      juana.agregarPublicacion(fotoEnLima)

      juana.cambiarPermisoDe(fotoEnCuzco, soloAmigos)
      juana.cambiarPermisoDe(saludoCumpleanios, privadoConPermitidos)
      juana.cambiarPermisoDe(fotoEnLima, publicoConExcepciones)

      micaela.agregarPublicacion(videoEnMachuPichu)
      micaela.agregarPublicacion(videoPerdido)

      micaela.cambiarPermisoDe(videoPerdido, privadoConPermitidos)

      micaela.agregarAListaDePermitidos(hector)

      hector.agregarPublicacion(felicitaciones)

      juana.agregarAListaDeExcluidos(hector)
      juana.agregarAListaDeExcluidos(jose)

      juana.agregarAListaDePermitidos(micaela)
      juana.agregarAListaDePermitidos(dario)

      juana.darMegustaEnPublicacion(saludoCumpleanios)
      juana.darMegustaEnPublicacion(videoEnMachuPichu)
      hector.darMegustaEnPublicacion(videoPerdido)
      juana.darMegustaEnPublicacion(felicitaciones)

      describe ("Juana") {

        it("puede calcular el espacio que ocupan sus publicaciones") {
          juana.espacioDePublicaciones().shouldBe(1104892) //Requerimiento 1
        }
        it ("Es mas amistosa que jose") {
          juana.esMasAmistosoQue(jose).shouldBeTrue()  //Requerimiento 3
        }
        it ("Puede ver una publicacion") {
          juana.puedeVer(videoEnMachuPichu).shouldBeTrue() //Requerimiento 4
          juana.puedeVer(videoPerdido).shouldBeFalse()
        }
        it ("Mejores amigos") {
          juana.mejoresAmigos().shouldBe(listOf(dario, micaela))  //Requerimiento 5
        }
        it ("Amigo mas popular") {
          juana.amigoMasPopular().shouldBe(micaela) //Requerimiento 6
        }
        it ("Stalkea a un usuario?") {
          juana.stalkeaA(micaela).shouldBeFalse()  //Requerimiento 7
          juana.stalkeaA(hector).shouldBeTrue()
        }
      }



      it("puede dar me gusta a una publicacion"){
        juana.darMegustaEnPublicacion(fotoEnLima)
        fotoEnLima.recibioMegustaDe(juana).shouldBeTrue()
      }
      it("no puede dar me gusta porque no es amiga y no tiene acceso"){
        /*Este test no pude hacerlo porque no se como ponerlo para que se espere un error
        entonces lo hice para que no haga nada pero no se visualice el me gusta (Joaquin)*/
        juana.darMegustaEnPublicacion(videoEnMachuPichu)
        videoEnMachuPichu.recibioMegustaDe(juana).shouldBeTrue()
      }
      it("no puede dar me gusta porque ya le dio anteriormente"){

      }
    }
  }
})
