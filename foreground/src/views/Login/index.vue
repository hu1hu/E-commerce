<script setup>
import { ref, watch } from "vue";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores/userStore";

const slideRef = ref(null);
const isUser = ref(true);

//获取表单示例做统一检验
const userLoginFormRef=ref(null);
const sellerLoginFormRef=ref(null);
const userRegisterFormRef=ref(null);
const sellerRegisterFormRef=ref(null);

const router=useRouter()

const registerFlag = ref(false);

const userStore = useUserStore()

// 点击移动滑块
const slide = () => {
  console.log(slideRef.value);
  if(!isUser.value) {
    slideRef.value.style.left = "0";
    isUser.value = true;
  } else {
    slideRef.value.style.left = "57%";
    isUser.value = false;
  }
};

const userRegisterForm = ref({
  phoneNumber: '',
  email: '',
  userName: '',
  password: '',
  gender: '',
})
//准备顾客校验规则对象
const userRegisterRules = {
  phoneNumber: [
    {pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message: '手机号码格式不正确', trigger: 'blur'},
    {required: true, message: '请输入手机号', trigger: 'blur'}
  ],
  email: [
    {pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '邮箱格式不正确', trigger: 'blur'},
    {required: true, message: '请输入邮箱', trigger: 'blur'}
  ],
  userName: [
    {required: true, message: '请输入用户名', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 14, message: '密码长度在6至14个字符之间', trigger: 'blur'}
  ],
  gender: [
    {required: true, message: '请选择性别', trigger: 'blur'}
  ],
}

const sellerRegisterForm = ref({
  shopName: '',
  username: '',
  email: '',
  password: '',
})
//准备校验规则对象
const sellerRegisterRules = {
  phoneNumber: [
    {pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message: '手机号码格式不正确', trigger: 'blur'},
    {required: true, message: '请输入手机号', trigger: 'blur'}
  ],
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '邮箱格式不正确', trigger: 'blur'}
  ],
  userName: [
    {required: true, message: '请输入用户名', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 14, message: '密码长度在6至14个字符之间', trigger: 'blur'}
  ],
  gender: [
    {required: true, message: '请选择性别', trigger: 'blur'}
  ],
}

const userLoginForm = ref({
  phone: '',
  password: '',
})

//准备顾客登录校验规则对象
const userLoginRules = {
  phoneNumber: [
    {pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message: '手机号码格式不正确', trigger: 'blur'},
    {required: true, message: '请输入手机号', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 14, message: '密码长度在6至14个字符之间', trigger: 'blur'}
  ]
}

const sellerLoginForm = ref({
  phone: '',
  password: '',
})

//准备顾客登录校验规则对象
const sellerLoginRules = {
  phoneNumber: [
    // 手机号码正则匹配
    {pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message: '手机号码格式不正确', trigger: 'blur'},
    {required: true, message: '请输入手机号', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 14, message: '密码长度在6至14个字符之间', trigger: 'blur'}
  ]
}

// 当registerFlag或isUser改变时，重置表单
watch([registerFlag, isUser], () => {
  if (registerFlag.value || isUser.value) {
    userLoginForm.value = {
      phone: '',
      password: '',
    };
    sellerLoginForm.value = {
      phone: '',
      password: '',
    };
    userRegisterForm.value = {
      phoneNumber: '',
      email: '',
      userName: '',
      password: '',
      gender: '',
    };
    sellerRegisterForm.value = {
      phoneNumber: '',
      email: '',
      userName: '',
      password: '',
      gender: '',
    };
  }
});

const doLogin=()=>{
  const {phoneNumber,password}=isUser.value?userLoginForm.value:sellerLoginForm.value
  const formRef=isUser.value?userLoginFormRef.value:sellerLoginFormRef.value
  console.log(isUser.value)
  formRef.validate(async (valid)=>{
    //当所有项都校验通过才为true
    if (valid){
      const type = isUser.value ? '0' : '1'
      await userStore.userLogin({phoneNumber, password, type})
      ElMessage({type:'success',message:'登录成功'});
      //跳转首页
      await router.replace({path: isUser.value?'/':'/seller'})
    }
  })
}

