# Elasticsearch学习

## Elasticsearch 7 更新
1. 默认自带 JDK 不用再为安装什么版本的JDK和环境冲突而苦恼了，下载安装即可使用。 包大了200MB+，正是JDK的大小。
2. 默认节点名称为主机名，可以在elasticsearch.yml中显式配置，实际业务场景中，以主机名区分不同节点比随机起名字更便于甄别，不易混淆
3. 默认分片数改为1，不再是5  (number_of_shards:1)
4. Elasticsearch 7.0 没有 Type 了，包括 API 层面的。正确的使用方法，使用默认的_doc作为type就可以了。type会在8.X版本彻底移除。es6时，官方就提到了es7会删除type，并且es6时已经规定每一个index只能有一个type。在es7中使用默认的_doc作为type，官方说在8.x版本会彻底移除type。api请求方式也发送变化，如获得某索引的某ID的文档：GET index/_doc/id其中index和id为具体的值
5. hits.total返回对象，而非仅结果值,现在，与搜索请求匹配的总命中数将作为具有值和关系的对象返回。 value表示匹配的匹配数，关系表示值是准确的（eq）还是非准确的（gte）。
6. 集群连接变化：TransportClient被废弃  es7的java代码，只能使用restclient

## es连接方式 
  * spring-data-elasticsearch 基于 Elasticsearch transport(TCP) 客户端封装
  * spring-data-jest 基于 Jest(HTTP API) 客户端封装
  * 官方明确表示在ES 7.0版本中将弃用TransportClient客户端，且在8.0版本中完全移除它
 
## 参考      
[Lucene](https://blog.csdn.net/wangmx1993328/article/details/82177447 "Lucene 实战之入门案例") 
[Spring boot](https://www.zuojl.com/spring-boot-integration-elasticsearch/ "Spring Boot 集成 Elasticsearch")

