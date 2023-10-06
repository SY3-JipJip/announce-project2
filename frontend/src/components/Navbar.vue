<script setup>
import { useRouter,useRoute } from 'vue-router';

const router = useRouter()
const route = useRoute()


const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
  router.push("/login");
}

</script>
<template>
  <div v-if="!route.fullPath.includes(`/login`)" class="sm:ml-64 flex items-center justify-center bg-blue-900 text-white">
    <div class="flex">
      <router-link :to="{ name: 'home'}">
        <h1 class="text-center p-4 text-2xl font-bold" style="text-shadow: 2px 2px 4px #000000">SIT Announcement System (SAS)</h1>
      </router-link>
    </div>
    <ul class="flex font-medium ml-auto">
      <li @click="logout" class="ann-menu flex items-center p-2 rounded-lg dark:text-white hover:text-gray-300 dark:hover:bg-gray-700 group cursor-pointer">
        Logout
      </li>
    </ul>
  </div>
</template>




<style scoped>
</style>
