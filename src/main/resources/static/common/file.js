// 파일 하나당 최대 용량 10MB
const LIMIT_BYTE = 10485760;

function fnSingleFileUpload(files, type) {
    let valid = fnTypeValidation(files, type);

    if(files[0].size > LIMIT_BYTE) {
        alert("[" + files[0].name + "]은(는) 허용용량을 초과하여 첨부가 불가능합니다. 파일은 최대 10MB까지 첨부 가능합니다.");
        document.getElementById("atchFileForm").value = '';
        return false;

    } else if(!valid) {
        alert("첨부할 수 없는 확장자입니다.");
        document.getElementById("atchFileForm").value = '';
        return false;

    } else {
        let formData = new FormData();
        formData.append("file", files[0]);

        axios.post('/file/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then(function(response) {
            document.getElementById('atchFileId').value = response.data;
            fnPageCall('file', '/file/form');
        })
        .catch(function(error) {
            console.log(error);
        })
    }
}

// noinspection JSUnusedGlobalSymbols
function fnFileDelete(atchFileId) {
    if(confirm('삭제하시겠습니까?')) {
        axios.delete('/file/delete', {
            params: {
                atchFileId: atchFileId
            }
        })
        .then(function(){
            document.getElementById("atchFileId").value = '';
            fnPageCall('file', '/file/form');
        })
        .catch(function(error) {
            console.log(error);
        })
    } else {
        return false;
    }
}

function fnTypeValidation(files, type) {
    if(type === 'image') {
        let reg = /jpg|jpeg|png|gif/i;
        return reg.test(files[0].type.split('/')[1]);
    }

    return true;
}