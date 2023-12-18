<script setup>
import {
  Document,
  Menu as IconMenu,
} from "@element-plus/icons-vue";

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}

</script>

<template>
  <div class="sidebar">
    <div class="logo">
      <img src="@/assets/logo.png"/>
    </div>
    <el-menu
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
    >
      <el-menu-item index="1" @click="$router.push(`/seller`)">
        <el-icon><document /></el-icon>
        <a>销量详情</a>
      </el-menu-item>
      <el-menu-item index="2" @click="$router.push(`/seller/product`)">
        <el-icon><icon-menu /></el-icon>
        <a>店铺商品</a>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<style scoped lang="scss">
.sidebar {
  width: 15vw;
  height: 100vh;
  background: #fff;
  border-right: 1px solid #eee;

  .logo {
    height: 12vh;
    color: white;
    font-size: 3vh;
    font-weight: bold;
    padding: 1rem;
    text-align: center;
    letter-spacing: 2px;

    img {
      width: 100%;
    }
  }

  .el-menu-vertical-demo {
    height: 88vh;
    background: #fff;

    .el-menu-item {
      color: #000;
      font-size: 16px;
      font-weight: bold;
      padding: 1rem;
      text-align: center;
      border-bottom: 1px solid #eee;

      &.is-active {
        background: #d6d6d6;
      }

      a {
        color: #000;
      }

      &:hover {
        background: #d6d6d6;
      }
    }
  }
}
</style>
