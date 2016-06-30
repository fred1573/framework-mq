/**
 * Created by  idea
 * user: yutaoxun
 * data: 2016/5/5
 * email: yutaoxun@gmail.com
 * company: fanqielaile
 */

/**
 * 创建消息类型.
 */
function sub_create() {
    var system = $("#system").val();
    var msgType = $("#msgType").val();
    var principal = $("#principal").val();
    var description = $("#description").val();

    if (msgType.length == 0 || msgType == undefined || msgType == null) {
        return;
    }

    if (description.length == 0 || description == undefined || description == null) {
        return;
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/msgType/createMsgType',
        data: "principal=" + principal + "&system=" + system + "&msgType=" + msgType + "&description=" + description,
        success: function (result) {
            alert("success");
        },
        error: function(result){
            alert("success");
        }
    });
}

/**
 * 删除消息类型.
 */
function sub_delete() {
    var value = $("#msgType").val();
    if (value.length == 0 || value == undefined || value == null) {
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "json",
        url: '/msgType/deleteMsgType',
        data: "msgType=" + value,
        success: function (result) {
            alert(result.message)
        }
    });
}

/**
 * 查询消息类型的详细描述信息.
 */
function sub_query() {
    var value = $("#msgType").val();
    if (value.length == 0 || value == undefined || value == null) {
        return;
    }

    $.ajax({
        type: "GET",
        dataType: "json",
        url: '/msgType/queryMsgType',
        data: "msgType=" + value,
        success: function (result) {
            var msgContent = "负责人: " + result.principal + "\n" + "系统: " + result.system + "\n" + "消息类型:　" + result.msgType + "\n" + "消息描述: " + result.description + "\n";
            $('#queryResult').css("display", "block");
            $('textarea#description').val(msgContent);
        }
    });
}
