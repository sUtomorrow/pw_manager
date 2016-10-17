package com.ltys.pw_manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.exit;

/**
 * Created by Administrator on 2016/10/16.
 */

public class ToolFunction {
    /**
     *用户名解密
     *@param ssoToken 字符串
     *@return String 返回加密字符串
     */
    public static String decrypt(String ssoToken) {
//        try{
//            byte[] _ssoToken = ssoToken.getBytes();
//            for (int i = 0; i < _ssoToken.length; i++) {
//                int asc = _ssoToken[i];
//                _ssoToken[i] = (byte) (asc ^ 27);
//            }
//            String name = new String(_ssoToken);
//            return name;
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
        return new String(Base64.decode(ssoToken.getBytes()));
    }

    /**
     *用户名加密
     *@param ssoToken 字符串
     *@return String 返回加密字符串
     */
    public static String encrypt(String ssoToken){
//        try
//        {
//            byte[] _ssoToken = ssoToken.getBytes();
//            for (int i = 0; i < _ssoToken.length; i++) {
//                int asc = _ssoToken[i];
//                _ssoToken[i] = (byte) (asc ^ 27);
//            }
//            String name = new String(_ssoToken);
//            return name;
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
        return new String(Base64.encode(ssoToken.getBytes()));
    }

    public static InputStream getStringIStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void write_pass(String path,int[] container){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        DataOutputStream dos= new DataOutputStream(baos);
        try{
            for(int z = 0;z<container.length;z++){
                dos.writeInt(container[z]);
//                Log.i("aasd",""+container[z]);
            }
            dos.writeInt(-1);
            dos.flush();
            dos.close();
        }
        catch(IOException e){
        e.printStackTrace();
        exit(0);
        }
        try {
            bytes = baos.toByteArray();
            for(int i = 0;i<bytes.length;i++){
                bytes[i] += 27;
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            exit(0);
        }
    }

    public static int[] read_pass(String path){
        File file = new File(path);
        int[] container = new int[signin_activity.width*signin_activity.width];
//        String pass = new String();
        byte[] bytes = new byte[1024];
        int curse = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();
            for(int i = 0;i<bytes.length;i++){
                bytes[i]-=27;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            DataInputStream dis= new DataInputStream(bais);
            int i = 0;
            curse = 0;
            while(i!=-1){
                i = dis.readInt();
                container[curse] = i;
//                Log.i("eeee",i+"");
                curse++;
            }
        } catch (Exception e){
            e.printStackTrace();
            exit(0);
        }


        return container;
    }

}
