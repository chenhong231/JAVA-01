package com.geektime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");

            Method declaredMethod = clazz.getDeclaredMethod("hello");
            declaredMethod.invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        byte [] bytes = loadFromFile("Hello.xlass");

        return defineClass(name,bytes, 0, bytes.length);
    }

    private byte[] loadFromFile(String path) throws ClassNotFoundException  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        int nextValue;
        try {
            while ((nextValue = inputStream.read()) != -1) {
                bout.write(nextValue);
            }
            return decode(bout.toByteArray());
        } catch (IOException e) {
            System.err.println("error ocourred when HelloClassLoader.loadClassData");
            e.printStackTrace();
            throw new ClassNotFoundException("xlass file not found");
        }
    }

    private byte[] decode(byte[] xlass) {
        for (int i = 0; i < xlass.length; i++) {
            xlass[i] = (byte) (255 - xlass[i]);
        }
        return xlass;
    }
}
