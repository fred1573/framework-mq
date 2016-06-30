/**
 *
 * Created by Administrator on 2015/12/9.
 */
function initPage(msgType, from, to) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/data/persist/page_init",      //提交到一般处理程序请求数据
        async: false,
        data: {
            'msgType': msgType,
            'pageIndex': pageIndex,
            'pageSize': pageSize,
            'from': from,
            'to': to
        },
        success: function (result) {
            $('#totalCount').val(result.page.totalCount);
            $('#pageSize').val(result.page.pageSize);
            $('#pageIndex').val(result.page.pageIndex);
        }
    });
}

function show_detail(id) {
    obtainDetail(id);
}

function obtainDetail(id) {
    $.getJSON('/data/persist/detail?id=' + id, function (result) {
        if (result.status != 200) {
            alert(result.message);
        }
        var html = "<h2>Messages Details</h2><table class='table table-condensed'>";
        var detail = result.data;
        html += "<tr><td>ID</td><td>" + detail.id + "</td></tr>";
        html += "<tr><td>message-type</td><td>" + detail.msgType + "</td></tr>";
        html += "<tr><td>persist-time</td><td>" + detail.persisTime + "</td></tr>";
        html += "<tr><td>content</td><td>" + JSON.stringify(detail.content) + "</td></tr>";
        html += "</table>";
        $("#showDetail").html(html);
    })
}

function onSub(){
    var startTime = $("#dtp_input1").val();
    var endTime = $("#dtp_input2").val();
    var key = $("#dtp_input3").val();
    var value = $("#dtp_input4").val();
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/data/persist/query',      //提交到一般处理程序请求数据
        data: "persisFrom=" + startTime + "&persisTo=" + endTime + "&key=" + key + "&value=" + value + "&pageIndex=0&pageSize=20",          //提交两个参数：pageIndex(页面索引)，pageSize(显示条数)
        success: function (result) {
            var collections = result.data;
            var html = "<tr><th>ID</th><th>message-type</th><th>persist-time</th></tr>>";
            $(collections).each(function (i, val) {
                var line = "<tr><td><a onclick=show_detail('" + val.id + "') title='" + val.id + "'>" + val.id.substr(0, 15) + "...</a>" + "</td><td>" + val.msgType + "</td><td>" + val.persisTime + "</td></tr>";
                html += line;
            });
            $('#data').html(html);
            $('#totalCount').val(result.totalCount);
            $('#pageSize').val(result.pageSize);
            $('#pageIndex').val(result.pageIndex);
            $("#Result tr:gt(0)").remove();        //移除Id为Result的表格里的行，从第二行开始（这里根据页面布局不同页变）
        }
    });
}