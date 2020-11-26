import org.json.JSONArray;
import org.json.JSONObject;
import utils.API;

public class Test {
    public static void main(String[] args) throws Exception {
        API api = new API();
        JSONObject ids = new JSONObject();
        JSONArray arr = new JSONArray().put(274);
        arr.put(276);
        ids.put("0",arr);
        JSONArray arr_2 = new JSONArray().put(277);
        arr_2.put(278);
        arr_2.put(279);
        ids.put("1",arr_2);
        ids.put("response",200);

//        api.seeOnePlan(ids);
    }
}