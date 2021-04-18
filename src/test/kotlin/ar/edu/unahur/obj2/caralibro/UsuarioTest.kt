package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val videoRecital = Video(60, sd) //Un video en calidad sd (Dany)

    val pepito = Usuario() //Agregué un objeto de tipo usuario (Dany)

    describe("Una publicación") {
      pepito.darMegustaEnPublicacion(fotoEnCuzco) //el usuario "pepito" le da MG la foto "fotoEnCuzco" (Dany)

      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes en 0.7") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }

        it("ocupa ancho * alto * compresion bytes en 0.9") { //Esta vez probamos cambiando el valor del factor. (Dany)
          factorDeCompresion.cambiarValorDeFactor(0.9)
          fotoEnCuzco.espacioQueOcupa().shouldBe(707789)
        }

        it ("Cantidad de (Me gusta)") {
          fotoEnCuzco.cantidadDeMeGusta.shouldBe(1) //El usuario "papito" le dió MG así que debería dar 1(Dany)
        }
        it ("Usuarios que les gutó") {
          fotoEnCuzco.usuariosQueLeGusto.shouldBe(setOf(pepito)) //Funciona pero chequear igual el "setOf" (Dany)
        }
      }

      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }


      describe("de tipo video") {
        it("ocupa tantos bytes en calidad sd") {
          videoRecital.espacioQueOcupa().shouldBe(60)
        }

        it("ocupa tantos bytes en calidad hd720p") {
          videoRecital.cambiarCalidad(hd720p) //Se tiene que desarrollar el método (Dany)
          videoRecital.espacioQueOcupa().shouldBe(60)
        }

        it("ocupa tantos bytes en calidad fullHd1080p") {
          videoRecital.cambiarCalidad(fullHd1080p) //Se tiene que desarrollar el método (Dany)
          videoRecital.espacioQueOcupa().shouldBe(60)
        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(707834)
      }
    }
  }
})
