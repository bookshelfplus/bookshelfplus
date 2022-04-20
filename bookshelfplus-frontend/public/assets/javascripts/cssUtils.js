function copyToClipboard(content) {

    // firefox 下必须为用户同步调用，不可以使用异步调用隔开
    // refer: http://www.caotama.com/43769.html

    function oldMethod() {
        var aux = document.createElement('input');
        aux.setAttribute('value', content);
        // 在 firefox for windows 93.0 版本下测试，添加这一行会无法复制到剪切板
        // aux.style.display = "none";
        document.body.appendChild(aux);
        aux.select();
        aux.focus();
        document.execCommand('copy');
        document.body.removeChild(aux);

        console.log("isSuccess", isSuccess);
    }
    if (navigator.clipboard) {
        navigator.permissions.query({ name: "clipboard-write" }).then(result => {
            if (result.state == "granted") {
                navigator.clipboard.writeText(content)
                    .then(function () {
                        // 复制成功
                        console.log("API复制成功");
                    }, function () {
                        // 使用API复制失败
                        console.log("API复制失败");
                        oldMethod();
                    })
            } else {
                console.log("支持API，但没有权限，使用旧方法复制");
                oldMethod();
            }
        });
    } else {
        console.log("不支持API，使用旧方法复制");
        oldMethod();
    }
}
function showTip(e, text) {
    var $i = $("<span>").text(text);
    $("body").append($i);
    var x = e.pageX - $i.outerWidth() / 2,
        y = e.pageY - $i.outerHeight() - 1;
    $i.css({
        "position": "absolute",
        "z-index": "10000",
        "top": y,
        "left": x,
        "color": "red",
        "font-size": "14px",
        "font-weight": "bold",
    });
    $i.animate({
        "top": y - 60,
        "left": x,
        "opacity": "0"
    }, 600, function () {
        $i.remove();
    });
    e.stopPropagation();
}