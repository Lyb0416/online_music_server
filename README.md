# 🎵 在线音乐平台 - 服务端（Server）

本项目是在线音乐平台的 **后端服务**，基于 **Spring Boot + MyBatis-Plus + MySQL + Redis + JWT** 构建，为客户端（User Client）与管理端（Admin Dashboard）提供完整 API 能力，包括歌曲管理、歌手管理、用户登录、支付、推荐系统等核心功能。

---

## 🚀 功能概述

### 🔐 用户模块
- 手机号 + 验证码登录  
- 用户注册、信息修改  
- 用户收藏、播放记录  

### 🎵 音乐模块
- 歌手管理  
- 歌曲管理  
- 歌单管理  
- 评论模块  
- 图片 / 音频文件上传  

### 💰 支付模块
- 生成订单  
- 集成 **支付宝 Page Pay** 支付  
- 扫码支付后自动回调  

### 🤖 推荐系统模块
- 基于 **KNN 协同过滤算法** 的个性化推荐  
- 每日定时任务自动生成推荐列表  
- 用户端首页访问自动读取推荐数据  

### ⚙️ 系统能力
- JWT 权限认证  
- Redis 缓存验证码、推荐数据  
- MyBatis-Plus 操作数据库  
- GlobalExceptionHandler 全局异常处理  
- CORS 跨域支持  
- Swagger 自动生成接口文档  

---

## 📁 项目结构

```bash
src/
├─ main/java/com/lyb/olinemusicserver/
│  ├─ algorithm/          # KNN 推荐算法实现
│  ├─ common/             # 全局返回包装、异常、枚举
│  ├─ config/             # Redis、JWT、拦截器、CORS 配置
│  ├─ constant/           # 常量定义
│  ├─ controller/         # 控制层接口：用户/歌手/歌曲/支付/推荐等
│  ├─ mapper/             # MyBatis-Plus Mapper 接口
│  ├─ model/
│  │  ├─ domain/          # 实体类（数据库表对应）
│  │  └─ request/         # 请求对象 DTO
│  ├─ service/            # 业务接口
│  │  └─ impl/            # 业务实现层
│  └─ util/               # 工具类（文件上传、验证码、加密等）
└─ resources/
   ├─ application.yml     # 配置文件（数据库、Redis、OSS、Alipay）
   └─ mapper/             # XML SQL 配置
```

---

## 🛠 技术栈

| 领域       | 技术                       |
|------------|-----------------------------|
| 主体框架   | Spring Boot                 |
| ORM        | MyBatis-Plus               |
| 数据库     | MySQL                      |
| 缓存       | Redis                      |
| 身份认证   | JWT                        |
| 文件上传   | 阿里云 OSS / 本地上传      |
| 支付       | 支付宝 Page Pay            |
| 推荐算法   | KNN 协同过滤               |
| API 文档   | Swagger                    |
| 其他       | Lombok、Quartz（定时任务） |

---

## ⚙️ 项目启动

### 1. 配置 MySQL & Redis

修改 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/music?serverTimezone=Asia/Shanghai
    username: root
    password: 123456

redis:
  host: localhost
  port: 6379
```

---

### 2. 导入数据库 SQL

使用你的 SQL 文件导入到 MySQL 数据库即可。

---

### 3. 启动项目

```bash
mvn spring-boot:run
```

---

### 默认服务地址

👉 http://localhost:8888

---

## 🤖 推荐算法说明（简述）

系统采用 **KNN 协同过滤推荐算法**：

- 记录用户播放 / 收藏行为  
- 计算用户之间的相似度  
- 根据 K 个相似用户（近邻）的行为生成推荐  
- 每日定时任务自动更新推荐结果  
- 首页从 Redis 读取推荐缓存，性能大幅提升  

---

## 📦 支付流程（概述）

1. 用户点击 “购买歌曲 / 歌单”
2. 后端生成订单（`out_trade_no`）
3. 调用 **支付宝 Page Pay** 接口
4. 客户端跳转到支付宝扫码页面
5. 用户支付成功后 → 支付宝回调 `notify_url`
6. 后端执行 **验签** 并更新订单状态

---
