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
            "category-manage": {
                title: "分类管理",
                baseTemplate: "form",
                pageTemplate: "CategoryManage",
            },
            "book-manage": {
                title: "书籍管理",
                baseTemplate: "table",
                pageTemplate: "BookManage",
                childPage: {
                    "detail": {
                        title: req.query.id ? "修改书籍" : "添加书籍",
                        baseTemplate: "form",
                        pageTemplate: "BookManage_Detail",
                    },
                }
            },
            "file-manage": {
                title: "文件管理",
                baseTemplate: "table",
                pageTemplate: "FileManage",
                childPage: {
                    "detail": {
                        title: "文件详情",
                        baseTemplate: "blank",
                        pageTemplate: "FileManage_Detail",
                    },
                    "object-manage": {
                        title: "文件对象管理",
                        baseTemplate: "table",
                        pageTemplate: "FileManage_ObjectManage",
                    },
                    "object-detail": {
                        title: "文件对象详情",
                        baseTemplate: "blank",
                        pageTemplate: "FileManage_ObjectManage_Detail",
                    },
                    "upload": {
                        title: "上传文件",
                        baseTemplate: "blank",
                        pageTemplate: "FileManage_Upload",
                    },
                }
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
                title: "系统配置",
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
                baseTemplate: "table",
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

    // 如果请求的页面在 dashboardPage 中
    if (Object.keys(dashboardPage).indexOf(req.params.page) > -1) {
        // 当前请求的页面
        var currentPage = dashboardPage[req.params.page];

        // 如果请求的就是主页面，或者当前页没有子页面
        if (!req.params.subpage) {
            // 渲染主页面
            console.log("page", req.params.page, req.params.subpage);
            res.render(`dashboard/${currentPage.baseTemplate}`, {
                pageUrl: (req._parsedUrl.pathname + "/").replace("//", "/"),
                htmlTitle: getPageTitle(headText),
                title: currentPage.title,
                pageTemplate: "./" + req.params.group + "/" + currentPage.pageTemplate + ".html",
                dashboardPage: dashboardPage,
                group: req.params.group,
                page: req.params.page,
            });
        } else {
            // 渲染子页面
            if (!currentPage.childPage || Object.keys(currentPage.childPage).indexOf(req.params.subpage) === -1) {
                // 请求的子页面不存在，直接返回404
                throw new Error("404 Not Found");
            }
            // 如果当前 page 有 subpage，则渲染子页面
            var currentSubPage = currentPage.childPage[req.params.subpage];
            console.log("subpage", req.params.page, req.params.subpage);
            res.render(`dashboard/${currentSubPage.baseTemplate}`, {
                pageUrl: (req._parsedUrl.pathname + "/").replace("//", "/"),
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
