package main

import com.google.inject.Guice
import com.google.inject.Injector
import controllers.ApiManager
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory

/**
 * Main entry point for the Ktor application. (API)
 */
fun main() {
    val logger = LoggerFactory.getLogger("main")
    // Initialize new server
    embeddedServer(Netty, port = 8080) {
        try {
            mainModule()
        } catch (e: Exception){
            logger.error(e.message, e)
            throw e
        }
    }.start(wait = true)
}

fun Application.mainModule() {
    // Set up Dependency Injection with Guice
    val injector: Injector = Guice.createInjector(AppModule())
    val apiManager = injector.getInstance(ApiManager::class.java)

    // Register routes using ApiManager
    apiManager.registerRoutes(this)
}