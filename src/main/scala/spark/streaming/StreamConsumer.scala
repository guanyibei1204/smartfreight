package spark.streaming

import logic.traits.Logic
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import spark.SparkObject
import uitls.mysql.ConnHandler



object StreamConsumer  {

  val kafkaConf = Map[String,Object](
    "bootstrap.servers"->"192.168.1.101:6667"
    ,"key.deserializer"->classOf[org.apache.kafka.common.serialization.StringDeserializer]
    ,"value.deserializer"->classOf[org.apache.kafka.common.serialization.StringDeserializer]
    ,"group.id"->"iidd"
    ,"auto.offset.reset"-> "earliest"
    ,"enable.auto.commit"-> (false:java.lang.Boolean)
  )

  /**
    * 这是一个kafka消费的封装对象
    * 需要消费的topic 和 logic处理
    * logic 是对业务逻辑的封装
    * @param topic  消费的topic
    * @param logic  业务逻辑
    */
  def toConsumer(topic:String,logic:Logic) {
    val ssc = SparkObject.getSsc()

    val messages = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Set(topic), kafkaConf)
    )
    /*
      这里是对kafka数据的消费
      在logic中是业务逻辑的封装
     */


    messages.foreachRDD(rdd => {
      rdd.map(_.value()).foreach(msg => {
        println(msg)
        synchronized{
          ConnHandler(logic.getSql(msg)) //消息字段处理  //执行业务逻辑

        }
      }
      )
    })

    ssc.start()
    ssc.awaitTermination()    //优雅关闭streaming
  }



}
