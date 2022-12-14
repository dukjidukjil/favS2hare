<template>
  <!-- eslint-disable -->
  <div>
    <v-container>
      <div class="bar-top">
        <router-link to="signin">
          <v-icon>mdi-keyboard-backspace</v-icon>
        </router-link>
      </div>
      <v-row class="logo-part text-center">
        <v-col>
          <img class="logo" src="@/assets/favshare.png" alt="Logo" />
        </v-col>
      </v-row>
      <v-form ref="sendingForm">
        <v-row no-gutters v-if="!isConfirmed">
          <v-col offset="1" cols="10">
            <v-text-field
              v-model="email"
              prepend-inner-icon="mdi-account-box"
              :rules="[rules.isEmail]"
              label="ID"
              placeholder="e-mail@example.com"
              background-color="#FFE3A9"
              filled
              rounded
              dense
              @keydown.enter.prevent="sendAuthNumber"
              @input="resetAuthNumber"
            ></v-text-field>
          </v-col>
          <v-col v-if="!isSent" offset="7" cols="4">
            <v-btn
              class="btn"
              color="#FF5D5D"
              rounded
              dark
              @click.prevent="sendAuthNumber"
              >인증번호 발송</v-btn
            >
          </v-col>
        </v-row>
      </v-form>
      <v-form ref="confirmForm" v-if="isSent && !isConfirmed">
        <v-row no-gutters>
          <v-col offset="1" cols="10">
            <v-text-field
              v-model="authNumber"
              :rules="[rules.authNumberLength]"
              label="인증번호"
              placeholder="_ _ _ _ _ _ _ _ _ _ _ _ _ _ _"
              background-color="#FFE3A9"
              filled
              rounded
              dense
              @keydown.enter.prevent="checkAuthNumber"
            ></v-text-field>
          </v-col>
          <v-col offset="7" cols="4">
            <v-btn
              color="#ff5d5d"
              class="btn"
              rounded
              dark
              @click.prevent="checkAuthNumber"
              >확인</v-btn
            >
          </v-col>
        </v-row>
      </v-form>
      <v-form ref="passwordForm" v-if="isConfirmed">
        <v-row>
          <v-col offset="1" cols="10">
            <v-text-field
              v-model="password"
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              :rules="[rules.pwLength]"
              label="PW"
              background-color="#FFE3A9"
              :type="showPassword ? 'text' : 'password'"
              filled
              rounded
              dense
              @click:append="showPassword = !showPassword"
            ></v-text-field>
          </v-col>
          <v-col offset="1" cols="10">
            <v-text-field
              v-model="password2"
              :rules="[rules.pwSame]"
              label="PW 확인"
              background-color="#FFE3A9"
              type="password"
              filled
              rounded
              dense
            ></v-text-field>
          </v-col>
        </v-row>
      </v-form>
    </v-container>
    <v-footer fixed padless>
      <v-btn
        v-if="isConfirmed"
        @click.prevent="changePassword"
        color="#ff5d5d"
        height="50"
        block
        dark
        tile
        >비밀번호 변경하기</v-btn
      >
    </v-footer>
  </div>
</template>

<script>
import axios from "axios";
import api from "@/api/springRestAPI";
/* eslint-disable */
export default {
  name: "FindPwView",
  data() {
    return {
      email: "",
      authNumber: "",
      password: "",
      password2: "",
      receivedAuthNumber: "111111111111111",
      isSent: false,
      isConfirmed: false,
      showPassword: false,
      rules: {
        isEmail: (value) => this.checkEmail(value) || "ID는 이메일 형식입니다",
        authNumberLength: (value) =>
          value.length == 15 || "인증번호는 15자입니다",
        pwLength: (value) =>
          (value.length >= 9 && value.length <= 16) ||
          "PW는 8~16자로 작성해주세요",
        pwSame: (value) => value === this.password || "PW와 일치하지 않습니다",
      },
    };
  },
  methods: {
    checkEmail(email) {
      /* eslint-disable-next-line */
      const reg =
        /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

      return reg.test(email);
    },
    sendAuthNumber() {
      if (this.$refs.sendingForm.validate()) {
        axios
          .get(api.user.email(this.email))
          .then(() => {
            axios
              .get(api.userPassword.sendAuthToEmail(this.email))
              .then((response) => {
                this.receivedAuthNumber = response.data;
                this.isSent = true;
              })
              .catch((error) => {
                console.log("인증번호 전송 API 에러: ", error);
              });
          })
          .catch((error) => {
            console.log("가입된 사용자 확인 API 에러: ", error);
            alert("회원가입되지 않은 ID입니다");
          });
      } else {
        this.email = "";
        alert("이메일 형식의 ID를 입력해주세요");
      }
    },
    checkAuthNumber() {
      if (this.$refs.confirmForm.validate()) {
        if (this.authNumber === this.receivedAuthNumber) {
          this.isConfirmed = true;
        } else {
          this.authNumber = "";
          alert("잘못된 인증번호입니다");
        }
      } else {
        this.authNumber = "";
        alert("인증번호의 자리수를 확인해주세요");
      }
    },
    changePassword() {
      if (this.$refs.passwordForm.validate()) {
        axios
          .put(
            api.userPassword.updatePw(),
            {},
            {
              params: {
                email: this.email,
                password: this.password,
              },
            }
          )
          .then(() => {
            alert("비밀번호 변경이 완료되었습니다. 다시 로그인해주세요");
            this.$router.push({ name: "signin" });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    resetAuthNumber() {
      this.authNumber = "";
      this.isSent = false;
    },
  },
};
</script>

<style scoped>
.bar-top {
  padding: 15px;
}

.logo-part {
  height: 220px;
  line-height: 220px;
  margin-bottom: 10px;
}

.logo {
  height: 55px;
}

.btn {
  width: 100%;
  margin-bottom: 30px;
}
</style>
