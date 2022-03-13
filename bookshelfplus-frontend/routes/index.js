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

router.get('/about', function (req, res) {
    res.render('about', {
        title: "关于"
    });
});

module.exports = router;