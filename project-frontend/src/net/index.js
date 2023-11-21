import axios from "axios";
import {ElMessage} from "element-plus";

const defaultFailure = (msg) => {
    ElMessage.warning(msg)
}

const defaultError = (msg) => {
    ElMessage.error('请求异常,请及时联系管理员')
}

function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(data => {
        if (data.data.success) {
            console.log('success')
            success(data.data.message, data.data.statusCode)
        } else {
            console.log('failure')
            failure(data.data.message)
        }
    }).catch(error)
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url)
        .then((data) => {
            if (data.data.success)
                success(data.data.message, data.data.statusCode)
            else
                failure(data.data.message)
        }).catch(error)
}

export {post, get}