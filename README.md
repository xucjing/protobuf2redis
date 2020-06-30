## Version
```Java 1.8```

## 项目说明
1. 提供 ```SpringBoot + Redis + SpringCache + Protobuf``` 的模板。
2. ```Redis Client```使用的是```Lettuce```，底层通信使用的Netty，如不了解，请参考《Netty实战》或《Netty权威指南》，对这方面有调优需求的同学请自行查阅相关资料，或提[issues](https://github.com/wuliangxue/protobuf2redis/issues)。
3. ```redis```模式本项目使用的是单机的，如有集群或哨兵模式需求的同学，请参考源码或查阅资料进行配置变更。
4. DEMO提供的是基于```@Cacheable```相关注解的功能，该注解针对的是```key-value```数据存储，如有其他数据结构存储需求，应该参考其他写法。

## 关于优化
1. 本项目主要是提供一个DEMO，优化方面没有做，除了必要的东西，更多的配置使用的是默认值。
2. 如对```hikari```或```redis```连接池有优化需求的同学，请查阅相关资料，或提[issues](https://github.com/wuliangxue/protobuf2redis/issues)。

## 测试
项目相关功能均通过测试，如使用遇到困难，请提[issues](https://github.com/wuliangxue/protobuf2redis/issues)。

## 后续
1. 项目后续部署打算新开一个章程，就叫做如何使用```Docker```搭建一次性测试环境吧。
2. ```Docker```范畴：```Docker file```，```Docker Compose```，```Docker Swarm```。
3. 如何做？ 使用```Shell```脚本自动化搭建。