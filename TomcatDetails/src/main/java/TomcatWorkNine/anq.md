#Tomact安全性
##领域：
- 对用户进行身份验证的组件
一个领域对象关联一个Context对象（使用setRealm函数）
Tomcat中用户的信息存储在tomcat-user.xml文件中
- 领域对象实现领域接口Realm
- Tomcat中的LoginConfig类：
封装了领域对象和所要使用的身份验证方法。
getAuthName()方法返回身份验证方法的名字（BASIC,DIGEST,FORM,CLIENT-CERT）。
Tomcat启动时通过web.xml文件中login-config元素进行配置时则会创建一个LoginConfig对象
- Authenticator接口：可用于检测是否是验证器实例，一个Context实例只能有一个验证器。tomcat在实现此类的过程中将其作为一个阀（Valve）实例
主要实现了以下几个阀：
>BasicAuthenticator类：支持基本的身份验证
>FormAuthenticator类：基于表单的身份验证
>DigestAuthentication类：基于信息摘要的身份验证
>SSLAuthenticator类：对SSL进行身份验证
>NonLoginAuthenticator类：对来访者的身份进行验证（用户未指定验证方法时调用）
- 安装验证器阀：
web.xml文件中login-config只能出现一次，其包含一个auth-method元素来指定身份验证方法；
auth-method元素的值：BASIC,DIGEST,FORM,CLIENT-CERT
对应验证器类：BasicAuthenticator......以上描述过。
默认为NONE,对应NonLoginAuthenticator。
##程序实例：
- 程序架构
>Bootstrap1,Bootstrap2两个测试类.
>SimpleWrapper类：wrapper容器的实例.
>SimpleWrapperValve类：此处用作SimpleWrapper的基础阀值实例
>SimplePipeline:管道的实例
>SimpleContextConfig类：实现LifecycleListener接口，间接当作验证器实例使用
>SimpleRealm类
>SimpleUserDatabaseRealm类
- Bootstrap1和Bootstrap2：
程序启动入口，前者使用了SimpleRealm类实例，后者使用SimpleUserDatabaseRealm实例。
启动过程：在设置catalina工作目录后创建连接器实例connector，创建两个Wrapper容器并进行填充，创建StandardContex容器实例。创建SimpleContextConfig对象，此对象为一个监听器，同时也作为一个Authenticator验证器使用，在添加到context的同时也为context间接添加了一个监听器。之后为context添加wrapper实例。创建SecurityCollection对象并填充，创建SecurityConstraint对象填充。最后创建LoginConfig和Realm的实例送给context，为connecotr添加Context实例，启动。。
>SecurityCollection实例重要方法
>>addPattern():指定某个URL要遵循的安全限制
>>addMethod():指定改安全限制要使用哪种验证方法

>SecurityConstraint实例重要方法
>>addCollection():添加SecurityCollection实例
>>addAuthRole():设置可以访问的角色
- SimplePipline,SimpleWrapper,SimpleWrapperValve类的实现和之前一样。
- SimpleContextConfig类:
此类实现了LifecycleListener接口，在实现lifecycleEvent（）方法时可以实例化Authenticator(验证器)，间接添加给context容器。
实例化过程：1.检测context容器是否有安全限制。2.检测Context容器是否有LoginConfig对象。3.检测context容器的基础阀/添加阀是否已经是验证器（借助Authenticator接口进行比对）。4.查找context关联的领域对象，动态载入BasicAuthenticator类添加到context容器的附加阀值数组中。
- SimpleRealm类:
实现Realm接口
- SimpleUserDatabaseRealm类：
继承了RealmBase类的实例，其读取了tomcat-users.xml文件。
