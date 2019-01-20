package TomcatWorkThirteen;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.ContextConfig;

public final class Bootstrap {
    public static void main(String[]args){
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector=new HttpConnector();
        Context context=new StandardContext();
        context.setPath("/myApp");
        context.setDocBase("myApp");
        LifecycleListener listener=new ContextConfig();
        ((StandardContext) context).addLifecycleListener(listener);
        Host host=new StandardHost();
        host.addChild(context);
        host.setName("localhost");
        host.setAppBase("myApp");
        Loader loader=new WebappLoader();
        context.setLoader(loader);
        connector.setContainer(host);
        try {
            connector.initialize();
            ((HttpConnector) connector).start();
            ((StandardHost) host).start();
            Container[] c=context.findChildren();
            int length=c.length;
            for (int i=0;i<length;i++){
                Container child=c[i];
                System.out.println(child.getName());
            }
            System.in.read();
            ((StandardHost) host).stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
