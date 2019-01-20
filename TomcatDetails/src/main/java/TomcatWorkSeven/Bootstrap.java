package TomcatWorkSeven;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.loader.WebappLoader;

public final class Bootstrap {
    public static void main(String[]args){
        //  为了通知StandardContext 实例到哪里查找应用程序目录，需要设置一个名为"catalina.base"的系统属性，其值为"user.dir"属性的值；
        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector=new HttpConnector();
        Wrapper wrapper1=new SimpleWrapper();
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        // 创建StandardContext 的一个实例，设置应用程序路径和上下文的文档根路径
        // 代码在功能上等同于下面的在server.xml 文件中的配置
        // <Context path="/myApp" docBase="myApp" />
        Context context=new StandardContext();
        context.setPath("/myApp");
        context.setDocBase("myApp");
        context.addChild(wrapper1);
        context.addChild(wrapper2);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");
        LifecycleListener listener=new SimpleContextConfig();
        ((Lifecycle)context).addLifecycleListener(listener);

        Loader loader=new WebappLoader();
        context.setLoader(loader);
        connector.setContainer(context);
        try {
            connector.initialize();
            ((Lifecycle)connector).start();
            ((Lifecycle)context).start();
            WebappClassLoader classLoader= (WebappClassLoader) loader.getClassLoader();
            String[] repositories=classLoader.findRepositories();
            for (int i=0;i<repositories.length;i++)
                System.out.println(" responsitory: "+repositories[i]);
            System.in.read();
            ((Lifecycle) context).stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
