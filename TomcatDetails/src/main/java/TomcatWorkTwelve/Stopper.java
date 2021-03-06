package TomcatWorkTwelve;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Stopper {
    public static void main(String[]args){
        int port=8005;
        try {
            Socket socket=new Socket("127.0.0.1",port);
            OutputStream stream=socket.getOutputStream();
            String shutdown="SHUTDOWN";
            for (int i=0;i<shutdown.length();i++)
                stream.write(shutdown.charAt(i));
            stream.flush();
            stream.close();
            socket.close();
            System.out.println("ending...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
