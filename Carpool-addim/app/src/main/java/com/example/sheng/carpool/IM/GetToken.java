package com.example.sheng.carpool.IM;

/**
 * Created by Santa on 16/12/20.
 */

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.concurrent.CountDownLatch;

public class GetToken {

    String requestUrl = "https://api.cn.ronghub.com/user/getToken.json";
    String App_Key = "qd46yzrfquv8f"; //开发者平台分配的 App Key。
    String App_Secret = "pr3EPAuev4y";
    String Timestamp;
    String Nonce;
    String Signature;
    String tag = "GetToken";
    int flag = 0;
    String token="";
//    private Handler handler = new Handler(){
//
//        public void handleMessage(Message msg){
//            if(msg.what == 1){
//                token = msg.getData().getString("token");
//                flag = 1;
//                Log.d(tag, "token ="+token);
//                Log.d(tag, "flag ="+flag);
//            }
//        }
//    };


    public String GetRongCloudToken(final String userId, final String name, final String portraitUri) {
        this.setValues();
        CountDownLatch cdl = new CountDownLatch(1);
        new MyThread(cdl, userId, name, portraitUri).start();
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if(token != null){
            Log.d(tag, "token="+token);
        }

        return token;
    }

    private void setValues(){
        Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。
    }

    //SHA1加密//http://www.rongcloud.cn/docs/server.html#通用_API_接口签名规则
    private static String sha1(String data){
        StringBuffer buf = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for(int i = 0 ; i < bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        }catch(Exception e){

        }
        return buf.toString();
    }

    //解析JSON数据
    private String parseJSON(String jsonData) {
        String token;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            token = jsonObject.getString("token");
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public class MyThread extends Thread{
        private CountDownLatch cdl;
        private String userId;
        private String name;
        private String portraitUri;

        public MyThread(CountDownLatch cdl, String userId, String name, String portraitUri){
            this.cdl=cdl;
            this.userId = userId;
            this.name = name;
            this.portraitUri = portraitUri;
        }

        @Override
        public void run(){
            super.run();
            HttpURLConnection connection = null;
            Log.d(tag, "point1");
            try{
                URL url = new URL(requestUrl);
                //打开和URL的链接
                connection = (HttpURLConnection) url.openConnection();
                Log.d(tag, "point2");
                //设置
                connection.setReadTimeout(3 * 1000);//缓存最长时间
                connection.setDoInput(true);//允许输入
                connection.setDoOutput(true);//允许输出
                connection.setRequestMethod("POST");
                //设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                //融云api接口需要的4个header
                connection.setRequestProperty("App-Key", App_Key);
                connection.setRequestProperty("Nonce", Nonce);
                connection.setRequestProperty("Timestamp", Timestamp);
                connection.setRequestProperty("Signature", Signature);

                ////获取URLConnection对象对应的输出流
                PrintWriter print = new PrintWriter(connection.getOutputStream());
                Log.d(tag, "point3");
                //发送请求参数
                StringBuffer params = new StringBuffer();
                params.append("userId="+userId+"&");
                params.append("name="+name+"&");
                params.append("portraitUri="+ portraitUri);
                print.write(params.toString());
                //flush输出流的缓冲
                print.flush();
                int responseCode = connection.getResponseCode();
                if(responseCode != 200){
                    Log.d(tag, "Error==="+responseCode);
                    cdl.countDown();
                }
                else {
                    Log.d(tag, "Post success!");
                }

                //定义BufferedReader输入流来读取URL的ResponseData
                StringBuffer responseResult = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = reader.readLine())!= null){
                    responseResult.append(line).append("\n");
                }
                Log.d(tag, "GetValues==="+responseResult.toString());
                token = parseJSON(responseResult.toString());
//                Message message = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("token", parseJSON(responseResult.toString()));
//                message.setData(bundle);
//                message.what=1;
//                handler.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
            cdl.countDown();
        }
    }
}
