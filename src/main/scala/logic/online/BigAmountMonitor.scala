package logic.online

import com.alibaba.fastjson.JSONObject
import logic.traits.Monitor


/**
  * 监控大额交易订单
  */
object BigAmountMonitor extends Monitor {

  /**
    * 对交易金额大于70000的监控记录
    * @param json
    * @return
    */
  override def execMonitor(json: JSONObject): Boolean = {
    if(json.getString("amount").toInt>70000){
      true
    }else{
      false
    }
  }

}
