function fnPageProcessing(url, form, atch) {
    const formData = new FormData(form)

    axios.post(url, formData, {
        responseType: 'text'
    })
    .then(function(res) {
        document.getElementById(atch).innerHTML = res.data;
    })
}

function fnPageCall(divn, url, vrb, val) {
    let form = document.getElementById('defaultFrm');
    form.setAttribute('action', url);

    switch (divn) {
        case 'list' :
        case 'insert' :
        case 'view' :
            if(vrb !== undefined) {
                document.getElementById(vrb).value = val;
            }
            form.submit();
            break;

        case 'addList' :
            fnPageProcessing(url, form, 'tbl');
            break;

        case 'file' :
            fnPageProcessing(url, form, 'file');
            break;
    }
}

function fnProcessing(method, url, btn) {
    document.getElementById(btn.id).classList.add('display_none');
    document.querySelectorAll('[id^="error_"]').forEach(ele => ele.innerText = "");

    axios({
        method: method,
        url: url,
        data: document.getElementById('defaultFrm')
    })
    .then(function(res) {
        alert(res.data.message);
        fnPageCall('list', res.data.return);
    })
    .catch(function(err) {
        if(err.response && err.response.status === 422) {
            const errors = err.response.data;
            for(const key in errors) {
                document.getElementById("error_" + key).innerText = errors[key];
            }
        }
        document.getElementById(btn.id).classList.remove('display_none');
    })
}

// noinspection JSUnusedGlobalSymbols
function fnPaging(page, divn) {
    document.getElementById('page').value = page;
    fnPageCall(divn, divn);
}