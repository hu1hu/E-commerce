<script setup>
import {useCartStore} from "@/stores/cartStore";
import {onMounted,computed} from "vue";
import {useRouter} from "vue-router";
import {useUserStore} from "@/stores/userStore";

const router = useRouter()

const userStore = useUserStore()

// const cartStore=useCartStore()

let catUrls = {
  'jujia': '/category/'+encodeURIComponent("居家"),
  'meishi': '/category/'+encodeURIComponent("美食"),
  'fushi': '/category/'+encodeURIComponent("服饰"),
  'muying': '/category/'+encodeURIComponent("母婴"),
  'gehu': '/category/'+encodeURIComponent("个护"),
  'yanxuan': '/category/'+encodeURIComponent("严选"),
  'shuma': '/category/'+encodeURIComponent("数码"),
  'yundong': '/category/'+encodeURIComponent("运动"),
  'zaxiang': '/category/'+encodeURIComponent("杂项"),
}


const cartStore=useCartStore()

onMounted(()=>{
  cartStore.updateCart()
})

</script>

<template>
  <header class='app-header'>
    <div class="container">
      <h1 class="logo">
        <RouterLink to="/">
          <img src="@/assets/logo.png">
        </RouterLink>
      </h1>
      <ul class="app-header-nav ">
        <li class="home">
          <RouterLink to="/">首页</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.jujia>居家</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.meishi>美食</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.fushi>服饰</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.muying>母婴</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.gehu>个护</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.yanxuan>严选</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.shuma>数码</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.yundong>运动</RouterLink>
        </li>
        <li>
          <RouterLink active-class="active" :to=catUrls.zaxiang>杂项</RouterLink>
        </li>
      </ul>
    </div>
  </header>
</template>


<style scoped lang='scss'>
.app-header {
  display: flex;
  align-items: center;
  height: 100px;
  background: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);

  .container {
    display: flex;
    align-items: center;
    justify-content: space-between;

    .logo {
      position: relative;
      bottom: -5px;
      display: flex;
      width: 300px;
    }

    ul {
      min-width: 70%;
      display: flex;
      height: 53px;
      //justify-content: flex-end;
      align-items: center;
      li {
        width: 100%;
        display: flex;
        justify-content: center;
        a {
          padding: 0 10%;
          color: #000;
          line-height: 1;
          font-size: 22px;
          display: inline-block;

          &:hover {
            color: $themeColor;
          }

          &.active {
            color: $themeColor;
          }
        }

        ~li {
          border-left: 2px solid #000;
        }
      }
    }
  }
}
</style>
