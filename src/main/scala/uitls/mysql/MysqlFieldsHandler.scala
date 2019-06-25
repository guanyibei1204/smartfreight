package uitls.mysql

import com.alibaba.fastjson.{JSON, JSONObject}
import logic.traits.{Logic, Monitor}
import org.apache.spark.sql.Row

/**
  * 1. 该类处理接收的数据，匹配设定的字段
  * 2. 生产sql插入语句
  * 3. 执行SQL
  */
object MysqlFieldsHandler extends Logic{

  var fields: List[String] = null
  var sql: String = null
  var tableName: String = null
  var monitor:Monitor=null

  //    def setFields(fields: Seq[String]): Unit = {
  //      this.fields = fields
  //    }
  //    def setTableName(tableName:String): Unit ={
  //      this tableName = tableName
  //    }

  /**
    * 配置生产sql 语句的表名和 获取数据字段的字段名
    * @param tableName
    * @param fields
    */
  def setTable(tableName: String, fields: List[String]): Unit = {
    this.tableName = tableName
    this.fields = fields


  }

  /**
    * 实时使用的监控设置+
    * @param monitor
    */
  def setMonitor(monitor: Monitor): Unit ={
    this.monitor = monitor
  }

  /**
    * 这里产生的是插入values（）中的字段
    *
    * 注意： 插入mySQL的数据作为全字符处理：
    *
    * @param fdMap
    * @return
    */
  def getW(fdMap:Map[String,Nothing]): String = {
    var w = ""
    for (i <- 0 to (fields.length-1)) {
      w = w + "','" + fdMap(fields(i))
    }
    w.substring(2)
  }

  /**
    * 这是对 hive 查询的row 生产插入MySQL的sql的接口
    * @param row
    */


  override def getSql(row: Row): String = {

    val fdMap= row.getValuesMap(this.fields)
    println(fields)
    sql = "insert into " + tableName + " values(" + getW(fdMap) + "')"
    println("打印：" + sql)
    sql
  }


  /**
    * 对 kafka 端的实时数据msg 处理 并插入MySQL的SQL语句
    * @param msg
    */
  override def getSql(msg: String): String ={

    val json = JSON.parseObject(msg)
    if(monitor.execMonitor(json)) {
      sql = "insert into " + tableName + " values(" + getW(json) + "')"
      println("打印：" + sql)
    }else{
      sql=null
    }
    sql
  }

  /**
    * 获取kafka的json数据 ， 对json数据的字段值获取。
    * @param json
    * @return
    */
  def getW(json:JSONObject):String={
    var w=""
    for(f<- fields){
      w = w+"','"+ json.getString(f)
    }
    w.substring(2)
  }



}
