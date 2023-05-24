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
    private var pool: JedisPool = JedisPool(host, port.toInt())
    private var jedis: Jedis = pool.resource

    fun set(key: String, value: String, ttl: Long) {
        println("set value $value for key $key")
        jedis.set(key, value, SetParams.setParams().ex(ttl))
    }

    fun get(key: String): String? {
        val res = jedis.get(key)
        return if (res == "nil" || res == "na") {
            null
        } else {
            res
        }
    }

    fun delete(key: String) {
        jedis.del(key)
    }
}