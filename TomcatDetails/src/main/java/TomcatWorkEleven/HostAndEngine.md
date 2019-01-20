#Host and Engine Container
- Host接口：
继承Container接口，map()方法返回处理HTTP请求的Context容器实例。
- StandardHost类
>继承ContainerBase类，实现Deployer接口

>构造函数：为管道添加一个基础阀StandardHostValve实例

>start()方法：
>>添加两个额外阀ErrorReportValve实例和ErrorDispatcherValve实例

>invoke()方法：未实现，Http请求来时直接使用父类的invoke，同时ContainerBase类的invoke使用了Host基础阀的invoke。

>map()方法:找到适合的 Context
- StandardHostValve类
>invoke():获取StandardHost实例，调用其方法map（）获得合适的Context实例,更新Session（访问时间），调用context的invoke传入下一层处理。
- Engine接口：
继承Container类，一个Engine容器可以与一个服务实例关联。
- StandardEngine类：
实现Engine接口，重要方法实现：
>构造函数：为其管道实例添加一个基础阀实例
>addChild():仅可以添加Host实例
>setParent():顶级容器不可以设置父容器。
- StandardEngineValve类：
>invoke()方法：验证request和response对象类型后调用StandardEngine实例的map（）方法找到适合的Host实例，再调用host的invoke（）方法进入下一层处理。
