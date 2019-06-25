package logic.online

import com.alibaba.fastjson.JSON
import logic.traits.Logic

object ProvinceSelect extends Logic {
  var msg: String = null
  var sql:String=null

  override def getSql(msg: String): String  = {
    val car = JSON.parseObject(msg)
    val car_number = car.getString("car_number")


    if (car_number.contains("京")){
      val car_model = car.getString("car_model")
      val car_Own = car.getString("car_Own")
      val car_production = car.getString("car_production")
      val car_Location = car.getString("car_Location")
      this.sql = "insert into " +
        "cara.car_table(car_number,car_model,car_Own,car_production,car_Location)" +
        " values(" + car_number + "," + car_model + "," +car_Own+","+car_production+","+car_Location +")"
    }

    sql
  }



}

/*
{
 "car_number":"京753309",
 "car_model":"container",
 "car_Own":"褚瀚昂",
 "regist_time":"2014-5-16 5-57-49",
 "car_mileage":"6439261",
 "car_inspect":"0",
 "car_term":"22",
 "car_insurance":"1",
 "car_production":"云南徐州",
 "car_tonnage":"11.91",
 "car_evaluate":"general",
 "car_maxload":"84.0",
 "car_maintain":"bad",
 "car_Location":"河南武昌",
 "car_tachograph":"1",
 "car_nextInspect":"4726980621818",
 "car_wheel":"10",
 "car_colour":"blue",
 "car_accident":"76",
 "car_Capacity":"32"
}

 */