<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'
import { formatDate } from '../../composable/formatDate'

const announcementData = ref([])
const router = useRouter()

const API_ROOT = import.meta.env.VITE_API_ROOT

onMounted(async()=>{
    announcementData.value = await loadData()
})


const showDescription = (id) => {
  router.push({name:'AdminAnnDetail',params:{id}})
}

const loadData = async () => {


  try {
    const res = await fetch(API_ROOT + "/api/announcements", {
      headers: {
        'Authorization': "Bearer " + localStorage.getItem('token')
      }
    });

    if (res.ok) {
      const data = await res.json();
      return data;

    }else {
      if(res.status===401){
        alert('Please login!')
        router.push({ name: 'login' })
      }
      throw new Error('Could not load data');
    }

  } catch (error) {
    console.error('error ', error);
  }
}


const deleteAnnouncement = async (announcementId) => {
    const confirmed = window.confirm('Do you want to delete');
    if (confirmed) {
        try {
            const res = await fetch(`${API_ROOT}/api/announcements/${announcementId}`, {
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': getToken()
                },
                method: 'DELETE'
            })

            if (res.ok) {
              announcementData.value = announcementData.value.filter((andata) => andata.id !== announcementId); 
            } else {
                alert(`There are no announcement with id = ${announcementId}`);
                throw new Error('Cannot delete data!');
            }
        } catch (error) {
            console.log(`ERROR: ${error}`);
        }
    } else {
        router.push({
            name: 'home'
        });
    }
}


</script>
<template>
   <div class="sm:ml-64">
    <div class="m-2 flex flex-row">
      
          <!-- DateTime of Local User -->
          <div class="flex w-full text-lg font-semibold p-2 items-center">
              <h3>Date/Time shown in Timezone : <span class="text-green-700">{{ Intl.DateTimeFormat().resolvedOptions().timeZone }}</span></h3>
          </div>

          <!-- Add Announcement-->
          <div class=" w-full p-2 justify-end flex  ">
              <router-link to="/admin/announcement/add">
                <button class="ann-button btn btn-accent rounded-3xl text-white mr-5">Add Announcement</button>
              </router-link>
          </div>
    </div>


      <!-- All Announcement -->
    <div class="overflow-x-auto">
      <table class="table w-full">
        <thead>
          <tr>
            <th>No</th>
            <th>Title</th>
            <th>Category</th>
            <th>Publish Date</th>
            <th>Close Date</th>
            <th>Display</th>
            <th class="text-center">Action</th>
          </tr>
        </thead>
        <tbody v-if="announcementData !== null || announcementData.length !== 0">
          <tr class="ann-item" v-for="announcement,index in announcementData">
            <th>{{ ++index }}</th>
            <td class="ann-title">{{ announcement.announcementTitle }}</td>
            <td class="ann-category">{{ announcement.announcementCategory }}</td>
            <td class="ann-publish-date" :class="announcement.publishDate === null ? 'text-center' : ''">{{ announcement.publishDate === null ? '-' : formatDate(announcement.publishDate) }}</td>
            <td class="ann-close-date" :class="announcement.closeDate === null ? 'text-center' : ''">{{ announcement.closeDate === null ? '-' : formatDate(announcement.closeDate) }}</td>
            <td class="ann-display">{{ announcement.announcementDisplay }}</td>
            <td class="flex justify-center space-x-2">
              <button @click="showDescription(announcement.id)"  class="ann-button border border-gray-600 p-1 pl-4 pr-4 border-y-6 bg-gray-500 rounded-md btn-sm btn">view</button>
              <button @click="deleteAnnouncement(announcement.id)"  class="ann-button border border-red-600 p-1 pl-3 pr-3 border-y-6 bg-red-600 rounded-md btn-sm btn">delete</button>
            </td>
          </tr>
        </tbody>
      </table>
      
      <h3 v-if="announcementData.length === 0" class="mt-3 flex justify-center items-center text-red-600 font-bold text-xl" >No Announcement</h3>
      
      <div class="flex flex-row">
        <div class="w-full flex justify-start mt-10 ">  
              <img class="w-52" src="../../assets/images/mafu.png"/>
        </div>

        <div class="w-full h-auto mt-20 flex justify-end mb-0">
            <img class="flex ml-72" src="../../assets/images/pow2.gif"/>
        </div>

      </div>
  </div>
</div>
</template>

<style>

</style>
