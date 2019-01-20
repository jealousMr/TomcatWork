#Digester
- Digester类中重要方法：
>addObjectCreate(String pattern,Class clazz):依据某种模式创建对象。
>addObjectCreate(String pattern,String attributeName,Class clazz):使用在xml中定义的类名。
>addSetProperties(String pattern):传入模式来设置属性
>addCallMethod(String pattern,String methodName):调用内部栈最顶层对象的某个方法。
>addSetNext(String pattern,String methodName):关联两个对象。
>setValidating(boolean validating):是否通过某种模式验证xml。默认false。
>addRuleSet(RuleSet ruleSet):向其Digester实例添加Rule对象。
>addRule(String pattern,Rule rule):在调用以上方法时都会通过调用addRule方法间接调用Rule类

- Rule类：
>begin():当Digester实例遇到xml标签开始时调用
>end()：当Digester实例遇到xml标签结束时调用
- RuleSet接口(完成把Rule对象添加到Digester实例,其中有一个实现的基类RuleSetBase可方便使用)
>addRuleInstance(Digester digester):将当前RuleSet集合中的Rule添加到Digester实例。
>getNamespaceURI():返回应用在RuleSet中所有Rule对象的命名空间的URI
- Tomcat中ContextConfig类：
1.读取解析默认的/自定义的web.xml文件。  2.为每个Servlet元素创建一个ServletWrapper实例。
重要方法：
>defaultConfig（）：负责解析位于%CATALINA_HOME%/conf目录下的默认web.xml文件
>applicationConfig():处理位于WEB-INF中自定义的部署描述.
