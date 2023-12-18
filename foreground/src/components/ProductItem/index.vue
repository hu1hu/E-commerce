<script setup>
import { computed } from 'vue'
import {useUserStore} from "@/stores/userStore";

const props = defineProps({
  product: {
    type: Object,
    default: () => ({})
  }
})

const link = computed(() => {
  return {
    path: `/detail/${props.product.id}`
  }
})

const userStore = useUserStore()

</script>
<template>
  <RouterLink :to=link class="goods-item">
    <img v-img-lazy="product.coverUrl" alt="" />
    <div class="right-wrapper">
      <p class="name ellipsis">{{ product.name }}</p>
      <p class="price">&yen;{{ product.price }}</p>
    </div>
  </RouterLink>
</template>
<style lang="scss">
.goods-item {
  display: flex;
  width: 370px;
  padding: 10px 15px;
  text-align: center;
  transition: all .5s;
  border-radius: 10px;
  background: rgb(253, 253, 253);

  &:hover {
    transform: translate3d(0, -3px, 0);
    box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
  }

  img {
    width: 130px;
    height: 130px;
    border-radius: 10px;
  }

  .right-wrapper {
    flex: 1;
    max-width: 230px;
    padding: 0 15px 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  p {
    padding-top: 10px;
  }

  .name {
    font-size: 16px;
    text-align: left;
  }

  .desc {
    color: #999;
    height: 29px;
  }

  .price {
    color: $priceColor;
    font-size: 20px;
    text-align: left;
  }
}
</style>
