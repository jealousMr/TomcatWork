#服务器组件和服务组件
##服务器组件
- Server接口的实例，以优雅方式启动或关闭整个系统（无需在对连接器和容器分别启动）；
>启动/关闭原理：启动服务器组件--->启动所有组件--->等待关闭命令--->关闭所有组件。
>服务器组件使用服务组件来包含其他组件
- Server接口：
重要方法：
>get/setPort():定义服务器组件从哪个端口获取关闭命令

>get/setShutdown():设置shutdown属性，保存Server实例用来关闭整个系统的关闭命令。

>addService():为服务器组件添加服务组件

>removeService():删除某个服务组件

>findServices():返回服务器组件中的所有服务组件

>initialize():系统启动前执行一些操作
- StandardServer类：
有关关闭机制的重要方法：
>initialize():初始化添加到其中的服务器组件（此处应为各个组件实现了Lifecycle接口，可之间调用其initialize方法进行初始化）.

>start()方法：启动所有服务组件，同理调用各组件的start方法即可启动

>stop()方法:关闭服务器组件

>await()方法：负责等待关闭整个Tomcat部署命令，此方法会一直阻塞，直到从指定端口（默认8085）接收关闭指令，调用stop（）；
##服务组件
- 服务组件是Service接口实例，一个服务组件可有一个servlet容器和多个连接器实例。
- Service接口：
重要方法：
>get/setContainer():设置与其关联的servlet容器

>get/setServer():设置关联的服务器组件

>addConnector():添加连接器，可添加多个

>findConnectors():返回与其关联的所有连接器实例

>removeConnector():删除某个连接器

>initialize():启动前的工作
- StandardService类：
重要方法及成员：
>initialize():初始化添加到其中的所有连接器，调用服务组件中所有连接器的initialize方法。

>成员container 和 connectors[]：含有两种组件：一个Servlet容器和多个连接器（让Tomcat可处理不同协议请求如HTTP/HTTPS）

>setContainer():servlet容器与服务组件关联，同时和每个连接器关联

>addConnector():添加连接器到服务组件中，所添加的connector在此会被初始化和启动

>removeConnector():找到要移除的connector，先停止其服务在进行删除

>start():启动添加到服务组件中的所有连接器和servlet容器，首先启动Servlet容器，在启动所有连接器。

>stop():关闭服务组件关联的servlet容器和所有连接器，首先关闭所有连接器，在关闭Servlet容器。
