package routes.auth

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.auth.UserCredentials
import models.user.User
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.reactivestreams.KMongo

fun Route.register(usersCollection: CoroutineCollection<User>){

}

object Database{
    private val client = KMongo.createClient().coroutine
    private val database = client.getDatabase("my_Database")
    val usersCollection = database.getCollection<User>()
}

fun Application.authRouting(){

    routing {
        post("/register"){
            val user = call.receive<UserCredentials>()
            val username = user.username
            val password = user.password
            val users = Database.usersCollection.find().toList()
            if (users.find { it.username == username && it.password == password } != null){
                Database.usersCollection.insertOne(User(username = username, password = password))
                call.respondText("You have registered successfully", status = HttpStatusCode.Created)
            }else{
                call.respondText("User already exists", status = HttpStatusCode.Conflict)
            }
        }
    }
}

