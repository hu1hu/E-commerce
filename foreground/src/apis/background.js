import httpInstance from "@/apis/http.js";

/**
 * 增加商品
 * @param name
 * @param price
 * @param count
 * @param description
 * @param coverUrl
 * @param category
 */
export const addProductApi = ({ name, price, count, description, coverUrl, category }) => {
    return httpInstance({
        url: '/background/product/add',
        method: 'post',
        data: {
            name,
            price,
            count,
            description,
            coverUrl,
            category
        }
    })
}

/**
 * 修改商品
 * @param id
 * @param name
 * @param price 可选
 * @param count
 * @param description
 * @param coverUrl
 * @param category
 */

export const updateProductApi = ({ id, name, price, count, description, coverUrl, category }) => {
    return httpInstance({
        url: '/background/product/update',
        method: 'put',
        data: {
            id,
            name,
            price,
            count,
            description,
            coverUrl,
            category
        }
    })
}

/**
 * 删除商品
 * @param [list]
 */
export const deleteProductApi = ({ list }) => {
    return httpInstance({
        url: '/background/product/delete',
        method: 'delete',
        data: [
            ...list
        ]
    })
}

/**
 * 获取商家所有商品
 * @param page
 * @param pageSize
 */
export const getProductListApi = ({ page, pageSize }) => {
    return httpInstance({
        url: '/background/product/get',
        params: {
            page,
            pageSize
        }
    })
}

/**
 * 获取销量数据
 * @param begin
 * @param end
 * @param page
 * @param pageSize
 */
export const getSalesApi = ({ begin, end, page, pageSize }) => {
    return httpInstance({
        url: '/background/saleProduct/getList',
        params: {
            begin,
            end,
            page,
            pageSize
        }
    })
}

/**
 * 文件上传
 * @param file
 */
export const uploadFileApi = (file) => {
    return httpInstance({
        url: '/background/upload',
        method: 'post',
        data: file
    })
}
