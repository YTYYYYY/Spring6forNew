# Spring6 框架





#### 1.Spring的核心模块：Ioc和AOP

Ioc (Inverse of Control)：控制反转，指把创建对象的过程交给Spring来管理

AOP (Aspect Oriented Programming)：面向切面编程。AOP用来封装多个类的公共行为，将那些与和业务无关，却为业务模块所共同调用的逻辑封装起来，减少系统的重复代码，降低模块间的耦合度。另外，AOP还解决一些系统层面上的问题，比如日志事务权限等。





#### 2.入门案例开发

> ​	**2.1.引入Spring相关依赖**
>
> ~~~
> <!--Spring的基础依赖-->
>  <dependency>
>      <groupId>org.springframework</groupId>
>      <artifactId>spring-context</artifactId>
>      <version>6.0.2</version>
>  </dependency>
> ~~~
>
> ​	**2.2.创建类，定义属性和方法**
>
> ~~~
> public class User {
>  public void add(){
>      System.out.println("add...");
>  }
>  public static void main(String[] args) {
>      User user = new User();
>      user.add();
>  }
> }
> ~~~
>
> ​	**2.3.按照Spring要求配置文件（xml）**
>
> ~~~
> <!--完成User对象的创建
>          bean标签
>              id属性：唯一标识
>              class属性：要创建对象所在类的全路径
> -->
> <bean id="user" class="com.xxx.User"></bean>
> ~~~
>
> ​	**2.4.在Spring配置文件配置相关信息**
>
> ​	**2.5.测试**
>
> ~~~
> @Test
> public void te1(){
>  //加载Spring配置文件，对象创建
>  ApplicationContext context =
>  new ClassPathXmlApplicationContext("bean.xml");
>  //获取创建的对象
>  User user = (User)context.getBean("user");
>  System.out.println(user);
>  //使用对象调用方法进行测试
>  user.add();
> }
> ~~~



**如何使用反射返回创建的对象？**

1.加载 bean.xml 配置文件

2.对xml文件进行解析操作

3.获取xml文件bean标签的属性值（id属性值和class属性值）

4.使用反射根据类全路径创建对象

~~~
//反射创建对象
@Test
public void testUserObject() throws Exception {
    //获取类class对象
    Class<?> cla = Class.forName("com.SpringforNew.User");
    //调用方法创建对象
    //Object o = cla.newInstance();
    User user = (User)cla.getDeclaredConstructor().newInstance();
    System.out.println(user);
}
~~~

**创建的对象放在哪里？**

`Map<String, BeanDefinition> beanDefinitionMap`

>key：唯一标识
>
>value：类的定义（描述信息）



>**2.6.整合 Log4j2 日志框架**
>
>1.引入依赖
>
>~~~
><!--log4j2-->
><dependency>
>   <groupId>org.apache.logging.log4j</groupId>
>   <artifactId>log4j-core</artifactId>
>   <version>2.19.0</version>
></dependency>
><dependency>
>   <groupId>org.apache.logging.log4j</groupId>
>   <artifactId>log4j-slf4j-impl</artifactId>
>   <version>2.19.0</version>
></dependency>
>~~~
>
>2.在根目录加入日志配置文件 `log4j2.xml`
>
>~~~
><?xml version="1.0" encoding="UTF-8"?>
><Configuration status="WARN">
><Loggers>
>   <!--
>       level指定日志级别，从低到高的优先级：
>           TRACE < DEBUG < INFO < WARN < ERROR < FATAL
>           trace：追踪，是最低级的日志级别，相当于追踪程序的执行
>           debug：调试，一般在开发中，都将其设置最低
>           info：信息，输出重要的信息，使用较多
>           warn：警告，输出警告的信息
>           error：错误，输出错误信息
>           fatal：严重错误
>   -->
>   <!-- Root logger -->
>   <Root level="DEBUG">
>       <AppenderRef ref="SizeBasedRollingFileAppender" />
>       <AppenderRef ref="ConsoleAppender" />
>   </Root>
></Loggers>
>
><Appenders>
>   <!-- 日志文件输出 -->
>   <!-- Size-based rolling policy -->
>   <RollingFile name="SizeBasedRollingFileAppender" fileName="logs/app.log" filePattern="logs/app-%d{yyyy-MM-dd}-%i.log" >
>       <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
>       <PatternLayout>
>           <pattern>%d %p %c{1.} [%t] %m%n</pattern>
>       </PatternLayout>
>       <Policies>
>           <SizeBasedTriggeringPolicy size="10 MB"/>
>       </Policies>
>       <DefaultRolloverStrategy max="10"/>
>   </RollingFile>
>
>   <!-- 控制台日志输出 -->
>   <!-- Console appender -->
>   <Console name="ConsoleAppender" target="SYSTEM_OUT">
>       <PatternLayout>
>           <pattern>%d %p %c{1.} [%t] %m%n</pattern>
>       </PatternLayout>
>   </Console>
>
></Appenders>
>
></Configuration>
>~~~
>
>或，手动配置 Logger
>
>~~~
>import org.slf4j.Logger;
>import org.slf4j.LoggerFactory;
>~~~
>
>~~~
>private Logger logger = LoggerFactory.getLogger(xxx.class);
>~~~
>
>~~~
>logger.info("info..");
>//logger.debug("debug..")
>~~~





#### 3.Ioc 容器



3.1. **控制反转 ( Ioc )**

> Spring 通过Ioc容器来管理
>
> ① 所有 Java 对象的实例化和初始化
>
> ② 控制对象和对象之间的依赖关系
>
> 我们将 Ioc 容器管理的 Java 对象成为 Spring Bean , 它与使用 new 关键字创建的 Java 对象没有区别



