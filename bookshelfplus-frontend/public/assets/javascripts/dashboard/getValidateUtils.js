// 表单验证基本函数
function getValidateUtils() {
    var validateUtils = {

        validateValue: "",
        result: true,
        msg: [],

        setValue: function (validateValue) {
            this.validateValue = validateValue;
            return this;
        },

        // 验证是否为空
        notNull: function (notValidMsg) {
            let value = this.validateValue;
            if (value === null || value === undefined) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为空 或者为空字符串
        notEmpty: function (notValidMsg) {
            let value = this.validateValue;
            if (value === null || value === undefined || value === "") {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为空 或者为空字符串 或者 trim() 为空字符串
        notEmptyAfterTrim: function (notValidMsg) {
            let value = this.validateValue;
            if (value === null || value === undefined || value === "" || value.trim() === "") {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证字符串长度
        length: function (min, max, notValidMsg) {
            let value = this.validateValue;
            if (value.length < min || value.length > max) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否包含特殊字符
        specialChar: function (notValidMsg) {
            let value = this.validateValue;
            let reg = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
            if (reg.test(value)) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 不是字符串
        notString: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "string") {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 不是数字
        notNumber: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "number" || isNaN(value)) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 不是字符串类型的数字
        notStringNumber: function (notValidMsg) {
            let value = this.validateValue;
            if ((typeof value !== "string" && typeof value !== "number") || isNaN(value)) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为整数
        notInteger: function (notValidMsg) {
            let value = this.validateValue;
            // parseInt(value) === value
            if (typeof value !== "number" || isNaN(value) || value % 1 !== 0) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为字符串类型的整数
        notStringInteger: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "string" || isNaN(value) || value % 1 !== 0) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为正整数
        notPositiveInteger: function (notValidMsg) {
            let value = this.validateValue;
            if ((typeof value !== "string" && typeof value !== "number") || isNaN(value) || value % 1 !== 0 || value <= 0) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 不是数组
        notArray: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "object" || !Array.isArray(value)) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 不是对象
        notObject: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "object") {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为布尔值
        notBoolean: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "boolean") {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为函数
        notFunction: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "function") {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为日期
        notDate: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "object" || !(value instanceof Date)) {
                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 验证是否为正则表达式
        notRegExp: function (notValidMsg) {
            let value = this.validateValue;
            if (typeof value !== "object" || !(value instanceof RegExp)) {

                this.result = false;
                this.msg.push(notValidMsg);
            }
            return this;
        },

        // 返回结果
        isValid: function () {
            // console.log("验证内容", this.validateValue, "验证结果", this.result, "错误信息", this.msg);
            return {
                result: this.result,
                msg: this.msg.join("；")
            }
        },
    };
    return validateUtils;
}