import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useStore} from "@/stores";

const store =useStore()
const defaultFailure = (msg) => {
    ElMessage.warning(msg)
}

const defaultError = (msg) => {
    ElMessage.error('请求异常,请及时联系管理员')
}

function post(url, data, success, type = 'x-www-form-urlencoded',failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            "Content-Type": 'application/'+type
        },
        withCredentials: true
    }).then(data => {
        if (data.data.statusCode === 401) {
            store.auth.user = null
            localStorage.removeItem("user")
            router.push('/')
        } else if (data.data.success) {
            success(data.data.message, data.data.statusCode)
        } else {
            failure(data.data.message)
        }
    }).catch(error)
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url)
        .then((data) => {
            // if (data.data.statusCode === 401) {
            //     store.auth.user = null
            //     localStorage.removeItem("user")
            //     router.push('/')
            // } else
            if (data.data.success)
                success(data.data.message, data.data.statusCode)
            else
                failure(data.data.message)
        }).catch(error)
}

export {post, get}