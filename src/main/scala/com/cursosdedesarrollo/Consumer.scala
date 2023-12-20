package com.cursosdedesarrollo

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import java.time.Duration
import java.util.Properties

object Consumer {
  def main(args: Array[String]): Unit = {
    println("Iniciando Consumidor")
    val properties = new Properties()
    // definimos los servidores donde conectar
    properties.put("bootstrap.servers", "localhost:9092")
    // definimos el deserializador de claves
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    // definimos el deserializador de valores
    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    // definimos el grupo al que pertenecen los consumidores
    properties.put("group.id", "my-group")
    // Empezamos a leer desde el principio del topic
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    // define que guarde cada X tiempo por donde va
    properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "500")
    // inicializamos el productor
    val consumer = new KafkaConsumer[String, String](properties)
    // nos subscribimos al topic my-topic
    consumer.subscribe(java.util.Arrays.asList("nombre-topic"))


    // Consume the messages
    while (true) {
      val records = consumer.poll(Duration.ofMillis(1000))
      records.forEach(record => {
        println(s"offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}")
      })
    }

    // Cerramos el producto
    consumer.close()
  }
}
