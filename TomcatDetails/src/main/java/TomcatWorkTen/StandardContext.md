#StandardContext
- StandardContext类：
两个检测属性：
1.configured:表明StandardContext实例是否正确配置。
2.available:StandardContext对象设置正确且关联的子容器组件启动成功。
3 reloadable:指明应用程序是否启动了重载功能（如果启动，web.xml文件变化或WEB-INF/classes目录下的文件被重新编译时应用程序将重载）
####重要方法：
>构造函数：为管道添加基础阀实例StandardContextValve

>start()方法：
>>触发BEFORE_START事件
>>设置configured和available为false
>>配置资源
>>设置载入器loader
>>设置session管理器
>>初始化字符集映射器
>>启动与Context关联的组件（Lifecycle接口start）
>>启动子容器
>>启动管道对象
>>启动Session管理器
>>触发START事件，修改configured=true；
>>检测configured，载入Wrapper实例，修改available=true
>>触发AFTER_START事件

>invoke()方法：
调用者：与其关联的连接器或Host容器的invoke方法
等待程序重载完成后调用父类ContainerBase的invoke方法
- StandardContextMapper类
作用：在基础阀中寻找合适的Wrapper实例
重要方法：
> setContainer(Container):StandardContextMapper实例与一容器关联，且只能与Context级别容器关联

> map(Request,bool):返回用来处理HTTP请求的子容器即返回Wrapper容器实例，StandardContextValve实例通过此方法找到特定的Wrapper实例
> 在Tomcat5中可直接通过Request实例获得Wrapper实例。

- StandardContext对重载的支持：
