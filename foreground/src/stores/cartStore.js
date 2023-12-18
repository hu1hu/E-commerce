//购物车逻辑
import {defineStore} from "pinia";
import {computed, ref} from "vue";
import {useUserStore} from "@/stores/userStore";
import {addProductToCartApi, deleteProductFromCartApi, getShoppingCartApi, createOrderApi } from "@/apis/foreground";

export const useCartStore = defineStore('cart', () => {
    const userStore=useUserStore();
    //isLogin是一个布尔值
    const isLogin=computed(()=>userStore.userInfo.token)
    //定义购物车列表
    const cartList = ref([]);
    //获取最新购物车数据
    const updateCart=async ()=>{
        const res=await getShoppingCartApi();
        clearCart();
        res.data.forEach((item) => {
            const product = cartList.value.find((product) => product.productId === item.productId);
            if (product) {
                product.count += item.count;
            } else {
                cartList.value.push(item);
            }
        });
    }
    const addCart =async (product) => {
        if (isLogin.value){
            const {productId, count}=product;
            await addProductToCartApi({productId, count});
            await updateCart()
        }else {
            const item = cartList.value.find((item) => product.productId === item.productId);
            if (item) {
                //在购物车中找到相同的，只需要数量加1
                item.count++;
            } else {
                //没找到，新增
                cartList.value.push(product);
            }
        }

    };

    //删除购物车商品
    const deleteCart=async (product)=>{
        if (isLogin.value){
            const {productId}=product;
            let list = [productId]
            await deleteProductFromCartApi({list});
            await updateCart()
        }else {
            const index = cartList.value.findIndex((item) => product.productId === item.productId);
            if (index !== -1) {
                cartList.value.splice(index, 1);
            }
        }
    }

    // 调用结算接口，清空购物车，生成订单
    const settlement=async ({addressId})=>{
        if (isLogin.value){
            // 获取购物车中所有选中的数组 order [{productId,count,addressId}]
            const order = cartList.value.map((item) => ({
                productId: item.productId,
                count: item.count,
                addressId: addressId
            }))
            await createOrderApi({order})
            // 获取购物车中所有选中的商品id数组
            const list = cartList.value.map((item) =>
                item.productId
            );
            await deleteProductFromCartApi({list});
            await updateCart()
        }
    }

    //清空购物车
    const clearCart=()=>{
        cartList.value=[]
    }

    // 1. 总的数量 所有项的count之和
    const allCount = computed(() => cartList.value.reduce((a, c) => a + c.count, 0))
    // 2. 总价 所有项的count*price之和
    const allPrice = computed(() => cartList.value.reduce((a, c) => a + c.count * c.productPrice, 0))

    // 单选功能
    const singleCheck = (skuId, selected) => {
        // 通过skuId找到要修改的那一项 然后把它的selected修改为传过来的selected
        const item = cartList.value.find((item) => item.skuId === skuId)
        item.selected = selected
    }

    return {
        cartList,
        addCart,
        allCount,
        allPrice,
        clearCart,
        updateCart,
        deleteCart,
        settlement,
    }
}, {
    //数据存储在浏览器存储，保证刷新不丢失
    persist: true
})
