import java.io.*;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Object o = new HelloClassLoader().findClass("Hello").newInstance();
            Method hello = o.getClass().getDeclaredMethod("hello");
            hello.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        File file = new File(HelloClassLoader.class.getResource("Hello.xlass").getPath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int n = 0;
        byte[] bytes = new byte[1];
        try(FileInputStream fio = new FileInputStream(file)) {
            while ((n = fio.read(bytes)) != -1) {
                bytes[0] = (byte) (255 - bytes[0]);
                bos.write(bytes);
            }
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bos.toByteArray(), 0, bos.size());
    }
}
