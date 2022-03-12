package top.zyaire.videogether.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.RandomStringUtils;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.entity.Link;
import top.zyaire.videogether.entity.RtmpEntity;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticUtils {
    public static List<Link> videoRoot = new ArrayList<>();
    //public static HashMap<String,User> onlineUser = new HashMap<>();
    public static HashMap<String, RtmpEntity<String,Process>> rtmpList = new HashMap<>();
    public static String getMd5(String input){
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String randomString(){
        return RandomStringUtils.randomAlphabetic(10);
    }
}
