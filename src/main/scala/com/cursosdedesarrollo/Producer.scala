package com.cursosdedesarrollo

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import java.time.Duration
import scala.concurrent.duration._
object Producer {
  def main(args: Array[String]): Unit = {
    println("Iniciando Productor")
    val properties = new Properties()
    // definimos los servidores donde conectar
    // properties.put("bootstrap.servers", "localhost:9092")
    // manejado con la clase ProducerConfig
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
    // definimos el serializador de claves
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // definimos el serializador de valores
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // inicializamos el productor
    val producer = new KafkaProducer[String, String](properties)
    var i = 0
    while (i < 10) {
      // Espera 3 segundos
      Thread.sleep(Duration.ofMillis(3000))
      // definimos el destino y el mensaje
      val record = new ProducerRecord[String, String]("nombre-topic", "Hello, world!")
      // Enviamos el mensaje al topic my-topic con la clave Hello, world! y el valor Hello, world!
      producer.send(record)
      producer.flush()
      println("Mensaje enviado")
      i +=1
    }

    i = 0
    while (i < 10) {
      // Espera 3 segundos
      Thread.sleep(Duration.ofMillis(3000))
      // definimos el destino y el mensaje
      val record = new ProducerRecord[String, String]("nombre-topic", "clave", "valor")
      // Enviamos el mensaje al topic my-topic con la clave Hello, world! y el valor Hello, world!
      producer.send(record)
      producer.flush()
      println("Mensaje enviado con key")
      i +=1
    }

    // Cerramos el productor
    producer.close()
  }
}
