## Spring学习笔记

书籍参考： *Spring in action*，跟随书中项目Taco Cloud

* 微服务分支，完整参考master分支

* MicroService
    * monoliths / microservice architecture
    * Spring Cloud Netflix
        * Eureka (Netflix service registry)
        * Ribon (Netflix client-side load-balancer)
            * 比中心化负载均衡的优点 centralized load balancer
                * 可以按客户端数量扩展
                * 每个客户端可以配置不同的最合适的负载均衡算法
    * service name
    * Eureka Service Registry
        * @EnableEurekaServer
        * http://localhost:8080    http://localhost:8080/eureka/apps REST API
        * 默认每30尝试与其他Eureka注册一次
        * 默认端口8761
        * eureka.instance.hostname
        * eureka.client.fetch-registry/register-with-eureka/service-url
        * eureka.server.enable-self-preservation 设置禁用取消服务注册
        * p-service-registry/p-config-server/p-circuit-breaker-dashboard
    * client configuration (and register service)
        * spring.application.name
        * server.port: 0
        * eureka.client.service-url.defaultZone: http://eureka1.tacocloud.com:8761/eureka/, http://eureka1.tacocloud.com:8762/eureka/
    * consuming service
        * RestTemplate
            * ```java
              @Bean
              @LoadBalanced
              public RestTemplate restTemplate(){
                  return new RestTemplate();
              }
              
              @LoadBalanced RestTemplate rest // inject
              
              rest.getForObject("http://ingredient-service/ingredients/{id}",Ingredient.class, ingredientId) // consume
              ```
        * WebClient
            * ``@LoadBalanced``
        * Feign-generated client interfaces
            * Feign (spring-cloud-starter-openfeign)
            * ``@EnableFeignClients``
            * ``@FeignClient("ingredient-service") @GetMapping/@RequestMapping``
        
