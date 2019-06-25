package logic.online

import com.alibaba.fastjson.JSONObject
import logic.traits.Monitor

/**
  * 订单状态监控
  */
object StatusMonitor  extends Monitor {


  /**
    * 对订单失败的监控记录
    * @param json
    * @return
    */
  override def execMonitor(json:JSONObject ): Boolean = {
    if(json.getString("status").toInt==(-1)){
      true
    }else {
      false
    }
  }

}
