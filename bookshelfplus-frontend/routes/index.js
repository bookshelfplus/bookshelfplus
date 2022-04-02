'use strict';
var express = require('express');
var router = express.Router();

function getPageTitle(title) {
    return `${title} | ${site.title}`
}
router.get('/', function (req, res) {
    res.render('index', {
        title: site.title,
        headText: "书栖网"
    });
});

router.get('/search', function (req, res) {
    res.render('search', {
        title: getPageTitle("搜一下"),
        headText: "搜一下"
    });
});

router.get('/category', function (req, res) {
    if (req.query.id) {
        // 分类详情页
        res.render('category-details', {
            title: getPageTitle("书籍分类"),
            headText: "书籍分类"
        });
    } else {
        // 分类首页
        res.render('category', {
            title: getPageTitle("书籍分类"),
            headText: "书籍分类"
        });
    }
});

router.get('/book', function (req, res) {
    res.render('book', {
        title: getPageTitle("书籍详情"),
        headText: "书籍详情"
    });
});

router.get('/about', function (req, res) {
    res.render('about', {
        title: getPageTitle("关于"),
        headText: "关于"
    });
});

router.get('/login', function (req, res) {
    res.render('login', {
        title: getPageTitle("用户登录"),
        headText: "用户登录"
    });
});

router.get('/register', function (req, res) {
    res.render('register', {
        title: getPageTitle("用户注册"),
        headText: "用户注册"
    });
});

router.get('/dashboard/:group/:page', function (req, res) {
    var navbarLinks = null;
    if (req.params.group === "admin") {
        navbarLinks = [
            {
                name: "仪表盘",
                url: "/dashboard/admin/index"
            }, {
                name: "用户管理",
                url: "/dashboard/admin/UserManage"
            }, {
                name: "书籍管理",
                url: "/dashboard/admin/BookManage"
            }, {
                name: "分类管理",
                url: "/dashboard/admin/CategoryManage"
            }
        ];
    } else if (req.params.group === "user") {
        navbarLinks = [
            {
                name: "仪表盘",
                url: "/dashboard/user/index"
            }, {
                name: "我的书架",
                url: "/dashboard/user/myBookshelf"
            }, {
                name: "我的收藏",
                url: "/dashboard/user/myCollection"
            }
        ];
    }

    // 仪表盘
    if (req.params.page == "index") {
        res.render(`dashboard/${req.params.group}/index`, {
            title: getPageTitle(req.params.group === "admin" ? "后台管理" : "用户中心"),
            headText: req.params.group === "admin" ? "后台管理" : "用户中心",
            links: navbarLinks,
            group: req.params.group,
            page: req.params.page,
        });
        return;
    }

    // 后台管理 其他管理页面
    if ((req.params.group === "admin" && ["UserManage", "BookManage", "CategoryManage"].indexOf(req.params.page) > -1) ||
        (req.params.group === "user" && ["myBookshelf", "myCollection"].indexOf(req.params.page) > -1)) {
        res.render(`dashboard/${req.params.group}/manage`, {
            title: getPageTitle(req.params.group === "admin" ? "后台管理" : "用户中心"),
            headText: req.params.group === "admin" ? "后台管理" : "用户中心",
            links: navbarLinks,
            group: req.params.group,
            page: req.params.page,
        });
        return;
    }
    throw new Error("404 Not Found");
});

router.get('/status', function (req, res) {
    res.render('status', {
        title: getPageTitle("网站状态检测"),
        headText: "网站状态检测"
    });
});

// 网站状态检测Api接口
router.get('/get-frontend-status', function (req, res) {
    res.end(JSON.stringify({ "server": "OK" }));
});

module.exports = router;
