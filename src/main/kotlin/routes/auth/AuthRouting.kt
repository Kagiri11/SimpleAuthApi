package routes.auth

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.auth.UserCredentials
import models.user.User
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.insertOne

fun Route.register(usersCollection: CoroutineCollection<User>){
    post("/register"){
        val user = call.receive<UserCredentials>()
        val username = user.username
        val password = user.password
        val users = usersCollection.find().toList()
        if (users.find { it.username == username && it.password == password } != null){
            usersCollection.insertOne(User(username = username, password = password))
            call.respondText("You have registered successfully", status = HttpStatusCode.Created)
        }else{
            call.respondText("User already exists", status = HttpStatusCode.Conflict)
        }
       
    }
}