3.2 **依赖注入**

获取bean对象：

~~~
//根据id获取：
User user = (User)context.getBean("user");
//根据类型获取：（根据类型获取bean的时候，要求Ioc容器中指定类型的bean只能有一个）
User user = context.getBean(User.class);
//根据id和类型获取：
User user = context.getBean("user", User.class);
~~~

依赖注入：

>1.生成属性set方法
>
>2.在spring配置文件配置
>
>
>
>① set 注入
>
>~~~
><bean id="book" class="com.SpringforNew.bi.Book">
><property name="name" value="xxx"></property>
><property name="author" value="xxxx"></property>
></bean>
>~~~
>
>
>
>② 构造注入
>
>~~~
><bean id="bookAn" class="com.SpringforNew.bi.Book">
><constructor-arg name="name" value="xxx"></constructor-arg>
><constructor-arg name="author" value="xxxx"></constructor-arg>
></bean>
>~~~
>
>___
>
>##### ***** *特殊值处理*
>
>* 字面量赋值
>
>~~~
><property name="name" value="xxx"></property>
>~~~
>
>* null值
>
>~~~
><property name="name">
>	<null/>
></property>
>~~~
>
>~~~
><!-- 注意，以下赋值为字符串“null” -->
><property name="name" value="null"></property>
>~~~
>
>* xml实体
>
>~~~
><!-- 小于号大于号在xml中用于标签，不能随便使用 -->
><!-- 解决方案一：使用xml实体来代替 -->
><!-- 		   &lt;(小于)  	 &gt;(大于) -->
><!-- 		   &le;(小于等于)	&ge;(大于等于) -->
><property name="expression" value="a &lt; b"></property>
>~~~
>
>* CDATA节
>
>~~~
><!-- 解决方案二：使用CDATA节 -->
><property name="expression">
>	<value><![CDATA[a < b]]></value>
></property>
>~~~
>
>
>
>③ 为对象型属性赋值
>
>* 引用外部bean
>
>~~~
><!-- 引入外部bean
>       1.创建两个类对象
>       2.在emp的bean标签里面用property引入dept的bean
>-->
>   <bean id="dept" class="com.SpringforNew.bitest.Dept">
>       <property name="dname" value="HR部门"></property>
>   </bean>
>
>   <bean id="emp" class="com.SpringforNew.bitest.Emp">
>           <!-- 注入对象属性 -->
>       <property name="dept" ref="dept"></property>
>           <!-- 普通属性注入 -->
>       <property name="ename" value="YTY"></property>
>       <property name="age" value="20"></property>
>   </bean>
>~~~
>
>* 内部bean
>
>~~~
><!-- 内部bean -->
>   <bean id="emp" class="com.SpringforNew.bitest.Emp">
>      		<!-- 普通属性注入 -->
>       <property name="ename" value="YTY"></property>
>       <property name="age" value="20"></property>
>       	<!-- 内部bean -->
>       <property name="dept">
>           <bean id="deptInner" class="com.SpringforNew.bitest.Dept">
>               <property name="dname" value="安保部门"></property>
>           </bean>
>       </property>
>   </bean>
>~~~
>
>* 级联属性赋值
>
>~~~
><!-- 级联赋值 -->
>   <bean id="deptTmp" class="com.SpringforNew.bitest.Dept"></bean>
>   <bean id="emp" class="com.SpringforNew.bitest.Emp">
>       	<!-- 普通属性注入 -->
>       <property name="ename" value="YTY"></property>
>       <property name="age" value="20"></property>
>       	<!-- 级联赋值 -->
>       <property name="dept" ref="deptTmp"></property>
>       <property name="dept.dname" value="财务部"></property>
>   </bean>
>~~~
>
>
>
>④ 为数组类型赋值
>
>~~~
><!-- 数组赋值 -->
>   <bean id="emp" class="com.SpringforNew.bitest.Emp">
>       <property name="ename" value="YTY"></property>
>       <property name="age" value="21"></property>
>       	<!-- 数组类型赋值 -->
>       <property name="hobbies">
>           <array>
>               <value>吃饭</value>
>               <value>睡觉</value>
>               <value>代码</value>
>           </array>
>       </property>
>   </bean>
>~~~
>
>
>
>⑤ 为集合类型赋值
>
>~~~
><!-- 集合赋值 -->
>   <bean id="emp1" class="com.SpringforNew.bitest.Emp"><property name="ename" value="张三"></property></bean>
>   <bean id="emp2" class="com.SpringforNew.bitest.Emp"><property name="ename" value="李四"></property></bean>
>   <bean id="dept" class="com.SpringforNew.bitest.Dept">
>       <property name="dname" value="财务部"></property>
>       <!-- 集合赋值 -->
>       <property name="empList">
>           <list>
>               <ref bean="emp1"></ref>
>               <ref bean="emp2"></ref>
>           </list>
>       </property>
>   </bean>
>~~~
>
>
>
>⑥ 为map类型赋值
>
>~~~
><bean id="stu" class="com.SpringforNew.bimap.Student">
>       <property name="sid" value="1"></property>
>       <property name="sname" value="YTY"></property>
>       <property name="teacherMap">
>           <map>
>               <entry>
>                   <key><value>老师1</value></key>
>                   <ref bean="teacher1"></ref>
>               </entry>
>               <entry>
>                   <key><value>老师2</value></key>
>                   <ref bean="teacher2"></ref>
>               </entry>
>           </map>
>       </property>
>   </bean>
>~~~
>
>
>
>* 引用集合类型的bean
>
>~~~
>
>~~~
>
>

>



