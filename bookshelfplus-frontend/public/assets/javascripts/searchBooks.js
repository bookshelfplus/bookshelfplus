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
                        书名: ` <a target="_blank" href="/book?id=${element.category.id}">
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
                    renderTable({ data: `没有搜索到与 <span style="color: red;">${searchboxValue}</span> 相关的书籍，请换个关键词再试试吧`, tableId: "result-table", renderTableHead: true });
                } else {
                    renderTable({ data: renderData, tableId: tableElementId, renderTableHead: true });
                }

                // 渲染后重新获取一次字体
                fontmin(getPageText());
            } else {
                alert(`出错啦！${data.errMsg} (错误码: ${data.errCode}) `);
            }
        });
}