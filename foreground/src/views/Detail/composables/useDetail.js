//封装分类数据业务
import {onMounted, ref} from "vue";
import {onBeforeRouteUpdate, useRouter} from "vue-router";
import {getProductDetailApi, getProductListApi} from "@/apis/foreground";

/**
 * 获取分类数据
 */
export function useDetail() {
    const detailData=ref({});
//获取路由参数，拿到参数ID
    const router=useRouter()
//id=route.params.id 意思是形参为id，默认值是route.params.id
    const getDetail=async (id=router.currentRoute.value.params.id)=>{
        const res=await getProductDetailApi({id});
        detailData.value=res.data;
    }
    onMounted(()=>getDetail())
    //解决路由缓存问题方案2：利用钩子函数，路由参数变化的时候，可以把分类数据接口重新发送
    onBeforeRouteUpdate((to)=>{
        //路由发生变化,重新发送xhr请求
        let id=to.params.id;
        getDetail(id).then();
    })
    return{
        detailData
    }
}
