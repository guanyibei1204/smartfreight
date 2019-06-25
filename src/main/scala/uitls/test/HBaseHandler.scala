package uitls.test

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import spark.SparkObject

/**
  * HBase Handler 的主要功能是
  *     1. 读取Hbase 中的数据
  *
  */
object HBaseHandler extends App {

  val conf = HBaseConfiguration.create()
  val sc = SparkObject.getSc()
  conf.set(TableInputFormat.INPUT_TABLE, "student")
  val stuRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat]
    , classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable]
    , classOf[org.apache.hadoop.hbase.client.Result])
  val count = stuRDD.count()
  println("Student RDD Count: " + count)
  stuRDD.cache()

  stuRDD.foreach({ case (_,result)=>
    val key = Bytes.toString(result.getRow)
    val name = Bytes.toString(result.getValue("info".getBytes(), "name".getBytes()))
    val gender = Bytes.toString(result.getValue("info".getBytes(),"gender".getBytes()))
    val age = Bytes.toInt(result.getValue("info".getBytes(),"age".getBytes()))
    println("Row key:"+key+" Name:"+name+" Gender:"+gender+" Age:" + age)

  })
  def readFromHbase(): Unit = {
    val conf = HBaseConfiguration.create()
    val sc = SparkObject.getSc()
    conf.set(TableInputFormat.INPUT_TABLE, "student")
    val stuRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat]
      , classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable]
      , classOf[org.apache.hadoop.hbase.client.Result])
    val count = stuRDD.count()
    println("Student RDD Count: " + count)
    stuRDD.cache()

    stuRDD.foreach({ case (_,result)=>
      val key = Bytes.toString(result.getRow)
      val name = Bytes.toString(result.getValue("info".getBytes(), "name".getBytes()))
      val gender = Bytes.toString(result.getValue("info".getBytes(),"gender".getBytes()))
      val age = Bytes.toInt(result.getValue("info".getBytes(),"age".getBytes()))
      println("Row key:"+key+" Name:"+name+" Gender:"+gender+" Age:" + age)

    })
  }

}
