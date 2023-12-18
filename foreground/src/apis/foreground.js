import httpInstance from "@/apis/http.js";

/**
 * 获取分类商品
 * @param category
 * @param page
 * @param pageSize
 */
export const getProductListApi = ({ category, page, pageSize }) => {
    return httpInstance({
        url: '/foreground/product/get',
        params: {
            category,
            page,
            pageSize
        }
    })
}

/**
 * 获取商品详情
 * @param id
 */
export const getProductDetailApi = ({ id }) => {
    return httpInstance({
        url: '/foreground/product/detail',
        params: {
            id
        }
    })
}

/**
 * 添加商品到购物车
 * @param productId
 * @param count
 */
export const addProductToCartApi = ({ productId, count }) => {
    return httpInstance({
        url: '/foreground/shoppingCart/add',
        method: 'post',
        data: {
            productId,
            count
        }
    })
}

/**
 * 从购物车中删除商品
 */
export const deleteProductFromCartApi = ({ list }) => {
    return httpInstance({
        url: '/foreground/shoppingCart/delete',
        method: 'delete',
        data: [
            ...list
        ]
    })
}

/**
 * 修改购物车
 * @param id
 * @param count
 */
export const updateProductFromCartApi = ({ id, count }) => {
    return httpInstance({
        url: '/foreground/shoppingCart/update',
        method: 'put',
        data: {
            id,
            count
        }
    })
}

/**
 * 获取购物车
 */
export const getShoppingCartApi = () => {
    return httpInstance({
        url: '/foreground/shoppingCart/get'
    })
}

/**
 * 生成订单
 * @param order [{productId,count,addressId}]
 */
export const createOrderApi = ({ order }) => {
    return httpInstance({
        url: '/foreground/order/addList',
        method: 'post',
        data: order
    })
}

/**
 * 查询历史浏览记录
 */
export const getHistoryApi = () => {
    return httpInstance({
        url: '/foreground/history/getList'
    })
}

/**
 * 查询订单
 * @param orderId
 */
export const getOrderByIdApi = ({orderId}) => {
    return httpInstance({
        url: '/foreground/order/getById',
        params: {
            orderId
        }
    })
}

/**
 * 获取订单列表
 */
export const getOrderListApi = () => {
    return httpInstance({
        url: '/foreground/order/getList'
    })
}
