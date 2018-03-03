package com.apache.android.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 01370340 on 2017/10/24.
 */

public class Md5Util {

    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messagedigest = null;
    static{
        try{
            messagedigest = MessageDigest.getInstance("MD5");
        }catch(NoSuchAlgorithmException nsaex){
            System.err.println(Md5Util.class.getName()+"初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }



    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /**
     * 校验文件md5值是否正确
     * @param url
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isTheFileValid(String url, File file)throws IOException {
        if (TextUtils.isEmpty(url) || file == null){
            throw new NullPointerException("url or file must not be null");
        }
        String fileMd5 = getFileMD5String(file);
        return url.equals(fileMd5);
    }



    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
        //2EA3E66AC37DF7610F5BD322EC4FFE48 670M 11s kuri双核1.66G 2G内存
        File big = new File("/Users/jerryliu/jerry/py/LoginTask.java");
        String md5 = getFileMD5String(big);
        long end = System.currentTimeMillis();
        System.out.println("md5:"+md5+" time:"+((end-begin) )+" ms");
    }
}

