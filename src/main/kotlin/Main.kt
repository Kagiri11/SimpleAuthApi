import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import routes.auth.authRouting

fun main() {
   embeddedServer(Netty, port = 8080){
        module()
   }.start(wait = true)
}

fun Application.module(){
    install(ContentNegotiation){
        gson()
    }
    authRouting()
}