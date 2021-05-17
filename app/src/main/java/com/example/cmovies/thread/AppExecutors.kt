package com.example.cmovies.thread
import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * This class is responsible for spawning a thread or threads when needed. It can spawn 3 threads
 * simultaneously making it ideal for doing concurrent work(string in database, using main thread, etc).
 * Also I made it a separate class for code maintenance purposes.
 * */
class AppExecutors : Application() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(3)
    //val executorService: ExecutorService = Executors.newFixedThreadPool(3)
}