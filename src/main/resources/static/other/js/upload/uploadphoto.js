/**
 * Created by NAGOYA on 2019/11/21.
 */
function upload_cover(obj) {

    ajax_upload(obj.id);  //function(data)是上传图片的成功后的回调方法

}
function ajax_upload(feid) { //具体的上传图片方法
    if (image_check(feid)) { //自己添加的文件后缀名的验证
        $.ajaxFileUpload({
            fileElementId: feid,    //需要上传的文件域的ID，即<input type="file">的ID。
            url: '/upload.do', //后台方法的路径
            type: 'post',   //当要提交自定义参数时，这个参数要设置成post
            dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
            enctype:"multipart/form-data" ,
            secureuri: false,   //是否启用安全提交，默认为false。
            async : true,   //是否是异步
            success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                var isrc=data.httpDir;
                 // alert("isrc:"+isrc);
                // alert(data.upload_result);
                 $('#show_choose_img').attr('src',isrc).data('img-src', isrc); //给<input>的src赋值去显示图片
                 $('#picture_upload1').val(isrc);
                 // alert($('#picture_upload1').val());
            },
            error: function(data, status, e) {  //提交失败自动执行的处理函数。
                console.error(e);
            }
        });
    }
}
function image_check(feid) { //自己添加的文件后缀名的验证
    var img = document.getElementById(feid);
    return /.(jpg|png|gif|bmp|JPG|PNG|GIF|jpeg|txt|mp4|war|zip|avi|js|css|html|pfx)$/.test(img.value)?true:(function() {
            // modals.info('图片格式仅支持jpg、png、gif、bmp格式，且区分大小写。');
            alert('图片格式仅支持jpg、png、gif、bmp格式，且区分大小写。');
            return false;
        })();
}

