// 统一 localStorage 的读取与写入

var localStorageUtils = {
    // 检查浏览器 localStorage 是否支持
    checkLocalStorage: function () {
        try {
            localStorage.setItem('test', 'test');
            localStorage.removeItem('test');
            return true;
        } catch (e) {
            swal("您的浏览器不支持localStorage，请更换浏览器！").then(function () {
                window.location.href = "/";
            });
            return false;
        }
    },

    // 获取 localStorage
    get: function (key) {
        return localStorage.getItem(key);
        // return JSON.parse(localStorage.getItem(key));
    },
    // 设置 localStorage
    set: function (key, value) {
        localStorage.setItem(key, value);
        // localStorage.setItem(key, JSON.stringify(value));
    },
    // 删除 localStorage
    remove: function (key) {
        localStorage.removeItem(key);
    },
    // 清空 localStorage
    clear: function () {
        localStorage.clear();
    },

    // 用户登录
    userLogin: function ({ token, is_admin }) {
        this.set('token', token);
        this.set('is_admin', is_admin);
    },

    // 用户退出登录
    userLogout: function () {
        this.remove('token');
        this.remove('is_admin');
    },

    // 获取用户登录信息 (从浏览器本地缓存读取)
    getUserLogin: function () {
        return {
            token: this.get('token'),
            is_admin: this.get('is_admin')
        }
    },
    getToken: function () {
        return this.get('token');
    },
    getIsAdmin: function () {
        return this.get('is_admin') === 'true';
    },
    getIsUser: function () {
        return this.get('is_admin') === 'false';
    },

    getLoginStatus: function () {
        return !!this.get('token') && !!this.get('is_admin');
    },
};
