package TomcatWorkEleven;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.loader.WebappLoader;

public final class Bootstrap {
    public static void main(String[]args){
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector=new HttpConnector();
        Wrapper wrapper1=new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2=new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");
        Context context=new StandardContext();
        context.setPath("/WEB-INF");
        context.setDocBase("WEB-INF");
        context.addChild(wrapper1);
        context.addChild(wrapper2);
        LifecycleListener listener=new SimpleContextConfig();
        ((Lifecycle)context).addLifecycleListener(listener);

        Host host=new StandardHost();
        host.addChild(context);
        host.setName("localhost");
        host.setAppBase("webroot");
        Loader loader=new WebappLoader();
        context.setLoader(loader);
        context.addServletMapping("/Primitive","Primitive");
        context.addServletMapping("/Modern","Modern");
        connector.setContainer(host);
        try {
            connector.initialize();
            ((Lifecycle)host).start();
            System.in.read();
            ((Lifecycle)host).stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
