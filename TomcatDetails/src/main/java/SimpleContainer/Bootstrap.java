package SimpleContainer;

import org.apache.catalina.connector.http.HttpConnector;

public final class Bootstrap {
    public static void main(String args[]){
        HttpConnector connector=new HttpConnector();
        SimpleContainer simpleContainer=new SimpleContainer();
        connector.setContainer(simpleContainer);
        try {
            connector.initialize();
            connector.start();
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
