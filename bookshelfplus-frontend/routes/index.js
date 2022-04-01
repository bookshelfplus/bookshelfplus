'use strict';
var express = require('express');
var router = express.Router();

router.get('/', function (req, res) {
    res.render('index', {
        title: site.title
    });
});

router.get('/search', function (req, res) {
    res.render('search', {
        title: "搜一下"
    });
});

router.get('/category', function (req, res) {
    res.render('category', {
        title: "书籍分类"
    });
});

router.get('/book', function (req, res) {
    res.render('book', {
        title: "书籍详情"
    });
});

router.get('/about', function (req, res) {
    res.render('about', {
        title: "关于"
    });
});

router.get('/login', function (req, res) {
    res.render('login', {
        title: "用户登录"
    });
});

router.get('/register', function (req, res) {
    res.render('register', {
        title: "用户注册"
    });
});

router.get('/dashboard/admin/index', function (req, res) { // '/admin(/index)?'
    res.render('dashboard/admin/index', {
        title: "后台管理（管理员）"
    });
});

router.get('/dashboard/user/index', function (req, res) { // '/admin(/index)?'
    res.render('dashboard/user/index', {
        title: "后台管理"
    });
});

router.get('/status', function (req, res) {
    res.render('status', {
        title: "网站状态检测"
    });
});

// 网站状态检测Api接口
router.get('/get-frontend-status', function (req, res) {
    res.end(JSON.stringify({ "server": "OK" }));
});

module.exports = router;
