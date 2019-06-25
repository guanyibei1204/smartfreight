package uitls.test

import org.apache.spark.sql.SparkSession

object HiveSql {

  def main(args: Array[String]): Unit = {

    val spark=SparkSession
      .builder()
      .appName("hive")
      //1.连接hive只需要添加hive配置,不能在resources下添加hive-site.xml配置文件
      //2.直接在resource下添加hive-site.xml配置文件
      //.config("hive.metastore.uris","thrift://192.168.1.101:9083")
      //不管用哪一种连接hive都需要使用.enableHiveSupport()方法支持
      .enableHiveSupport()
      .master("local[*]")
      .getOrCreate()

    //索引库下表只要database.table
    //spark.sql("select  * from test.student").show()
    //7.每月用户新增数量；
    val sql1="select a.month, count(a.u_id) from (select substring(u_register_time,1,7) as month ,u_id from user_cara) a group by a.month"
    //16.各地区货物运送流出统计；address_start  address_target  order_table
    val sql2="select count(order_id) from order_table where !address_start.equal(address_target)"
    //发生历史事故的车辆统计
    val sql3="select count(car_number) from car_table where !(car_accident.equals(null))"
    //求每个地区每个日\周\月\年产生的总订单数
    val sql4="select order_start,substring(order_time,1,10),count(order_id) from order_table group by order_start ,group by substring(order_time,1,10)"
    //
    val sql5=""

    //可以查询加载一张表,然后对表进行操作
    spark.sql("select name  from test.peter2 group by name order by name  ").show
//      .foreach(row=>{
//      row.getValuesMap(Seq("name","age")).foreach(println(_))

//    })



  }

}
