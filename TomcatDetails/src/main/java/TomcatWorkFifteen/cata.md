# Tomcat启动
- Catalina类
>用于启动或关闭Server对象
>含有Digester对象解析server.xml
>含有Service对象
>调用process（）方法运行Tomcat
- Bootstrap类
>启动Tomcat入口点
>运行startup.bat/.sh文件运行了此类的main（）方法
>负责实例化Catalina类，并调用其process（）方法
>创建了三个类载入器，防止应用程序使用WEB-INF/classes和WEB-INF/lib目录之外的类：
>>commonLoader类载入：%CATALINA_HOME%/common/classes目录和%CATALINA_HOME%/common/endorsed目录和%CATALINA_HOME%/common/lib目录下的java类

>>CatalinaLoader类载入servlet容器所需的类，可载入%CATALINA_HOME%/server/classes目录和%CATALINA_HOME%/server/lib目录。

>>sharedLoader类载入%CATALINA_HOME%/shared/classes目录和%CATALINA_HOME%/shared/lib目录.
- DOS脚本命令
>rem:注释
>pause:暂停正在执行的批处理文件
>echo:在DOS控制台输出
>echo off和@echo off:防止把批处理文件命令输出
>set:设置用户定义或命名的环境变量（获取变量方式%var%）
>label:使用冒号设置一个标签，goto提供语句跳转
>goto
>if:测试变量值，文件是否存在，错误值。
>not:语句取反（if not）
>exist:测试文件是否存在（if exist）
>接收参数：%1 %2 %3...(接收第一个，第二个，第三个。。。参数)
>shift:参数后移一位
>start:开启新的控制台
#部署器
- 默认程序部署目录：%CATALINA_HOME%/webapps
- Deploy接口
>部署器实现此接口（StandardHost实现了此接口）
- StandardHostDeployer类
>辅助类，完成web应用程序部署到StandardHost实例的工作。
>install(URL config,URL war):安装一个描述符
>install(String contextPath,URL war):安装一个war文件
>start（）：启动Context实例
>stop():停止Context实例
#Manager应用程序
>默认部署目录：%CATALINA_HOME%/server/webapps
>使用部署文件manager.xml完成部署
- Containerservlet接口
>实现该接口的servlet实例可访问表示自身的wrapper对象,可以访问到catalina内部的类
>getWrapper();
>setWrapper();
#JMX的管理
