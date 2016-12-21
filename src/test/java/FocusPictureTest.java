import java.util.HashMap;
import java.util.Map;


/**
 * Created by leishuzhou on 2016/11/10.
 */
public class FocusPictureTest {
    private static Map keywordMap = new HashMap();
    private static int thread_num = 2;
    private static int client_num = 20;

   public static void main(String[] args) throws Exception {
/*        // TODO Auto-generated method stub
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(thread_num);
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        String url = "http://211.157.179.221:22020/yijiajiao-oss/feedback/add";
                        Object obj= JSON.toJSON(ImmutableMap.of("content","123","openId","c6baf9b5-5f32-47c6-96ed-6f43861cf760","contactWay","dfs"));
                        String json = Request.Post(url)
                                .addHeader("Content-Type", "application/json")
                                .bodyString(obj.toString(), ContentType.APPLICATION_JSON)
                                .execute()
                                .returnContent()
                                .asString();
                        System.out.print( new String(json.getBytes("ISO-8859-1")));
                        System.out.println("第：" + NO + " 个");
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
    }*/
   }
}
