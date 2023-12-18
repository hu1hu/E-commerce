<script setup>
import {getProductListApi} from '@/apis/foreground'
import { ref, onMounted } from 'vue'
import ProductItem from '@/components/ProductItem/index.vue'
import {useCategory} from "@/views/Category/composables/useCategory.js";
import {useRouter} from "vue-router";
import banner1 from '@/assets/banner1.jpg';
import banner2 from '@/assets/banner2.jpg';
import banner3 from '@/assets/banner3.png';
import banner4 from '@/assets/banner4.jpg';

const bannerUrls = ref([banner1, banner2, banner3, banner4])


const router = useRouter()

const {categoryData} = useCategory();
</script>

<template>
  <div class="container">
    <div class="home-banner">
      <el-carousel height="660px">
        <el-carousel-item v-for="item in bannerUrls" :key="item">
          <img :src="item" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
  <div class="product-container">
    <div class="container">
      <h2>{{router.currentRoute.value.params.id}}</h2>
      <div class="product-list">
        <ProductItem v-for="product in categoryData" :key="product.id" :product="product"/>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  width: 1180px;
  margin: 50px auto;
  .home-banner {
    width: 1180px;
    height: 660px;

    img {
      width: 100%;
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
