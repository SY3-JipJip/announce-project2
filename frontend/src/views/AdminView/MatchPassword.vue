<script setup>
import {ref} from 'vue'
import { getNewToken } from '../../composable/getNewToken';
const username = ref('')
const password = ref('')
const alertText = ref('')
const className = ref('')
const warning = ref(false)
const statusCode = ref(0)
const API_ROOT = import.meta.env.VITE_API_ROOT

const getToken = () =>{
  const token = localStorage.getItem("token")
  return "Bearer " + token
}

const match = async (token) => {
  let userInfo = {
    username: username.value.trim(),
    password: password.value.trim()
  };
  try {
    const res = await fetch(API_ROOT + '/api/users/match', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': getToken()
      },
      body: JSON.stringify(userInfo)
    });

    if (res.status === 200) {
      statusCode.value = 200;
      alertText.value = 'Password Matched';
      className.value = 'alert-success';
    } else if (res.status === 404) {
      statusCode.value = 404;
      alertText.value = 'The specified username DOES NOT exist';
      className.value = 'alert-error';
    } else if (res.status === 401) {
      statusCode.value = 401;
      alertText.value = 'Password NOT Matched';
      className.value = 'alert-error';

      // Token is invalid, attempt to refresh it
      await getNewToken();

      // Retry the `match` function with the new token
      await match(getToken());
    }
    warning.value = true;
  } catch (error) {
    return error
  }
};


</script>
 
<template>
<div class="p-4 sm:ml-64">
<div class="w-full h-full">
    <section class="bg-gray-50 dark:bg-gray-900">
  <div class="flex flex-col items-center justify-center h-full mt-16 w-full">

   <!-- Warning Text -->
   <div :class="[ warning ? className : '', 'alert rounded-lg shadow dark:border md:mt-2 sm:max-w-md m-2']" v-if="warning">
                <span class="ann-message font-semibold">{{alertText}}</span>
            </div>

    <div class="w-full bg-white rounded-lg shadow dark:border md:mt-2 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
              <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                  Match Password
              </h1>

                  <div>
                      <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                      <input v-model="username" type="text" class="ann-username bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="siam" required>
                  </div>
                  <div>
                      <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                      <input v-model="password" type="password" placeholder="••••••••" class="ann-password bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                  </div>
                  <div class="flex items-center justify-between">
                      <button class="ann-button border borcder-black p-2 rounded bg-gray-200 hover:bg-gray-300" @click="match">Match or Not</button>
                  </div>
                 
          </div>

      </div>
  </div>
</section>
</div>
</div>
</template>
 
<style scoped>

</style>