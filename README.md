# 在线音乐平台服务端

本项目为在线音乐平台的后端服务，基于 **Spring Boot 3** 构建，提供用户管理、音乐管理、支付、第三方登录、个性化推荐、短信/邮件服务等完整的后端能力。

此外，系统集成了 **基于 KNN 协同过滤算法的音乐智能推荐功能**，可根据用户行为动态生成个性化推荐列表。

---

## 技术栈

### 核心框架
- Spring Boot 3.5.x（Web、Actuator）
- MyBatis-Plus（数据库访问）
- MySQL 8.x
- Redis（缓存/验证码/限流）

### 第三方服务与功能
- 阿里云 OSS（文件存储）
- 阿里云短信服务
- 支付宝 SDK（登录/支付）
- JustAuth（第三方登录）
- JWT（Token 鉴权）
- Lombok（简化代码）
- OkHttp（HTTP 客户端）

### 构建与工具
- Maven
- Druid 连接池
- FastJSON / Commons-lang3
- JUnit 测试
- Spring Boot DevTools 热部署

---

## 功能模块

### ✔ 用户与权限模块
- 用户注册 / 登录（密码 / 邮箱验证码 / 支付宝登录 / 第三方登录）
- Token 鉴权（JWT）
- 人脸识别登录（基于百度 AI）

### ✔ 音乐管理模块
- 歌曲上传（OSS）
- 歌曲分类、搜索、播放记录
- 歌单管理
- 收藏与点赞

### ✔ 推荐系统（核心）
系统内置 **基于 KNN 协同过滤算法的音乐推荐功能**，特点如下：

1. **基于用户行为（播放/收藏/点赞）构建用户特征向量**  
2. **计算用户之间的相似度（余弦相似度）**  
3. **根据相似用户的偏好生成推荐歌曲列表**
4. **定时任务批量计算推荐数据并缓存到 Redis**
5. **前端首页调用时直接读取缓存推荐结果**

（你的后续代码也可以放入 `/recommend/xx` 模块）

### ✔ 订单与支付模块
- 音乐购买订单
- 支付宝扫码支付（PC 端）
- 支付回调与订单状态处理

### ✔ 短信 / 邮件模块
- 登录验证码
- 重置密码
- 邮件通知

---

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.x
- Redis 6+
- 阿里云账号（OSS + SMS）
- 支付宝开放平台应用

---

## 项目结构（示例）

src/
├─ main/java/com/lyb/  
│ ├─ algorithm / # KNN 推荐算法实现  
│ ├─ common / # 常用  
│ ├─ constant / # 常量  
│ ├─ controller/ # 控制层  
│ ├─ service/ # 业务层  
│ ├─ mapper/ # MyBatis-Plus Mapper  
│ ├─ model/ # 实体类  
│ ├─ config/ # 配置（JWT、OSS、Druid 等）  
│ └─ recommend/ # KNN 推荐算法实现  
└─ resources/  
├─ application.yml # 配置文件  
└─ mapper/ # SQL 映射文件  


---

## 本地运行步骤

```bash
# 1. 克隆项目
git clone https://github.com/你的用户名/OlineMusicServer.git
cd OlineMusicServer

# 2. 修改 application.yml（数据库、Redis、OSS、短信等配置）

# 3. 启动项目
mvn spring-boot:run
