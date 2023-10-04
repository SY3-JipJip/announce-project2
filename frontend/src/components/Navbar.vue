<script setup>
</script>
<template>
  <div class="w-full flex flex-col md:flex-row items-center justify-between bg-blue-900 text-white">
    
   
        <router-link :to="{ name: 'home'}">
          <h1 class="text-center p-4 text-2xl font-bold" style="text-shadow: 2px 2px 4px #000000">SIT Announcement System (SAS)</h1>
        </router-link>
    <ul class="flex space-x-4 font-medium">
      <li>
        <router-link :to="{ name: 'home'}" class="ann-menu flex items-center p-2 rounded-lg dark:text-white hover:text-gray-300 dark:hover:bg-gray-700 group">
          Announcement
        </router-link>
      </li>

      <li>
        <router-link :to="{ name: 'AdminUserView'}" class="ann-menu flex items-center p-2 rounded-lg dark:text-white hover:text-gray-300 dark:hover:bg-gray-700 group">
          User
        </router-link>
      </li>

      <li>
        <router-link :to="{ name: 'MatchPassword'}" class="ann-menu flex items-center p-2 rounded-lg dark:text-white hover:text-gray-300 dark:hover:bg-gray-700 group">
          Match Password
        </router-link>
      </li>
    </ul>
  </div>
</template>


<style scoped>
</style>
