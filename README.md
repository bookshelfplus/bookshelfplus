<div align="center">
<h1>书栖网 网站开源项目</h1>
<p>一个完全免费无门槛的计算机类电子书下载网站</p>
</div>

项目官网：https://bookshelf.plus

开源仓库：<a href="https://github.com/bookshelfplus/bookshelfplus" target="_blank">GitHub</a>  <a href="https://gitee.com/bookshelfplus/bookshelfplus" target="_blank">Gitee</a>

![](docs/image/homepage.png)

## 简介

前项目为书栖网官网开源项目，你也可以通过这个项目搭建一个属于自己的电子书分享与管理平台。

> 如需获取计算机类电子书，请访问https://bookshelf.plus/ ，或前往Git仓库👉（[GitHub](https://github.com/only-4/computer-related-books)、[Gitee](https://gitee.com/only4/computer-related-books)）



## 开始使用

> 所需环境：Java JDK 8，Maven，MySQL 5.7+，nodejs，Redis等

### 安装环境

```bash
# 安装 nodejs
# 官方网站：https://nodejs.org/zh-cn/
# 下载地址：https://nodejs.org/dist/v16.14.0/node-v16.14.0-x64.msi

# 安装 JDK 8
# TODO

# 安装 Maven
# TODO

# 安装 MySQL (5.7 以上版本)
# TODO

# 安装 Redis
# TODO

# pm2
npm i pm2 -g

# nodemon (可选)
# 开发使用 nodemon (代码变动后自动重启)
# 使用以下代码安装 nodemon
npm i nodemon -g
```



### 环境配置

#### 配置国内镜像源

##### 配置 npm 国内镜像源

```bash
# 查看当前配置的镜像源 默认为: https://registry.npmjs.org/
npm config get registry

# 修改为国内镜像源 这里使用淘宝镜像源: https://registry.npm.taobao.org/
npm config set registry https://registry.npm.taobao.org/
```



##### 配置 Maven 国内镜像源

编辑 Maven 安装目录下 `conf/settings.xml` 文件，如下

```xml
  <mirrors>
    <!-- mirror
     | Specifies a repository mirror site to use instead of a given repository. The repository that
     | this mirror serves has an ID that matches the mirrorOf element of this mirror. IDs are used
     | for inheritance and direct lookup purposes, and must be unique across the set of mirrors.
     |
    <mirror>
      <id>mirrorId</id>
      <mirrorOf>repositoryId</mirrorOf>
      <name>Human Readable Name for this Mirror.</name>
      <url>http://my.repository.com/repo/path</url>
    </mirror>
     -->
    <!-- ######### 从这里开始 ######### -->
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>https://maven.aliyun.com/repository/public</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <!-- ######### 到这里结束 ######### -->
  </mirrors>
```



### 项目配置

```bash
# 配置 nginx.conf
# TODO

# [前端]
# 配置后台 Api 地址
# TODO
# 配置前端网站名称
# TODO

# [后端]
# TODO
```

配置好后，需要重启 nginx

```bash
# 重启 nginx
nginx -s reload
# 或者使用其他重启命令
# 例如 Ubuntu 系统下使用 systemctl restart nginx
```



### 处理依赖

```bash
# [前端]
npm install

# [后端]
mvn clean install
```



### 数据导入

```bash
# [数据库]
# 导入数据库 SQL 脚本
# TODO
```



### 编译后端项目

```bash
mvn clean install

# 如果提示: Cannot create resource output directory: xxx
# 那么说明权限不够，在前面加上 sudo
# sudo mvn clean install
```

编译成功后，可以看到如下输出（其中有 `BUILD SUCCESS`）：

```bash
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  12:17 min
[INFO] Finished at: 2022-04-03T14:22:18+08:00
[INFO] ------------------------------------------------------------------------
```



### 启动项目

```bash
# [前端]
# 启动前端服务 (默认监听 3000 端口)
npm run prod


# [后端]
# 启动后端服务 (默认监听 8090 端口)
mvn install -Djar.forceCreation spring-boot:run
# 如果提示: Cannot create resource output directory: xxx
# 那么说明权限不够，在前面加上 sudo
# sudo mvn install -Djar.forceCreation spring-boot:run

# 启动 nginx
# TODO
```

若启动时提示以下 `WARNING`，是因为 `JDK` 版本过高，一般不影响使用。

```bash
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```





### 停止项目

```bash
# 停止 nginx
# 有如下几种停止方式
nginx -s quit # 从容停止
nginx -s stop # 立即停止
systemctl stop nginx # 使用 systemctl 停止
# 也可直接杀掉 nginx 进程

# 停止前端服务
npm run prod-stop

# 停止后端服务
# TODO
```



## 功能展示

### 功能列表

- [x] 首页。简约（说白了其实就是懒），一个搜索框就够了。后期考虑添加热门搜索功能。
- [x] 书籍列表页，也是搜索结果页。就是一个书单列表，带分页功能。
- [x] 书籍详情页。主要是显示书籍的各种详细信息（书名，简介，缩略图等），还有下载方式，同时还有反馈功能（连接失效反馈，版权问题申诉下架等）
- [x] 分类列表页
- [x] 分类详情页
- [ ] 管理员后台。
- [ ] 用户登录后台。

### 功能截图

截图待补充...



## 项目架构

> 项目前后端分离开发，使用了不同的技术，通过nginx进行反向代理

**前端**采用`nodejs`开发，使用`axios`、`jQuery`等组件。

**后端**采用`SpringBoot`开发，数据库连接使用`mybatis`、`alibaba druid`，接口文档生成使用`swagger2`，参数验证采用`hibernate`，日期时间处理使用`joda-time`工具类，同时还使用了`lombok`简化代码。

**数据库**采用`MySQL`，会话缓存采用`redis`。

**反向代理**使用`nginx`。

**对象存储**对接腾讯云COS存储（`cos_api`）。

**第三方登录**使用 `JustAuth` 开源项目（配合 `okhttp3`）



## 开发工具

前端：VS Code，后端：idea，数据库：Navicat

> 以上为项目开发时所使用的开发工具，也可以使用其他的开发工具打开，但建议使用以上工具打开本项目，避免产生一些莫名错误。



## 注意事项

- nginx启动目录不能包含中文，否则无法启动
