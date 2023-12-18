<script setup>
import { ref,onMounted,watch } from 'vue'
import {useCartStore} from "@/stores/cartStore.js";
import {onBeforeRouteUpdate, useRouter} from "vue-router";
import {useUserStore} from "@/stores/userStore.js";
import {getHistoryApi, getOrderListApi} from "@/apis/foreground.js";

const sideBarRef = ref(null)

const isExpand = ref(false)

const isCart = ref(false)

const isUser = ref(false)

const router = useRouter()

const userStore = useUserStore()

const userInfo = ref({})

//点击左移侧边栏
const moveSideBar = () => {
  if (isExpand.value) {
    sideBarRef.value.style.right = '-20vw'
    isExpand.value = false
  }else {
    sideBarRef.value.style.right = '0'
    isExpand.value = true
  }
}

// 点击个人中心
const clickUser = () => {
  console.log('clickUser')
  if(isExpand.value) {
    if(isCart.value) {
      isCart.value = false;
      isUser.value = true;
    } else {
      moveSideBar()
      isUser.value = false;
    }
  } else {
    isUser.value = true;
    isCart.value = false;
    moveSideBar()
  }
}

// 点击购物车
const clickCart = () => {
  console.log('clickCart')
  if(isExpand.value) {
    if(isUser.value) {
      isUser.value = false;
      isCart.value = true;
    } else {
      moveSideBar()
      isCart.value = false;
    }
  } else {
    isCart.value = true;
    isUser.value = false;
    moveSideBar()
  }
}

const cartStore=useCartStore()

const cartList = ref([])
const browseHistory = ref([])
const orderList = ref([])

const activeName = ref('first')

// 获取浏览历史
const getBrowserHistory = async () => {
  const res = await getHistoryApi()
  browseHistory.value = res.data
}

// 查询我的订单
const getOrderList = async () => {
  const res = await getOrderListApi()
  orderList.value = res.data
}

onMounted(()=>{
  cartStore.updateCart()
  cartList.value = cartStore.cartList
  userInfo.value = userStore.userInfo
  getBrowserHistory()
  getOrderList()
})
const deleteCart = (item) => {
  cartStore.deleteCart(item)
}

// 结算
const handlePay = () => {
  router.push('/pay')
  moveSideBar()
}

// 退出登录
const logout = () => {
  console.log('logout')
  userStore.clearUserInfo()
  router.push('/login')
}

//路由发生变化，重新获取购物车列表
onBeforeRouteUpdate(()=>{
  if(isExpand.value) {
    moveSideBar()
    isUser.value = false;
    isCart.value = false;
  }
})

//监听isUser变化，如果为true，则重新获取浏览历史和订单列表
watch(isUser, (newVal, oldVal) => {
  if(newVal) {
    getBrowserHistory()
    getOrderList()
  }
})

</script>

