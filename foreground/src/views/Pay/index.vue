<script setup>
import {onMounted, ref, computed} from "vue";
import {addAddressApi, getAddressApi} from "@/apis/common";
import {useRouter} from "vue-router";
import {useCartStore} from "@/stores/cartStore";
import {ElMessage} from "element-plus";
import { provinceAndCityData, codeToText } from 'element-china-area-data'

let options=ref(provinceAndCityData);

let province,city;

const handleChange= (value)=> {
  province= codeToText[value[0]];  //省中文
  city= codeToText[value[1]];  //市中文
}

const router=useRouter()
const showDialog=ref(false);
const addFlag=ref(false);
const checkInfo = ref({})  // 订单对象
const curAddress=ref({})
const newAddress=ref({})
const cartStore=useCartStore()
const getOrderInfo=async ()=>{
  checkInfo.value=cartStore.cartList;
  console.log(checkInfo.value)
}
const getAddress=async ()=>{
  const res=await getAddressApi()
  checkInfo.value.addresses=res.data;
  curAddress.value=res.data.find((item,i)=>i===0)
}

onMounted(()=>{
  getOrderInfo()
  getAddress()
})
//切换地址
const activeAddress=ref({})
const switchAddress=(item)=>{
  activeAddress.value=item;
}
const confirmAddress=()=>{
  curAddress.value=activeAddress.value;
  showDialog.value=false;
  activeAddress.value={}
}
const addAddress=async ()=>{
  console.log(newAddress.value)
  await addAddressApi({
    receiver: newAddress.value.receiver,
    address: province+city + newAddress.value.address,
    phoneNumber: newAddress.value.phoneNumber
  })
  addFlag.value=false
  newAddress.value={
    receiver:'',
    address:'',
    phoneNumber:''
  }
  await getAddress()
}
//创建订单
const createOrder=async ()=>{
  const addressId = curAddress.value.id;
  await cartStore.settlement({addressId});
  //更新购物车
  ElMessage({type:'success',message:'支付成功'});
  await router.push('/')
}

// 获取购物车商品数量
const productCount = computed(() => {
  return cartStore.cartList.reduce((total, item) => {
    return total + item.count;
  }, 0);
})

// 获取购物车商品总价
const productPrice = computed(() => {
  return cartStore.cartList.reduce((total, item) => {
    return total + item.count * item.productPrice;
  }, 0);
})
</script>

<template>
  <div class="xtx-pay-checkout-page">
    <div class="container">
      <div class="wrapper">
        <!-- 收货地址 -->
        <h3 class="box-title">收货地址</h3>
        <div class="box-body">
          <div class="address">
            <div class="text">
              <div class="none" v-if="!curAddress">您需要先添加收货地址才可提交订单</div>
              <ul v-else>
                <li><span>收<i />货<i />人：</span>{{ curAddress.receiver }}</li>
                <li><span>联系方式：</span>{{ curAddress.phoneNumber }}</li>
                <li><span>收货地址：</span>{{ curAddress.address }}</li>
              </ul>
            </div>
            <div class="action">
              <el-button size="large" @click="showDialog = true">切换地址</el-button>
              <el-button size="large" @click="addFlag = true">添加地址</el-button>
            </div>
          </div>
        </div>
        <!-- 商品信息 -->
        <h3 class="box-title">商品信息</h3>
        <div class="box-body">
          <table class="product">
            <thead>
            <tr>
              <th width="520">商品信息</th>
              <th width="170">单价</th>
              <th width="170">数量</th>
              <th width="170">实付</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="i in checkInfo" :key="i.id">
              <td>
                <a href="javascript:;" class="info">
                  <img :src="i.productCoverUrl" alt="">
                  <div class="right">
                    <p>{{ i.productName }}</p>
                  </div>
                </a>
              </td>
              <td>&yen;{{ i.productPrice }}</td>
              <td>{{ i.count }}</td>
              <td>&yen;{{ i.count * i.productPrice }}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <!-- 金额明细 -->
        <h3 class="box-title">金额明细</h3>
        <div class="box-body">
          <div class="total">
            <dl>
              <dt>商品件数：</dt>
              <dd>{{ productCount }}件</dd>
            </dl>
            <dl>
              <dt>商品总价：</dt>
              <dd>¥{{ productPrice }}</dd>
            </dl>
          </div>
        </div>
        <!-- 提交订单 -->
        <div class="submit">
          <el-button type="primary" size="large" @click="createOrder">结算</el-button>
        </div>
      </div>
    </div>
  </div>
  <!-- 切换地址 -->
  <el-dialog v-model="showDialog" title="切换收货地址" width="30%" center>
    <div class="addressWrapper">
      <div @click="switchAddress(item)" :class="{active:activeAddress?activeAddress.id===item.id:curAddress.id===item.id}" class="text item" v-for="item in checkInfo.addresses"  :key="item.id">
        <ul>
          <li><span>收<i />货<i />人：</span>{{ item.receiver }} </li>
          <li><span>联系方式：</span>{{ item.phoneNumber }}</li>
          <li><span>收货地址：</span>{{ item.address }}</li>
        </ul>
      </div>
    </div>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="showDialog=false">取消</el-button>
      <el-button type="primary" @click="confirmAddress">确定</el-button>
    </span>
    </template>
  </el-dialog>
  <!-- 添加地址 -->
  <el-dialog v-model="addFlag" title="添加收货地址" width="30%" center>
    <el-form :model="newAddress" label-width="80px">
      <el-form-item label="收货人">
        <el-input v-model="newAddress.receiver"></el-input>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="newAddress.phoneNumber"></el-input>
      </el-form-item>
      <el-form-item label="所在地区">
        <el-cascader
            :options="options"
            v-model="newAddress.addressName"
            @change="handleChange"
            placeholder="请选择省市区"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="详细地址">
        <el-input v-model="newAddress.address"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="addFlag=false">取消</el-button>
      <el-button type="primary" @click="addAddress">确定</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
