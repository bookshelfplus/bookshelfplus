'use strict';
var express = require('express');
var router = express.Router();
const bodyParser = require('body-parser');
var rename = require('gulp-rename');
const fs = require('fs');

// refer: https://blog.csdn.net/qq_36812165/article/details/108363511 , https://github.com/font-size/node-fontmin-project

// 设置可用字体
const fonts = [
    { font: 'HarmonyOS_Sans_SC_Regular', name: 'HarmonyOS Sans' },
]

// 路径是以 app.js 所在路径为准的
var destPath = './public/fontmin/';    // 输出路径

// 转化参数设置
router.use(bodyParser.json());

router.use(bodyParser.urlencoded({
    extended: true
}));

// post 接口
router.post('/getfont', function (request, response) {
    const params = request.body
    const font = params.font || "HarmonyOS_Sans_SC_Regular"
    const text = params.text
    // 如果传递的font字体在后台没有就返回400
    const item = fonts.find(e => e.font === font)

    var targetFileName = require('crypto').createHash('md5').update(text).digest('hex') + '.ttf';
    var exists = fs.existsSync(destPath + "/" + targetFileName);
    if (exists) {
        response.send({
            url: '/fontmin/' + targetFileName,
            font: font,
            info: "使用已生成字体"
        });
        return true;
    } else {
        if (item && text) {
            fontmin(font, text, targetFileName, function (e) {
                if (e === 'done') {
                    // 拼接参数 返回请求
                    let back = {
                        url: '/fontmin/' + targetFileName,
                        font: font,
                        info: "新生成字体"
                    }
                    response.send(back);
                }
            });
        } else {
            response.status(400);
            response.send(JSON.stringify({
                "errMsg": '没有请求的字体文件或没有传递文字'
            }));
        }
    }
});

module.exports = router;

function fontmin(font, text, targetFileName, callback) {
    const Fontmin = require('fontmin')
    var srcPath = `./fonts/${font}.ttf`; // 字体源文件
    var text = text || '';

    // 初始化
    var fontmin = new Fontmin()
        .src(srcPath)               // 输入配置
        .use(rename(targetFileName))
        .use(Fontmin.glyph({        // 字型提取插件
            text: text              // 所需文字
        }))
        // .use(Fontmin.ttf2eot())     // eot 转换插件
        // .use(Fontmin.ttf2woff())    // woff 转换插件
        // .use(Fontmin.ttf2svg())     // svg 转换插件
        // .use(Fontmin.css())         // css 生成插件
        .dest(destPath);            // 输出配置

    // 执行
    fontmin.run(function (err, files, stream) {
        if (err)                      // 异常捕捉
            console.error(err);
        return callback('done')        // 成功
    });
}