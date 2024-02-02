<template>
  <div class = "sign-in">
    <form class="input-box">
      <div style="text-align: right; margin-bottom: 10px;">
          <button class = "btn btn-outline-dark" @click = "setSignUpMode">Sign Up</button>
        </div>
      <div style="text-align: center;">
        <img class="logo" src="../assets/icons8-gmail-94.png" alt="Logo">
      </div>
      <h1 class = 'head'>Sign In</h1>

      <div style="text-align:center">
        <input type="text" v-model="email" class="input-field" placeholder="Email" required>
        <input type="password" v-model="password" class="input-field" placeholder="Password" required>
        <br><br><br><br>
        Create an account instead
        <br><br>
        <button type="button" class="btn btn-outline-success input-button" @click="signIn">Sign In</button>
      </div>
    </form>
  </div>  
</template>

<script>
import axios from 'axios';

export default {
  name: 'SignIn',
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    signIn() {
      if (this.validateForm()) {
        axios.post("http://localhost:8080/signin", {
          email: this.email,
          password: this.password,
          receivers: [],
          attachment: [], 
        })
        .then(response => {
          const result = response.data;
          console.log(result)
          if (result === -1) {
            alert('E-mail not found');
          } 
          else if (result === 0) {
            alert('wrong password');
          } 
          else {
            this.$root.sender = result ;
          }

        })
        
      }
    },
    validateForm() {
      // Validation logic
      if (!this.email.trim()) {
        alert('Email address is required');
        return false;
      }

      return true;
    },

    setSignUpMode() {
        const element1 = document.querySelector('.sign-up') ;
        const element2 = document.querySelector('.sign-in') ;

        if (element2.style.display === "block")
            element2.style.display = "none" ;

        element1.style.display = "block";

      },
  }
}
</script>

<style>
.sign-in{
  display: block;
  width: 25%;
  height: 65vh;
  margin-top: 11%;
  margin-left: 37%;
  display: none;
  box-shadow: 20px 20px 20px 20px;
  border-radius: 20px;
}

.logo {
  width: 100px;
}
.input-field {
  width: 300px;
  height: 40px;
  display: block;
  border: 1px solid #ccc;
  margin-bottom: 25px;
  margin-right: auto;
  margin-left: auto;
}

.input-button {
  width: 150px;
  height: 40px;
  margin-right: auto;
  margin-left: auto;
  border: 1px solid #ccc;
  text-align: center;
  align-items: center;
  display: inline-block;
  cursor: pointer;
}

.input-box {
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  background-color: white;
  border: 3px solid gray;
  border-radius: 10px;
}
</style>