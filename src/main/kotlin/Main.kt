import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import models.user.User
import routes.auth.authRouting
import utils.Database

fun main() {
    val userOne = User(username = "First User", password = "password")
   embeddedServer(Netty, port = 8080){
       runBlocking {
           Database.usersCollection.insertOne(userOne)
       }
        module()
   }.start(wait = true)
}

fun Application.module(){
    install(ContentNegotiation){
        gson()
    }
    authRouting()
}