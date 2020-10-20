package aa;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoad extends ClassLoader {
    private final String path;
    public MyClassLoad(String path){
        super(null);
        this.path = path;
    }
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
       MyClassLoad cload =  new MyClassLoad("E:\\code_else\\dmy\\src\\main\\java\\aa");
       Class<?> aclass = cload.loadClass("Hello.xlass");

        Constructor cons = aclass.getDeclaredConstructor((Class[]) null);
        Object obj = cons.newInstance();
        Method setBrand = aclass.getMethod("hello");
        setBrand.invoke(obj);
       System.out.println(aclass.getClassLoader());
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] by = new byte[0];
        try {
            by = readBytes(name);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return defineClass(by, 0, by.length);
    }

    public byte[] readBytes(String name) throws IOException {
       String classFilePath = name;
       File file = new File(path+File.separatorChar+classFilePath);
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        byte[] bytes = new byte[1024];
        byte[]readBytes = null;
        try{
            bos  = new ByteArrayOutputStream();
            is = new FileInputStream(file);
            int len=0;
            while((len = is.read(bytes))!=-1){
                bos.write(bytes,0,len);
            }
            readBytes = bos.toByteArray();
            for(int i=0;i<readBytes.length;i++){
                readBytes[i] = (byte)(255-readBytes[i]);
            }
        }catch(Exception ex){

        }finally {
            if(is!=null){
                is.close();
            }
            if(bos!=null){
                bos.close();
            }
            return readBytes;
        }
    }
}
