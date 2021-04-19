package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoPerdido = Video(12, sd)
    val boomerangConAmigos = Video(5, hd720p)
    val videoEnMachuPichu = Video(120, hd1080p)
    val fotoEnLima = Foto(1042, 760)

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }
      describe("de tipo video"){
        describe("calidad sd"){
          it("ocupa tantos bytes como la duracion en segundos"){
            videoPerdido.espacioQueOcupa().shouldBe(12)
          }
        }
        describe("calidad hd720p"){
          it("el tamaño es igual al triple de la duracion en segundos"){
            boomerangConAmigos.espacioQueOcupa().shouldBe(15)
          }
        }
        describe("calidad hd1080p"){
          it("el doble del tamaño de 720p"){
            videoEnMachuPichu.espacioQueOcupa().shouldBe(720)
          }
        }
      }
    }

    describe("Un usuario") {
      val juana = Usuario()
      juana.agregarPublicacion(fotoEnCuzco)
      juana.agregarPublicacion(saludoCumpleanios)

      val jose = Usuario()
      jose.agregarPublicacion(fotoEnLima)
      jose.agregarPublicacion(videoEnMachuPichu)
      videoEnMachuPichu.cambiarAcceso(soloAmigos)

      it("puede calcular el espacio que ocupan sus publicaciones") {
        juana.espacioDePublicaciones().shouldBe(550548)
      }
      it("puede dar me gusta a una publicacion"){
        juana.darMegustaEnPublicacion(fotoEnLima)
        fotoEnLima.recibioMegustaDe(juana).shouldBeTrue()
      }
      it("no puede dar me gusta porque no es amiga y no tiene acceso"){
        /*Este test no pude hacerlo porque no se como ponerlo para que se espere un error
        entonces lo hice para que no haga nada pero no se visualice el me gusta (Joaquin)*/
        juana.darMegustaEnPublicacion(videoEnMachuPichu)
        videoEnMachuPichu.recibioMegustaDe(juana).shouldBeFalse()
      }
      it("no puede dar me gusta porque ya le dio anteriormente"){

      }
    }
  }
})
