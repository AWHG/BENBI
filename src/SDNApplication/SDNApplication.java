package SDNApplication;

import SDNApplication.HTTPUtils.HTTPUtils;
import SEAlgorithm.SEAlgorithm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jessy on 2017/10/30.
 */
public class SDNApplication {

    /*{"switch": "00:00:00:00:00:00:00:01",
                 * "name":"flow-mod-1",
                 * "priority":"32768",
                 * "ingress-port":"1",
                 * "active":"true",
                 * "actions":"output=2"}
                 *
             *http://<controller_ip>:8080/wm/staticflowpusher/json
             * 'Content-type': 'application/json',
             * 'Accept': 'application/json'
    */
    protected static void flowpusher() throws UnsupportedEncodingException {
        Map<String,String> mapString = new HashMap<>();
        mapString.put("Content-type","application/json");
        mapString.put("Accept", "application/json");

        String url = "http://localhost:8080/wm/staticflowpusher/json";
//        String stringJson = "{\"switch\": \"00:00:00:00:00:00:00:01\", \"name\":\"flow-mod-1\", \"priority\":\"32768\", \"ingress-port\":\"1\",\"active\":\"true\", \"actions\":\"output=2\"}";

        byte[] key = "12345".getBytes();
        byte[] IV = {100, -85, -55, 85, -72, 17, 16, -70, 64, 101, -128, -25, 76, 74, -96, 67};/*SEAlgorithm.generateIV();*/

        String stringJson = "{\"switch\":"+ "\"" + SEAlgorithm.streamEnc("00:00:00:00:00:00:00:01",key,IV) + "\""
                + ", \"name\":" + "\"" + SEAlgorithm.streamEnc("flow-2",key,IV) + "\""
                + ", \"priority\":" + "\"" + SEAlgorithm.streamEnc("3",key,IV) + "\""
                + ", \"ingress-port\":" + "\"" + SEAlgorithm.streamEnc("1",key,IV) + "\""
                + ", \"active\":" + "\"" + SEAlgorithm.streamEnc("true",key,IV) + "\""
                + ", \"actions\":" + "\"" + SEAlgorithm.streamEnc("output=2",key,IV) + "\""
                +"}";

        Map<String,String> params = new HashMap<>();
        params.put("switch", SEAlgorithm.streamEnc("00:00:00:00:00:00:00:01",key,IV));
//        params.put("name", SEAlgorithm.streamEnc("flow-mod-1",key,IV));
//        params.put("priority", SEAlgorithm.streamEnc("32768",key,IV));
//        params.put("ingress-port", SEAlgorithm.streamEnc("1",key,IV));
        params.put("actions", SEAlgorithm.streamEnc("output=2", key, IV));


        CloseableHttpResponse response = HTTPUtils.httpPostRaw(url, stringJson, mapString, "utf-8");
        System.out.println("response:: " + response.getEntity().toString());

    }


    public static void main(String args[]){
        /*try {
            flowpusher();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        Map<String,String> mapString = new HashMap<>();
        mapString.put("Content-type","application/json");
        mapString.put("Accept", "application/json");
        String url = "http://192.168.211.1:8080/wm/topology/links/json";
        HttpResponse response = HTTPUtils.httpGet(url, mapString, "utf-8");
        if(response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            try {
                InputStream in = entity.getContent();
                String s = new String(inputStreamToByte(in));
                System.out.println("s -- " + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //ip.dst==192.168.211.164 && tcp.port == 8080

    public static byte[] inputStreamToByte(InputStream is) {
        ByteArrayOutputStream baos = null;
        byte[] buffer = new byte[8 * 1024];
        int c = 0;
        try {
            while ((c = is.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
