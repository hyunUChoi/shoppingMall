// 파일 하나당 최대 용량 10MB
const LIMIT_BYTE = 10485760;

function fnFileUpload(files, type) {
    let valid = true;

    for(let i = 0; i < files.length; i++) {
        if(files[i].size > LIMIT_BYTE) {
            alert("[" + files[i].name + "]은(는) 허용용량을 초과하여 첨부가 불가능합니다. 파일은 최대 10MB까지 첨부 가능합니다.");
            document.getElementById("atchFileForm").value = '';
            valid = false;
            break;
        }

        if(!fnTypeValidation(files[i], type)) {
            alert("첨부할 수 없는 확장자입니다.");
            document.getElementById("atchFileForm").value = '';
            valid = false;
            break;
        }
    }

    if(valid) {
        let formData = new FormData();
        for(let i = 0; i < files.length; i++) {
            formData.append("files", files[i]);
        }

        axios.post('/file/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then(function(response) {
            document.getElementById('atchFileId').value = response.data;
            fnPageCall('file', returnForm(type));
        })
        .catch(function(error) {
            console.log(error);
        })
    }
}

// noinspection JSUnusedGlobalSymbols
function fnFileDelete(atchFileId, type) {
    if(confirm('삭제하시겠습니까?')) {
        axios.delete('/file/delete', {
            params: {
                atchFileId: atchFileId
            }
        })
        .then(function(response){
            if(response.data === 0) {
                document.getElementById("atchFileId").value = '';
            }
            fnPageCall('file', returnForm(type));
        })
        .catch(function(error) {
            console.log(error);
        })
    } else {
        return false;
    }
}

function fnTypeValidation(files, type) {
    let divn = type.toLowerCase();

    if(divn.indexOf('image') > -1) {
        let reg = /jpg|jpeg|png|gif/i;
        return reg.test(files.type.split('/')[1]);

    } else if(divn.indexOf('file') > -1) {
        let reg = /gif|jpg|jpeg|png|doc|docx|gul|hwp|hwt|pdf|ppt|pptx|xls|xlsx|xlsm|csv/i;
        return reg.test(files.type.split('/')[1]);
    }

    return true;
}

function returnForm(type) {
    let returnUrl;

    switch (type){
        case 'singleImage' :
            returnUrl = '/file/singleImg';
            break;
        case 'singleFile' :
            returnUrl = '/file/singleFile';
            break;
        case 'multiImage' :
            returnUrl = '/file/multiImg';
            break;
        case 'multiFile' :
            returnUrl = '/file/multiFile';
            break;
    }

    return returnUrl;
}