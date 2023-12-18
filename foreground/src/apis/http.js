import axios from 'axios'
import {ElMessage} from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import {useUserStore} from "@/stores/userStore.js";
import {useRouter} from "vue-router";

// 创建axios实例
const httpInstance = axios.create({
    baseURL: 'http://47.113.149.158/api',
    timeout: 5000
})
// axios请求拦截器(对发出的数据进行处理)
httpInstance.interceptors.request.use(config => {
    //从pinia获取token数据
    const userStore=useUserStore()
    //拿取token
    if (userStore.userInfo.token){
        config.headers.token = userStore.userInfo.token
    }
    return config
}, e => Promise.reject(e))

// axios响应式拦截器(对返回的数据进行处理)
httpInstance.interceptors.response.use(res => {
    // //从pinia获取token数据
    if (!(res.data.code===200)){
        ElMessage({
            type:'error',
            message:res.data.msg
        })
        return Promise.reject(res.data.msg)
    }
    return res.data
}, e => {
    return Promise.reject(e)
})


export default httpInstance