<template>
  <div ref="sideBarRef" class="sidebar" >
    <div :class="{'side-tool-bar': true,'expand': isExpand}">
      <div class="side-tool-bar-item"  @click="clickUser">
        <div class="side-tool-bar-item-icon">
          <i class="iconfont icon-user"></i>
        </div>
        <div class="side-tool-bar-item-text">个人中心</div>
      </div>
      <div class="side-tool-bar-item" @click="clickCart">
        <div class="side-tool-bar-item-icon">
          <i class="iconfont icon-cart"></i>
        </div>
        <div class="side-tool-bar-item-text">购物车</div>
      </div>
      <div class="side-tool-bar-item">
        <el-popconfirm title="确认退出吗?" confirm-button-text="确认" cancel-button-text="取消" @confirm="logout">
          <template #reference>
            <a href="javascript:;">
              <div class="side-tool-bar-item-icon">
                <i class="iconfont icon-tuichudenglu"></i>
              </div>
              <div class="side-tool-bar-item-text">
                退出登录
              </div>
            </a>
          </template>
        </el-popconfirm>
      </div>
    </div>
    <div class="drawer-container">
      <div class="list-container" v-if="isCart">
        <div class="layer">
          <h3>购物车</h3>
          <div class="list">
            <div class="item" v-for="i in cartStore.cartList" :key="i">
              <RouterLink :to="'/detail/' + i.productId">
                <img :src="i.productCoverUrl" alt="" />
              </RouterLink>
              <div class="center">
                <p class="name ellipsis-2">
                  {{ i.productName }}
                </p>
              </div>
              <div class="right">
                <p class="price">&yen;{{ i.productPrice }}</p>
                <p class="count">x{{ i.count }}</p>
              </div>
              <td class="tc">
                <p>
                  <el-popconfirm title="确认删除吗?" confirm-button-text="确认" cancel-button-text="取消" @confirm="deleteCart(i)">
                    <template #reference>
                      <a href="javascript:;">删除</a>
                    </template>
                  </el-popconfirm>
                </p>
              </td>
            </div>
          </div>
          <div class="foot">
            <div class="total">
              <p>共 {{cartStore.allCount}} 件商品</p>
              <p>&yen; {{ cartStore.allPrice }} </p>
            </div>
            <el-button size="large" type="primary" @click="handlePay">去购物车结算</el-button>
          </div>
        </div>
      </div>
      <div class="list-container" v-if="isUser">
        <div class="layer">
          <h3>个人中心</h3>
          <div>
            <h4>用户：{{userInfo.userName}}</h4>
            <p>邮箱：{{userInfo.email}}</p>
          </div>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="浏览历史" name="first" style="height: 50vh">
              <div class="list" style="height: 100%">
                <div class="item" v-for="i in browseHistory" :key="i">
                  <RouterLink :to="'/detail/' + i.id">
                    <img :src="i.coverUrl" alt="" />
                  </RouterLink>
                  <div class="center" style="width: 180px">
                    <p class="name ellipsis-2">
                      {{ i.name }}
                    </p>
                    <p class="attr" style="font-size: 12px">
                      {{i.time}}
                    </p>
                  </div>
                  <div class="right" style="width: 40px">
                    <p class="price" style="font-size: 14px">&yen;{{ i.price }}</p>
                  </div>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="我的订单" name="second" style="height: 50vh">
              <div class="list" style="height: 100%">
                <div class="item" v-for="i in orderList" :key="i">
                  <RouterLink :to="'/detail/' + i.productId">
                    <img :src="i.productCoverUrl" alt="" />
                  </RouterLink>
                  <div class="center" style="width: 180px">
                    <p class="name ellipsis-2">
                      {{ i.productName }}
                    </p>
                    <p class="attr" style="padding-top:0">订单编号：{{i.orderId}}</p>
                    <p class="attr" style="padding-top:0;font-size: 12px">
                      {{i.time}}
                    </p>
                  </div>
                  <div class="right" style="width: 40px">
                    <p class="price" style="font-size: 14px">&yen;{{ i.price }}</p>
                    <p class="count">x{{ i.count }}</p>
                    <p></p>
                  </div>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.sidebar {
  display: flex;
  align-items: center;
  position: fixed;
  right: -20vw;
  top: 150px;
  transition: all 0.5s ease-in-out;
  z-index: 999;

  .side-tool-bar {
    width: 3.5vw;
    height: fit-content;
    display: flex;
    flex-direction: column;
    border-top-left-radius: 20px;
    border-bottom-left-radius: 20px;
    overflow: hidden;
    background: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    transition: all .5s;
    z-index: 4;

    &.expand {
      width: 3vw;

      .side-tool-bar-item-text {
        font-size: 0;
      }
    }

    .side-tool-bar-item {
      width: 100%;
      height: 80px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      color: #989898;
      font-size: 12px;
      cursor: pointer;
      //border: 1px solid #f3f3f3;

      a {
        color: #989898;
      }

      i {
        font-size: 24px;
      }

      &:hover {
        color: #f36807;

        a {
          color: #f36807;
        }
      }

      .side-tool-bar-item-icon {
        font-size: 24px;
        text-align: center;
      }
    }
  }
  .drawer-container {
    height: 70vh;
    width: 20vw;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    border-top-left-radius: 20px;
    border-bottom-left-radius: 20px;
    background: #fff;
    z-index: 3;

    .list-container {
      width: 100%;
      height: 100%;
      padding: 10px;

      .layer {
        transition: all 0.4s 0.2s;
        width: 100%;
        height: 100%;
        padding-top: 10px;

        h3 {
          text-align: center;
          font-size: 20px;
          font-weight: bold;
          line-height: 20px;
          padding-left: 10px;
          margin-bottom: 10px;
        }

        .foot {
          height: 10%;
          margin-top: 5%;
          width: 100%;
          padding: 10px;
          display: flex;
          justify-content: space-between;
          background: #ffffff;
          align-items: center;

          .total {
            padding-left: 10px;
            color: #999;

            p {
              &:last-child {
                font-size: 18px;
                color: $priceColor;
              }
            }
          }
        }
      }

      .el-tabs__content {
        height: 100px;
      }

      .list {
        height: 85%;
        overflow: auto;
        padding: 0 10px;

        &::-webkit-scrollbar {
          width: 10px;
          height: 10px;
        }

        &::-webkit-scrollbar-track {
          background: #f8f8f8;
          border-radius: 2px;
        }

        &::-webkit-scrollbar-thumb {
          background: #eee;
          border-radius: 10px;
        }

        &::-webkit-scrollbar-thumb:hover {
          background: #ccc;
        }

        .item {
          border-bottom: 1px solid #f5f5f5;
          padding: 10px 0;
          position: relative;
          display: flex;
          align-items: center;

          a {
            width: 60px;
            height: 60px;

            img {
              height: 100%;
              width: 100%;
            }

          }

          .center {
            padding: 0 10px;
            width: 130px;

            .name {
              font-size: 14px;
            }

            .attr {
              color: #999;
              padding-top: 5px;
            }
          }

          .right {
            width: 60px;
            padding-right: 20px;
            text-align: center;

            .price {
              font-size: 16px;
              color: $priceColor;
            }

            .count {
              color: #999;
              margin-top: 5px;
              font-size: 16px;
            }
          }
        }
      }
    }
  }
}
</style>
