// 请求头 Content-Type
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

// 带参数的post请求
function postRequest(url, params) {
    var data = [];
    for (var key in params) {
        data.push(key + '=' + encodeURIComponent(params[key]));
    }
    return axios({
        method: 'post',
        url: url,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: data.join('&'),
    })
}

// 带参数的get请求
function getRequest(url, params) {
    return axios({
        method: 'get',
        url: url,
        headers: {
            'Content-Type': 'application/json'
        },
        params: params,
    })
}

// // 带参数的put请求
// function putRequest(url, params) {
//     return axios({
//         method: 'put',
//         url: url,
//         data: params
//     })
// }