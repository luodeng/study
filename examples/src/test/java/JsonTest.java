import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * http://apis.map.qq.com/ws/district/v1/list?key=K3VBZ-M6WWV-PPSPY-UVGGC-DRM2Z-PGBMV
 */
public class JsonTest {

    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        //String path = JsonTest.class.getClassLoader().getResource("F:\\addressJSON.txt").getPath();
        String s = readJsonFile("F:\\addressJSON.txt");
        JSONObject jobj = JSON.parseObject(s);
        JSONArray data = jobj.getJSONArray("result");

        JSONArray p = data.getJSONArray(0);
        JSONArray c = data.getJSONArray(1);
        JSONArray d = data.getJSONArray(2);

        JSONArray jsonArray=new JSONArray();

        for(int i=0;i<p.size();i++){
            JSONObject jsonObject=new JSONObject();
            JSONObject jsonP=p.getJSONObject(i);
            jsonObject.put("code",jsonP.getString("id"));
            jsonObject.put("name",jsonP.getString("fullname"));
            JSONArray pp=new JSONArray();
            for(int j=0;j<c.size();j++){
                JSONObject jsonCity=c.getJSONObject(j);
                if(!jsonCity.getString("id").substring(0,2).equals(jsonP.getString("id").substring(0,2))){
                   continue;
                }
                JSONObject jsonObject2=new JSONObject();
                jsonObject2.put("code",jsonCity.getString("id"));
                jsonObject2.put("name",jsonCity.getString("fullname"));
                JSONArray cc=new JSONArray();
                for(int k=0;k<d.size();k++){
                    JSONObject jsondis=d.getJSONObject(k);
                    if(!jsondis.getString("id").substring(0,4).equals(jsonCity.getString("id").substring(0,4))){
                        continue;
                    }
                    JSONObject jsonObject3=new JSONObject();
                    jsonObject3.put("code",jsondis.getString("id"));
                    jsonObject3.put("name",jsondis.getString("fullname"));
                    cc.add(jsonObject3);
                }

                jsonObject2.put("areaList",cc);
                pp.add(jsonObject2);
            }
            jsonObject.put("cityList",pp);
            jsonArray.add(jsonObject);
        }

        PrintWriter pw=new PrintWriter("D:/temp/city.json");
        pw.print(JSON.toJSONString(jsonArray));
        pw.close();
    }
}

