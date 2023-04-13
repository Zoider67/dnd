package com.zoider.dnd.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams

@Component
class RedisClient(
    @Value("\${redis.host}")
    private val host: String,
    @Value("\${redis.port}")
    private val port: String
) {
    private lateinit var pool: JedisPool

    init {
        pool = JedisPool(host, port.toInt())
    }

    fun set(key: String, value: String, ttl: Long) {
        val jedis = pool.resource
        jedis.set(key, value, SetParams.setParams().ex(ttl))
    }

    fun get(key: String): String? {
        val jedis = pool.resource
        val res = jedis.get(key)
        return if (res == "nil") {
            null
        } else {
            res
        }
    }

    fun delete(key: String) {
        val jedis = pool.resource
        jedis.del(key)
    }
}