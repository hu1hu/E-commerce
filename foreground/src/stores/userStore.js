//管理用户相关数据
import {defineStore} from "pinia";
import {ref} from "vue";
import {userLoginApi, userRegisterApi} from "@/apis/common";
import {useCartStore} from "@/stores/cartStore";

export const useUserStore=defineStore('user',()=>{
    const userInfo=ref({});

    // 用户注册
    const userRegister=async ({phoneNumber, email, userName, password, gender, type})=>{
        try {
            const res=await userRegisterApi({phoneNumber,email,userName,password,gender,type});
            if(res.code===200) {
                userInfo.value = {
                    token: res.data,
                    phoneNumber,
                    userName,
                    email,
                    password,
                    gender
                }
            }else {
                throw new Error(res.msg)
            }
        } catch (e) {
            console.log(e)
            return Promise.reject(e)
        }
    }
    const userLogin=async ({phoneNumber,password,type})=>{
        try {
            const res = await userLoginApi({phoneNumber,password,type});
            if(res.code === 200){
                userInfo.value=res.data
            } else {
                throw new Error(res.msg)
            }
        }catch (e) {
            console.log(e)
            return Promise.reject(e)
        }
    }
    //退出登录函数,清除用户信息
    const clearUserInfo=()=>{
        userInfo.value={};
        useCartStore().clearCart();
    }

    return{
        userInfo,
        userRegister,
        userLogin,
        clearUserInfo
    }
},{
    //数据持久化
    persist:true
})
