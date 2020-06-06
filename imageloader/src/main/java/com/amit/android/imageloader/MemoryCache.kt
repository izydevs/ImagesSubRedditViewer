package com.amit.android.imageloader

import android.opengl.ETC1.getHeight
import android.graphics.Bitmap
import android.text.method.TextKeyListener.clear
import android.util.Log
import java.nio.file.Files.size
import java.util.*
import java.util.Collections.synchronizedMap
import kotlin.collections.LinkedHashMap


class MemoryCache {
    private val TAG = "MemoryCache"
    private val cache = Collections.synchronizedMap(
        LinkedHashMap<String, Bitmap>(10, 1.5f, true)
    )//Last argument true for LRU ordering
    private var size: Long = 0//current allocated size
    private var limit: Long = 1000000//max memory in bytes

    fun MemoryCache() {
        //use 25% of available heap size
        setLimit(Runtime.getRuntime().maxMemory() / 4)
    }

    fun setLimit(new_limit: Long) {
        limit = new_limit
        Log.i(TAG, "MemoryCache will use up to " + limit.toDouble() / 1024.0 / 1024.0 + "MB")
    }

    operator fun get(id: String): Bitmap? {
        try {
            return if (!cache.containsKey(id)) null else cache.get(id)
            //NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            return null
        }

    }

    fun put(id: String, bitmap: Bitmap) {
        try {
            if (cache.containsKey(id))
                size -= getSizeInBytes(cache.get(id))
            cache.put(id, bitmap)
            size += getSizeInBytes(bitmap)
            checkSize()
        } catch (th: Throwable) {
            th.printStackTrace()
        }

    }

    private fun checkSize() {
        Log.i(TAG, "cache size=" + size + " length=" + cache.size)
        if (size > limit) {
            val iter =
                cache.entries.iterator()//least recently accessed item will be the first one iterated
            while (iter.hasNext()) {
                val entry = iter.next()
                size -= getSizeInBytes(entry.value)
                iter.remove()
                if (size <= limit)
                    break
            }
            Log.i(TAG, "Clean cache. New size " + cache.size)
        }
    }

    fun clear() {
        try {
            //NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78
            cache.clear()
            size = 0
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        }

    }

    fun getSizeInBytes(bitmap: Bitmap?): Long {
        return if (bitmap == null) 0 else (bitmap.rowBytes * bitmap.height).toLong()
    }
}