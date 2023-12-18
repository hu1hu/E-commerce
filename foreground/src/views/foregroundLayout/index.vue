<script setup>
import LayoutHeader from './components/LayoutHeader.vue'
import LayoutFooter from './components/LayoutFooter.vue'
import LayoutSider from "@/views/foregroundLayout/components/LayoutSider.vue";
</script>

<template>
  <div class="consumer-container">
    <LayoutHeader />
    <RouterView />
    <LayoutFooter />
    <LayoutSider/>
  </div>
</template>

<style scoped lang='scss'>
.consumer-container {
  min-height: 100vh;
  background: #ffffff;
  overflow: auto;
}
</style>
