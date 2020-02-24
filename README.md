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
    
* 输入符合信用卡Credit Card Number验证的值比较麻烦，可以用 http://www.getcreditcardnumbers.com/ 生成

* 书中很多地方讲解不完整需要自行解决才能顺利运行
    * 参考 https://github.com/habuma/spring-in-action-5-samples
    * google & stack overflow

* JPA domain-specific language (DSL)
    * verb+\[subject\]+By+predicate
    * findByDeliveryZip()
    * readOrdersByDeliveryZipAndPlacedAtBetween()
    * many operators
    
* JdbcTemplate切换到JPA注意事项
    * 需要删除schema.sql和data.sql
    * 需要执行clean，否则构建后的sql文件不会被清除，会导致执行错误

* Spring Security
    * User store
        * in memory
        * database
        * LDAP, TODO: LDAP暂时忽略
    * register
    * login
    * logout
    * WebSecurityConfigurerAdapter 
    * User & UserRepository
    * UserDetailsService
    * Controller中确定User
        * Authentication @AuthenticationPrincipal Principal

* JPA utf-8 设置
    * MySQL charset需要设置为utf-8，见[start-mysql.sh](start-mysql.sh)
    * spring.datasource.url需要设置为``jdbc:mysql://localhost:13306/tacocloud?useUnicode=yes&characterEncoding=utf-8``
    * MySQL charset设置为utf8mb4会报错，字符串类型的primary key提示key too long，最大为767bytes，待解决
        * 原因varchar(255)，utf8mb4为4字节

* Configuration
    * application.yml/application.properties
    * properties metadata: ``src/resource/META-INF/additional-spring-configuration-metadata.json``
    * 生产环境和开发环境配置
        * 环境变量
            ```shell
            export SPRING_DATASOURCE_URL=jdbc:mysql://localhost/tacocloud
            export SPRING_DATASOURCE_USRERNAME=tacouser
            export SPRING_DATASOURCE_PASSWORD=tacopassword
            ```
        * profile
            * 使用``application-{profile name}.yml`` or ``application-{profile name}.properties``文件
                * ``application-prod.yml``
            * yml文件特有方法，使用``---``分隔，并指定spring.profiles
                ```yaml
                ---
                spring:
                  profiles: prod
                ```
            * activating profiles
                * ``spring.profiles.active`` (worst way)
                  ```yaml
                  spring:
                    profiles:
                      active:
                        - prod
                        - audit
                        - ha
                  ```
                * 环境变量 ``SPRING_PROFILES_ACTIVE=prod,audit,ha``
                * 命令行参数 ``java -jar taco-cloud.jar --spring.profiles.active=prod``
                * Cloud Foundry会自动激活cloud profile               
        * 根据profile有条件地创建bean
            * ``@Profile``
            * ``@Profile("dev")``
            * ``@Profile("dev", "qa")`` dev或qa
            * ``@Profile("!prod", "!qa")`` prod与qa都没有被激活
* REST API
    * ``@GetMapping @PostMapping @PutMapping @PatchMapping @DeleteMapping @RequestMapping``              
    * ``@RequestMapping(path="/", produces="application/json") @RequestMapping(path="/", produces={"application/json","text/xml"})``
    * ``@CrossOrigin(origins="*")``
    * ``@PathVariable @RequestBody``
    * ``consumes="application/json"``
    
