package lib

import logic.traits.Monitor
import logic.online._


/**
  * Libs 是一个常量存储对象
  *
  */
object Libs {

  /**
    * sql list
    */

  val OFF_SQL_MAP = Map[Int, String](

    1 ->
      "select start_ad, count(*) c from smartcara.order_table group by start_ad order by c desc"
    , 2 ->
      "select end_ad, count(*) c from smartcara.order_table group by end_ad order by c desc"
    , 3 ->
      "select good_type,count(*) counts from smartcara.order_table group by good_type order by counts desc"
    , 4 ->
      "select start_ad ,sum(amount) money from smartcara.order_table group by start_ad order by money desc"
    , 5 ->
      "select order.start_ad ,count(owner.owner_id) counts from smartcara.owner_table owner join smartcara.order_table order on owner.owner_id=order.owner_id where owner.owner_local != order.start_ad group by order.start_ad"

  )



  //key : 选择的业务编号
  //value : 对应业务需要的参数

  // 参数： MySQL的表名 | 字段列表  | 执行hql语句

  val OFF_CONFIG_MAP = Map[Int,Tuple3[String,List[String],String]](
    1-> Tuple3("statistics.region_statistics"
      ,List("start_ad","c"),
      OFF_SQL_MAP(1))
    ,2 -> Tuple3("statistics.input_region_statistics"
      ,List("end_ad","c"),
      OFF_SQL_MAP(2)
    )
    ,3 -> Tuple3("statistics.type_statistics"
      ,List("good_type","counts")
      ,OFF_SQL_MAP(3)
    )
    ,4 -> Tuple3("statistics.money_statistics"
      ,List("start_ad","money")
      ,OFF_SQL_MAP(4)
    )
    ,5 -> Tuple3("statistics.no_local_owner_statistics"
      ,List("start_ad","counts")
      ,OFF_SQL_MAP(5)
    )
  )




  //key : 选择的业务编号
  //value : 对应业务需要的参数
  // 参数： MySQL的表名 | 字段名   | topic名  | 监视器
  val ON_CONFIG_MAP = Map[Int,Tuple4[String,List[String],String,Monitor]](
    1 ->
      Tuple4("monitor.fail_monitor"
      ,List("order_id","user_id","owner_id","start_ad","end_ad","amount","good_type")
      ,"order_topic"
      ,StatusMonitor
    )
    ,2 ->
      Tuple4("monitor.bigAmount_monitor"
        ,List("order_id","user_id","owner_id","start_ad","end_ad","amount","status","good_type")
        ,"order_topic"
        ,BigAmountMonitor
      )
    ,3 -> Tuple4("monitor.car_monitor"
      ,List("car_num","car_type")
      ,"car_topic"
      ,CarMonitor
    )
    ,4 ->
      Tuple4("monitor.user_monitor"
        ,List("user_id","user_local","user_sex")
        ,"user_topic"
        ,UserMonitor
      )
    ,5 ->
      Tuple4("monitor.year_monitor"
        ,List("owner_id","owner_local"
          ,"car_num","owner_age")
        ,"owner_topic",YearMonitor
      )
  )



}
