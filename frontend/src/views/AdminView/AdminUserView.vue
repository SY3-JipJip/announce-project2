<script setup>
import {ref,onMounted} from 'vue'
const userData = ref([])
const API_ROOT = import.meta.env.VITE_API_ROOT

onMounted(async()=>{
  userData.value = await loadData()
})

const loadData = async() => {
  try{
    const res = await fetch(API_ROOT+"/api/announcements")
    if(res.ok){
      const data = await res.json()
      return data
    }else{
      throw new Error('could not load data')
    }
    
  } catch (error) {
    console.log(`ERROR: ${error}`)
  }
}

const showUserDetail = (userId) =>{
  router.push({
    name : 'AddminUserDetail',
    params : {id : userId}
  })
}

const deleteUser = async (userId) =>{
  router.push({
    name : 'deleteAnnounce',
    params : {id : userId}
  })
}
</script>
 
<template>
<div class="p-4 sm:ml-64 flex flex-col">

    <div class="w-full flex justify-center text-3xl mb-5 mt-3">
        <img class="h-10 mr-2" src="../../assets/images/management.png"/>
        <h1 class="ann-title font-bold text-gray-700">User Management</h1>
        
    </div>


   <!-- DateTime & Button -->
    <div class="w-full flex flex-row">

        <!-- time zone -->
        <div class="w-full text-lg font-semibold">
            <h3>Date/Time shown in Timezone : <span class="text-green-700">{{ Intl.DateTimeFormat().resolvedOptions().timeZone }}</span></h3>
        </div>
        
        <!-- button Add User -->
        <div class="w-full flex justify-end">
          <router-link to="/admin/user/add">
            <button class="btn btn-neutral">
                <img class="h-8 mr-1" src="../../assets/images/add-user.png" alt="GIF"/>
                Add User
            </button>
          </router-link>
        </div>
    </div>


    <!-- User List Table -->
  <table class="table mt-5">
    <!-- head -->
    <thead>
      <tr>
        <th>No.</th>
        <th>Username</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Created On</th>
        <th>Updated On</th>
        <th class="flex justify-center">Action</th>
      </tr>
    </thead>
    <tbody v-if="userData !== null || userData.length !== 0">
          <tr class="ann-item" v-for="user,index in userData">
            <th>{{ ++index }}</th>
            <td class="ann-username">{{ user.username }}</td>
            <td class="ann-name">{{ user.name }}</td>
            <td class="ann-email">{{ user.email }}</td>
            <td class="ann-role">{{ user.role }}</td>
            <td class="ann-created-on">{{ user.created }}</td>
            <td class="ann-updated-on">{{ user.updated }}</td>
            <td class="flex justify-center space-x-2">
              <button @click="showUserDetail(user.id)"  class="ann-button border border-gray-600 p-1 pl-4 pr-4 border-y-6 bg-gray-500 rounded-md btn-sm btn">view</button>
              <button @click="deleteUser(user.id)"  class="ann-button border border-red-600 p-1 pl-3 pr-3 border-y-6 bg-red-600 rounded-md btn-sm btn">delete</button>
            </td>
          </tr>
        </tbody>
  </table>


</div>


</template>
 
<style scoped>

</style>