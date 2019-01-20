#Servlet容器
- Engine,Host,Context,Wrapper
>Engine:整个Catalina servlet引擎
>Host：包含一个或多个Context容器的虚拟主机
>Wrapper:一个独立的servlet
- Container接口重要方法
>addChild():添加子容器
>removeChild():移除某一子容器
>findChild()/findChildren:获得子容器
- 管道任务
>管道：包含servlet容器将调用的任务，由添加阀和基础阀构成，阀值得执行顺序先执行添加阀在执行基础阀。

>Pipeline接口
>>invoke():调用管道中的阀和基础阀
>>addValve():添加阀值
>>get/setBasic():基础阀
>>...

>Valve接口
>>getInfo():阀值信息
>>invoke()

>Contained接口：实例可通过方法至多与一个servlet容器关联
>>set/getContainer()

>ValveContext接口:实例可以访问管道的所有成员
>>getInfo()
>>invokeNext()

### Wrapper应用程序
程序架构：
>Bootstrap:程序启动类
>SimpleWrapper类：Wrapper实例
>SimplePipeline类：Pipeline的实例
>SimpleLoader:Loader实例，类的加载器
>SimpleWrapperValve：基础阀实例
>ClientlPLoggerValve,HeaderLoggerValve:添加的阀值实例，实现Valve, Contained接口
- SimpleLoader类
完成类的载入工作，放回ClassLoader对象
- SimplePipeline类:
管道的实例；
类中的成员：
>Valve basic:基础阀。
>Container container:关联管道的容器。
>Valve valves[]:所要添加的额外阀。

>内部类SimplePipelineValueContext
>>实现ValveContext接口的invokeNext（）方法，保证管道在调用invoke（）方法时可以完成添加阀和额外阀的全部遍历及处理。
- SimpleWrapperValue类：
实现Valve, Contained两个接口，作为管道的基础阀值实例。其中的invoke（）方法会直接调用其Wrapper实例的allocate（）方法获得servlet实例，执行service。
- SimpleWrapper类：
类中成员：
>Servlet instance(Servlet实例)
>String servletClass
>Loader loader
>String name
>SimplePipeline pipeline(为Wrapper容器添加的管道实例)
>Container parent(父容器)

    重要方法：
> SimpleWrapper():构造函数，为自己的管道实例创建一个SimpleWrapperValue基础阀实例。

>invoke():调用管道的invoke

>allocate()和load（）方法：通过类加载器实例（此处使用SimpleLoader）加载servlet实例。
- Bootstrap:
首先创建连接器实例，创建一个Wrapper实例并进行填充，此处填充了类的加载器实例，额外阀值类的实例，最后将wrapper实例放入连接器中。

###Context应用程序
- 映射器（寻找wrapper）Mapper接口：
>get/setContainer():与映射器关联的容器
>get/setProtocol()：映射器处理的协议
>map()：返回要处理某个特定请求的子容器实例

- 应用程序架构
>Bootstrap
>SimpleContext:Context实例
>SimpleContextMapper:Mapper实例
>SimpleContextValve:基础阀实例
>Wrapper应用程序的类
- SimpleContextMapper类：
实现Mapper接口，setContainer（）方法关联一个Context容器，map（）方法放回特定的wrapper实例
- SimpleContextValve类：
实现Valve, Contained，Context容器的基础阀值类，invoke方法中通过获得所需wrapper实例，调用wrapper的invoke方法进入下一层去处理请求。
- SimpleContext类：
主要成员：
>HashMap children
>Loader loader
> SimplePipeline pipeline
> HashMap servletMappings(wrapper实例名之间的映射区分)
> Mapper mapper（映射器实例）
>HashMap mappers
>Container parent
主要方法（和SimpleWrapper实例区别不大）
- Bootstrap
创建连接器，实例化两个wrapper容器，创建一个context实例，为context的管道添加两个额外阀，创建一个映射器和加载器给context实例，添加wrapper映射模式，最后context和连接器关联。
