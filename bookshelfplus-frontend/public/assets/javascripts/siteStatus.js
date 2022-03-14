
var timeout = null;
$('.info').css("display", "none");

$(document).ready(function () {
    startCheck();
    $('#container').css("visibility", "");
    $(".removeAfterScriptLoaded").remove();
});

function startCheck() {
    if (timeout) clearTimeout(timeout);

    document.getElementById("checkBtn").value = "检测中";
    document.getElementById("checkBtn").disabled = "disabled";

    $('.info').html("loading...");
    $('.info-disabled').html("暂不提供检测");
    $('.info').css("display", "");

    $(".info-ok").removeClass("info-ok");
    $(".info-err").removeClass("info-err");

    //执行前等待一段时间，显得更加真实
    // 给定一个阶梯值，每次增加一个阶梯值同时加上一个随机数
    var i = 0, timeSpan = 80;
    setTimeout(checkOnlineStatus, timeSpan * ++i + Math.random() * 600);
    setTimeout(checkBackendStatus, timeSpan * ++i + Math.random() * 600);
    setTimeout(checkFrontendStatus, timeSpan * ++i + Math.random() * 600);
    setTimeout(checkTimeOff, timeSpan * ++i + Math.random() * 600);
    setTimeout(finishCheck, timeSpan * ++i + Math.random() * 600);
}

function checkOnlineStatus() {
    var onlineStatus = window.navigator.onLine;
    $("#onlineStatus").text(onlineStatus ? "已连接" : "您当前未连接互联网");
    $("#onlineStatus").addClass(onlineStatus ? "info-ok" : "info-err");
}

function checkBackendStatus() {
    var backendStatus = false;
    getRequest("/status/get", {})
        .then(function (response) {
            console.log("response.data", response.data);
            if (response.data.server === "OK") {
                $("#backendStatus").text("后台连接正常");
                $("#backendStatus").addClass("info-ok");
            } else {
                $("#backendStatus").text("服务器内部异常");
                $("#backendStatus").addClass("info-err");
            }
        })
        .catch(function (error) {
            $("#backendStatus").text("无法连接到服务器");
            $("#backendStatus").addClass("info-err");
        });
}

function checkFrontendStatus() {
    var backendStatus = false;
    getRequest("../get-frontend-status", {})
        .then(function (response) {
            console.log("response.data", response.data);
            if (response.data.server === "OK") {
                $("#frontendStatus").text("前台连接正常");
                $("#frontendStatus").addClass("info-ok");
            } else {
                $("#frontendStatus").text("服务器内部异常");
                $("#frontendStatus").addClass("info-err");
            }
        })
        .catch(function (error) {
            $("#frontendStatus").text("无法连接到服务器");
            $("#frontendStatus").addClass("info-err");
        });
}

function finishCheck() {
    document.getElementById("checkBtn").value = "重新检测";
    document.getElementById("checkBtn").disabled = "";

    if (timeout) clearTimeout(timeout);
    // timeout = setTimeout(startCheck, 10 * 1000);
}

function checkTimeOff(targetUrl = null) {
    if (!targetUrl) targetUrl = location.href;

    // refer: https://juejin.cn/post/6844903960705236999
    var xhr = new window.XMLHttpRequest;
    xhr.responseType = "document";
    // 通过get的方式请求当前文件
    xhr.open("head", targetUrl);
    xhr.send(null);

    // // 考虑请求花费时间
    // var requestStartTime = getNowDate(Date.now(), 8);
    // var requestEndTime;

    // 监听请求状态变化
    xhr.onreadystatechange = function () {
        var time = null,
            curDate = null;
        if (xhr.readyState === 2) {
            // 获取响应头里的时间戳
            time = xhr.getResponseHeader("Date");
            // console.log("请求结束时本地时间(东八区时间): " + new Date(time).getTime());

            // requestEndTime = Date.now();
            // console.log("请求开始时本地时间(用于计算时间差): " + requestStartTime);
            // console.log("请求结束时本地时间(用于计算时间差): " + requestEndTime);
            // console.log("发送请求到收到响应的时间差: " + (requestEndTime - requestStartTime));
            // console.log("发送请求到收到响应的时间差/2: " + (requestEndTime - requestStartTime) / 2);

            var timeOff = getTimeOff(new Date(time).getTime());
            // timeOff = timeOff + (requestEndTime - requestStartTime) / 2;

            var target = document.getElementById("timeOff");
            if (target) {
                // 产生随机时间（测试功能用）
                // timeOff = Math.random() * 10 * 60 * 1000;
                var spanSeconds = Math.abs(timeOff / 1000);
                target.innerHTML = "本地时间比服务器" + (timeOff > 0 ? "慢" : "快") + spanSeconds.toFixed(3) + "秒";
                $(target).addClass((spanSeconds >= 5 * 60) ? "info-err" : "info-ok");
            }
        }
    };

    function getTimeOff(time) {
        let serverTime = getNowDate(time, 8); // Head请求，返回服务器当前时间戳
        let localTime = getNowDate(Date.now(), 8); // 用户本地时间戳

        let timeOff = serverTime - localTime;
        return timeOff;
    }

    function getNowDate(localTime, timeZone) {
        var timezone = timeZone || 8; //目标时区时间，东八区
        // 本地时间和格林威治的时间差，单位为分钟
        var offset_GMT = new Date().getTimezoneOffset();
        // 本地时间距 1970 年 1 月 1 日午夜（GMT 时间）之间的毫秒数
        var nowDate = localTime;
        var targetDate = nowDate + offset_GMT * 60 * 1000 + timezone * 60 * 60 * 1000;
        return targetDate;
    }
}