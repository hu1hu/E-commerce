<script setup>
import { ref } from 'vue';
import {getSalesApi} from '@/apis/background';
import {onMounted,computed} from "vue";

const startTime = ref('');
const endTime = ref('');

const salesList = ref([]);

// Function to fetch product data
const fetchSales = async () => {
  // Add your logic here to fetch product data
  const res = await getSalesApi({begin:startTime.value,end:endTime.value,page:1,pageSize:40});
  salesList.value = res.data.rows;
};

onMounted(() => fetchSales());

// 计算总销售额
const totalSales = computed(() => {
  let total = 0;
  salesList.value.forEach(item => {
    total += item.sum;
  });
  return total;
});

</script>

<template>
  <div class="sales-container">
    <el-form>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="startTime"
          placeholder="选择日期"
          value-format="YYYY-MM-DD"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="endTime"
          placeholder="选择日期"
          value-format="YYYY-MM-DD"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchSales">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="salesList" class="product-table" style="width: 100%">
      <div class="holder-container" v-if="salesList.length === 0">
        <el-empty description="暂无订单数据" />
      </div>
      <el-table-column prop="id" label="商品ID" width="300">
      </el-table-column>
      <el-table-column prop="name" label="商品名称" width="400">
      </el-table-column>
      <el-table-column prop="totalNumber" label="销量（件）" width="400">
      </el-table-column>
      <el-table-column prop="totalMoney" label="销售额（元）" width="400">
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.sales-container {
  padding: 0 2rem;
  height: calc(90vh - 101px);

}
.demo-form-inline el-input {
  --el-input-width: 220px;
}
.product-table {
  padding: 1rem 2rem;
}

.el-form {
  display: flex;
  gap: 20px;
  width: 100%;
  padding: 30px 30px 0;
}
</style>
