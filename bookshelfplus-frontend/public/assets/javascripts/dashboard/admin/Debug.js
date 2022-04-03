postRequest('/debug/status', { token: localStorage.token })
    .then(function (response) {
        var axiosData = response.data;
        var status = axiosData.status;
        var data = axiosData.data;

        if (status === "success") {
            console.log(data);
            $(".main").html("请在 F12 查看");
        } else {
            alert(`出错啦！${data.errMsg} (错误码: ${data.errCode}) `);
        }
    }).catch(function (error) {
        console.log(error);
    });