<script setup>
import {ref, onMounted } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
import { formatDate } from '../../composable/formatDate'
const router = useRouter()
const { params } = useRoute()
const API_ROOT = import.meta.env.VITE_API_ROOT
const userDetail = ref([])
const errorMSG = ref('')
onMounted(()=>{
    loadDetail()
})

const loadDetail = async () =>{
    try {
        const res = await fetch(`${API_ROOT}/api/user/${params.id}`)
        if(!res.ok){
            alert('The request page is not available')
            router.push({
                name : 'home'
            })
            throw new Error(res.status)
        }else{
            userDetail.value = await res.json()
        }
    }catch(err){
        errorMSG.value = err
    }
}

const editUser = (userId) =>{
    router.push({
        name : 'AdminEditUser',
        params : {id : userId}
    })
}

</script>

<template>
     <div class="sm:ml-64">
       
        <div class="flex w-full">

            <div class="flex w-2/6 justify-end items-center">
                <img src="../../assets/images/detail.png" class="h-16">
            </div>
            
        

            <div class="w-2/6 flex justify-center items-center">
                <h1 class="ml-10 font-bold text-4xl mb-1.5" style="line-height: 2.5;">Announcement Detail</h1>
            </div>

                 <!-- Button -->
            <div class="w-2/6 flex justify-end items-end p-2 pr-10">
                <button @click="editUser(announcementDetail.id)" class="ann-button edit-btn"><img src="../../assets/images/edit.png" class="h-10"></button>
                
            </div>

        </div>

       

        <!-- Information -->
        <div class=" ml-10 mr-10 mb-10 border-black border-2 p-10 rounded-lg" style=" background-color: #ffffff;line-height:50px " >
            <table class="ann-item">
                <tr >
                    <td><span class="font-bold ">Title  </span></td>
                    <td class="ann-title pl-10" >{{ announcementDetail.announcementTitle}}</td>
                </tr>
                <tr >
                    <td><span class="font-bold ">Category </span></td>
                    <td class="ann-category pl-10" >{{ announcementDetail.announcementCategory }}</td>
                </tr>
                <tr>
                    <td><span class="font-bold ">Description </span></td>
                    <td class="ann-description pl-10" >{{ announcementDetail.announcementDescription }}</td>
                </tr>
                <tr >
                    <td><span class="font-bold ">Publish Date </span></td>
                    <td class="ann-publish-date pl-10" >{{ announcementDetail.publishDate === null || announcementDetail.publishDate === undefined ? '-' :  formatDate(announcementDetail.publishDate)}}</td>
                </tr>
                <tr >
                    <td><span class="font-bold ">Close Date </span></td>
                    <td class="ann-close-date pl-10" >{{ announcementDetail.closeDate === null || announcementDetail.closeDate === undefined ? '-' : formatDate(announcementDetail.closeDate) }}</td>
                </tr>
                <tr >
                    <td><span class=" font-bold ">Display </span></td>
                    <td class="ann-display pl-10" >{{ announcementDetail.announcementDisplay}}</td>
                </tr>
                
            </table>
           
        </div >
        
        <router-link to="/admin/announcement"><button class="ann-button ml-10 btn bg-gray-500 border border-gray-500 mb-5">Back</button></router-link>
</div>
</template>
<style>


.edit-btn {
  background-color: #ffffff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
}
</style>
