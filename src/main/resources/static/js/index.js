let file_chooser = document.getElementById("file_chooser");
let drag_area_frame = document.getElementById("drag_area_frame");
let file_info_frame = document.getElementById("file_info_frame");
let process_frame = document.getElementById("process_frame");
let pickup_code_input = document.getElementById("pickup_code_input");
let save_time_input = document.getElementById("save_time_input");
let upload_result_content = document.getElementById("upload_result_content");
let download_info_contents = document.getElementById("download_info_contents");
let download_url;

init_page();

file_chooser.addEventListener("change", draw_file_info);

drag_area_frame.ondragstart = function (e) {
    e.preventDefault();
}
drag_area_frame.ondragenter = function (e) {
    e.preventDefault();
}
drag_area_frame.ondragover = function (e) {
    e.preventDefault();
}
drag_area_frame.ondragleave = function (e) {
    e.preventDefault();
}
drag_area_frame.ondragend = function (e) {
    e.preventDefault();
}
drag_area_frame.ondrop = function (e) {
    e.preventDefault();
    file_chooser.files = e.dataTransfer.files;
    draw_file_info();
}








function init_page() {
    drag_area_frame.innerHTML =
        "            <img id=\"upload_img\" src=\"img/upload.png\" width=\"100px\" height=\"100px\" style=\"opacity: 0.5\">\n" +
        "            <div id=\"upload_txt\">\n" +
        "                <p>将文件拖放至此处，或<a href=\"#\" onclick=\"choose_file()\">点击上传</a></p>\n" +
        "                <p>存储天数1～7天，请牢记取件码，按时下载，过期将自动删除</p>\n" +
        "            </div>\n"
}

function draw_file_info() {
    let file = file_chooser.files[0];
    let file_name = file["name"];
    let file_type = file["type"];
    let file_size = file["size"];
    if(file_size < 1024) {
        file_size = file_size + "bit";
    } else if(file_size >= 1024 && file_size < 1048576) {
        file_size = (file_size / 1024).toFixed(2) + "K";
    } else if(file_size >= 1048576 && file_size < 1073741824) {
        file_size = (file_size / 1048576).toFixed(2) + "M";
    } else {
        file_size = (file_size / 1073741824).toFixed(2) + "G";
    }
    file_info_frame.innerHTML =
        "            <table class=\"table\">\n" +
        "                <thead>\n" +
        "                <tr>\n" +
        "                    <th scope=\"col\">#</th>\n" +
        "                    <th scope=\"col\">名称</th>\n" +
        "                    <th scope=\"col\">类型</th>\n" +
        "                    <th scope=\"col\">大小</th>\n" +
        "                </tr>\n" +
        "                </thead>\n" +
        "                <tbody>\n" +
        "                <tr>\n" +
        "                    <th scope=\"row\">1</th>\n" +
        "                    <td>" + file_name + "</td>\n" +
        "                    <td>" + file_type + "</td>\n" +
        "                    <td>" + file_size + "</td>\n" +
        "                </tr>\n" +
        "                </tbody>\n" +
        "            </table>";
    process_frame.innerHTML = "";
}

function choose_file() {
    file_chooser.click();
}

function upload() {
    if(file_chooser.files[0] != null) {
        let file = file_chooser.files[0];
        let save_time = save_time_input.value;

        if(file !== null) {
            let formData = new FormData;
            formData.append("file", file);
            formData.append("save_time", save_time);

            let url = "/upload";
            let request = $.ajaxSettings.xhr();
            request.upload.addEventListener("progress", function (e) {
                //e.loaded 已上传文件字节数
                //e.total 文件总字节数
                let percentage = e.loaded / e.total * 100;

                process_frame.innerHTML =
                    "            <div class=\"progress\" id=\"upload_process\">\n" +
                    "                <div class=\"progress-bar\" role=\"progressbar\" aria-valuenow=\"75\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: " + percentage + "%\">" + percentage.toFixed(2) +"%</div>\n" +
                    "            </div>";

            },false);
            request.onreadystatechange = function () {
                if(request.readyState === 4 && request.status === 200) {
                    let response = JSON.parse(request.response);
                    upload_result_content.innerHTML =
                        "<p>文件名称：" + response['file_name'] + "</p>" +
                        "<p>文件类型：" + response['file_type'] + "</p>" +
                        "<p>文件大小：" + response['file_size'] + "</p>" +
                        "<p>上传时间：" + response['upload_time'] + "</p>" +
                        "<p>有效期：" + response['save_time'] + "</p>" +
                        "<h4>取件码：" + response['pickup_code'] + "</h4>"
                    $("#upload_result").modal("show");
                }
            }
            request.open("POST", url);
            request.send(formData);
        }
    } else {
        alert("未选择任何文件！");
    }
}

function pickup_file() {
    if(pickup_code_input.value !== "") {
        let formData = new FormData();
        let url = "/query_download";
        let pickup_code = pickup_code_input.value;
        formData.append("pickup_code", pickup_code);

        let request = $.ajaxSettings.xhr();
        request.onreadystatechange = function () {
            if(request.readyState === 4 && request.status === 200) {
                download_url = "/download?pickup_code=" + pickup_code;
                let response = JSON.parse(request.response);
                if(response["status"] === 1) {
                    download_info_contents.innerHTML =
                        "<p>文件名称：" + response['file_name'] + "</p>" +
                        "<p>文件类型：" + response['file_type'] + "</p>" +
                        "<p>文件大小：" + response['file_size'] + "</p>" +
                        "<p>上传时间：" + response['upload_time'] + "</p>" +
                        "<p>有效期：" + response['save_time'] + "</p>";
                    $("#download_info").modal("show");
                } else {
                    alert("取件码不存在，请检查后重试！");
                }
            }
        }
        request.open("POST", url);
        request.send(formData);
    } else {
        alert("取件码不能为空，请检查后重试");
    }

}


function download() {
    window.location.href = download_url;
}





