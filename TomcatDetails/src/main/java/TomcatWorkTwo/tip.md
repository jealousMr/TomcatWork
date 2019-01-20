###程序2，模拟request，response请求静态资源文件和servlet
##结构：
+ HttpServer1.java
+ Request.java
+ Response.java
+ ServletProcessor1.java
+ StaticResourceProcessor.java

##请求结构：
- HttpServer1作为程序入口，在await函数中使用socke循环监听请求，封装response和request对象。
- Request类实现ServletRequest接口，负责解析uri，提供getURI函数。
- Response类实现ServletResponse接口，内部编写了一个sendStaticResource函数用于发送静态资源文件，
- ServletProcessor1 类的process函数以response，request为参数，调用request之前解析得到的uri，借助类加载器加载相应的类。
- StaticResourceProcessor类负责调用response对象的sendStaticResource函数处理静态资源文件。
