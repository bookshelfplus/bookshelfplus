function getParams() {
    var params = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
        params[key] = decodeURIComponent(value);
    });
    return params;
}
getParams()