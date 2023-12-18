<script setup>
import {getProductListApi} from '@/apis/foreground'
import { ref, onMounted } from 'vue'
import ProductItem from '@/components/ProductItem/index.vue'
import {useRouter} from "vue-router";
import {useUserStore} from "@/stores/userStore.js";
import banner1 from '@/assets/banner1.jpg';
import banner2 from '@/assets/banner2.jpg';
import banner3 from '@/assets/banner3.png';
import banner4 from '@/assets/banner4.jpg';

const bannerUrls = ref([banner1, banner2, banner3, banner4])

const productList = ref([])

const pageNum = ref(1)

const getProducts = async () => {
  const res = await getProductListApi({ page: pageNum.value, pageSize: 20 });
  productList.value = res.data.rows;
}

onMounted(() => getProducts())

const disabled = ref(false)

const load = async () => {
  pageNum.value++;
  const res = await getProductListApi({ page:pageNum.value, size: 10 });
  productList.value = productList.value.concat(res.data.rows);
  if(!res.data.row) {
    disabled.value = true
  }
}

const router = useRouter()

const userStore = useUserStore()

//如果token为空，跳转到登录页面
onMounted(() => {
  if(!userStore.userInfo.token) {
    router.push('/login')
  }
})

</script>

<template>
  <div class="container">
    <div class="home-banner">
      <el-carousel height="36vw">
        <el-carousel-item v-for="item in bannerUrls" :key="item">
          <img :src="item" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
  <div class="product-container">
    <div class="container">
      <h2>推荐商品</h2>
      <div class="product-list" v-infinite-scroll="load" :infinite-scroll-disabled="disabled">
        <ProductItem v-for="product in productList" :key="product.id" :product="product"/>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  width: 70vw;
  margin: 30px auto 0;
  .home-banner {
    width: 70vw;
    border-radius: 20px;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
    }
  }
}

.product-container {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);

  .container {
    margin: 50px auto 0;
    padding: 20px 10px;

    h2 {
      font-size: 28px;
    }

    .product-list {
      padding: 10px 0;
      display: flex;
      gap: 20px;
      flex-wrap: wrap;
    }
  }
}
</style>
