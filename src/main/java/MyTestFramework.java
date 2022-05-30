import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;;

public class MyTestFramework {
    public static void main(String[] args) {
        String url = "E:/AN2/ProiectePA/Lab2/target/classes/";
        String className = "Compulsory";
        Class aClass;

        try {
            URL classUrl = new URL("file:///" + url);
            URLClassLoader ucl = new URLClassLoader(new URL[]{classUrl});
            aClass = ucl.loadClass(className);
        } catch (MalformedURLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        extractMethods(aClass);
        try {
            invokeStatic(aClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractMethods(Class aClass) {
        for (Method method : aClass.getMethods()) {
            System.out.print(method.getReturnType() + " " + method.getName() + "(");
            boolean type = true;
            for (Parameter parameter : method.getParameters()) {
                if (type) {
                    System.out.print(parameter.getType()+ " " + parameter.getName());
                    type = false;
                } else {
                    System.out.print(", " + parameter.getType() + " " + parameter.getName());
                }
            }
            System.out.print(")" + "\n");
        }
    }

    public static void invokeStatic(Class aClass) {
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}