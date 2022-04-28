function getNetdiskShareDetails(shareText) {
    var result = {
        success: false,
        url: null,
        pwd: "",
        platform: null,
        origin: shareText
    };
    try {
        result.url = shareText.match(/https?:\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/g)[0];
        var pwdRegExpResult = shareText.match(/提取码[:|：][ ]*([^ \n]*)[ |\n]?/); //.match(/提取码[:|：] *(.*?)[ |\n]?/);
        // console.log(shareText, pwdRegExpResult);
        // console.log("--------")
        // return;
        result.pwd = pwdRegExpResult && pwdRegExpResult.length > 1 ? pwdRegExpResult[1] : "";
        result.platform = result.url.indexOf("pan.baidu.com") > -1
            ? { display: "百度网盘", name: "BAIDU_NETDISK" }
            : (result.url.indexOf("aliyundrive.com") > -1
                ? { display: "阿里云盘", name: "ALIYUN_DRIVE" }
                : { display: null, name: null }
            );
        if (!result.platform || !result.platform.name) {
            throw new Exception("error");
        }
        result.success = true;
    } catch (error) {

    }
    // console.log(result);
    return result;
}


// // 百度网盘（手机版）有密码
// getNetdiskShareDetails(`--来自百度网盘超级会员V3的分享
// hi，这是我用百度网盘分享的内容~复制这段内容打开「百度网盘」APP即可获取
// 链接:https://pan.baidu.com/s/1cxv2Rw5WgXrnE-kfyfkpxA?pwd=k2hk
// 提取码:k2hk`);

// // 百度网盘（电脑版）有密码
// getNetdiskShareDetails(`链接：https://pan.baidu.com/s/1YBeqYwrka7Z9G0H0q0GO_w?pwd=60va
// 提取码：60va
// --来自百度网盘超级会员V3的分享`);

// // 百度网盘（PC端）有密码
// getNetdiskShareDetails(`链接：https://pan.baidu.com/s/1YBeqYwrka7Z9G0H0q0GO_w?pwd=60va
// 提取码：60va
// 复制这段内容后打开百度网盘手机App，操作更方便哦`);

// // 百度网盘无密码
// console.log("👇无提取码");
// getNetdiskShareDetails(`链接：https://pan.baidu.com/s/1YBeqYwrka7Z9G0H0q0GO_w?pwd=60va`);

// // 阿里云盘有密码
// getNetdiskShareDetails(`「实战Nginx.pdf」https://www.aliyundrive.com/s/pquxbpPfj2u 提取码: en86
// 点击链接保存，或者复制本段内容，打开「阿里云盘」APP ，无需下载极速在线查看，视频原画倍速播放。`);

// // 阿里云盘无密码
// console.log("👇无提取码");
// getNetdiskShareDetails(`「ZenTaoPMS.16.4.win64.exe」https://www.aliyundrive.com/s/aZLhoqNFyiv
// 点击链接保存，或者复制本段内容，打开「阿里云盘」APP ，无需下载极速在线查看，视频原画倍速播放。`);

// // 其他情况
// console.log("👇以下是非分享链接");
// getNetdiskShareDetails(`非链接`);
// getNetdiskShareDetails(`其他的链接https://www.baidu.com/s?wd=60va dsadsads`);
// getNetdiskShareDetails(`链接：https://pan.woshijiade.com/s/1YBeqYwrka7Z9G0H0q0GO_w?pwd=60va
// 提取码：60va`);
