package com.soywiz.korge.build

import com.soywiz.korge.*
import com.soywiz.korge.build.ResourceProcessor.Companion.processorsByExtension
import com.soywiz.korio.file.std.*
import kotlinx.coroutines.*
import java.io.*

// Used at korge-gradle-plugin
@Suppress("unused")
class KorgeBuildService {
    fun init() {
        KorgeManualServiceRegistration.register()
    }

    fun version(): String = Korge.VERSION

    fun processResourcesFolder(src: File, dst: File) {
        if (!src.exists()) return // Ignore empty folders

        runBlocking {
            runCatching { dst.mkdirs() }
            println("PROCESSORS:")
            for (processor in processorsByExtension) {
                println(" - $processor")
            }
            println("FILES:")
            val s = src.toVfs().jail()
            val d = dst.toVfs().jail()

            ResourceProcessor.process(listOf(s), d)

            /*
            println("$s -> $d")
            for (file in s.listRecursive()) {
                if (!file.isDirectory()) {
                    println(" -- $file")
                    ResourceProcessor.process(listOf(file), d)
                }
            }
            */
        }
    }
}