const doRegister = () => {
  const {phoneNumber,userName,email,password,gender}=isUser.value?userRegisterForm.value:sellerRegisterForm.value
  const formRef=isUser.value?userRegisterFormRef.value:sellerRegisterFormRef.value
  formRef.validate(async (valid)=>{
    //当所有项都校验通过才为true
    if (valid){
      const type = isUser.value ? '0' : '1'
      await userStore.userRegister({phoneNumber,email,userName, password,gender, type})
      ElMessage({type:'success',message:'注册成功'});
      //跳转首页
      await router.replace({path: isUser.value?'/':'/seller'})
    }
  })
}
</script>

<template>
  <div class="login-container">
    <div class="login_bg">
      <img src="@/assets/login_bg.png" alt="bg"/>
    </div>
    <div class="login-form">
      <div ref="slideRef" class="slide-mask">
        <div class="slide-mask-logo">
          <img src="@/assets/logo.png" alt="" />
        </div>
        <div class="slide-mask-bg">
        </div>
        <div class="slide-mask-text">
          <button @click.prevent="slide">我是{{!isUser?"顾客":"商家"}}</button>
        </div>
      </div>
      <el-form ref="sellerLoginFormRef" :model="sellerLoginForm" :rules="sellerLoginRules" class="form">
        <h2>商家登录</h2>
        <el-form-item prop="phoneNumber">
          <el-input maxlength="11" placeholder="请输入手机号" v-model="sellerLoginForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input maxlength="14" placeholder="请输入密码" type="password" show-password v-model="sellerLoginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doLogin">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button class="register" type="primary" @click="registerFlag=true">注册</el-button>
        </el-form-item>
      </el-form>
      <el-divider direction="vertical" />
      <el-form ref="userLoginFormRef" :model="userLoginForm" :rules="userLoginRules" class="form">
        <h2>顾客登录</h2>
        <el-form-item prop="phoneNumber">
          <el-input maxlength="11" placeholder="请输入手机号" v-model="userLoginForm.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input maxlength="14" placeholder="请输入密码" type="password" show-password v-model="userLoginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doLogin">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button class="register" type="primary" @click="registerFlag=true">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  <el-dialog close-icon="false" v-model="registerFlag" width="30%" center>
    <h2>{{isUser?'顾客注册':'商家注册'}}</h2>
    <el-form
        v-if="isUser"
        ref="userRegisterFormRef"
        :model="userRegisterForm"
        :rules="userRegisterRules"
        class="form"
    >
      <el-form-item prop="phoneNumber">
        <el-input type="text" class="form_input"
                  v-model="userRegisterForm.phoneNumber"
                  placeholder="手机号"
                  maxlength="11"
        />
      </el-form-item>
      <el-form-item prop="email">
        <el-input type="text" class="form_input"
                  v-model="userRegisterForm.email"
                  placeholder="邮箱"
        />
      </el-form-item>
      <el-form-item prop="userName">
        <el-input type="text" class="form_input"
                  v-model="userRegisterForm.userName"
                  placeholder="用户名"
                  maxlength="12"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" class="form_input"
                  v-model="userRegisterForm.password"
                  placeholder="密码"
                  maxlength="14"
                  show-password
        />
      </el-form-item>
      <el-form-item prop="gender">
        <el-radio-group
            v-model="userRegisterForm.gender"
        >
          <el-radio label="男">男性</el-radio>
          <el-radio label="女">女性</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doRegister">注册</el-button>
      </el-form-item>
    </el-form>
    <el-form
        v-else
        ref="sellerRegisterFormRef"
        :model="sellerRegisterForm"
        :rules="sellerRegisterRules"
        class="form"
    >
      <el-form-item prop="phoneNumber">
        <el-input type="text" class="form_input"
                  v-model="sellerRegisterForm.phoneNumber"
                  placeholder="手机号"
                  maxlength="11"
        />
      </el-form-item>
      <el-form-item prop="email">
        <el-input type="text" class="form_input"
                  v-model="sellerRegisterForm.email"
                  placeholder="邮箱"
        />
      </el-form-item>
      <el-form-item prop="userName">
        <el-input type="text" class="form_input"
                  v-model="sellerRegisterForm.userName"
                  placeholder="用户名"
                  maxlength="12"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" class="form_input"
                  v-model="sellerRegisterForm.password"
                  placeholder="密码"
                  maxlength="14"
                  show-password
        />
      </el-form-item>
      <el-form-item prop="gender">
        <el-radio-group
            v-model="sellerRegisterForm.gender"
        >
          <el-radio size="large" label="男">男性</el-radio>
          <el-radio label="女">女性</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doRegister">注册</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="scss">
