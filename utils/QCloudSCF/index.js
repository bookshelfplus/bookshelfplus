"use strict";

// 后端配置信息
const backendApi = {
  hostname: "dev.bookshelf.plus",
  path: "/api/file/upload/cos-check-file-state",
};

function postback(event, context) {
  return new Promise(function (resolve) {
    const querystring = require("querystring");
    const http = require("http");

    const postData = querystring.stringify({
      event: JSON.stringify(event),
      context: JSON.stringify(context),
    });

    console.log("⭐postData");
    console.log(postData);

    const options = {
      hostname: backendApi.hostname,
      port: 80,
      path: backendApi.path,
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        "Content-Length": Buffer.byteLength(postData),
      },
    };

    const req = http.request(options, (res) => {
      console.log(`⭐STATUS: ${res.statusCode}`);
      console.log(`⭐HEADERS: ${JSON.stringify(res.headers)}`);
      res.setEncoding("utf8");
      res.on("data", (chunk) => {
        console.log(`⭐BODY: ${chunk}`);
        resolve({ status: "success", data: JSON.parse(chunk) });
      });
      res.on("end", () => {
        console.log("⭐No more data in response.");
      });
    });

    req.on("error", (e) => {
      console.error(`⭐problem with request: ${e.message}`);
      resolve({ status: "failed", data: JSON.parse(e) });
    });

    // Write data to request body
    req.write(postData);
    req.end();
  });
}
exports.main_handler = async (event, context) => {
  console.log("👉event");
  console.log(event);
  console.log("👉JSON.stringify(event)");
  console.log(JSON.stringify(event));
  console.log('👉event["non-exist"]');
  console.log(event["non-exist"]);
  console.log("👉context");
  console.log(context);

  var result = await postback(event, context);
  var tryTime = 0; // 如果失败那么重试几次
  while (tryTime++ < 3 && result.status !== "success") {
    await new Promise(function (resolve) {
      setTimeout(function () {
        resolve();
      }, 100);
    });
    result = await postback(event, context);
  }
  result.tryTime = tryTime;
  if (result.status !== "success") {
    // 如果重试 3 次仍然失败，那么抛出异常，等待重新出发当前云函数
    throw new Exception("重试 3 次仍然无法请求后端服务，请检查后端是否启动，网络是否连通，以及是否支持以 http 协议发送请求等。");
  }
  return result;
};
