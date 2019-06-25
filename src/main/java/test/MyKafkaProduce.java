package test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * 造数据
 */
public class MyKafkaProduce {


    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("bootstrap.servers", "server01:6667");
        map.put("acks", "all");
        map.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        map.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(map);


        //String topic, K key, V value
        for (int i = 0; i < 10000; i++) {


            kafkaProducer.send(new ProducerRecord("user_topic", "key" + i,getUserJson() ));
            System.out.println("p;;;");
            kafkaProducer.flush();
        }

    }
    static String getOrderJson(){
        Random rd = new Random();


        return "{" +
                "\"order_id\":\""+getID()+ "\"" +
                ",\"user_id\":\""+getID()+" \"" +
                ",\"owner_id\":\""+getID()+" \"" +
                ",\"start_ad\":\""+provinces[rd.nextInt(34)]+ "\"" +
                ",\"end_ad\":\""+provinces[rd.nextInt(34)]+ "\"" +
                ",\"amount\":\""+rd.nextInt(100000)+ "\"" +
                ",\"status\":\""+statuss[rd.nextInt(3)]+ "\"" +
                ",\"good_type\":\""+good_typess[rd.nextInt(5)]+ "\"" +
                "}";
    }
    static String getOwnerJson(){
        Random rd = new Random();
        String str = "{" +
                "\"owner_id\":\""+getID()+"\","+
                "\"owner_local\":\""+provinces[rd.nextInt(34)]+"\","+
                "\"car_num\":\""+getCarID()+"\","+
                "\"owner_age\":\""+rd.nextInt(70)+"\""+
                "}";
        return str;
    }
    static String getCarJson(){
        Random rd = new Random();
        return "{" +
                "\"car_num\":\""+getCarID()+"\","+
                "\"car_type\":\""+car_type[rd.nextInt(5)]+"\""+
                "}";
    }


    static String getCarID(){
        Random rd = new Random();
        String[] types={"number","word"};
        HashMap<String,String[]> car_id= new HashMap();
        car_id.put("number",new String[]{"1","2","3","4","5","6","7","8","9","0","1","2","3","4","5","6","7","8","9","0","1","2","3","4","5","6","7","8","9","0"});
        car_id.put("word",new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"});
        return car_nums[rd.nextInt(34)]+car_id.get("word")[rd.nextInt(26)]+"."+car_id.get(types[rd.nextInt(2)])[rd.nextInt(26)]+
                car_id.get(types[rd.nextInt(2)])[rd.nextInt(26)]+
                car_id.get(types[rd.nextInt(2)])[rd.nextInt(26)]+
                car_id.get(types[rd.nextInt(2)])[rd.nextInt(26)]+
                car_id.get(types[rd.nextInt(2)])[rd.nextInt(26)];
    }

    static String getUserJson(){
        Random rd = new Random();
        return "{" +
                "\"user_id\":\""+getID()+"\","+
                "\"user_local\":\""+provinces[rd.nextInt(34)]+"\","+
                "\"user_sex\":\""+sex[rd.nextInt(2)]+"\""+
            "}";
    }

    static String getID(){
        String str ="";
        Random rd = new Random();
        int i=0;
        while(i<13){
            str+= word_smail[rd.nextInt(26)];
            i++;
        }
        return str;
    }



    static String[] statuss={"-1","0","1"};
    static String[] good_typess={"家具","办公","货物","材料","行李"};
    static String[] car_nums = {"京","津","冀","晋","蒙","辽","吉","黑","沪","苏","浙","皖","闽","赣","鲁","豫","鄂","湘","粤","桂","琼","渝","川","黔","滇","藏","陕","甘","青","宁","新","台","港","澳"};

    static String[] word_smail = {"z","x","c","v","b","n","m","a","s","d","f","g","h","j","k","l","q","w","e","r","t","y","u","i","o","p"};

    static String[] provinces ={"北京","天津","河北","山西","内蒙古","辽宁","吉林","黑龙江","上海","江苏","浙江省","安徽","福建","江西","山东","河南","湖北","湖南","广东","广西","海>南","重庆","四川","贵州","云南","西藏","陕西","甘肃省","青海","宁夏","新疆","台湾","香港特别行政区","澳门"};

    static String[] sex = {"男","女"};
    static String[] car_type = {"江铃", "斯太尔","威铃","解放","东风"};
    //order_id	user_id	owner_id	start_ad	end_ad	amount	status	good_type
    //owner_id	owner_local	car_num	owner_age
    //user_id	user_local	user_sex
}

