import ImageView from './ImageView/index.vue'
import ProductItem from '@/components/ProductItem/index.vue'
export const componentPlugin={
    install(app){
        //app.component('组件名字',组件配置对象)
        app.component('ImageView',ImageView);
        app.component('ProductItem',ProductItem);
    }
}
