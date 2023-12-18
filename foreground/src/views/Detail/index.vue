<script setup>
import {getProductDetailApi} from "@/apis/foreground.js";
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {useCartStore} from "@/stores/cartStore";
import {useDetail} from "@/views/Detail/composables/useDetail.js";

const cartStore=useCartStore()
const router = useRouter()

const {detailData} = useDetail()

//商品数量
const count = ref(0);
const countChange = (count) => {
  count.value=count;
}
//添加购物车
const addCart = () => {
  cartStore.addCart({
    productId:detailData.value.id,
    price:detailData.value.price,
    count:count.value,
    selected:true
  });
  count.value=0;
  ElMessage.success('成功加入购物车');
}
</script>

<template>
  <div class="xtx-product-page">
    <el-backtop :right="20" :bottom="20"/>
    <div class="info-container" v-if="!!detailData">
      <div>
        <div class="product-info">
          <div class="media">
            <!-- 图片预览区 -->
            <img :src="detailData.coverUrl"/>
          </div>
          <div class="spec">
            <!-- 商品信息区 -->
            <p class="g-name"> {{ detailData.name }}</p>
            <p class="g-name"> {{ detailData.shopName }}</p>
            <p class="g-price">
              <span>{{ detailData.price }}</span>
            </p>
            <p class="g-stock">
              库存：
              <span>
                {{ detailData.count }}
              </span>
            </p>
            <div class="category">
              <span>{{ detailData.category }}</span>
            </div>
            <p class="g-desc">{{ detailData.description }} </p>
            <!-- 数据组件 -->
            <el-input-number v-model="count" :min="1" @change="countChange"/>
            <!-- 按钮组件 -->
            <div>
              <el-button size="large" class="btn" @click="addCart">
                加入购物车
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped lang='scss'>
.xtx-product-page {
  width: 100%;

  .product-info {
    display: flex;
    width: 60%;
    min-height: 70vh;
    margin: 3vw auto 2vw;
    border-radius: 10px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 1vw 2vw;

    .media {
      width: 100%;
      height: 100%;
      padding: 30px 50px;

      img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }

    .spec {
      flex: 1;
      min-width: 50%;
      padding: 30px 30px 30px 0;
    }
  }

  .number-box {
    display: flex;
    align-items: center;

    .label {
      width: 60px;
      color: #999;
      padding-left: 10px;
    }
  }

  .g-name {
    font-size: 26px;
  }

  .g-stock {
    color: #999;
    margin-top: 10px;
  }

  .category {
    width: 50px;
    height: 30px;
    margin-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(0, 0, 0, 0.3);
    border-radius: 10px;
    color: #fff;
  }

  .g-desc {
    color: #999;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  .g-price {
    margin-top: 10px;

    span {
      &::before {
        content: "¥";
        font-size: 14px;
      }

      &:first-child {
        color: $priceColor;
        margin-right: 10px;
        font-size: 22px;
      }
    }
  }

}

.btn {
  margin-top: 20px;
}

</style>
