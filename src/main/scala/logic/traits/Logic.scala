package logic.traits

import org.apache.spark.sql.Row

/**
  * 用于对所有logic接口统一规范
  */
trait Logic extends scala.Serializable {

  /**
    * 这是针对 kafka ----> MySQL的接口
    * @param msg
    */
   def getSql(msg:String): String={
     ""
   }

  /**
    *
    * 这是针对hive ---> MySQL的接口
    *
    * @param row
    */

  def getSql(row:Row):String={
    ""
  }



}
