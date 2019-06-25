package uitls.hive

import spark.SparkObject
import logic.traits.Logic
import uitls.mysql.ConnHandler

object HiveHandler {

  /**
    * hive handler 主要实施:
    *   1. 对数据的读取分析
    *   2. 对部分数据的写入
    *
    * @param sql        hivesql
    * @param logic      字段处理
    */
  def readHive2Mysql(sql: String, logic: Logic) {

    val spark = SparkObject.getSpark()

    spark.sql(sql).foreach(row => {
      synchronized {
        ConnHandler.insertResult(logic.getSql(row))

        //row.getValuesMap(Seq("start_ad","c")).foreach(m=>{println(m._1+""+m._2)})
        //print(row)
        //kvMap=row.getValuesMap(Seq("name", "age"))
        //("name"->"peter",age->34)
      }
    })
  }

  //
  //  val spark = SparkObject.getSpark()
  //  var kvMap :Map[String,String]=null
  //
  //  spark.sql("select * from test.peter").foreach(row => {
  //
  //    kvMap=row.getValuesMap(Seq("name", "age"))
  //    println("Name:"+kvMap("name")+" \tage:"+kvMap("age"))
  //
  //  })

}
