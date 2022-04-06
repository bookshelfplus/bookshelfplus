// #######################################################
// 渲染元素
// #######################################################
function renderElements(control) {
    var element = document.createElement(control.tag);

    // 为元素添加属性
    Object.keys(control.attr).forEach((key, index) => {
        var value = control.attr[key];
        element.setAttribute(key, value);
    });

    // 如果设置了 innerHTML 属性，则添加 innerHTML 属性
    if (Object.keys(control).indexOf('innerHTML') > -1) {
        element.innerHTML = control.innerHTML;
    }

    // 如果有 children 属性，则递归渲染 children
    if (Object.keys(control).indexOf('children') > -1) {
        control.children.forEach(function (child) {
            var childElement = renderElements(child);
            element.appendChild(childElement);
        });
    }
    return element;
}

// #######################################################
// 渲染表单对象
// #######################################################
function renderFormControls({ Controls = [] }) {
    console.log(Controls);

    var controlList = [];
    Controls.forEach(function (control) {
        if (Object.keys(control).indexOf('tag') === -1 || Object.keys(control).indexOf('attr') === -1) {
            console.log("元素渲染出错");
            return;
        }

        // #########################################
        // 创建元素对象
        // #########################################
        var element = renderElements(control);

        // 为 element 添加 "form-elements" class，以便点击提交时可以获取到表单元素，进行批量处理
        element.classList.add("form-elements");

        // #########################################
        // 创建元素label
        // #########################################
        var label = document.createElement("label");
        label.innerHTML = control.label.value;
        label.setAttribute("for", control.attr.id);

        label.innerHTML += `<span style='color: red; user-select: none;'> ${control.required ? '*' : '&nbsp;'}</span>`

        // 为 element 添加 "form-elements" class，以便点击提交时可以获取到表单元素，进行批量处理
        label.classList.add("form-labels");

        // #########################################
        // 返回结果
        // #########################################
        controlList.push({
            label: label,
            control: element
        });
    });
    // console.log(controlList);
    return controlList;
}