.xtx-pay-checkout-page {
  width: 100%;
  margin-top: 3vw;
  margin-bottom: 2vw;

  .wrapper {
    width: 100%;
    border-radius: 20px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    padding: 1vw 2vw;

    .box-title {
      font-size: 16px;
      font-weight: normal;
      padding-left: 10px;
      line-height: 70px;
      border-bottom: 1px solid #f5f5f5;
    }

    .box-body {
      padding: 20px 0;
    }
  }
}

.address {
  border: 1px solid #f5f5f5;
  display: flex;
  align-items: center;

  .text {
    flex: 1;
    min-height: 90px;
    display: flex;
    align-items: center;

    .none {
      line-height: 90px;
      color: #999;
      text-align: center;
      width: 100%;
    }

    >ul {
      flex: 1;
      padding: 20px;

      li {
        line-height: 30px;

        span {
          color: #999;
          margin-right: 5px;

          >i {
            width: 0.5em;
            display: inline-block;
          }
        }
      }
    }

    >a {
      color: $themeColor;
      width: 160px;
      text-align: center;
      height: 90px;
      line-height: 90px;
      border-right: 1px solid #f5f5f5;
    }
  }

  .action {
    width: 420px;
    text-align: center;

    .btn {
      width: 140px;
      height: 46px;
      line-height: 44px;
      font-size: 14px;

      &:first-child {
        margin-right: 10px;
      }
    }
  }
}

.product {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;

  .info {
    display: flex;
    text-align: left;

    img {
      width: 70px;
      height: 70px;
      margin-right: 20px;
    }

    .right {
      line-height: 24px;

      p {
        &:last-child {
          color: #999;
        }
      }
    }
  }

  tr {
    th {
      background: #f5f5f5;
      font-weight: normal;
    }

    td,
    th {
      text-align: center;
      padding: 20px;
      border-bottom: 1px solid #f5f5f5;

      &:first-child {
        border-left: 1px solid #f5f5f5;
      }

      &:last-child {
        border-right: 1px solid #f5f5f5;
      }
    }
  }
}

.my-btn {
  width: 228px;
  height: 50px;
  border: 1px solid #e4e4e4;
  text-align: center;
  line-height: 48px;
  margin-right: 25px;
  color: #666666;
  display: inline-block;

  &.active,
  &:hover {
    border-color: $themeColor;
  }
}

.total {
  dl {
    display: flex;
    justify-content: flex-end;
    line-height: 50px;

    dt {
      i {
        display: inline-block;
        width: 2em;
      }
    }

    dd {
      width: 240px;
      text-align: right;
      padding-right: 70px;

      &.price {
        font-size: 20px;
        color: $priceColor;
      }
    }
  }
}

.submit {
  text-align: right;
  padding: 60px;
  border-top: 1px solid #f5f5f5;
}

.addressWrapper {
  max-height: 500px;
  overflow-y: auto;
}

.text {
  flex: 1;
  min-height: 90px;
  display: flex;
  align-items: center;

  &.item {
    border: 1px solid #f5f5f5;
    margin-bottom: 10px;
    cursor: pointer;

    &.active,
    &:hover {
      border-color: $themeColor;
      background: lighten($themeColor, 50%);
    }

    >ul {
      padding: 10px;
      font-size: 14px;
      line-height: 30px;
    }
  }
}
</style>
