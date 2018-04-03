package SDNApplication.HTTPUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import SDNApplication.ResourceConfig;
import org.apache.http.HttpEntity;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.caeit.cloud.dev.entity.HttpResponse;
/**
 * Created by jessy on 2017/10/30.
 */
public class HTTPUtils {

        public static CloseableHttpResponse httpGet(String url,Map<String,String> headers,String encode){
//            HttpResponse response = null;
            if(encode == null){
                encode = "utf-8";
            }
            String content = null;
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(),entry.getValue());
                }
            }
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpGet);

                HttpEntity entity = httpResponse.getEntity();

            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResponse;
        }

        public static CloseableHttpResponse httpPostForm(String url,Map<String,String> params, Map<String,String> headers,String encode){
//            HttpResponse response = null;
            if(encode == null){
                encode = "utf-8";
            }
            //HttpClients.createDefault()�ȼ��� HttpClientBuilder.create().build();
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPost httpost = new HttpPost(url);

            //����header
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpost.setHeader(entry.getKey(),entry.getValue());
                }
            }
            //��֯�������
            List<NameValuePair> paramList = new ArrayList <NameValuePair>();
            if(params != null && params.size() > 0){
                Set<String> keySet = params.keySet();
                for(String key : keySet) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            try {
                httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            String content = null;
            CloseableHttpResponse  httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpost);
                HttpEntity entity = httpResponse.getEntity();
//                content = EntityUtils.toString(entity, encode);
                /*response.setEntity(entity);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());*/
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {  //�ر����ӡ��ͷ���Դ
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResponse;
        }


        public static CloseableHttpResponse httpPostRaw(String url,String stringJson,Map<String,String> headers, String encode){
            if(encode == null){
                encode = "utf-8";
            }
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPost httpost = new HttpPost(url);

            httpost.setHeader("Content-type", "application/json");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpost.setHeader(entry.getKey(),entry.getValue());
                }
            }
            StringEntity stringEntity = new StringEntity(stringJson, encode);
            httpost.setEntity(stringEntity);
            String content = null;
            CloseableHttpResponse  httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpost);
                HttpEntity entity = httpResponse.getEntity();

            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResponse;
        }
        public static CloseableHttpResponse httpPutRaw(String url,String stringJson,Map<String,String> headers, String encode){
//            HttpResponse response = null;
            if(encode == null){
                encode = "utf-8";
            }
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPut httpput = new HttpPut(url);

            httpput.setHeader("Content-type", "application/json");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpput.setHeader(entry.getKey(),entry.getValue());
                }
            }
            StringEntity stringEntity = new StringEntity(stringJson, encode);
            httpput.setEntity(stringEntity);
            String content = null;
            CloseableHttpResponse  httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpput);
                HttpEntity entity = httpResponse.getEntity();

            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResponse;
        }

        public static CloseableHttpResponse httpDelete(String url,Map<String,String> headers,String encode){
            if(encode == null){
                encode = "utf-8";
            }
            String content = null;
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpDelete httpdelete = new HttpDelete(url);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpdelete.setHeader(entry.getKey(),entry.getValue());
                }
            }
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpdelete);
                HttpEntity entity = httpResponse.getEntity();

            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResponse;
        }


        public static CloseableHttpResponse httpPostFormMultipart(String url,Map<String,String> params, List<File> files,Map<String,String> headers,String encode){
//            HttpResponse response = null;
            if(encode == null){
                encode = "utf-8";
            }
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPost httpost = new HttpPost(url);

            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpost.setHeader(entry.getKey(),entry.getValue());
                }
            }
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            mEntityBuilder.setCharset(Charset.forName(encode));

            ContentType contentType = ContentType.create("text/plain",Charset.forName(encode));
            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    mEntityBuilder.addTextBody(key, params.get(key),contentType);
                }
            }
            if (files != null && files.size() > 0) {
                for (File file : files) {
                    mEntityBuilder.addBinaryBody("file", file);
                }
            }
            httpost.setEntity(mEntityBuilder.build());
            String content = null;
            CloseableHttpResponse  httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpost);
                HttpEntity entity = httpResponse.getEntity();
//                content = EntityUtils.toString(entity, encode);
                /*response.setEntity(entity);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());*/
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return httpResponse;
        }

}
