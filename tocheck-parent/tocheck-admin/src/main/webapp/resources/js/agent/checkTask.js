/**
 * Created by Administrator on 2017/6/22.
 */
function uploadPaperFile(object, id) {
    var pos = -1;
    var filePath = object.value;
    if (filePath.indexOf("/") > -1) {
        pos = filePath.lastIndexOf("/") * 1;
    } else if (filePath.indexOf("\\") > -1) {
        pos = filePath.lastIndexOf("\\") * 1;
    }
    var fileName = filePath.substring(pos + 1);
    var fileExtension = fileName.toLowerCase().substr(fileName.lastIndexOf('.'));
    if (!paperFileExtReg.test(fileExtension)) {
        layer.msg("只支持.rar或者.zip的压缩包");
        return;
    } else {
        $("#fileName" + id).val(fileName);
    }
}
function upload(id, systemId) {
    if($("#file"+id).val()==''){
        return;
    }
    var index = loading();
    $.ajaxFileUpload({
        url: 'uploadReport.ajax',
        type: 'POST',
        secureuri: false,
        fileElementId: 'file' + id,
        data: {id: id, systemId: systemId},
        dataType: 'json',
        success: function (json) {
            if (json.state) {
                layer.msg("上传成功!", function () {
                    location.reload();
                },1000)
            } else {
                layer.msg(json.message);
            }
            layer.close(index);
        },
        error: function () {
            layer.close(index);
            layer.msg('提交文件失败，请重试!');
        }
    });
}