.login-container {
  width: 100vw;
  min-height: 100vh;
  overflow: auto;
  display: flex;
  justify-content: center;
  align-items: center;

  .login_bg {
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 0;

    img {
      width: 100%;
      opacity: 0.85;
    }

    &::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      backdrop-filter: blur(10px);
      z-index: 1;
    }
  }

  .login-form {
    display: flex;
    justify-content: space-between;
    gap: 4%;
    width: 850px;
    height: 600px;
    position: relative;
    background: #ffffff;
    border-radius: 15px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
    padding: 50px 20px;

    .slide-mask {
      position: absolute;
      top: 0;
      left: 0;
      width: 43%;
      height: 100%;
      background: url("@/assets/mask_bg.png") no-repeat center;
      background-size: cover;
      transition: all 0.7s ease-in-out;
      border-radius: 10px;
      box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
      z-index: 2;

      .slide-mask-text {
        position: absolute;
        bottom: 30px;
        left: 50%;
        transform: translateX(-50%);
        display: flex;
        align-items: center;
        justify-content: center;
        width: 150px;
        height: 80px;
        border-radius: 20px;
        border: 2px solid rgba(0, 0, 0, 0.3);
        background: rgba(0, 0, 0, 0.05);

        button {
          width: 100%;
          height: 100%;
          font-size: 24px;
          font-weight: bold;
          color: rgba(0, 0, 0, 0.6);
          cursor: pointer;
        }

        &:hover {
          border: 2px solid rgba(0, 0, 0, 0.2);
          box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);

          button {
            color: rgba(0, 0, 0, 0.5);
          }
        }
      }
    }

    .el-divider {
      margin-top: 10%;
      height: 70%;
    }

    .form {
      width: 50%;
      padding: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;

      h2 {
        font-size: 30px;
        font-weight: bold;
        color: rgba(0, 0, 0, 0.6);
        margin-bottom: 30px;
      }

      .el-form-item {
        width: 100%;
        margin-bottom: 15px;
        font-size: 20px;
        font-weight: bold;
        color: rgba(0, 0, 0, 0.6);

        .el-input {
          width: 100%;
          height: 50px;
          font-size: 18px;
          font-weight: bold;
          color: rgba(0, 0, 0, 0.6);
        }

        .el-button {
          width: 100%;
          height: 50px;
          font-size: 20px;
          font-weight: bold;
          color: #ffffff;
          margin-top: 15px;

          &.register {
            font-size: 16px;
            width: 15%;
            background: none;
            border: none;
            color: #8E88ACdd;
            margin-top: 0;
            margin-left: auto;
            transform: translateX(10px);

            &:hover {
              color: #8E88AC;
            }
          }
        }
      }
    }
  }
}

.el-dialog {
  display: flex;
  flex-direction: column;
  justify-content: center;

  h2 {
    margin-top: 0;
    text-align: center;
    font-size: 30px;
  }

  .el-button {
    width: 100%;
    height: 50px;
    font-size: 20px;
    font-weight: bold;
    color: #ffffff;
  }

  .form {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 0 30px;

    .el-form-item {
      width: 100%;
      margin-bottom: 20px;
      font-size: 20px;
      font-weight: bold;
      color: rgba(0, 0, 0, 0.6);

      &:last-child {
        margin-bottom: 0;
      }

      .el-input {
        width: 100%;
        height: 50px;
        font-size: 18px;
        font-weight: bold;
        color: rgba(0, 0, 0, 0.6);
      }

      .el-radio-group {
        display: flex;
        justify-content: center;
        width: 100%;
        height: 50px;
        font-size: 24px;
        font-weight: bold;
        color: rgba(0, 0, 0, 0.6);

        .el-radio {
          transform: scale(1.2);
        }
      }
    }
  }
}
</style>
