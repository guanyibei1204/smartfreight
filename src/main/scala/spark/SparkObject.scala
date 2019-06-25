package spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * spark object 对象的功能：
  *   1。 配置集群信息
  *   2。 提供统一接口
  */
object SparkObject {
  /**
    * 固定配置信息：
    *
    */
//  val sparkConf = new SparkConf().setAppName("SparkApp").setMaster("local[*]")
//    .set("spark.driver.host", "localhost")
//    .set("spark.port.maxRetries","100")
//    .set("yarn.resourcemanager.hostname", "192.168.1.101")
//    .set("spark.executor.instances","2")
//    .setJars(List(
//      "/home/peter/kafka_2.10-0.10.1.2.6.1.0-129.jar"
//      ,"/home/peter/kafka-clients-0.10.1.2.6.1.0-129.jar"
//      ,"/home/peter/spark-streaming-kafka-0-10_2.11-2.1.2.jar"))
  val spark = SparkSession.builder().appName("SparkApp").master("local[*]")
   //.config("spark.sql.warehouse.dir", "/apps/hive/warehouse")
    .enableHiveSupport()
    .getOrCreate()
  val sc =spark.sparkContext
  sc.setLogLevel("warn")

  val scc =new StreamingContext(sc,Seconds(5))

  def getSpark():SparkSession={
    this.spark
  }

  def getSc():SparkContext={
    this.sc
  }

  def getSsc():StreamingContext={
    this.scc
  }

}
