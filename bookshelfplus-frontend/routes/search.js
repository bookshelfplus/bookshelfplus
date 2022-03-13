'use strict';
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res) {
    res.render('search', {
        title: site.title,
        page: "首页"
    });
});

module.exports = router;
