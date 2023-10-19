<script setup >
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const token = ref(null)
const refreshToken = ref(null)

const FETCH_API = import.meta.env.VITE_API_ROOT
const router = useRouter()
const username = ref('')
const password = ref('')
const statusCode = ref(0)
const errText = ref('')
const activeClass = ref(false)
const className = ref('')
const warning = ref(false)


const login = async () =>{
  let user = {
    username : username.value.trim(),
    password : password.value.trim()
  }
  try {
        const res = await fetch(FETCH_API + '/api/token',{
                    method : "POST",
                    headers: {
                      "Content-Type": "application/json"
                    },
                    body: JSON.stringify(user)
                  })


         //เข้าสู่ระบบเสร็จสมบูรณ์ ตั้งค่าคุกกี้ "token" และ "refreshToken" ด้วย $cookies.set() และนำทางผู้ใช้ไปยังหน้า "api/admin/announcement" ด้วย router.push().         
        if(res.status === 200){
          statusCode.value = 200
          errText.value = 'Login Successfully'
          activeClass.value = true
          className.value = 'alert-success'

          const tokens = await res.json()

          token.value = tokens.token
          refreshToken.value = tokens.refreshToken
          
          localStorage.setItem("token",token.value)
          localStorage.setItem("refreshToken",refreshToken.value)

          setTimeout(function() {
              localStorage.removeItem("refreshToken");
          }, 2 * 60 * 1000);

          router.push({name:'home'})

        }else if(res.status === 404){
          statusCode.value = 404
          errText.value = 'A user with the specified username DOES NOT exist'
          activeClass.value = true
          className.value = 'alert-error'
        }else if(res.status === 401){
          statusCode.value = 401
          errText.value = 'Password Incorrect'
          activeClass.value = true
          className.value = 'alert-error'
        }
        warning.value = true   
    } catch (error) {
        console.log('error ',error)
        router.push('/login');
    }
}

</script>
<template>
    <div class="w-full h-full">
      <div class="flex flex-col w-full h-full mt-32 flex justify-center items-center drop-shadow-xl">
        <div class="w-1/2 bg-white rounded-lg shadow dark:border w-2/5 dark:bg-gray-800 dark:border-gray-700">
          
            <div class="p-6 space-y-4 md:space-y-6 sm:p-10">

            <h1 class="text-xl font-bold leading-tight tracking-tight text-blue-800 md:text-3xl dark:text-white">
              SAS Login
            </h1>
  
            <div>
              <label for="email" class="block mb-2 text-lg font-medium text-gray-900 dark:text-white">Username</label>
              <input
                v-model="username"
                type="text"
                class="ann-username bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="siam"
                required
              />
            </div>
  
            <div>
              <label for="password" class="block mb-2 text-lg font-medium text-gray-900 dark:text-white">Password</label>
              <input
                v-model="password"
                type="password"
                placeholder="••••••••"
                class="ann-password bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                required
              />
            </div>
  
                <!-- Warning Text -->
            <div :class="[warning ? className : '', 'alert rounded-lg shadow dark:border']" v-if="warning">
            <span class="ann-message font-semibold">{{ errText }}</span>
            </div>

            <div class="flex w-full justify-center items-center">
                <button class="ann-button border border-blue-500 rounded-lg bg-blue-800 hover:bg-blue-900 text-white w-full h-full py-2.5" @click="login">LOGIN</button>
            </div>
          </div>
        </div>

        <router-link :to="{name:'UserAnnView'}"><div class="mt-5 hover:text-blue-500 cursor-pointer">
          Continue without logging in
        </div></router-link>
        
      </div>
    </div>
  </template>
  


