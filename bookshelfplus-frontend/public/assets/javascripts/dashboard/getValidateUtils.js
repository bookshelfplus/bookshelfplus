// 表单验证基本函数
function getValidateUtils() {
    var validateUtils = {
        // 验证是否为空
        isEmpty: function (value) {
            return value === undefined || value === null || value === '';
        },
        // 验证是否为数字
        isNumber: function (value) {
            return !isNaN(value);
        },
        // 验证是否为整数
        isInteger: function (value) {
            return this.isNumber(value) && parseInt(value) === value;
        },
        // 验证是否为正整数
        isPositiveInteger: function (value) {
            return this.isInteger(value) && value > 0;
        },
        // 验证是否为对象
        isObject: function (value) {
            return typeof value === 'object';
        },
        // 验证是否为数组
        isArray: function (value) {
            return Array.isArray(value);
        },
        // 验证是否为字符串
        isString: function (value) {
            return typeof value === 'string';
        },
        // 验证是否为布尔值
        isBoolean: function (value) {
            return typeof value === 'boolean';
        },
        // 验证是否为函数
        isFunction: function (value) {
            return typeof value === 'function';
        },
        // 验证是否为日期
        isDate: function (value) {
            return value instanceof Date;
        },
        // 验证是否为正则表达式
        isRegExp: function (value) {
            return value instanceof RegExp;
        },
    };
}