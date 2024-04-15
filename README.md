# Spring6 框架





### 1.Spring的核心模块：Ioc和AOP

Ioc (Inverse of Control)：控制反转，指把创建对象的过程交给Spring来管理

AOP (Aspect Oriented Programming)：面向切面编程。AOP用来封装多个类的公共行为，将那些与和业务无关，却为业务模块所共同调用的逻辑封装起来，减少系统的重复代码，降低模块间的耦合度。另外，AOP还解决一些系统层面上的问题，比如日志事务权限等。





### 2.入门案例开发

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



#### **如何使用反射返回创建的对象？**

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





### 3.Ioc 容器



#### 3.1. **控制反转 ( Ioc )**

> Spring 通过Ioc容器来管理
>
> ① 所有 Java 对象的实例化和初始化
>
> ② 控制对象和对象之间的依赖关系
>
> 我们将 Ioc 容器管理的 Java 对象成为 Spring Bean , 它与使用 new 关键字创建的 Java 对象没有区别



#### 3.2 **基于xml管理bean**

##### 1.获取bean对象：

~~~
//根据id获取：
User user = (User)context.getBean("user");
//根据类型获取：（根据类型获取bean的时候，要求Ioc容器中指定类型的bean只能有一个）
User user = context.getBean(User.class);
//根据id和类型获取：
User user = context.getBean("user", User.class);
~~~

##### 2.依赖注入：

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
>      1.创建两个类对象
>      2.在emp的bean标签里面用property引入dept的bean
>-->
>  <bean id="dept" class="com.SpringforNew.bitest.Dept">
>      <property name="dname" value="HR部门"></property>
>  </bean>
>
>  <bean id="emp" class="com.SpringforNew.bitest.Emp">
>          <!-- 注入对象属性 -->
>      <property name="dept" ref="dept"></property>
>          <!-- 普通属性注入 -->
>      <property name="ename" value="YTY"></property>
>      <property name="age" value="20"></property>
>  </bean>
>~~~
>
>* 内部bean
>
>~~~
><!-- 内部bean -->
>  <bean id="emp" class="com.SpringforNew.bitest.Emp">
>     		<!-- 普通属性注入 -->
>      <property name="ename" value="YTY"></property>
>      <property name="age" value="20"></property>
>      	<!-- 内部bean -->
>      <property name="dept">
>          <bean id="deptInner" class="com.SpringforNew.bitest.Dept">
>              <property name="dname" value="安保部门"></property>
>          </bean>
>      </property>
>  </bean>
>~~~
>
>* 级联属性赋值
>
>~~~
><!-- 级联赋值 -->
>  <bean id="deptTmp" class="com.SpringforNew.bitest.Dept"></bean>
>  <bean id="emp" class="com.SpringforNew.bitest.Emp">
>      	<!-- 普通属性注入 -->
>      <property name="ename" value="YTY"></property>
>      <property name="age" value="20"></property>
>      	<!-- 级联赋值 -->
>      <property name="dept" ref="deptTmp"></property>
>      <property name="dept.dname" value="财务部"></property>
>  </bean>
>~~~
>
>
>
>④ 为数组类型赋值
>
>~~~
><!-- 数组赋值 -->
>  <bean id="emp" class="com.SpringforNew.bitest.Emp">
>      <property name="ename" value="YTY"></property>
>      <property name="age" value="21"></property>
>      	<!-- 数组类型赋值 -->
>      <property name="hobbies">
>          <array>
>              <value>吃饭</value>
>              <value>睡觉</value>
>              <value>代码</value>
>          </array>
>      </property>
>  </bean>
>~~~
>
>
>
>⑤ 为集合类型赋值
>
>~~~
><!-- 集合赋值 -->
>  <bean id="emp1" class="com.SpringforNew.bitest.Emp"><property name="ename" value="张三"></property></bean>
>  <bean id="emp2" class="com.SpringforNew.bitest.Emp"><property name="ename" value="李四"></property></bean>
>  <bean id="dept" class="com.SpringforNew.bitest.Dept">
>      <property name="dname" value="财务部"></property>
>      <!-- 集合赋值 -->
>      <property name="empList">
>          <list>
>              <ref bean="emp1"></ref>
>              <ref bean="emp2"></ref>
>          </list>
>      </property>
>  </bean>
>~~~
>
>
>
>⑥ 为map类型赋值
>
>~~~
><bean id="stu" class="com.SpringforNew.bimap.Student">
>      <property name="sid" value="1"></property>
>      <property name="sname" value="YTY"></property>
>      <property name="teacherMap">
>          <map>
>              <entry>
>                  <key><value>老师1</value></key>
>                  <ref bean="teacher1"></ref>
>              </entry>
>              <entry>
>                  <key><value>老师2</value></key>
>                  <ref bean="teacher2"></ref>
>              </entry>
>          </map>
>      </property>
>  </bean>
>~~~
>
>
>
>* 引用集合类型的bean
>
>~~~
><!-- 在beans标签中添加属性： -->
>xmlns:util="http://www.springframework.org/schema/util"
>xsi:schemaLocation="http://www.springframework.org/schema/util
>      http://www.springframework.org/schema/util/spring-util.xsd
>      http://www.springframework.org/schema/beans
>      http://www.springframework.org/schema/beans/spring-beans.xsd"
>~~~
>
>~~~
><!-- 使用 util:类型 定义 -->
><!-- 在bean引入 util:类型 定义bean，完成list，map类的注入 -->
>	<util:list id="coulist">
>       <ref bean="course1"></ref>
>       <ref bean="course2"></ref>
>   </util:list>
>   <util:map id="teamap">
>       <entry>
>           <key><value>老师1</value></key>
>           <ref bean="teacher1"></ref>
>       </entry>
>       <entry>
>           <key><value>老师2</value></key>
>           <ref bean="teacher2"></ref>
>       </entry>
>   </util:map>
>
>   <bean id="stu" class="com.SpringforNew.bimap.Student">
>       <property name="sid" value="1"></property>
>       <property name="sname" value="YTY"></property>
>       <property name="teacherMap" ref="teamap"></property>
>       <property name="courseList" ref="coulist"></property>
>   </bean>
>~~~
>
>
>
>⑦ p命名空间注入
>
>~~~
><!-- 在beans标签中添加属性： -->
>xmlns:p="http://www.springframework.org/schema/p"
>~~~
>
>~~~
>	<bean id="stu" class="com.SpringforNew.bimap.Student"
>   p:sid="007" p:sname="YYY" p:courseList-ref="coulist" p:teacherMap-ref="teamap">
>   </bean>
>~~~



##### 3.引入外部属性文件

① 引入依赖

~~~
    <!-- MYSQL驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
    <!--数据源-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.6</version>
        </dependency>
~~~

② 编写配置文件 `jdbc.properties`

~~~
jdbc.user=root
jdbc.password=xxx
jdbc.url=jdbc:mysql://localhost:3306/xxxx?serverTimezero=GMT%2b8
jdbc.driver=com.mysql.cj.jdbc.Driver
~~~

③ 引入context命名空间

~~~
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
~~~

