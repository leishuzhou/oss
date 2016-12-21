import com.alibaba.fastjson.JSON;
import com.yijiajiao.oss.util.Config;
import redis.clients.jedis.Jedis;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by leishuzhou on 2016/11/29.
 */
public class TEst {
    private static String redisIp = Config.getString("redis.ip");
    private static int redisPort = Config.getInt("redis.port");
    static int maxSize = 10;

           public static void main(String args[]) {





            /*   Jedis jedis = new Jedis(redisIp, redisPort);
               jedis.select(2);

               String key = jedis.get("hotCurriculum");
               Map<String, Object> obj = JSON.parseObject(key);
               String s = JSON.toJSONString(obj);
               List list = new ArrayList<>();
               list.add(s);
               System.out.println(list.get(0));
               Map<String, Integer> map = new HashMap<>();
               int pageNo = 1; //当前页
               int page_max_count = 5;  //每页显示5条
               int rec = 0;  //记录号，第几个数据

               for (int j = 0; j <= page_max_count; j++) {
                   rec = page_max_count * pageNo + j;
                   //如果是list的话，在这个地方直接就根据索引去把值取出来。
                   System.out.println(list.get(rec));
               }*/
       Jedis jedis = new Jedis(redisIp, redisPort);
        jedis.select(2);


        Map<String, Object> map = new LinkedHashMap<String, Object>(){

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                // 当前记录数大于设置的最大的记录数，删除最旧记录（即最近访问最少的记录）
                return size() > maxSize;
            }
        };
               String key = jedis.get("hotCurriculum");
               Map<String, Object> mas = JSON.parseObject(key);

               map.putAll(mas);

        System.out.println(map.size());
        System.out.println(map);

    }


}
