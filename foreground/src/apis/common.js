import httpInstance from "@/apis/http.js";

/**
 * 用户注册
 * @param phoneNumber
 * @param email
 * @param userName
 * @param password
 * @param gender
 * @param type
 */
export const userRegisterApi = ({phoneNumber, email, userName, password, gender, type}) => {
    return httpInstance({
        url: '/common/account/register',
        method: 'post',
        data: {
            phoneNumber,
            email,
            userName,
            password,
            gender,
            type
        }
    })
}

/**
 * 用户登录
 * @param phoneNumber
 * @param password
 * @param type
 */
export const userLoginApi = ({phoneNumber, password, type}) => {
    return httpInstance({
        url: '/common/account/login',
        method: 'post',
        data: {
            phoneNumber,
            password,
            type
        }
    })
}

/**
 * 查询个人信息
 */
export const getUserInfoApi = () => {
    return httpInstance({
        url: '/common/account/getInfo'
    })
}

/**
 * 添加地址
 * @param address
 * @param receiver
 * @param phoneNumber
 */

export const addAddressApi = ({address, receiver, phoneNumber}) => {
    return httpInstance({
        url: '/common/address/add',
        method: 'post',
        data: {
            address,
            receiver,
            phoneNumber,
        }
    })
}

/**
 * 修改地址
 * @param id
 * @param address
 * @param receiver
 * @param phoneNumber
 */
export const updateAddressApi = ({id, address, receiver, phoneNumber}) => {
    return httpInstance({
        url: '/common/address/update',
        method: 'put',
        data: {
            id,
            address,
            receiver,
            phoneNumber,
        }
    })
}

/**
 * 删除地址
 * @param [list]
 */

export const deleteAddressApi = ({list}) => {
    return httpInstance({
        url: '/common/address/delete',
        method: 'delete',
        params: {
            list
        }
    })
}

/**
 * 获取地址
 */
export const getAddressApi = () => {
    return httpInstance({
        url: '/common/address/get'
    })
}
