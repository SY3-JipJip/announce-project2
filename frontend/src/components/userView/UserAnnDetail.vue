<script setup>
import {ref, onMounted } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
import { formatDate } from '../../composable/formatDate'
import { getNewToken } from '../../composable/getNewToken';

const router = useRouter()
const { params } = useRoute()
const API_ROOT = import.meta.env.VITE_API_ROOT
const announcementDetail = ref([])

onMounted(async()=>{
        await loadDetail()
})

const loadDetail = async () => {

  try {
    const res = await fetch(`${API_ROOT}/api/announcements/${params.id}`, {
      headers: {
        "Content-Type": "application/json",
        'Authorization': "Bearer " + localStorage.getItem('token')
      }
    });
        if(res.ok){
        announcementDetail.value = await res.json();

        }else if(res.status==403){
            alert("access deny");
            router.push('/announcement')

        }else {
            try {
                await getNewToken();
                const newRes = await fetch(API_ROOT + "/api/users", {
                    headers: {
                    'Authorization': "Bearer " + localStorage.getItem('token')
                    }
                });

                if (newRes.ok) {
                    announcementDetail.value = await res.json();
                } 
                
            } catch (error) {
                // console.error('Failed to get new token:', error);
            }
    }

    } catch (error) {
        console.log('error ',error)
    }
};



</script>

<template>
    <div class="p-4 sm:ml-64">
    <div class="w-full h-full flex">
        
        <div class="w-full flex flex-col p-5 m-5 pb-40 rounded-lg border-2 border-gray-300 shadow-xl ">
            <div class="flex flex-row">
                <div class="w-full flex flex-col">
                    <h1 class="ann-title font-bold text-2xl" >{{ announcementDetail.announcementTitle}}</h1>
                    <h1 class="ann-category text-gray-500 text-md"  >{{ announcementDetail.announcementCategory}}</h1>  
                </div>
                <div class="w-full flex justify-end">
                    <h1 class="ann-close-date text-red-600 text-md mr-5 font-bold">Closed on : <span class="text-gray-400 font-bold">{{ announcementDetail.closeDate === null || announcementDetail.closeDate === undefined ? '-' : formatDate(announcementDetail.closeDate) }}</span></h1> 
                </div>  
            </div> 
                    
                <div class="mt-5">
                    <h1 class="ann-description text-lg" >{{ announcementDetail.announcementDescription}}</h1>   
                </div>  
                    
        </div>          
    
    </div>
    <div class="flex flex-row">
        <router-link to="/announcement"><button class=" ml-5 font-bold pr-10 pl-10 flex ann-button btn bg-gray-400 border border-gray-300 text-gray-100">Back</button></router-link>
    </div>  
</div>
</template>

<style scoped>


</style>
