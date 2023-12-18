<script setup>
import { ref } from 'vue';
import {addProductApi, deleteProductApi, updateProductApi, getProductListApi} from '@/apis/background';
import {useUserStore} from "@/stores/userStore";
import {onMounted} from "vue";
import {ElMessage} from "element-plus";
import ProductItem from "@/components/ProductItem/index.vue";

const addFlag = ref(false);
const editFlag = ref(false);

const product = ref({
  name: '',
  coverUrl: '',
  category: '',
  price: '',
  count: '',
});

const newProduct = ref({
  name: '',
  coverUrl: '',
  category: '',
  price: '',
  count: '',
});

const userStore = useUserStore();

const productList = ref([]);

// Function to add a new product
const addProduct = async (product) => {
  // Add your logic here to handle adding a new product
  await addProductApi({ ...product });
  ElMessage.success('添加成功');
  addFlag.value = false;
  newProduct.value = {
    name: '',
    coverUrl: '',
    category: '',
    price: '',
    count: '',
  };
  await fetchProducts()
};

// Function to delete a product
const deleteProduct = async (productId) => {
  // Add your logic here to handle deleting a product
  const list = [productId]
  await deleteProductApi({ list });
  await fetchProducts()
};

// Function to update a product
const updateProduct = async (product) => {
  // Add your logic here to handle updating a product
  await updateProductApi({ ...product });
  ElMessage.success('修改成功');
  editFlag.value = false;
};

// Function to fetch product data
const fetchProducts = async () => {
  // Add your logic here to fetch product data
  const res = await getProductListApi({page:1,pageSize:200});
  productList.value = res.data.rows;
};

onMounted(() => fetchProducts());

const onEdit = (row) => {
  console.log(row);
  product.value = row;
  editFlag.value = true;
};
</script>

<template>
  <div class="product-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-button type="primary" @click="addFlag = true">添加商品</el-button>
      </el-form-item>
    </el-form>

    <div class="product-list">
      <div class="product-detail" v-for="product in productList" :key="product.id">
        <img v-img-lazy="product.coverUrl" alt="" />
        <div class="right-wrapper">
          <p class="name ellipsis">商品名称：{{ product.name }}</p>
          <p class="id">商品id：{{product.id}}</p>
          <p class="price">商品价格：&yen;{{ product.price }}</p>
          <p class="count">商品存货：{{ product.count }}</p>
          <p class="category">商品分类：{{ product.category }}</p>
        </div>
        <div class="buttons">
          <el-button type="primary" size="small" @click="onEdit(product)">修改</el-button>
          <el-popconfirm title="确认删除吗?" confirm-button-text="确认" cancel-button-text="取消" @confirm="deleteProduct(product.id)">
            <template #reference>
              <a href="javascript:;">
                <el-button type="danger" size="small">删除</el-button>
              </a>
            </template>
          </el-popconfirm>
        </div>
      </div>
    </div>

    <el-dialog v-model="editFlag" title="修改商品详情" width="30%" center>
      <el-form :model="product" label-width="100px" class="demo-ruleForm">
        <el-form-item label="商品名" prop="name">
          <el-input v-model="product.name"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="product.description"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="product.category" placeholder="请选择">
            <el-option label="居家" value="居家"></el-option>
            <el-option label="美食" value="美食"></el-option>
            <el-option label="服饰" value="服饰"></el-option>
            <el-option label="母婴" value="母婴"></el-option>
            <el-option label="个护" value="个护"></el-option>
            <el-option label="严选" value="严选"></el-option>
            <el-option label="数码" value="数码"></el-option>
            <el-option label="运动" value="运动"></el-option>
            <el-option label="杂项" value="杂项"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="coverUrl">
          <el-input v-model="product.coverUrl"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="product.price"></el-input>
        </el-form-item>
        <el-form-item label="存货" prop="count">
          <el-input v-model="product.count"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="editFlag = false">取 消</el-button>
      <el-button type="primary" @click="updateProduct(product)">确 定</el-button>
    </span>
    </el-dialog>

    <el-dialog v-model="addFlag" title="添加商品" width="30%" center>
      <el-form :model="newProduct" label-width="100px" class="demo-ruleForm">
        <el-form-item label="商品名" prop="name">
          <el-input v-model="newProduct.name"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="newProduct.description"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="newProduct.category" placeholder="请选择">
            <el-option label="居家" value="居家"></el-option>
            <el-option label="美食" value="美食"></el-option>
            <el-option label="服饰" value="服饰"></el-option>
            <el-option label="母婴" value="母婴"></el-option>
            <el-option label="个护" value="个护"></el-option>
            <el-option label="严选" value="严选"></el-option>
            <el-option label="数码" value="数码"></el-option>
            <el-option label="运动" value="运动"></el-option>
            <el-option label="杂项" value="杂项"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="coverUrl">
          <el-input v-model="newProduct.coverUrl"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="newProduct.price"></el-input>
        </el-form-item>
        <el-form-item label="存货" prop="count">
          <el-input v-model="newProduct.count"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="addFlag = false">取 消</el-button>
      <el-button type="primary" @click="addProduct(newProduct)">确 定</el-button>
    </span>
    </el-dialog>
  </div>

</template>

<style scoped lang="scss">
.product-container {
  width: 100%;
  height: calc(90vh - 101px);

  .product-list {
    width: 100%;
    height: 80%;
    overflow-y: auto;
    overflow-x: hidden;
    padding: 0 2rem;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    gap: 20px;

    .product-detail {
      position: relative;
      display: flex;
      gap: 5px;
      width: 400px;
      padding: 10px 15px;
      height: fit-content;
      transition: all .5s;
      border-radius: 10px;
      background: rgba(250, 250, 250, 1);

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
        margin-left: 1rem;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        width: 100%;

        .name {
          width: 220px;
          font-size: 1.2rem;
          font-weight: bold;
        }

        .ellipsis {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }

      .buttons {
        position: absolute;
        right: 10px;
        bottom: 10px;
        display: flex;
        gap: 5px;
      }
    }
  }
}
.demo-form-inline {
  padding: 2rem 2rem;
  float: right;
}
.demo-form-inline el-input {
  --el-input-width: 220px;
}
.product-table {
  padding: 0 2rem;
}
</style>