* HATEOAS(Hypermedia as the Engine of Application State)
    * 书中不一致的地方，参考[Migrating to Spring HATEOAS 1.0](https://docs.spring.io/spring-hateoas/docs/current/reference/html/#migrate-to-1.0)
        * ResourceSupport -> RepresentationModel
        * Resource -> EntityModel
        * Resources -> CollectionModel
        * PagedResources -> PagedModel
    * Deprecated
        * ControllerLinkBuilder -> WebMvcLinkBuilder
    * Resource Assembler
        * hateoas版本更新导致的书中不一致
            * ResourceAssemblerSupport -> RepresentationModelAssemblerSupport
            * toResource -> toModel
    * ``@Relation(value="taco", collectionRelation="tacos")``
    
* Spring Data REST
    * ``spring.data.rest.base-path: /api``
    * 直接访问base path可以查看可用的url
    * ``RepositoryRestController``
    * ResourceProcessor -> RepresentationModelProcessor
    
* RestTemplate
    * 41 methods: ``delete getForEntity getForObject headForHeaders optionsForAllow patchForObject postForEntity postForLocation postForObject put``
        * Object/Entity(ResponseEntity)
    * 三种方式
        * url+按顺序给定placeholder
        * ulr+Map
        * UriComponents
        
* Traverson
    * ``follow toObject asLink getHref``
    * ParameterizedTypeReference
    * 只能浏览不能POST/PUT/DELETE，如果需要，需要使用RestTemplate
    * **TODO: Traverson如何处理通过ResourceAssembler保证的嵌套数据**
    * **TODO: linkTo/methodOn如何处理RepositoryRestController的base url**

* asynchronous message
    * ActiveMQ/Artemis
        * ``spring.artemis.host/port/user/password``
        * ``spring.activemq.broker-url/user/password/in-memory``
        * 打开 [http://127.0.0.1:8161](http://127.0.0.1:8161 ) 查看Management Console
    * JmsTemplate
        * 发送消息
            * send MessageCreator
            * convertAndSend Object MessagePostProcessor
            * 指定目标 default/Destination/String
        * ``spring.jms.template.default-destination: tacocloud.order.queue``
        * MessageConverter (toMessage fromMessage)
            * MappingJackson2MessageConverter
                * 从org.springframework.jms.support.converter而不是org.springframework.messaging.converter导入
                * setTypeIdPropertyName setTypeIdMappings
            * MarshallingMessageConverter
            * MessagingMessageConverter
            * SimpleMessageConverter，default，需要对象实现Serializable
        * MessagePostProcessor
        * 接收消息
            * model: pull model/push model
            * pull model
                * receive Message
                * receiveAndConvert Object
            * push model ``@JmsListener``
    * RabbitMQ/AMQP
        * exchange / routing key
        * Sender Exchange Binding Queue Receiver
        * Default/Direct/Topic/Fanout/Headers/Dead letter
        * *RabbitMQ in Action* by Alvaro Videla & Jason J.W. Williams
        * ``spring.rabbitmq.addresses/host/port/username/password``
        * RabbitTemplate
        * 发送消息
            * send convertAndSend
            * Message exchange routingKey  MessagePostProcessor
        * ``@Qualifier``
        * 接收消息
            * pulling(``RabbitTemplate``)  listener(``@RabbitListener``)
            * RabbitTemplate receiving
                * receive Message
                * receiveAndConvert Object/ParameterizedTypeReference
                * 参数：queueName/timeoutMillis
        * 如何处理同时使用Jms和RabbitMQ时，都需要注入MessageConverter，但Bean不能重名的问题
            * 没有问题，只是jms和rabbitmq配置时都使用了同名的MessagingConfig的Configuration导致的Bean重复
    * Kafka
        * Kafka启动，见[start-kafka.sh](start-kafka.sh)和[docker-compose.kafka.yml](docker-compose.kafka.yml)，采用单broker，多broker端口会变化需要修改配置文件
        * 通用配置 ``spring.kafka.bootstrap-servers/template.default-topic``
        * 发送消息
            * ``ListenableFuture<SendResult<K,V>> send(String topic, Integer partition, Long timestamp, K key, V data)`` & 去除部分参数
            * ``ListenableFuture<SendResult<K,V>> send(ProducerRecord<K,V> record)``
            * ``ListenableFuture<SendResult<K,V>> send(Message<?> message)``
            * ``ListenableFuture<SendResult<K,V>> sendDefault(Integer partition, Long timestamp, K key, V data)`` & 去除部分参数
            * 配置 ``producer.keySerializer/valueSerializer``
        * 接收消息
            * ``@KafkaListener`` 
            * ``Order ConsumerRecord<Order> Message<Order>``
            * 配置 ``consumer.group-id/value-deserializer/properties(spring.json.trusted.packages)``

* Spring Integration
    * *Enterprise Integration Patterns* by Gregor Hohpe and Bobby Woolf
    * File System
    * ``@MessagingGateway @Header(FileHeaders.FILENAME)``
    * 3中配置方法
        * XML ``@ImportResource("classpath:/filewriter-config.xml")``
        * Java ``@Transformer @ServiceActivator`` 会自动创建channel
        * Java with a DSL ``IntegrationFlow IntegrationFlows ``
    * *Channels, Filters, Transformers, Routers, Splitters, Aggregators, Service activators, Channel adapters, Gateways*
        * Channel: PublishSubscribeChannel QueueChannel PriorityChannel RendezvousChannel DirectChannel ExecutorChannel FluxMessageChannel
    * Endpoint Modules: AMQP, Spring application events, RSS and Atom, FileSystem, FTP/FTPS, GemFire, HTTP, JDBC, JMS, Email, MongoDB, MQTT, Redis, RMI, SFTP, STOMP, Stream, Syslog, TCP/UDP, Twitter, Web Services, WebFlux, WebSocket, XMPP, ZooKeeper
    * 需要引入javax.mail
    * ``org.apache.commons.text.similarity.LevenshteinDistance``
    * ``AbstractMailMessageTransfomer AbstractIntegrationMessageBuilder GenericHandler``
    * spring.main.web-application-type: none/servlet/reactive
    * 需要邮箱中有未读邮件，且收取后会设为已读
    
    