~~~
<!-- 引入外部属性文件 -->
<context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
~~~

④ 配置bean

~~~
    <!--数据库信息注入-->
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.user}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="driverClassName" value="${jdbc.driver}"></property>
    </bean>
~~~



*测试

~~~
	@Test
    public void te2(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-jdbc.xml");
        DruidDataSource dataSource = context.getBean(DruidDataSource.class);
        System.out.println(dataSource.getUrl());
    }
~~~



##### 4.bean的作用域

>在Spring中可以通过配置bean标签的scope属性来指定bean的作用域对象，各取值含义参考如下：
>
>| 取值             | 含义                                  | 创建对象的时机  |
>| ---------------- | ------------------------------------- | --------------- |
>| singleton (默认) | 在IOC容器中，这个bean对象始终为单实例 | IOC容器初始化时 |
>| prototype        | 这个bean在IOC容器中有多个实例         | 获取bean时      |
>
>如果是在 WebApplicationContext 环境下还会有另外几个作用域（不常用）：
>
>| 取值    | 含义                 |
>| ------- | -------------------- |
>| request | 在一个请求范围内有效 |
>| session | 在一个会话范围内有效 |

---

> ①示例
>
> 单实例模式
>
> ~~~
> 	<bean id="orders" class="com.SpringforNew.scope.Orders"
>        scope="singleton">
>  </bean>
> ~~~
>
> ~~~
> @Test
>  public void te(){
>      ApplicationContext context =
>              new ClassPathXmlApplicationContext("bean-scope.xml");
>      Orders orders = context.getBean(Orders.class);
>      System.out.println(orders);		//com.SpringforNew.scope.Orders@45c8d09f
>      Orders orders1 = context.getBean(Orders.class);
>      System.out.println(orders1);	//com.SpringforNew.scope.Orders@45c8d09f
>  }
> ~~~
>
> 
>
> 多实例模式
>
> ~~~
> 	<bean id="orders" class="com.SpringforNew.scope.Orders"
>        scope="prototype">
>  </bean>
> ~~~
>
> ~~~
> 	@Test
>  public void te(){
>      ApplicationContext context =
>              new ClassPathXmlApplicationContext("bean-scope.xml");
>      Orders orders = context.getBean(Orders.class);
>      System.out.println(orders);		//com.SpringforNew.scope.Orders@5af3a0f
>      Orders orders1 = context.getBean(Orders.class);
>      System.out.println(orders1);	//com.SpringforNew.scope.Orders@19ae6bb
>  }
> ~~~



##### 5.bean的生命周期

> ①具体的生命周期过程：
>
> * bean对象创建（调用无参构造）
> * 给bean对象设置属性
> * bean的后置处理器（初始化之前）
> * bean对象初始化（需在配置bean时指定初始化方法）
> * bean的后置处理器（初始化之后）
> * bean对象就绪可以使用
> * bean对象销毁（需要在配置bean时指定销毁方法）
> * IOC容器关闭
>
> ~~~
> public class User {
>     private String name;
> 
> 	public User() { System.out.println("1.无参构造"); }
> 	public void setName(String name) {
>         System.out.println("2.设置属性");
>         this.name = name;
>     }
>     public void initMethod(){ System.out.println("4.对象初始化"); }
>     public void destroyMethod(){ System.out.println("7.对象销毁"); }
> }
> ~~~
>
> ~~~
> public class MyBeanPost implements BeanPostProcessor {
>     @Override
>     public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
>         System.out.println("3.初始化之前,"+beanName+"::"+bean);
>         return bean;
>     }
>     @Override
>     public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
>         System.out.println("5.初始化之后,"+beanName+"::"+bean);
>         return bean;
>     }
> }
> ~~~
>
> ~~~
> <bean id="user" class="com.SpringforNew.beanlife.User"
> scope="singleton" init-method="initMethod" destroy-method="destroyMethod">
>     <property name="name" value="YTY"></property>
> </bean>
> <bean id="myBeanPost" class="com.SpringforNew.beanlife.MyBeanPost"></bean>
> ~~~
>
> ~~~
> //测试
> @Test
> public void te(){
>     ClassPathXmlApplicationContext context =
>     		new ClassPathXmlApplicationContext("bean-life.xml");
>     User user = context.getBean(User.class);
>     System.out.println("6.对象就绪:"+user);
>     context.close();
> }
> ~~~
>
> ~~~
> 1.无参构造
> 2.设置属性
> 3.初始化之前,user::com.SpringforNew.beanlife.User@9573584
> 4.对象初始化
> 5.初始化之后,user::com.SpringforNew.beanlife.User@9573584
> 6.对象就绪:com.SpringforNew.beanlife.User@9573584
> 7.对象销毁
> ~~~



##### 6. FactoryBean

> ```
> public class Myfbean implements FactoryBean<User> {
>  @Override
>  public User getObject() throws Exception {return new User();}
>  @Override
>  public Class<?> getObjectType() {return User.class;}
> }
> ```
>
> ~~~
> <bean id="user" class="com.SpringforNew.myfactorybean.Myfbean"></bean>
> ~~~
>
> ~~~
> @Test
> public void te(){
>  ApplicationContext context =
>  		new ClassPathXmlApplicationContext("bean-factorybean.xml");
>  User user = (User)context.getBean("user");
>  System.out.println(user);
> }
> ~~~



##### 7.基于xml的自动装配

> Controller 层
>
> ~~~
> public class UserController {
> 
>  //UserService userService = new UserServiceImpl();
>  private UserService userService;
>  public void setUserService(UserService userService) {this.userService = userService;}
> 
>  public void addUser(){
>      System.out.println("UserController..");
>      userService.addUserService();
>  }
> }
> ~~~
>
> Service 层
>
> ~~~
> public class UserServiceImpl implements UserService{
> 
>  //UserDao userDao = new UserDaoImpl();
>  private UserDao userDao;
> 	public void setUserDao(UserDao userDao) {this.userDao = userDao;}
> 
>  @Override
>  public void addUserService() {
>      System.out.println("UserService...");
>      userDao.addUserDao();
>  }
> }
> ~~~
>
> Dao 层
>
> ~~~
> public class UserDaoImpl implements UserDao{
>  @Override
>  public void addUserDao() {
>      System.out.println("UserDao...");
>  }
> }
> ~~~
>
> xml 自动装配
>
> ①byType 方式：（确保bean对象唯一）
>
> ~~~
> <bean id="userController" class="com.SpringforNew.autoxml.controller.UserController"
> autowire="byType"></bean>
> 
> <bean id="userService" class="com.SpringforNew.autoxml.service.UserServiceImpl"
> autowire="byType"></bean>
> 
> <bean id="userDao" class="com.SpringforNew.autoxml.dao.UserDaoImpl"></bean>
> ~~~
>
> ②byName 方式：（确保对象名称和bean的id相同）
>
> ~~~
> <bean id="userController" class="com.SpringforNew.autoxml.controller.UserController"
> autowire="byName"></bean>
> 
> <bean id="userService" class="com.SpringforNew.autoxml.service.UserServiceImpl"
> autowire="byName"></bean>
> 
> <bean id="userDao" class="com.SpringforNew.autoxml.dao.UserDaoImpl"></bean>
> ~~~
>
> 测试：
>
> ~~~
> 	@Test
>  public void te2(){
>      ApplicationContext context =
>              new ClassPathXmlApplicationContext("bean-auto.xml");
>      UserController userController = 
>      		context.getBean("userController", UserController.class);
>      userController.addUser();
>  }
> ~~~





