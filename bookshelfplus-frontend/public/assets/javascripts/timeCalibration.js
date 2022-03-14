// refer: https://juejin.cn/post/6844903960705236999
var xhr = new window.XMLHttpRequest;
xhr.responseType = "document";
// 通过get的方式请求当前文件
xhr.open("head", location.href);
xhr.send(null);
// 监听请求状态变化
xhr.onreadystatechange = function () {
    var time = null,
        curDate = null;
    if (xhr.readyState === 2) {
        // 获取响应头里的时间戳
        time = xhr.getResponseHeader("Date");
        // countDown(new Date(time).getTime());
        var timeOff = getTimeOff(new Date(time).getTime());

        var target = document.getElementById("timeOff");
        if (target) {
            target.innerHTML = "本地时间比服务器" + (timeOff > 0 ? "慢" : "快") + Math.abs(timeOff / 1000) + "秒";
        }
    }
};

function getTimeOff(time) {
    let serverTime = getNowDate(time, 8); // Head请求，返回服务器当前时间戳
    let localTime = getNowDate(Date.now(), 8); // 用户本地时间戳

    let timeOff = serverTime - localTime;
    return timeOff;
}

// function countDown(time) {
//     let targetTime =  Date.parse('2025/12/12 00:00:00');
//     let serverTime = getNowDate(time, 8); // Head请求，返回服务器当前时间戳
//     let localTime = getNowDate(Date.now(), 8); // 用户本地时间戳

//     let timeOff = serverTime - localTime;
//     let rightTargetTime = targetTime - timeOff; // 去除偏差后的目标时间

//     if(rightTargetTime <= localTime) {
//         console.log('按钮可点击')
//     } else {
//         console.log('按钮不可点击')
//     }
// }

function getNowDate(localTime, timeZone) {
    var timezone = timeZone || 8; //目标时区时间，东八区
    // 本地时间和格林威治的时间差，单位为分钟
    var offset_GMT = new Date().getTimezoneOffset();
    // 本地时间距 1970 年 1 月 1 日午夜（GMT 时间）之间的毫秒数
    var nowDate = localTime;
    var targetDate = nowDate + offset_GMT * 60 * 1000 + timezone * 60 * 60 * 1000;
    return targetDate;
}