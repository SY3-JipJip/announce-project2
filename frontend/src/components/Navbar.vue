<script setup>
import { useRoute } from 'vue-router';
const route = useRoute()




</script>
<template>
  <div v-if="!route.fullPath.includes(`/login`)" class="sm:ml-64 flex items-center justify-center bg-blue-900 text-white">
    <div class="flex">
      <router-link :to="{ name: 'home'}">
        <h1 class="text-center p-4 text-2xl font-bold" style="text-shadow: 2px 2px 4px #000000">SIT Announcement System (SAS)</h1>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
</style>