#### 3.3. 基于注解管理bean

Spring通过注解自动装配步骤

> 1.引入依赖
>
> 2.开启组件扫描
>
> 3.使用注解定义bean
>
> 4.依赖注入



##### 1.开启组件扫描

> ~~~
> <?xml version="1.0" encoding="UTF-8"?>
> <beans xmlns="http://www.springframework.org/schema/beans"
>     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>     xmlns:context="http://www.springframework.org/schema/context"
>     xsi:schemaLocation="http://www.springframework.org/schema/context
>     http://www.springframework.org/schema/context/spring-context.xsd
>     http://www.springframework.org/schema/beans
>     http://www.springframework.org/schema/beans/spring-beans.xsd">
> 
>  <!-- 开启组件扫描 -->
>  <context:component-scan base-package="com.SpringforNew"></context:component-scan>
> </beans>
> ~~~
>
> 最基本的扫描方式
>
> ~~~
> <!-- 开启组件扫描 -->
> <context:component-scan base-package="com.SpringforNew"></context:component-scan>
> ~~~
>
> 指定要排除的组件
>
> ~~~
> <!-- 指定排除扫描 -->
>  <context:component-scan base-package="com.SpringforNew">
>      <!-- context:exclude-filter 指定排除规则 -->
>      <!--
>          type:设置排除或包含的依据
>          type="annotation":根据注解排除，expression设置要排除的注解的全类名
>          type="assignable":根据类型排除，expression设置要类型的注解的全类名
>      -->
>      <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:exclude-filter>
>      <context:exclude-filter type="assignable" expression="com.SpringforNew.Main"</context:exclude-filter>
>  </context:component-scan>
> ~~~



##### 2.使用注解创建bean

| 注解        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| @Component  | 该注解用于描述Spring中的Bean，他是一个泛指的概念。可以作用在应用的任何层次 |
| @Repository | 该注解用于将数据访问层（Dao层）的类标识为Spring中的Bean，其功能与@Component相同 |
| @Service    | 该注解通常作用在业务层（Service层），用于将业务层中的类标识为Spring中的Bean，其功能与@Component相同 |
| @Controller | 该注解通常作用在控制层（如SpringMVC的Controller层），用于将控制层的类标识为Spring中的Bean，其功能与@Component相同 |



##### 3.@Autowired 注入

默认根据类型注入（byType注入）



##### 4.@Autowired和@Qualifier联合注入

> @Autowired
>
> @Qualifier(value = "   ")



##### 5.@Resource 注入

@Resource是JDK拓展包中的具有通用性，@Autowired是Spring框架自己的。

**@Resource默认根据名称装配byName，未指定name时使用属性名作为name，通过name找不到的话会自动通过类型byType装配。**@Autowired默认通过byType装配，如果想根据名称装配，需要和@Qualifier联合使用。

@Resource注解用在属性上，setter方法上。@Autowired注解用在属性上，setter方法上，构造方法上，构造方法参数上。

@Resource需要引入额外依赖（JDK8不用引入，否则需要引入）

~~~
<dependency>
    <groupId>jakarta.annotation</groupId>
    <artifactId>jakarta.annotation-api</artifactId>
    <version>2.1.1</version>
</dependency>
~~~

> 通过指定名称注入：
>
> ~~~
> @Service("myUserService")
> public class UserServiceImpl implements UserService{}
> ~~~
>
> ~~~
> @Resource(name = "myUserService")
> private UserService userService;
> ~~~
>
> 属性名称注入：
>
> ```
> @Repository("myUserDao")
> public class UserDaoImpl implements UserDao{}
> ```
>
> ~~~
> @Resource
> private UserDao myUserDao;
> ~~~



##### 6.Spring 全注解开发

> 全注解开发就是不再使用spring配置文件了，而是用一个配置类来配置文件

  ~~~
@Configuration  //配置类 来替代xml配置文件
@ComponentScan("com.SpringforNew.anno_resource")
public class SpringConfig { }
  ~~~

~~~
public class TestCon {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController userController = context.getBean(UserController.class);
        userController.addUser();
    }
}
~~~





### 4. Ioc 原理



#### 4.1. Java 反射机制

想要解剖一个类，就必须先要获取到该类的Class对象，Class对象时反射的根源 

~~~
Class cla = Class.forName("com.SpringforNew.Car");
Constructor[] constructors = cla.getDeclaredConstructors();
for(Constructor c : constructors){
	System.out.println(c.getName()+","+c.getParameterCount());
}
//com.SpringforNew.Car,0
//com.SpringforNew.Car,3
~~~

~~~
//有参构造 private
Constructor claDecConstructor
			= cla.getDeclaredConstructor(String.class, String.class, int.class);
claDecConstructor.setAccessible(true);
Car car = (Car)claDecConstructor.newInstance("宝马", "白色", 120000);
~~~

~~~
//获取属性
Field[] declaredFields = cla.getDeclaredFields();
    for(Field f:declaredFields){
    if("name".equals(f.getName())){
        f.setAccessible(true);
        f.set(car,"奥迪");
    }
}
System.out.println(car);    //Car{name='奥迪', color='null', price=0}
~~~

~~~
//获取方法
Method[] declaredMethods = cla.getDeclaredMethods();
for(Method m : declaredMethods){
    if("setName".equals(m.getName())){
    	m.invoke(car,"奥迪");
    }
}
System.out.println(car);    //Car{name='奥迪', color='null', price=0}
~~~



#### 4.2. 手搓实现Spring 的 Ioc

1.创建测试类 Service ，Dao

~~~
//ServiceImpl类
@Bean
public class UserServiceImpl implements UserService {
    @Di
    private UserDao userDao;
    @Override
    public void add() {
        System.out.println("service...");
        userDao.add();
    }
}
~~~

~~~
//DaoImpl类
@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void add() { System.out.println("dao..."); }
}
~~~

2.创建两个注解

> @Bean			创建对象
>
> @Di				 属性注入

~~~
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean { }
~~~

~~~
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Di { }
~~~

3.创建Bean容器接口 ApContext （模拟ApplicationContext）

~~~
public interface ApContext {
    Object getBean(Class clazz);
}
~~~

4.实现Bean容器接口

> ① 返回对象
>
> ② 根据包规则加载 bean

~~~
public class AnnoApContext implements ApContext{

    //创建map集合，存放bean对象
    private Map<Class,Object> beanFac = new HashMap<>();
    private static String rootPath;

    //返回对象
    @Override
    public Object getBean(Class clazz) {return beanFac.get(clazz);}

