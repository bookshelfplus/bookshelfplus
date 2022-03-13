'use strict';
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res) {
    res.render('test', {
        title: "测试页 | " + site.title
    });
});

module.exports = router;
