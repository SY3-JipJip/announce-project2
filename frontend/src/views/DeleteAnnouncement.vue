<script setup>
import {ref, onMounted } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
const router = useRouter()
const { params } = useRoute()
const API_ROOT= import.meta.env.VITE_API_ROOT
const announcementDetail = ref([])

onMounted(async()=>{
  deleteAnnouncement()
})

const getToken = () =>{
  const token = localStorage.getItem("token")
  return "Bearer " + token
}

const deleteAnnouncement = async () => {
    const confirmed = window.confirm('Do you want to delete');
    if (confirmed) {
        try {
            const res = await fetch(`${API_ROOT}/api/announcements/${params.id}`, {
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': getToken()
                },
                method: 'DELETE'
            })

            if (res.ok) {
                announcementDetail.value = announcementDetail.value.filter((andata) => andata.id !== params.id);
                console.log('Delete successful');
                router.push({
                    name: 'home'
                });
            } else {
                alert(`There are no announcement with id = ${params.id}`);
                router.push({
                    name: 'home'
                });
                throw new Error('Cannot delete data!');
            }
        } catch (error) {
            console.log(`ERROR: ${error}`);
            router.push({
                name: 'home'
            });
        }
    } else {
        router.push({
            name: 'home'
        });
    }
}

</script>
<template>
<div>

</div>
</template>
 
<style scoped>

</style>
 