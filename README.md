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

> 所需环境：Java JDK 8+，Maven，MySQL 5.7+，nodejs等

### 安装环境

```bash
# 安装 nodejs
# 官方网站：https://nodejs.org/zh-cn/
# 下载地址：https://nodejs.org/dist/v16.14.0/node-v16.14.0-x64.msi

# 安装 JDK 8

# 安装 Maven

# 安装 MySQL 5.7

# 导入数据库SQL脚本

# nodemon（可选）
# 开发使用 nodemon，代码变动后自动重启。
# 使用以下代码安装 nodemon
npm i nodemon -g
```



## 功能展示

### 功能列表

- [x] 首页。简约（说白了其实就是懒），一个搜索框就够了。后期考虑添加热门搜索功能。
- [ ] 书籍列表页，也是搜索结果页。就是一个书单列表，带分页功能。
- [ ] 书籍详情页。主要是显示书籍的各种详细信息（书名，简介，缩略图等），还有下载方式，同时还有反馈功能（连接失效反馈，版权问题申诉下架等）
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



## 开发工具

前端：VS Code，后端：idea，数据库：MySQL



## 注意事项

- nginx启动目录不能包含中文，否则无法启动

