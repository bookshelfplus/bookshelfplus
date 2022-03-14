// 带参数的post请求
function postRequest(url, params) {
    return axios({
        method: 'post',
        url: url,
        data: params,
    })
}

// 带参数的get请求
function getRequest(url, params) {
    return axios({
        method: 'get',
        url: url,
        params: params,
    })
}

// 带参数的put请求
function putRequest(url, params) {
    return axios({
        method: 'put',
        url: url,
        data: params
    })
}