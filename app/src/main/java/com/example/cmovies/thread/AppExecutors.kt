package com.example.cmovies.thread
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * This class is responsible for spawning a thread or threads when needed. It can spawn 3 threads
 * simultaneously making it ideal for doing concurrent work.
 * Also I made it a separate class for code maintenance purposes.
 * */
class AppExecutors {
    private var mDiskIO: Executor? = null

    private var mNetworkIO: Executor? = null

    private var mMainThread: Executor? = null

    private fun AppExecutors(
        diskIO: Executor,
        networkIO: Executor,
        mainThread: Executor
    ) {
        mDiskIO = diskIO
        mNetworkIO = networkIO
        mMainThread = mainThread
    }

    fun AppExecutors() {
        this(
            Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
            MainThreadExecutor()
        )
    }

    private operator fun invoke(newSingleThreadExecutor: ExecutorService, newFixedThreadPool: ExecutorService?, mainThreadExecutor: AppExecutors.MainThreadExecutor) {

    }

    fun diskIO(): Executor? {
        return mDiskIO
    }

    fun networkIO(): Executor? {
        return mNetworkIO
    }

    //
    fun mainThread(): Executor? {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}