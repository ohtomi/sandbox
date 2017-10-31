package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class App {

    private static final byte[] key = "abcdefghijklmnop".getBytes();
    private static final byte[] iv = "0123456789012345".getBytes();

    private byte[] encrypt(byte[] input) throws Exception {
        SecretKeySpec key = new SecretKeySpec(App.key, "AES");
        IvParameterSpec iv = new IvParameterSpec(App.iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        return cipher.doFinal(input);
    }

    private byte[] decrypt(byte[] input) throws Exception {
        SecretKeySpec key = new SecretKeySpec(App.key, "AES");
        IvParameterSpec iv = new IvParameterSpec(App.iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        return cipher.doFinal(input);
    }

    public static void main(String... args) throws Exception {
        App app = new App();

        {
            String original = "your name is";
            byte[] input = original.getBytes(StandardCharsets.UTF_8);
            byte[] encrpyted = app.encrypt(input);
            byte[] decrpypted = app.decrypt(encrpyted);
            String result = new String(decrpypted, StandardCharsets.UTF_8);

            System.out.println(String.format(" original: '%s'", original));
            System.out.println(String.format("encrypted: '%s'", Base64.getEncoder().encodeToString(encrpyted)));
            System.out.println(String.format("decrypted: '%s'", Base64.getEncoder().encodeToString(decrpypted)));
            System.out.println(String.format("   result: '%s'", result));
            System.out.println(String.format("            123456789 123456789 123456789 123456789"));
        }

        int loop = 100000;

        {
            byte[] headOfReturns = new byte[loop];
            long from = System.currentTimeMillis();
            for (int i = 0; i < loop; i++) {
                byte[] ret = app.encrypt(String.format("hoge-%d", i).getBytes(StandardCharsets.UTF_8));
                headOfReturns[i] = ret[0];
            }
            long to = System.currentTimeMillis();

            System.out.println();
            System.out.println(String.format("%d times, elapsed: %d msec,\nreturns: %s", loop, to - from, Base64.getEncoder().encodeToString(headOfReturns)));
        }
        {
            SecretKeySpec key = new SecretKeySpec(App.key, "AES");
            IvParameterSpec iv = new IvParameterSpec(App.iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] headOfReturns = new byte[loop];
            long from = System.currentTimeMillis();
            for (int i = 0; i < loop; i++) {
                byte[] ret = cipher.doFinal(String.format("hoge-%d", i).getBytes(StandardCharsets.UTF_8));
                headOfReturns[i] = ret[0];
            }
            long to = System.currentTimeMillis();

            System.out.println();
            System.out.println(String.format("%d times, elapsed: %d msec,\nreturns: %s", loop, to - from, Base64.getEncoder().encodeToString(headOfReturns)));
        }
    }
}
