<template>
  <div class="login-container">
    <div class="login-left">
      <div class="login-title">
        <h2>로그인</h2>
      </div>
      <div class="login-form">
        <div>
          <label for="user_id">아이디 :</label>
          <input v-model="userId" id="user_id" class="input-field" type="text" name="id" maxlength="20" placeholder="아이디를 입력해주세요" required>
        </div>
        <div>
          <label for="user_pw">비밀번호 :</label>
          <input v-model="userPw" id="user_pw" class="input-field" type="password" name="password" maxlength="20" placeholder="비밀번호를 입력해주세요" required>
        </div>
        <div>
          <img src="@/assets/Basic_Logo.png" alt="Logo">
        </div>
      </div>
    </div>
    <div class="login-right">
      <button type="button" @click="checkLogin" class="login-btn">로그인</button>
      <button type="button" @click="regPage" class="register-btn">회원등록</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      userPw: ''
    };
  },
  methods: {
    checkLogin() {
      const data = { userId: this.userId, userPw: this.userPw };

      if (!data.userId || !data.userPw) {
        alert('아이디와 비밀번호를 확인해 주세요.');
        return;
      }

      //axios.post('http://localhost:9420/VueProject-Basic-BO/check_login_pro', data)
      axios.post('/api/check_login_pro', data)
        .then(response => {
          if (response.data.success) {
            alert(response.data.message);
            this.$router.push({ name: 'main' });
          } else {
            alert(response.data.message);
            this.userPw = '';
          }
        })
        .catch(error => {
          console.log(error?.response?.status ?? '응답을 받지 못했습니다.');
        });
    },
    regPage() {
      this.$router.push("user/user_regi");
    }
  }
}
</script>

<style scoped>
body, html {
    height: 100%;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 500px;
    border: solid;
}

.login-left, .login-right {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: relative;
    left: 115px;
}

.login-right {
    padding: 10px;
    left: -40px;
    top: -9px;
}

.login-btn, .register-btn {
    margin-top: 40px;
    padding: 5px 10px;
    cursor: pointer;
}

.login-form > div {
    display: flex;
    align-items: center;
    margin: 5px;
    padding-bottom: 10px;
}

.input-field {
    margin-left: 10px;
    flex-grow: 1;
    border-width: 0px; /* 테두리 */
}

label {
    margin-right: 10px;
}

.login-title {
    position: relative;
    margin-bottom: 30px; 
}

.login-title:after {
    content: "";
    position: absolute;
    bottom: 6px;
    left: 50%; 
    transform: translateX(-50%); 
    width: 400px; 
    height: 2px;
    background-color: #000;
}

div.login-form{
	position: relative;
	top: -20px;
	right: 69px;
}

div.login-title > h2{
	margin-top: 5px;
	color: #004aad;
}

img {
	width: 240px;
	height: 70px;
}

div.login-right > button.login-btn{
	width: 100px;
	height: 55px;
	background: white;
}

div.login-right > button.register-btn{
	width: 100px;
	background: white;
}

div.login-left > div.login-form > div > label{
	color: #004aad;
}
</style>
