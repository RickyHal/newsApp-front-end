package com.example.win10.personality_newsapp.user;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String parseStrToMd532(String str){
        String reStr=null;
        try{
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer= new StringBuffer();
            for (byte b: bytes){
                int bt=b&0xff;
                if(bt<16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr=stringBuffer.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return  reStr;
    }
}
