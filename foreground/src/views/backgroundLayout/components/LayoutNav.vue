<script setup>
import {useRouter} from "vue-router";
import {useUserStore} from "@/stores/userStore";
import {ElMessage} from "element-plus";
import {onMounted, ref} from "vue";

const router = useRouter()

const userStore = useUserStore()

const shopName = ref('')

const handleConfirm = () => {
  router.push('/login')
  userStore.clearUserInfo()
}

onMounted(()=>{
  const userStore = useUserStore()
  if (!userStore.userInfo || !Object.keys(userStore.userInfo).length) {
    ElMessage.warning('请先登录')
    router.push('/login')
  }
  shopName.value = userStore.userInfo.userName
})

</script>

<template>
  <nav class="app-topnav">
    <ul>
      <template v-if="true">
        <li><a href="javascript:;"><i class="iconfont icon-dianpu"></i>{{shopName}}</a></li>
        <li>
          <el-popconfirm
              title="确认退出吗?"
              confirm-button-text="确认"
              cancel-button-text="取消"
              @confirm="handleConfirm"
          >
            <template #reference>
              <a href="javascript:;">退出登录</a>
            </template>
          </el-popconfirm>
        </li>
      </template>
    </ul>
  </nav>
</template>


<style scoped lang="scss">
.app-topnav {
  //background: $themeColor;
  border-bottom: 1px solid #27BA9B22;
  ul {
    display: flex;
    height: 10vh;
    justify-content: flex-end;
    align-items: center;
    li {
      a {
        font-size: 18px;
        padding: 0 15px;
        color: #9a9595;
        line-height: 1;
        display: inline-block;

        i {
          font-size: 18px;
          margin-right: 2px;
        }

        &:hover {
          color: #000;
        }
      }

      ~li {
        a {
          border-left: 2px solid #E9E2E2;
        }
      }
    }
  }
}
</style>
