<template>
  <div class="login">
    <div class="login-container">
      <h1 class="login-title">Welcome Back</h1>
      <form class="login-form">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" class="form-control" v-model="email"
                 placeholder="Enter your email">
          <label for="password">Password</label>
          <input type="password" id="password" class="form-control" v-model="password"
                 placeholder="Enter your password">
        </div>
        <button type="submit" class="btn btn-primary" @click.prevent="login">Login</button>
      </form>
      <div class="login-footer">
        <a href="#" class="login-link">Forgot Password?</a>
        <span class="login-separator"></span>
        <a href="#" class="login-link">Create an Account</a>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    login() {
      // 在这里处理登录逻辑
      console.log('Email:', this.email)
      console.log('Password:', this.password)
    },
  },
}
</script>
<style scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-size: cover;
}

/*.login {
  background: url("https://anhusky.oss-cn-beijing.aliyuncs.com/img/yanbinyan.jpg") center center no-repeat;
}*/

.login-container {
  position: relative;
  width: 400px;
  padding: 30px;
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.login-title {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 28px;
  text-align: center;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
}

.form-control {
  display: block;
  width: 100%;
  height: 40px;
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  background-color: #f5f5f5;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1), 1px 1px 1px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
}

.btn {
  display: block;
  width: 100%;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #4caf50;
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
}

.btn:hover {
  background-color: #3e8e41;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.2);
}

.login-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
  margin-top: 20px;
}

.login-link {
  color: #4caf50;
  text-decoration: none;
  font-size: 16px;
  margin: 0 10px;
  transition: all 0.3s ease;
}

.login-link:hover {
  text-decoration: underline;
}
</style>
