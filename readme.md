本工程主要实现 dubbo 测试 demo。使用 nacos 为注册中心。  
________________________________________________
一、创建工程 spring-dubbo-demo 为父类工程  
1.1).初始化 pom.xml , 修改内容  
    a).设置 spring boot 版本号 2.1.6.RELEASE 、 packaging 为 pom  
    b).引入实现多环境的构建可移植性的 profiles ， 其中配置 maven 打包依赖：build 
    c).配置 maven 项目的远程仓库：repositories 和 maven 插件的远程仓库：pluginRepositories
1.2).删除 src 文件夹  
1.3).引入统一依赖管理，基于模块 dependencies 实现完成后修改，详读步骤 2.3 解释。  
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.yuansb</groupId>
                <artifactId>spring-dubbo-demo-dependencies</artifactId>
                <version>${project.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
-
二、创建模块 spring-dubbo-demo-dependencies 实现[统一的依赖管理]  
2.1).初始化
    a).删除 parent 标签内容， 设置模块信息  
        <modelVersion>4.0.0</modelVersion>
        <groupId>com.yuansb</groupId>
        <artifactId>spring-dubbo-demo-dependencies</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <packaging>pom</packaging>
    b).引入 dubbo 、spring-cloud 、spring-cloud-alibaba 相关依赖  
    c).配置 maven 项目的远程仓库：repositories 和 maven 插件的远程仓库：pluginRepositories
2.2).删除 src 文件夹  
2.3).因为将模块 dependencies 作为统一的依赖管理，所以需要在父类的工程 pom.xml 中引入依赖
    该步骤特别重要，取决于是否可以继续进行。配置内容参考步骤 1.3 实现。注意 groupId 和 artifactId 一致性。
-
三、创建模块 spring-dubbo-demo-provider 为[服务提供者模块]，用于统一管理提供者
3.1).初始化：设置 packaging 为 pom  
3.2).删除 src 文件夹  
-
四、创建模块 spring-dubbo-demo-provider-api 为[服务提供者 接口]（在 provider 模块下）
4.1).初始化：设置 packaging 为 jar  
4.2).创建文件夹 com.yuansb.demo.dubbo.provider.api  
4.3).创建接口 EchoService , 实现简单的 echo() 方法  
4.4).删除 src.main.java.resource 文件夹
-
五、创建模块 spring-dubbo-demo-provider-service 为[服务提供者 实现]（在 provider 模块下）
5.1).初始化：设置 packaging 为 jar ， 同时引入相关依赖  
    依赖时候可能会报错：Cannot resolve com.yuansb:spring-dubbo-demo-provider-api:0.0.1-SNAPSHOT  
    需要手动处理报错，参考[引入内部依赖]： [api] 报错处理  
5.2).创建配置文件 application.yml  
5.3).创建启动类 ProviderApplication  
5.4).在 service 文件夹下创建 EchoServiceImpl 实现类并继承 EchoService 接口
-
六、创建模块 spring-dubbo-demo-consumer 为[服务消费者]  
6.1).初始化：设置 packaging 为 jar ， 同时引入相关依赖  
6.2).创建配置文件 application.yml  
6.3).创建启动类 ConsumerApplication  
6.4).在 controller 文件夹下创建 EchoController 实现 echo() 方法调用
-
七、测试环节
7.1).保证 nacos 启动成功
7.2).运行 provider  
7.3).运行 consumer
7.4).浏览器访问测试
    路径：http:127.0.0.1:8080/echo/Yuansb
    结果：Hello Apache Dubbo : Yuansb 
________________________________________________
容易产生问题及解决思路：
1). [引入内部依赖] ： [dependencies] 的处理：
    需要确定 dependencies 模块的 groupId 、artifactId 与 父工程 pom.xml 是否一致。
2). [引入内部依赖] ： [api] ： [Cannot resolve com.yuansb:spring-dubbo-demo-provider-api:0.0.1-SNAPSHOT] 的处理：   
    需要对 api 模块进行 mvn install 处理，可以核查本地 maven 库是否存在该包。
    如果已经存在可以删除 idea 的缓存 .idea 文件夹后重启工具继续进行。
    也可以将 Maven 配置 [Always Update snapshots 打钩] 进行同步（之后注意取消）。
    -
    怎么添加jar到本地仓库？（未实际测试使用）
        1.cmd 命令进入该 jar 包所在路径
        2.执行命令： mvn install:install-file -Dfile=hadoop-hdfs-2.2.0.jar -DgroupId=org.apache.hadoop -DartifactId=hadoop-hdfs -Dversion=2.2.0 -Dpackaging=jar
            就是指把hadoop-hdfs-2.2.0.jar安装到repository\org.apache.hadoop\hadoop-hdfs\2.2.0目录下，
        3.执行完命令后，如果需要在项目中使用这个jar，则在pom.xml中添加如下配置即可：    
            <dependency>
                  <groupId>org.apache.hadoop</groupId>
                  <artifactId>hadoop-hdfs</artifactId>
                  <version>2.2.0</version>
            </dependency>
3). [Maven打包时出现无法下载org.apache.maven.plugins插件]
    解决方式： 使用 mvn clean package -U 打包即可。
    出现错误 [Failed to execute goal org.apache.maven.plugins:maven-enforcer-plugin:3.0.0-M2:enforce] 将 pom.xml 对应该段进行注释。
 