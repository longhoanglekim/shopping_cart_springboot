
<template>
  <div class="container">
    <div style="margin-top: 200px;">
      <form @submit.prevent="login" id="loginForm">
        <table>
          <tr>
            <td>Enter the account:</td>
            <td><input type="text" id="username" v-model="username" required="required"></td>
          </tr>
          <tr>
            <td>Enter the password:</td>
            <td><input type="password" id="password" v-model="password" required="required"></td>
          </tr>
          <tr>
            <td><input type="submit" value="Login" class="button-green"></td>
          </tr>
        </table>
        <input type="hidden" id="token" value="">

<!--        <p style="text-align: left">Or login with admin account <span><a href="${pageContext.request.contextPath}/admin_login"-->
<!--                                                class="button-green">Admin login</a> </span></p>-->
      </form>
    </div>
    <p>Haven't got an account?</p>
<!--    <a href="/register" id="register-link">Register</a>-->
  </div>
</template>
<script>
export default {
  name: 'LoginUser',
  data() {
    return {
      username : '',
      password : '',
      socket: null,
      messages: [] // Đảm bảo rằng 'messages' được khởi tạo là một mảng
    }
  },
  methods: {
    async login() {
      try {
        const response = await fetch("http://localhost:8080/api/auth/login" , {
          method : 'POST',
          headers: {
            'Content-Type' : 'application/json'
          },
          body: JSON.stringify({
            username : this.username,
            password : this.password
          })
        })
        if (!response.ok) {
          throw new Error("Login error");
        }
        const data = await response.json();
        if (data.token) {
          console.log("Token :" + data.token);
        }
      }
      catch (error) {
        console.error(error);
      }
    }
   ,
    sendMessage(message) {
      if (this.socket && this.socket.readyState === WebSocket.OPEN) {
        this.socket.send(message);  // Gửi tin nhắn tới server qua WebSocket
      } else {
        console.error('WebSocket is not open');
      }
    }
  },
  mounted() {
    document.title = 'My login page';
        // Kết nối WebSocket tới endpoint /ws của Spring Boot
    //this.socket = new WebSocket('ws://localhost:8080/ws');
    this.socket = new WebSocket('ws://localhost:8080/ws?token=' + localStorage.getItem('token'));

    // Lắng nghe sự kiện mở kết nối
    this.socket.onopen = () => {
      console.log('WebSocket connection established');
      this.sendMessage('Hello from client!');
    };

    // Lắng nghe khi nhận tin nhắn từ server
    this.socket.onmessage = (event) => {
      console.log('Message from server:', event.data);
      this.messages.push(event.data);  // Lưu tin nhắn vào mảng
    };

    // Lắng nghe sự kiện khi kết nối bị đóng
    this.socket.onclose = () => {
      console.log('WebSocket connection closed');
    };

    // Lắng nghe khi có lỗi
    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  }
}
</script>
<style scoped>
.button-green {
  background-color: #04AA6D;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 10px;
}

.button-green:hover {
  background-color: darkgreen;
  color: white;
}
#register-link {
  background-color: steelblue;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 10px;
}
#register-link:hover {
  background-color: #0a58ca;
}
</style>