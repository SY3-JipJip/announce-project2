<script setup>
import {ref, onMounted } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
const router = useRouter()
const { params } = useRoute()
const API_ROOT= import.meta.env.VITE_API_ROOT
const userDetail = ref([])
const token = ref('')
import { inject } from 'vue'
const $cookies = inject('$cookies')
onMounted(async()=>{
 token.value = "Bearer " + $cookies.get("token")
 deleteUser(token.value)
})


const deleteUser = async (token) =>{
  const confirmed = window.confirm('Do you want to delete');
  if (confirmed) {
    try {
      const res = await fetch(`${API_ROOT}/api/users/${params.id}`,{
      headers:{
        'Authorization': token.value
      },
        method:'DELETE'
      })
      if(res.ok){
        userDetail.value = userDetail.value.filter((data)=>data.id !== params.id)
        console.log('delete successfully')
        router.push({
          name : 'AdminUserView'
        })
      } else {
        alert(`There are no user id = ${params.id}`);
        router.push({
          name : 'AdminUserView'
        })
        throw new Error('cannot delete data!')
      }
    } catch(error) {
      console.log(`ERROR: ${error}`)
      router.push({
        name : 'AdminUserView'
      })
    }
  }else{
    router.push({
        name : 'AdminUserView'
      })
  }
}
</script>
<template>
<div>

</div>
</template>
 
<style scoped>

</style>
 