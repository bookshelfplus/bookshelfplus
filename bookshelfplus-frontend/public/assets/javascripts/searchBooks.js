function search({ tableElementId = "", searchText = "", categoryId = 0 }) {
    getRequest("/book/search", { bookName: searchText, categoryId: categoryId })
        .then(function (responseData) {
            var axiosData = responseData.data;
            var status = axiosData.status;
            var data = axiosData.data;
            if (status === "success") {
                // console.log(data)

                // 数据进行转换
                var renderData = [];
                data.forEach(element => {
                    var mainDivWidth = 80/*vw*/; // 定义div的宽度（用于计算表格中的数据的显示长度）
                    var columnWidth = [23, 17, 30, 10, 20];
                    renderData.push({
                        书名: ` <a target="_blank" href="/book?id=${element.id}">
                                    <span class="overflow-omit" style="max-width: ${columnWidth[0] * mainDivWidth / 100}vw; max-height: 2em; margin: 0 auto;">
                                        ${element.bookName}
                                    </span>
                                </a>`,
                        分类: ` <a target="_blank" href="/category?id=${element.category.id}">
                                    <span class="overflow-omit" style="max-width: ${columnWidth[1] * mainDivWidth / 100}vw; max-height: 2em; margin: 0 auto;">
                                        ${element.category.name}
                                    </span>
                                </a>`,
                        简介: ` <span class="overflow-omit" style="max-width: ${columnWidth[2] * mainDivWidth / 100}vw; max-height: 2em; margin: 0 auto;">
                                    ${element.description}
                                </span>`,
                        语言: ` <span class="overflow-omit" style="max-width: ${columnWidth[3] * mainDivWidth / 100}vw; max-height: 2em; margin: 0 auto;">
                                    ${element.language}
                                </span>`,
                        出版社: `<span class="overflow-omit" style="max-width: ${columnWidth[4] * mainDivWidth / 100}vw; max-height: 2em; margin: 0 auto;">
                                    ${element.publishingHouse}
                                </span>`,
                    })
                });

                if (renderData.length == 0) {
                    console.log("没有搜索到相关书籍");
                    function htmlEncode(str) {
                        // refer: https://stackoverflow.com/questions/4183801/escape-html-chracters
                        var div = document.createElement('div');
                        var txt = document.createTextNode(str);
                        div.appendChild(txt);
                        return div.innerHTML;
                    }
                    if (searchText && searchText != "") {
                        //
                        renderTable({ data: `没有搜索到与 <span style="color: red;">${htmlEncode(searchText)}</span> 相关的书籍，请换个关键词再试试吧`, tableId: tableElementId, renderTableHead: true });
                    } else if (categoryId && categoryId != 0) {
                        //
                        renderTable({ data: `该分类下暂无电子书`, tableId: tableElementId, renderTableHead: true });
                    }
                } else {
                    renderTable({ data: renderData, tableId: tableElementId, renderTableHead: true });
                }

                // 渲染后重新获取一次字体
                if (typeof (fontmin) === "function") {
                    fontmin(getPageText());
                }
            } else {
                alert(`出错啦！${data.errMsg} (错误码: ${data.errCode}) `);
            }
        }).catch(function (error) {
            console.log(error);
            alert("无法连接到服务器，请检查网络连接！");
        });
}