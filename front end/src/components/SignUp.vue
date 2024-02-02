<template>
  <div class = "sign-up">
    <form class="input-box">
      <div style="text-align: right; margin-bottom: 10px;">
        <button class = "btn btn-outline-dark" @click = "setSigninMode">Sign In</button>
      </div>
      <div style="text-align: center;">
        <img class="logo" src="../assets/icons8-gmail-94.png" alt="Logo">
      </div>
      <h1 class = "head">Sign Up</h1>
      <div style="text-align:center">
        <div class="mb-3">
          <input type="text" v-model="name" @input="validateField('name')" class="form-control" :class="{'is-valid': isValidUsername, 'is-invalid': !isValidUsername && fieldTouched.name}" placeholder="Username" required>
          <div v-if="fieldTouched.name" class="invalid-feedback">Username is invalid. It should contain only letters.</div>
        </div>
        
        <div class="mb-3">
          <input type="text" v-model="email" @input="validateField('email')" class="form-control" :class="{'is-valid': isValidEmail, 'is-invalid': !isValidEmail && fieldTouched.email}" placeholder="Email" required>
          <div v-if="fieldTouched.email" class="invalid-feedback">Invalid email address. Only Gmail addresses are allowed.</div>
        </div>

        <div class="mb-3">
          <input type="password" v-model="password" @input="validateField('password')" class="form-control" :class="{'is-valid': isValidPassword, 'is-invalid': !isValidPassword && fieldTouched.password}" placeholder="Password" required>
          <div v-if="fieldTouched.password" class="invalid-feedback">Password should be at least 8 characters long.</div>
        </div>

        <br><br><br>
        Do you have an account?
        <br><br>
        <button type="button" class="btn btn-outline-success" @click="signUp">Sign Up</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'SignUp',
  data() {
    return {
      name: '',
      email: '',
      password: '',
      fieldTouched: {
        name: false,
        email: false,
        password: false,
      },
    };
  },
  computed: {
    isValidUsername() {
      return /^[a-zA-Z]+$/.test(this.name);
    },
    isValidEmail() {
      const emailRegex = /^[a-zA-Z0-9._-]+@gmail\.com$/i;
      return emailRegex.test(this.email);
    },
    isValidPassword() {
      return this.password.length >= 8;
    },
  },
  methods: {
    signUp() {
      // Validation
      if (!this.validateForm()) {
        return;
      }

      // Axios POST request
      axios.post("http://localhost:8080/signup", {
        username: this.name,
        email: this.email.toLowerCase(),
        password: this.password,
        receivers: [],
        attachment: [], 
      })
      .then(response => {
        const result = response.data;
        if (result === 0) {
          alert('Email address already exists');
        } else {
          alert('email created successfully');
        }
      })
    },
    validateField(fieldName) {
      this.fieldTouched[fieldName] = true;
    },
    validateForm() {
      // Other validation checks...
      return this.isValidUsername && this.isValidEmail && this.isValidPassword;
    },

    setSigninMode() {
        const element1 = document.querySelector('.sign-in') ;
        const element2 = document.querySelector('.sign-up') ;

        if (element2.style.display != "none")
            element2.style.display = "none" ;

        element1.style.display = "block";

      },
  }
}
</script>

<style>
.sign-up{
  width: 30%;
  height: 70vh;
  margin-top: 10%;
  margin-left: 35%;
  display: block;
  box-shadow: 20px 20px 20px 20px;
  border-radius: 20px;
}

.logo {
  width: 100px;
  margin-bottom: 10px ;
}
.head{
  margin-bottom: 50px;
}
.input-box {
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  background-color: white;
  border: 3px solid gray;
  border-radius: 10px;
  padding: 20px;
}

/* Bootstrap 5 styles for the invalid state */
.is-invalid {
  border-color: #dc3545;
  padding-right: calc(1.5em + 0.75rem);
  background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-exclamation-triangle" viewBox="0 0 16 16"><path d="M8.482.138a1.12 1.12 0 0 0-1.962 0L.275 15.862a1.12 1.12 0 0 0 1.962 1.1l6.245-11.724 6.245 11.724a1.12 1.12 0 0 0 1.962-1.1L8.482.138z"/><path d="M7.5 5a.5.5 0 0 1 1 0v4a.5.5 0 0 1-1 0v-4a.5.5 0 0 1 1 0z"/></svg>');
  background-repeat: no-repeat;
  background-position: right calc(0.375em + 0.1875rem) center;
  background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
}

/* Bootstrap 5 styles for the valid state */
.is-valid {
  border-color: #198754;
  padding-right: calc(1.5em + 0.75rem);
  background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16"><path d="M13.404 3.97a.5.5 0 0 1 0 .707l-7.07 7.071a.5.5 0 0 1-.708 0L2.95 9.95a.5.5 0 1 1 .708-.708l2.828 2.828 6.364-6.364a.5.5 0 0 1 .708 0z"/></svg>');
  background-repeat: no-repeat;
  background-position: right calc(0.375em + 0.1875rem) center;
  background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
}

/* Bootstrap 5 styles for feedback messages */
.invalid-feedback {
  display: block;
  margin-top: 0.25rem;
  font-size: 80%;
  color: #dc3545; /* For invalid feedback */
}
</style>