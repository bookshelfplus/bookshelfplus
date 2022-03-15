'use strict';
var debug = require('debug')('my express app');
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
let dotenv = require('dotenv');

// 读取配置文件
dotenv.config('./env');
// console.log(process.env);
// 引入路由文件
var indexRoute = require('./routes/index');
var fontminRoute = require('./routes/fontmin');
const { copyFileSync } = require('fs');

// 创建应用
var app = express();

// gzip
if (process.env.gzip == "true") {
    const compression = require('compression');
    app.use(compression());
    console.log("[GZIP] gzip enabled");
} else {
    console.log("[GZIP] gzip disabled");
}

// 设置视图引擎 view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs'); // pug
app.engine('.html', require('ejs').__express);
app.set('view engine', 'html');

app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// 路由
app.use('/', indexRoute);
app.use('/fontmin', fontminRoute);

// 捕获404并转发到错误处理程序 catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// 错误处理 error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    console.log("[NODE_ENV] development");
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err,
            title: '出错啦'
        });
    });
} else {
    // production
    console.log("[NODE_ENV] production");
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});

app.set('port', process.env.PORT || 3000);

var server = app.listen(app.get('port'), function () {
    debug('Express server listening on port ' + server.address().port);

    // 引入站点配置文件
    global.site = require("./settings.json");

    // 取得API路径
    if (!global.site) {
        console.log('settings.json is not defined');
        process.exit(1);
    }
    // console.log("[global.site]");
    // console.log(JSON.stringify(global.site));
    console.log(" ***************************** 启动成功 ***************************** ");
});


// 注册SIGINT信号事件
process.on('SIGINT', function () {
    console.clear();
    console.log(" ***************************** 正在清理 ***************************** ");

    require('./cleanup.js');

    console.log(" ************************* 清理完毕，已退出 ************************* ");
    console.log('Exit now!');

    process.exit();
    // return true;
});