package TomcatWorkTwelve;

import TomcatWorkEight.SimpleWrapper;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.*;
import org.apache.catalina.loader.WebappLoader;

public final class Bootstrap {
    public static void main(String[]args){
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector=new HttpConnector();
        Wrapper wrapper1=new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2=new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");
        Context context=new StandardContext();
        context.setPath("/app1");
        context.setDocBase("app1");
        context.addChild(wrapper1);
        context.addChild(wrapper2);
        LifecycleListener listener=new SimpleContextConfig();
        ((Lifecycle)context).addLifecycleListener(listener);
        Host host=new StandardHost();
        host.addChild(context);
        host.setName("localhost");
        host.setAppBase("webapps");
        Loader loader=new WebappLoader();
        context.setLoader(loader);
        context.addServletMapping("/Primitive","Primitive");
        context.addServletMapping("/Modern","Modern");
        Engine engine=new StandardEngine();
        engine.addChild(host);
        engine.setDefaultHost("localhost");

        Service service=new StandardService();
        service.setName("one Services");
        Server server=new StandardServer();
        server.addService(service);
        service.addConnector(connector);
        service.setContainer(engine);
        if(server instanceof Lifecycle){
            try {
                server.initialize();
                ((Lifecycle) server).start();
                server.await();//等待关闭指令
            }catch (LifecycleException e){
                e.printStackTrace();
            }
        }
        if(server instanceof Lifecycle){
            try {
                ((Lifecycle)server).stop();
            }catch (LifecycleException e){
                e.printStackTrace();
            }
        }
    }
}
