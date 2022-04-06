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

router.get('/dashboard/:group/:page/:subpage?', function (req, res) {

    // baseTemplate 基于哪个html模板渲染页面
    // pageTemplate 引入这个文件中的页面脚本
    if (req.params.group === "admin") {
        var dashboardPage = {
            "index": {
                title: "仪表盘",
                baseTemplate: "index",
            },
            "book-manage": {
                title: "书籍管理",
                baseTemplate: "blank",
                pageTemplate: "BookManage",
                childPage: {
                    "add": {
                        title: "添加书籍",
                        baseTemplate: "form",
                        pageTemplate: "BookManage_Add",
                    },
                }
            },
            "category-manage": {
                title: "分类管理",
                baseTemplate: "form",
                pageTemplate: "CategoryManage",
            },
            "user-manage": {
                title: "用户管理",
                baseTemplate: "form",
                pageTemplate: "UserManage",
            },
            "account": {
                title: "账号设置",
                baseTemplate: "blank",
                pageTemplate: "Account",
            },
            "debug": {
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
            // "my-bookshelf": {
            //     title: "我的书架",
            //     baseTemplate: "form",
            //     pageTemplate: "myBookshelf",
            // },
            "my-collection": {
                title: "我的收藏",
                baseTemplate: "blank",
                pageTemplate: "myCollection",
            },
            "my-account": {
                title: "账号设置",
                baseTemplate: "blank",
                pageTemplate: "myAccount",
            }
        };
        var headText = "用户中心";
    }

    // function isChildPage(page) {
    //     console.log(page);
    //     // 查找 dashboardPage 中每一项的 childPage 字段，并与 page 比较
    //     for (var key in dashboardPage) {
    //         console.log(key);
    //         if (dashboardPage[key].childPage && dashboardPage[key].childPage[page]) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    console.log("req.params.page\t\t" + req.params.page)
    console.log("req.params.subpage\t" + req.params.subpage)
    // 如果请求的页面在 dashboardPage 中
    if (Object.keys(dashboardPage).indexOf(req.params.page) > -1) {
        // 当前请求的页面
        var currentPage = dashboardPage[req.params.page];

        // 如果请求的就是主页面，或者当前页没有子页面，则渲染主页面
        if (!req.params.subpage || !currentPage.childPage || Object.keys(currentPage.childPage).indexOf(req.params.subpage) === -1) {
            console.log("page");
            res.render(`dashboard/${currentPage.baseTemplate}`, {
                htmlTitle: getPageTitle(headText),
                title: currentPage.title,
                pageTemplate: "./" + req.params.group + "/" + currentPage.pageTemplate + ".html",
                dashboardPage: dashboardPage,
                group: req.params.group,
                page: req.params.page,
            });
        } else {
            // 如果当前 page 有 subpage，则渲染子页面
            var currentSubPage = currentPage.childPage[req.params.subpage];
            console.log("subpage");
            res.render(`dashboard/${currentSubPage.baseTemplate}`, {
                htmlTitle: getPageTitle(headText),
                title: currentSubPage.title,
                pageTemplate: "./" + req.params.group + "/" + currentSubPage.pageTemplate + ".html",
                dashboardPage: dashboardPage,
                group: req.params.group,
                page: req.params.page,
                subpage: req.params.subpage
            });
        }
        return;
    }

    // 如果请求的页面不在 dashboardPage 中，则渲染错误页面
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