    //设置包扫描的规则
    public AnnoApContext(String scanPackages) {
        try {
            //1.把.替换成\
            String packagePaths = scanPackages.replaceAll("\\.", "\\\\");
            //2.获取包的绝对路径
            Enumeration<URL> urls = Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResources(packagePaths);
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                rootPath = filePath.substring(0,filePath.length()-packagePaths.length());
                //包扫描
                loadBean(new File(filePath));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        loadDi();
    }

    //包扫描过程
    private void loadBean(File file) throws Exception {
        //1.判断是否是文件夹
        if(file.isDirectory()){
            //2.获取文件夹内所有内容
            File[] childFiles = file.listFiles();
            //3.判断文件夹内是否为空，直接返回
            if(childFiles == null || childFiles.length == 0) return;
            //4.如果文件夹不为空，获取文件内所有数据
            for(File child : childFiles){
                //4.1.遍历得到的每个File对象，如果还是文件夹，递归
                if(child.isDirectory()) loadBean(child);
                else{
                    //4.2.File对象是文件
                    //4.3.得到包路径+类名称部分-字符串截取
                    String pathWithClass = child.getAbsolutePath()
                                        .substring(rootPath.length() - 1);
                    //4.4.判断当前文件类型是否为.class
                    if(pathWithClass.contains(".class")){
                        //4.5.如果是.class类型，把路径\换成.   把.class去掉
                        String fullName = pathWithClass.replaceAll("\\\\", "\\.")
                                                    .replace(".class", "");
                        //4.6.判断类上面是否有注解@Bean，如果有实例化
                        Class<?> cla = Class.forName(fullName);
                        if (! cla.isInterface()){
                            Bean annotation = cla.getAnnotation(Bean.class);
                            if(annotation != null){
                                //4.7.实例化之后放进map集合中
                                Object instance = cla.getConstructor().newInstance();
                                //4.7.1 判断当前类如果有接口，放接口class作为map的key
                                if(cla.getInterfaces().length > 0){
                                    beanFac.put(cla.getInterfaces()[0],instance);
                                }else {
                                    beanFac.put(cla,instance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadDi(){
        //实例化对象在 beanFac 的map集合里面
        //1.遍历beanFac的map集合
        Set<Map.Entry<Class, Object>> entries = beanFac.entrySet();
        for(Map.Entry<Class, Object> entry : entries){
            //2.获取map集合的每个对象(value),每个对象属性获取到
            Object obj = entry.getValue();
            Class<?> cla = obj.getClass();
            Field[] dFields = cla.getDeclaredFields();
            //3.遍历每个对象属性数组，得到每个属性
            for(Field f : dFields){
                //4.判断每个属性上面是否有@Di注解
                Di annotation = f.getAnnotation(Di.class);
                if(annotation != null){
                    f.setAccessible(true);
                    //5.如果有@Di注解，把对象进行设置(注入)
                    try {
                        f.set(obj,beanFac.get(f.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
~~~

测试：

~~~
public class TestMake {
    public static void main(String[] args) {
        ApContext context = new AnnoApContext("com.SpringforNew.make");
        UserService userService = (UserService)context.getBean(UserService.class);
        userService.add();
    }
}
~~~





### 5. 面向切面：AOP



#### 5.1. 代理模式

> 让不属于目标方法核心逻辑的代码从目标方法中剥离出来——解耦。调用目标方法时调用代理对象的方法，减少对目标方法的调用和打扰，同时让附加功能集中在一起也有利于维护。
>
> ① 代理：将非核心的逻辑剥离出来以后，封装这些非核心逻辑的类、对象、方法。
>
> ② 目标：被代理 “套用” 了非核心逻辑代码的类、对象、方法。



#### 5.2. 代理模式

##### 1.静态代理

~~~
public class CalculatorStaticProxy implements Calculator{
	//设置静态代理
    private Calculator calculator;
    public CalculatorStaticProxy(Calculator calculator) {
        this.calculator = calculator;
    }
    @Override
    public int add(int x, int y) {
        System.out.println("[Log]: add() start.");
        System.out.println("add...");
        int res = calculator.add(x,y);
        System.out.println("[Log]: add() end.");
        return res;
    }
}
~~~

> 日志功能还是分散的，没有统一管理
>
> 将日志功能集中到一个代理类中，将来有任何日志需求，都通过这个代理类来实现。这就是要用到动态代理技术了。



##### 2. 动态代理

~~~
//动态代理类
public class ProxyFactory {

    //目标对象
    private Object target;
    public ProxyFactory(Object target){
        this.target = target;
    }
    //返回代理对象
    public Object getProxy(){
        /*
        *   Proxy.newProxyInstance(ClassLoader loader,
        *                          Class<?>[] interfaces,
        *                          InvocationHandler h)
        *   ClassLoader：加载动态生成代理类的类加载器
        *   Class<?>[] interfaces：目标对象实现的所有接口的class类型数组
        *   InvocationHandler：设置代理对象实现目标对象的过程
        * */
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        /*
        *   Object proxy：代理对象
        *   Method method：需要重写目标对象的方法
        *   Object[] args：method方法的参数
        * */
        InvocationHandler h = (proxy, method, args) -> {
            //调用方法之前输出
            System.out.println("[Logs]:"+method.getName()+",args:"+ Arrays.toString(args));
            //调用目标方法
            Object result = method.invoke(target, args);
            //调用方法之后输出
            System.out.println("[Logs]:"+method.getName()+",result:"+ result);
            return result;
        };
        return Proxy.newProxyInstance(loader,interfaces,h);
    }
}
~~~

测试：

~~~
public class TestCal {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new CalculatorImpl());
        Calculator proxy = (Calculator)proxyFactory.getProxy();
        proxy.add(1,2);
    }
}
~~~

~~~
[Logs]:add,args:[1, 2]
add...
[Logs]:add,result:3
~~~



#### 5.3. AOP 概念和术语



##### 1.概述

> AOP 是一种设计思想，是软件设计领域中的**面向切面编程**。利用AOP可以对业务逻辑的各个部分进行隔离，从而使业务逻辑各个部分之间的耦合度降低，提高程序的可重用性，同时提高开发的效率。



##### 2.相关术语

> ###### ①横切关注点
>
> 分散在各个模块中解决相同的问题，如用户验证，日志管理，事务处理，数据缓存都属于横向关注点。
>
> 这个概念不是语法层面的，而是根据附加功能的逻辑上的需要。
>
> ###### ②通知 / 增强
>
> 每一个横切关注点上要做的一件事情都需要写一个方法来实现，这样的方法叫做**通知方法**
>
> * 前置通知：在被代理的目标方法**前**执行
> * 返回通知：在被代理的目标方法**成功结束后**执行
> * 异常通知：在被代理的目标方法**异常结束后**执行
> * 后置通知：在被代理的目标方法**最终结束后**执行
> * 环绕通知：使用 try..catch..finally..结构围绕整个被代理的目标方法，包括上面四种通知的**所有**位置
>
> ###### ③切面
>
> 封装通知方法的类
>
> ###### ④目标
>
> 被代理的目标对象
>
> ###### ⑤代理
>
> 向目标对象应用通知之后创建的代理对象
>
> ###### ⑥连接点
>
> Spring允许使用通知的地方
>
> ###### ⑦切入点
>
> 定位连接点的方式，**Spring的AOP技术可以用过切入点定位到特定的连接点。就是要实际去增强的方法**



##### 3.作用

* 简化代码：把固定位置的重复代码抽取出来，让被抽取的方法更专注于自己的核心内容，提高内聚性
* 代码增强：把特定的功能封装到切面类中，看哪里有需要就往哪里面套，被套用了切面逻辑的方法就被切面给增强了。



#### 5.4. 基于注解的 AOP

##### 1. JDK动态代理和cglib动态代理

> ①有接口，使用JDK动态代理，生成接口实现类代理对象
>
> 代理对象和目标对象实现相同的接口
>
> ②没有接口，使用cglib动态代理，生成子类代理对象继承目标对象
>
> * AspectJ ：AOP框架
>
> 是AOP的思想的一种体现，本质上是静态代理，将代理逻辑植入被代理的目标类所编译出来的字节码文件，所以最终效果是动态的。weaver就是植入器，Spring只是借用了AspectJ中的注解。



##### 2. 引入依赖

~~~
<!-- Spring aop依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>6.0.6</version>
</dependency>

<!-- Spring aspects依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>6.0.6</version>
</dependency>
~~~



##### 3. xml配置文件

~~~
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 开启组件扫描 -->
    <context:component-scan base-package="com.SpringforNew.anno_aop"></context:component-scan>
    <!-- 开启aspectj自动代理，为目标对象生成代理 -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
~~~



##### 4. 切入点表达式语法

> ```
> 切入点表达式：
> 例：execution(public int com.SpringforNew.anno_aop.LogAspect.div(int,int))
> 
> execution:固定格式
> public:权限修饰符
> int:方法返回值
> 	public int:用*表示权限修饰符和返回值任意
> com.SpringforNew.anno_aop:用*表示包名任意，用*..表示包名任意且包的层次深度任意
> .LogAspect:类名全部用*表示类名任意，类名部分用*表示，例如*Se表示匹配以Se结尾的类或接口
> 	com.SpringforNew.anno_aop.LogAspect:方法所在类型的全类名
> .div:方法名。方法名全部用*表示方法名任意，方法名部分用*表示，例如get*表示匹配以get开头的方法
> (int,int):参数列表，用(..)表示参数列表任意
> ```



##### 5.重用切入点表达式

①声明

~~~
@Pointcut("execution(* com.SpringforNew.anno_aop.*.*(..))")
public void pointCut(){}
~~~

②编写切面

> 通知类型：
>  前置：@Before(value="切入点表达式配置切入点")
>  返回：@AfterReturning
>  异常：@AfterThrowing
>  后置：@After()
>  环绕：@Around()

~~~
@Before("pointCut()")
public void beforeMethod(JoinPoint joinPoint){
    String methodName = joinPoint.getSignature().getName();
    System.out.println("[Logger-before]name:"+
                        methodName+
                        ",args:"+
                        Arrays.toString(joinPoint.getArgs()));
}
~~~

~~~
@After("pointCut()")
public void afterMethod(JoinPoint joinPoint){
    String methodName = joinPoint.getSignature().getName();
    System.out.println("[Logger-after]name"+
                        methodName+
                        ",args:"+
                        Arrays.toString(joinPoint.getArgs()));
}
~~~

~~~
//可以获得返回的参数
@AfterReturning(value = "pointCut()",returning = "result")
public void afterReturningMethod(JoinPoint joinPoint,Object result){
    String methodName = joinPoint.getSignature().getName();
    System.out.println("[Logger-afterReturning]name:"+methodName+",res:"+result);
}
~~~

~~~
//目标方法有异常这个会执行
@AfterThrowing(value = "pointCut()",throwing = "ex")
public void afterThrowingMethod(JoinPoint joinPoint,Throwable ex){
    String methodName = joinPoint.getSignature().getName();
    System.out.println("[Logger-afterThrowing]name:"+methodName+",error:"+ex);
}
~~~

~~~
@Around("pointCut()")
public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){
    String methodName = proceedingJoinPoint.getSignature().getName();
    String args = Arrays.toString(proceedingJoinPoint.getArgs());
    Object result = null;
    try {
        System.out.println("[Logger-around-before]name:"+methodName+",args:"+args);
        result = proceedingJoinPoint.proceed();
        System.out.println("[Logger-around-afterReturning]name:"+
                            methodName+
                            ",res:"+
                            result);
    }catch (Throwable throwable){
        System.out.println("[Logger-around-afterThrowing]name:"+
                            methodName+
                            ",error:"+
                            throwable);
    }finally {
    	System.out.println("[Logger-around-after]name"+methodName+",args:"+args);
    }
    return result;
}
~~~



##### 6.切面优先级

相同方法上存在多个切面时，切面的优先级控制切面的内外嵌套顺序

> 优先级高的切面在外面
>
> 优先级低的切面在里面

使用@Order(较小的数)：优先级高

使用@Order(较大的数)：优先级低

![image-20240412212234315](C:\Users\13207\AppData\Roaming\Typora\typora-user-images\image-20240412212234315.png)



#### 5.5. 基于xml的 AOP

~~~
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="logAspect" class="com.SpringforNew.anno_aop.LogAspect"></bean>

    <context:component-scan base-package="com.SpringforNew.anno_aop"></context:component-scan>
    <aop:config>
        <!--配置切面类-->
        <aop:aspect ref="logAspect">
        	<!--切入点-->
            <aop:pointcut id="pointCut" 
                expression="execution(* com.SpringforNew.anno_aop.CalculatorImpl.*(..))"/>
            <!--前置通知-->
            <aop:before method="beforeMethod" pointcut-ref="pointCut"></aop:before>
            <!--返回通知-->
            <aop:after-returning method="afterReturningMethod" pointcut-ref="pointCut"
                returning="result"></aop:after-returning>
            <!--异常通知-->
            <aop:after-throwing method="afterThrowingMethod" pointcut-ref="pointCut"
                throwing="ex"></aop:after-throwing>
            <!--后置通知-->
            <aop:after method="afterMethod" pointcut-ref="pointCut"></aop:after>
            <!--环绕通知-->
            <aop:around method="aroundMethod" pointcut-ref="pointCut"></aop:around>
        </aop:aspect>
    </aop:config>
</beans>
~~~





### 6. 单元测试 JUnit



#### 6.1.通过Spring整合JUnit5

~~~
<!-- Spring对junit的支持相关依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>6.0.6</version>
</dependency>
~~~

~~~
//方式一
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:bean.xml")
//方式二
@SpringJUnitConfig(locations = "classpath:bean.xml")
public class SpringJunit5Te {
    @Autowired
    private User user;
    @Test
    public void te(){
        System.out.println(user);
        user.work();
    }
}
~~~



#### 6.2. JUnit4整合

~~~
<!-- junit4测试 -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
</dependency>
~~~

~~~
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean.xml")
public class SpringJunit4Te {
    @Autowired
    private Worker worker;
    @Test
    public void te(){
        System.out.println(worker);
        worker.work();
    }
}
~~~





### 7. 事务



#### 7.1.  jdbcTemplate

> Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库的操作

①引入依赖

~~~
<!-- Spring jdbc   Spring持久化层支持jar包 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>6.0.6</version>
</dependency>

<!-- mysql驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
~~~

② 配置文件 `jdbc.properties`

~~~
jdbc.user=root
jdbc.password=xxxxxx
jdbc.url=jdbc:mysql://localhost:3306/xxxxxxx?serverTimezero=GMT%2b8
jdbc.driver=com.mysql.cj.jdbc.Driver
~~~

③xml配置文件

~~~
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 引入外部属性文件 -->
    <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>

    <!--数据库信息注入-->
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.user}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="driverClassName" value="${jdbc.driver}"></property>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="druidDataSource"></property>
    </bean>
</beans>
~~~

④注入

~~~
@SpringJUnitConfig(locations = "classpath:bean-jdbc.xml")
public class JdbcTemplateTe {
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
~~~

⑤实现sql的CRUD操作

* 添加数据

~~~
String sqlInsert = "INSERT INTO t_emp (name,age,sex) VALUES (?,?,?)";
Object[] params1 = {"李四",21,"女"};
int updateTimes = jdbcTemplate.update(sqlInsert, params1);
~~~

* 删除数据

~~~
String sqlDelete = "DELETE FROM t_emp WHERE id=?";
int updateTimes = jdbcTemplate.update(sqlDelete, 2);
~~~

* 修改数据

~~~
String sqlUpdate = "UPDATE t_emp SET age=? where id=?";
Object[] params2 = {222,1};
int updateTimes = jdbcTemplate.update(sqlUpdate, params2);
~~~

* 查询数据

~~~
//查询数据
//方式一：自定义封装（单行结果Object）
String sqlSelect1 = "SELECT * FROM t_emp WHERE id=?";
Emp empObj = jdbcTemplate.queryForObject(sqlSelect1,(resultSet,mapRow)->{
        Emp emp = new Emp();
        emp.setId(resultSet.getInt("id"));
        emp.setName(resultSet.getString("name"));
        emp.setAge(resultSet.getInt("age"));
        emp.setSex(resultSet.getString("sex"));
        return emp;
    },1);
    
//方式二：自动封装（单行结果Object）
Emp empObj = jdbcTemplate.queryForObject(sqlSelect1,
                    new BeanPropertyRowMapper<>(Emp.class),
                    1);
~~~

~~~
//方式一：键值对读取（多行结果List）
String sqlSelect2 = "SELECT * FROM t_emp";
List<Map<String, Object>> maps = jdbcTemplate.queryForList(sqlSelect2);

//方式二：对象读取（多行结果List）
List<Emp> query = jdbcTemplate.query(sqlSelect2,new BeanPropertyRowMapper<>(Emp.class));
~~~

~~~
//返回单个值
String sqlSelect3 = "SELECT name FROM t_emp WHERE id=?";
String empName = jdbcTemplate.queryForObject(sqlSelect3, String.class, 1);
~~~



#### 7.2.声明式事务

> 事务：要么不做，要么全做
>
> 事务的特性（ACID）：①原子性 ②一致性 ③隔离性 ④持久性

##### 1.编程式事务：

~~~
Connection conn = ...
try{
	//开启事务：关闭事务的自动提交
	coon.setAutoCommit(false);
	//核心操作
	..
	//提交事务
	conn.commit();
}catch(Exception e){
	//回滚事务
	conn.rollback();
}finally{
	//释放数据库连接
	conn.close();
}
~~~

> 缺陷：
>
> ①细节没有被屏蔽
>
> ②代码复用性不高



##### 2.声明式事务

通过配置让框架实现功能



#### 7.3. 基于注解的声明式事务

##### 1.bookshop案例

①配置文件

`jdbc_tx.properties` :

~~~
jdbc.user=root
jdbc.password=xxxxxxxx
jdbc.url=jdbc:mysql://localhost:3306/spring_jdbc_bookshop?serverTimezero=GMT%2b8
jdbc.driver=com.mysql.cj.jdbc.Driver
~~~

`bean-jdbc-tx.xml` :

~~~
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <context:component-scan base-package="com.SpringforNew.jdbc_tx"></context:component-scan>
    <!-- 引入外部属性文件 -->
    <context:property-placeholder location="classpath:jdbc_tx.properties"></context:property-placeholder>

    <!--数据库信息注入-->
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.user}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="driverClassName" value="${jdbc.driver}"></property>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="druidDataSource"></property>
    </bean>
</beans>
~~~

② 三层模型

controller层：

~~~
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    //买书
    public void buyBook(Integer bookId,Integer userId){
        bookService.buyBook(bookId,userId);
    }
}
~~~

service层：

~~~
@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void buyBook(Integer bookId,Integer userId) {
        //获取图书库存
        Integer bookNum = bookDao.getNumById(bookId);
        //获取图书价格
        Integer bookPrice = bookDao.getPriceById(bookId);
        //获取用户余额
        Integer userMoney = userDao.getMoneyById(userId);
        //更新图书库存
        bookDao.updateNum(bookId,(bookNum-1));
        //更新用户余额
        userDao.updateMoney(userId,(userMoney-bookPrice));
    }
}
~~~

dao层：

~~~
@Repository
public class BookDaoImpl implements BookDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getPriceById(Integer bookId) {
        return jdbcTemplate.queryForObject(
                "SELECT price FROM spring_jdbc_bookshop.book_info WHERE id=?",
                Integer.class, bookId);
    }

    @Override
    public Integer getNumById(Integer bookId) {
        return jdbcTemplate.queryForObject(
                "SELECT num FROM spring_jdbc_bookshop.book_info WHERE id=?",
                Integer.class,bookId);
    }

    @Override
    public void updateNum(Integer bookId, Integer updatedNum) {
        jdbcTemplate.update(
                "UPDATE spring_jdbc_bookshop.book_info SET num=? WHERE id=?",
                updatedNum,bookId);
    }

}
~~~

~~~
@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getMoneyById(Integer userId) {
        return jdbcTemplate.queryForObject(
                "SELECT money FROM spring_jdbc_bookshop.user WHERE id=?",
                Integer.class,userId);
    }

    @Override
    public void updateMoney(Integer userId, Integer updatedMoney) {
        jdbcTemplate.update(
                "UPDATE spring_jdbc_bookshop.user SET money=? WHERE id=?",
                updatedMoney,userId);
    }
}
~~~

③测试类

~~~
@SpringJUnitConfig(locations = "classpath:bean-jdbc-tx.xml")
public class TestBook {

    @Autowired
    private BookController bookController;

    @Test
    public void te1(){
        bookController.buyBook(1,1);
    }
}
~~~

> 问题：
>
> 当用户余额小于图书价格时，由于用户余额是unsigned，赋值进去会报错
>
> 会导致图书库存的代码执行而用户余额未发生改变，这就需要事务处理来解决

④声明事务

配置文件

~~~
<!--新增命名空间属性-->
xmlns:tx="http://www.springframework.org/schema/tx"
xsi:schemaLocation="http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
~~~

~~~
<!--事务-->
<bean id="transactionManager"
	class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<property name="dataSource" ref="druidDataSource"></property>
</bean>

<!--
    开启事务的注解驱动
    通过注解@Transactional所标识的方法或标识的类中的所有方法，都会被事务管理器管理事务
-->
<!-- transaction-manager的默认属性是transactionManager
	 如果事务管理器的bean的id正好是这个可以忽略这个属性 -->
<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
~~~

⑤添加@Transactional注解

​	**@Transactional 参数**

> * 只读和超时
>
> | 参数             | 作用                                                         |
> | ---------------- | ------------------------------------------------------------ |
> | read-only = true | 只读，只允许读取数据的操作，修改数据会报错                   |
> | timeout = -1     | 运行超时，默认为-1表示永不超时。可设置超时时间，一旦超时便报错 |
>
> * 回滚策略
>
> 声明式事务默认只对运行时异常回滚，编译时异常不回滚：
>
> | 参数                                      |
> | ----------------------------------------- |
> | rollbackFor = Class类型的对象             |
> | rollbackForClassName = 字符串类型的全类名 |
> | noRollbackFor = Class类型的对象           |
> | norollbackFor = 字符串类型的全类名        |
>
> ~~~
> @Transactional(noRollbackFor = ArithmeticException.class)
> public void buyBook(Integer bookId,Integer userId) {
>     func();
>     System.out.println(1/0);
> }
> ~~~
>
> 虽然buyBook()方法出现了异常（ArithmeticException），但是我们设置了回滚策略，当出现ArithmeticException的时候不进行回滚,因此func()正常执行。
>
> * 隔离级别 isolation
>
> | 参数                                   | 脏读 | 不可重复读 | 幻读 | 作用     |
> | -------------------------------------- | ---- | ---------- | ---- | -------- |
> | isolation = Isolation.READ_UNCOMMITTED | 有   | 有         | 有   | 读未提交 |
> | isolation = Isolation.READ_COMMITTED   | 无   | 有         | 有   | 读已提交 |
> | isolation = Isolation.REPEATABLE_READ  | 无   | 无         | 有   | 可重复读 |
> | isolation = Isolation.SERIALIZABLE     | 无   | 无         | 无   | 串行化   |
>
> 在MYSQL中，isolation = Isolation.DEFAULT相当于isolation = Isolation.REPEATABLE_READ
>
> 在Oracle中，isolation = Isolation.DEFAULT相当于isolation = Isolation.READ_COMMITTED
>
> * 传播行为
>
> 事务方法之间的嵌套的事务传播方式
>
> | 参数                                   | 作用                                                         |
> | -------------------------------------- | ------------------------------------------------------------ |
> | propagation = Propagation.REQUIRED     | （默认）以外层的整个线程进行回滚。即本层有一个做不了，外层全做不了。 |
> | propagation = Propagation.REQUIRED_NEW | 以当前层的线程作为独立的线程进行回滚。即本层能做就做，不能做就不做，不影响整体外层的回滚。 |

⑥ 全注解开发

配置类

~~~
@Configuration  //配置类
@ComponentScan("com.SpringforNew.jdbc_tx")  //组件扫描
@EnableTransactionManagement    //开启事务
public class SpringConfig {
    @Bean(name = "druidDataSource")
    public DataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("yty");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/spring_jdbc_bookshop?serverTimezero=GMT%2b8");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return druidDataSource;
    }
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource druidDataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(druidDataSource);
        return jdbcTemplate;
    }
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource druidDataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(druidDataSource);
        return transactionManager;
    }
}
~~~

```
//测试方法
@Test
public void te2(){
    ApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);
    BookController bookCon = context.getBean(BookController.class);
    bookCon.buyBook(1,1);
}
```



#### 7.4. 基于xml的声明式事务

1.修改Spring配置文件

> * 开启组件扫描
> * 创建数据源
> * 创建JdbcTemplate，注入数据源
> * 创建事务管理器，注入数据源
> * 配置事务的通知，设置事务相关属性
> * 配置切入点表达式，把事务方法添加到方法上

~~~
<!---->
<tx:advice id="txAdvice" transaction-manager="myTransactionManager">
    <tx:attributes>
        <tx:method name="buy*"/>
    </tx:attributes>
</tx:advice>

<!--配置切入点和通知使用的方法-->
<aop:config>
    <aop:pointcut id="pointCut"
    		expression="execution(* com.SpringforNew.jdbc_xmltx.service.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="pointCut"/>
</aop:config>
~~~





### 8.资源操作：Resources



#### 8.1. Resource 接口

> Spring 的Resource接口用于抽象对低级资源的访问



#### 8.2. Resource 的实现类

##### 1. UrlResource 实现类

> 用来访问网络资源，支持URL的绝对路径

 ~~~
//访问前缀http、ftp、file(根路径下)
public static void loadUrlResource(String path){
    try {
        //创建 Resource 实现类的对象 UrlResource
        UrlResource urlResource = new UrlResource(path);
        //获取资源信息
        System.out.println(urlResource.getURL());
        System.out.println(urlResource.getFilename());
        System.out.println(urlResource.getDescription());
        System.out.println(urlResource.getInputStream().read());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
 ~~~



##### 2. ClassPathResource 实现类

> 访问类加载路径下的资源 (resources 文件路径下的文件)

~~~
public static void loadClassPathResource(String path){
    //创建 Resource 实现类的对象 UrlResource
    ClassPathResource resource = new ClassPathResource(path);
    System.out.println(resource.getFilename());
    System.out.println(resource.getDescription());
    //获取文件内容
    try {
        InputStream is = resource.getInputStream();
        byte[] bys = new byte[1024];
        int len;
        while ((len = is.read(bys)) != -1){
        	System.out.println(new String(bys,0,len));
        }
        is.close();
    } catch (IOException e) {
    	throw new RuntimeException(e);
    }
}
~~~



##### 3. FileSystemResource 实现类

> 访问文件资源系统，使用FileSystemResource来访问文件系统并没有太大的优势，因为Java提供的File类也可以用于访问文件资源

~~~
public static void loadFileSystemResourceTe(String path){
    //创建 Resource 实现类的对象 UrlResource
    FileSystemResource resource = new FileSystemResource(path);
    System.out.println(resource.getFilename());
    System.out.println(resource.getDescription());
    //获取文件内容
    try {
        InputStream is = resource.getInputStream();
        byte[] bys = new byte[1024];
        int len;
        while ((len = is.read(bys)) != -1){
    		System.out.println(new String(bys,0,len));
        }
        is.close();
    } catch (IOException e) {
    	throw new RuntimeException(e);
    }
}
~~~



##### 4. 其他实现类

> ServletContextResource 实现类
>
> InputStreamResource 实现类
>
> ByteArrayResource 实现类





#### 8.3. ResourceLoader 接口

> 该接口实现类的对象可以获得一个Resource实例
>
> Resource getResource( String location) ：该接口仅有这个方法，用于返回一个Resource实例。
>
> ApplicationContext实现类都实现了ResourceLoader 接口，因此ApplicationContext可以直接获取Resource实例。

演示：

~~~
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext();
    //通过ApplicationContext访问资源
    //ApplicationContext实例获取Resource实例时
    //默认使用与ApplicationContext相同的资源访问策略
    Resource resource = context.getResource("Demo.txt");
    System.out.println(resource.getFilename());
}
~~~

~~~
public static void main(String[] args) {
    ApplicationContext context = new FileSystemXmlApplicationContext();
    Resource resource = context.getResource("Demo.txt");
    System.out.println(resource.getFilename());
}
~~~





#### 8.4. ResourceLoaderAware 接口

> ResourceLoaderAware 接口实现类的实例获得一个ResourceLoader的引用，ResourceLoaderAware 接口也提供了一个setResourceLoader( ) 方法，该方法有Spring容器负责调用，Spring容器会将一个ResourceLoader对象作为该方法的参数传入

演示：

~~~
public class TestBean implements ResourceLoaderAware{

    private ResourceLoader resourceLoader;
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public ResourceLoader getResourceLoader(){
        return this.resourceLoader;
    }
}
~~~

~~~
//测试方法
@Test
public void te(){
    ApplicationContext context =
    				new ClassPathXmlApplicationContext("bean-test.xml");
    TestBean testBean = context.getBean(TestBean.class);
    ResourceLoader resourceLoader = testBean.getResourceLoader();
    System.out.println(resourceLoader == context);
}
~~~

~~~
true
~~~



#### 8.5.使用Resource作为属性

~~~
public class User {
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
~~~



### 9. Validation 数据校验

> 开发中经常有参数校验的需求，比如用户注册的时候，用户名不能为空，用户名长度要求，合法的手机格式等。Spring的Validation允许通过注解的方式来定义对象校验规则，把校验和业务逻辑分离开。Spring Validation其实就是对Hibernate Validator进一步的封装，方便在Spring中使用。

**第一种：通过实现org.springframework.validation.Validator接口，然后在代码中调用类**

**第二种：按照BeanValidation方式进行校验，即通过注解的方式**

**第三种：基于方法实现校验**

**除此之外还可以自定义校验**



#### 9.1. 通过Validator接口实现

①引入依赖

~~~
<!--hibernate-validator-->
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.0.Final</version>
</dependency>

<!---->
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>jakarta.el</artifactId>
    <version>4.0.2</version>
</dependency>
~~~

②创建实体类

~~~
public class Person {
    private String name;
    private Integer age;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age;}
}
~~~

③创建校验类

~~~
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    @Override   //校验规则
    public void validate(Object target, Errors errors) {
        //name不为空
        ValidationUtils.rejectIfEmpty(errors,"name","name.empty","用户名空");
        //age大于0小于150
        Person person = (Person)target;
        if(person.getAge() < 0){
            errors.rejectValue("age","age.value.error","age < 0");
        }else if(person.getAge() > 150){
            errors.rejectValue("age","age.value.error","age > 150");
        }
    }
}
~~~

④测试

~~~
public class TestPerson {
    public static void main(String[] args) {
        //创建person对象
        Person person = new Person();
        person.setName("");
        person.setAge(200);
        //创建person对应databinder
        DataBinder dataBinder = new DataBinder(person);
        //设置校验器
        dataBinder.setValidator(new PersonValidator());
        //调用方法进行校验
        dataBinder.validate();
        //输出校验结果
        BindingResult result = dataBinder.getBindingResult();
        System.out.println(result.getAllErrors());
        System.out.println(result.getErrorCount());
    }
}
~~~



#### 9.2. 通过Bean Validation 注解实现

> Spring 默认有一个实现类LocalValidatorFactoryBean，它实现了上面Bean Validation中的接口，并且也实现了org.springframework.validation.Validator接口
>
> 常用注解说明：
>
> | 注解               | 说明                                                       |
> | ------------------ | ---------------------------------------------------------- |
> | @NotNull           | 限制必须不为null                                           |
> | @NotEmpty          | 只作用于字符串类型，限制不为空并且长度不为0                |
> | @NotBlank          | 只作用于字符串类型，限制不为空并且trim()后不为空串         |
> | @DecimalMax(value) | 限制为不大于value的数字                                    |
> | @DecimalMin(value) | 限制为不小于value的数字                                    |
> | @Max(value)        | 限制为不大于value的数字                                    |
> | @Min(value)        | 限制为不小于value的数字                                    |
> | @Pattern(value)    | 限制必须符合指定的正则表达式                               |
> | @Size(max,min)     | 限制字符长度必须在min到max之间                             |
> | @Email             | 限制email格式，也可以用正则表达式和flag指定自定义email格式 |

①创建配置类，配置LocalValidatorFactoryBean

~~~
@Configuration
@ComponentScan("com.SpringforNew.validationAnno")
public class ValidationConfig {
    @Bean
    public LocalValidatorFactoryBean getValidator(){
        return new LocalValidatorFactoryBean();
    }
}
~~~

②创建实体类

~~~
public class User {
    @NotNull
    private String name;
    @Max(150)
    @Min(0)
    private Integer age;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age;}
}
~~~

③创建service层

~~~
@Service
public class UserService {
    @Autowired
    private Validator validator;

    public boolean validatorByUser(User user){
        BindException bindException = new BindException(user,user.getName());
        validator.validate(user,bindException);
        return bindException.hasErrors();
    }
}
~~~

④测试

~~~
@Test
public void te(){
    ApplicationContext context =
    			new AnnotationConfigApplicationContext(ValidationConfig.class);
    ValidatorFac userService = context.getBean(UserService.class);
    User user = new User();
    user.setName("YTY");
    user.setAge(200);
    System.out.println(userService.validatorByUser(user));
}
~~~



#### 9.3. 基于方法实现校验

①创建配置类，配置MethodValidationPostProcesser

~~~
@Configuration
@ComponentScan("com.SpringforNew.validationMethod")
public class ValidationConfig {
    @Bean
    public MethodValidationPostProcessor getValidator(){
        return new MethodValidationPostProcessor();
    }
}
~~~

②创建实体类，使用注解规定校验规则

~~~
public class User {
    @NotNull
    private String name;
    @Max(150)
    @Min(0)
    private Integer age;
    @Pattern(regexp = "^1(3|5|7|8|9)\\d{9}$", message = "手机格式错误")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
}
~~~

③创建service层

~~~
@Service
@Validated
public class UserService {
    public String testMethod(@NotNull @Valid User user){
        return user.toString();
    }
}
~~~

④测试

~~~
public class TestUser {
    @Test
    public void te(){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ValidationConfigNew.class);
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user.setName("YTY");
        user.setAge(100);
        user.setPhone("13344445555");
        System.out.println(userService.testMethod(user));
    }
}
~~~



#### 9.4. 自定义校验





### 10. 提前编译 AOT



#### 10.1.JIT和AOT

> JIT（just in time）动态编译（实时）,边运行边编译。
>
> 优点：在程序运行的时候，动态生成代码。
>
> 缺点：启动比较慢，编译的时候需要占用运行时资源

> AOT（ahead of time）提前编译
>
> 优点：可以把源代码直接转换成机器码，启动快，内存占用低
>
> 缺点：运行的时候不能优化，程序安装时间过长
