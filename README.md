## Spring学习笔记

书籍参考： *Spring in action*，跟随书中项目Taco Cloud

* IDEA无法自动更新的解决办法
    * 打开Build project automatically
    * CTRL+SHIFT+A
    * 输入Registry
    * 勾选``compiler.automake.allow.when.app.running``
    * 勾选``ide.windowSystem.autoShowProcessPopup``，含义不明
    * 参考
        * [Stack Overflow](https://stackoverflow.com/a/45640726)
        * [mykong](https://www.mkyong.com/spring-boot/intellij-idea-spring-boot-template-reload-is-not-working/)
        
* LiveReload插件安装
    * Firefox插件链接失效
    * Chrome安装，需要点击使图标中心变为实心点
        * Site Access需要设置为On all sites或者On specific sites，On Click会报错

* 应用的classpath的根目录下有schema.sql/data.sql文件，会在应用开始时被执行
    * 应该将schema.sql和data.sql放在src/main/resources

* http://localhost:8080/h2-console
    * JDBC URL: ``jdbc:h2:mem:testdb``
    * 其他默认
    
* 书中很多地方讲解不完整需要自行解决才能顺利运行
    * 参考 https://github.com/habuma/spring-in-action-5-samples
    * google & stack overflow
