#StandardWrapper
- 方法调用序列：
>Connector对象
>>StandardContext实例--->invoke()
>>>StandardContextPipeline实例--->invoke()
>>>>StandardContextValve实例--->invoke()
>>>>>StandardWrapper实例--->invoke()
>>>>>>StandardWrapperValve实例---》allocate(),service()
>>>>>>>Servlet实例--->load(),init();
- SingleThreadModel接口
Servlet类实现此接口保证其实例一次只处理一个请求，此接口不能防止servlet访问的共享资源造成的同步问题。
- StandardWrapper任务：
载入其所代表的servlet类，实例化。
此类不调用servlet的service（），由其管道中的基础阀进行调用，同时其基础阀的实例通过allocate（）从StandardWrapper获得servlet实例。StandardWrapper在载入servlet时需要考虑servlet是否实现SingleThreadModel接口，若没有实现才会载入一次servlet实例，否则需要进行线程同步。
- ServletConfig对象
StandardWrapper载入servlet后调用init（）时需要传入一个ServletConfig对象，同时StandardWrapper实现了ServletConfig接口。
重要方法
>getServletContext():调用父容器（Context）的getServletContext（）；
>getServletName():调用了父类的getName();
>getInitParameter()
>getInitParameterNames()
- StandardWrapperFacade类
作用：在调用servlet的init（）方法时代替StandardWrapper作为ServletConfig实例传入，实现隐藏StandardWrapper效果。
结构：StandardWrapperFacade和StandardWrapper都实现了ServletConfig接口，在实现StandardWrapperFacade时需要有一个StandardWrapper进行包装。
- StandardWrapperValve类
作用：作为Wrapper实例基础阀，执行与该servlet实例关联的全部过滤器，调用servlet的service（）方法。
invoke()方法执行内容：
>通过StandardWrapper实例的allocate（）方法获取servlet实例；
>>使用方法createFilterChain()创建过滤器链
>>>调用过滤器链的doFilter（），释放过滤器
>>>>调用Wrapper实例deallocate（）
###Servlet过滤器实现
- FilterDef类：表示了一个过滤器的定义
类成员：
>String description,displayName,filterClass,filterName,largeIcon,smallIcon

>Map parameters:存储了初始化过滤器时的全部参数。
- ApplicationFilterConfig类
实现FilterConfig接口，管理web程序第一次启动时创建的所有过滤器实例，可以使用一个Context对象和一个FilterDef对象构造ApplicationFilterConfig实例。
重要方法：getFilter():实例化并返回Filter对象
- ApplicationFilterChain类
实现FilterChain接口
doFilter(request,responses,chain)方法：chain把自己传入调用doFilter（）在进行调用另一个过滤器，若为最后一个则直接会调用serlvet类的service（）。
##StandardWrapper应用程序：
程序架构：
>Bootstrap:(使用StandardWrapper作为Wrapper的实例)
>SimpleContextConfig
