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

router.get('/callback/:platform', function (req, res) {
    // 第三方登录回调页面
    res.render('callback', {
        title: getPageTitle("正在跳转"),
        platform: req.params.platform
    });
});

router.get('/dashboard/:group/:page', function (req, res) {

    // baseTemplate 基于哪个html模板渲染页面
    // pageTemplate 引入这个文件中的页面脚本
    if (req.params.group === "admin") {
        var dashboardPage = {
            "index": {
                title: "仪表盘",
                baseTemplate: "index",
            },
            "BookManage": {
                title: "书籍管理",
                baseTemplate: "form",
                pageTemplate: "BookManage",
            },
            "CategoryManage": {
                title: "分类管理",
                baseTemplate: "form",
                pageTemplate: "CategoryManage",
            },
            "UserManage": {
                title: "用户管理",
                baseTemplate: "form",
                pageTemplate: "UserManage",
            },
            "Account": {
                title: "账号设置",
                baseTemplate: "blank",
                pageTemplate: "Account",
            },
            "Debug": {
                title: "调试",
                baseTemplate: "blank",
                pageTemplate: "Debug",
            }
        };
        var headText = "后台管理";
    } else if (req.params.group === "user") {
        var dashboardPage = {
            "index": {
                title: "仪表盘",
                baseTemplate: "index",
            },
            "myBookshelf": {
                title: "我的书架",
                baseTemplate: "form",
                pageTemplate: "myBookshelf",
            },
            "myCollection": {
                title: "我的收藏",
                baseTemplate: "form",
                pageTemplate: "myCollection",
            },
            "myAccount": {
                title: "账号设置",
                baseTemplate: "blank",
                pageTemplate: "myAccount",
            }
        };
        var headText = "用户中心";
    }

    if (Object.keys(dashboardPage).indexOf(req.params.page) > -1) {
        var currentPage = dashboardPage[req.params.page];
        res.render(`dashboard/${currentPage.baseTemplate}`, {
            htmlTitle: getPageTitle(headText),
            title: currentPage.title,
            pageTemplate: "./" + req.params.group + "/" + currentPage.pageTemplate + ".html",
            dashboardPage: dashboardPage,
            group: req.params.group,
            page: req.params.page,
        });
        return;
    }
    throw new Error("404 Not Found");

    // 仪表盘
    if (req.params.page == "index") {
        res.render(`dashboard/${req.params.group}/index`, {
            title: title,
            headText: headText,
            headSubTextArr: {},
            links: navbarLinks,
            group: req.params.group,
            page: req.params.page,
        });
        return;
    }

    // 后台管理 新增或者修改页面
    if ((req.params.group === "admin" && ["UserManage", "BookManage", "CategoryManage", "Debug"].indexOf(req.params.page) > -1) ||
        (req.params.group === "user" && ["myBookshelf", "myCollection"].indexOf(req.params.page) > -1)) {
        res.render(`dashboard/${req.params.group}/manage`, {
            title: title,
            headSubTextArr: headSubTextArr,
            links: navbarLinks,
            group: req.params.group,
            page: req.params.page,
            // 引入Scripts
            generateCategoryHierarchy: ["BookManage", "CategoryManage"].indexOf(req.params.page) > -1
        });
        return;
    }

    // 后台管理 其他管理页面
    if ((req.params.group === "admin" && [].indexOf(req.params.page) > -1) ||
        (req.params.group === "user" && ["myAccount"].indexOf(req.params.page) > -1)) {
        res.render(`dashboard/${req.params.group}/${req.params.page}`, {
            title: title,
            headSubTextArr: headSubTextArr,
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
