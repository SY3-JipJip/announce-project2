<script setup>  
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'
import { formatDate } from '../../composable/formatDate'
import { userAnnouncement } from '../../Store/userMode'


const getToken = () =>{
  const token = localStorage.getItem("token")
  return "Bearer " + token
}

const API_ROOT = import.meta.env.VITE_API_ROOT
const router = useRouter()

//pinia
const userAnn = userAnnouncement()
const isActive =ref()

//data from backend
const announcementData = ref({})


onMounted(async ()=>{
  //get ค่า active มาว่าเป็น จริงหรือเท็จ
  isActive.value = userAnn.getMode()
  // console.log(isActive.value)

  //ถ้าเป็นจริงก็จะดึงข้อมูลที่อยู่สถานะ active
  if(isActive.value){
    await getActiveAnnouncements()
  //ถ้าเป็นเท็จก็จะดึงข้อมูลที่อยู่สถานะ close
  }else{
    await getClosedAnnouncements()
  }
})

const changeMode = async () =>{
  isActive.value = userAnn.setMode()
  if(isActive.value){
    await getActiveAnnouncements()
  }else{
    await getClosedAnnouncements()
  }
  console.log(isActive.value)
}




const getActiveAnnouncements = async () => {
    try {
        const res = await fetch(API_ROOT + '/api/announcements?mode=active', {
            headers: {
                'Authorization': getToken()
            }
        });

        if (res.ok) {
            announcementData.value = await res.json();
        } else if (res.status === 401) {
            // 401 Unauthorized: เรียกใช้งานการเปลี่ยนเส้นทางไปยังหน้า login
            router.push({ name: 'login' });
        } else {
            throw new Error('Could not load data');
        }
    } catch (error) {
        alert(error);
    }
}

const getClosedAnnouncements = async () => {
    try {
        const res = await fetch(API_ROOT + '/api/announcements?mode=close', {
            headers: {
                'Authorization': getToken()
            }
        });

        if (res.ok) {
            announcementData.value = await res.json();
        } else if (res.status === 401) {
            // 401 Unauthorized: เรียกใช้งานการเปลี่ยนเส้นทางไปยังหน้า login
            router.push({ name: 'login' });
        } else {
            throw new Error('Could not load data');
        }
    } catch (error) {
        alert(error);
    }
}



const showDetail = (announcementId) =>{
  router.push({
    name : 'UserAnnDetail',
    params : {id : announcementId}
  })
}

</script>


<template>
  
<!--------------------------------- TimeZone ---------------------------------->
  <div class=" p-4 sm:ml-64 p-2">
    <div class="flex flex-row">
            <div class="w-full mr-5 text-xl font-semibold p-7">
                <h3>Date/Time shown in Timezone : <span class="text-green-700">{{ Intl.DateTimeFormat().resolvedOptions().timeZone }}</span></h3>
            </div>
<!---------------------------- Button Change Mode ---------------------------->
    <div class=" w-full  mt-3 justify-end flex  ">
      <div class="w-full flex justify-end"> 
                <img class="w-40" src="../../assets/images/neko.gif" alt="GIF"/>
        </div>
        <div class="w-3/5 space-x-2  justify-end flex" >
            <button id="text-but" @click="changeMode" class="ann-button  mr-5 p-2 buttonclose btn">
                  <h1  class="pl-2 ">{{ isActive ? 'Closed Announcement' : 'Active Announcement'  }}</h1></button>
        </div>
    </div>
</div>

    <!-- table-->     
    <div class="overflow-x-auto">
      <table class="table table-zebra w-full ">
        <thead>
          <tr class="text-left">
            <th>No</th>
            <th>Title</th>
            <th v-if="!isActive">Close Date</th>
            <th>Category</th>
          </tr>
        </thead>
        <tbody v-if="announcementData !== null || announcementData.length !== 0">
          <tr class="ann-item text-left" v-for="announcement,index in announcementData">
            <th>{{ ++index }}</th>
            <td @click="showDetail(announcement.id)" class="ann-title ">{{ announcement.announcementTitle }}</td>
            <td class="ann-close-date" v-if="!isActive">{{ announcement.closeDate === null ? '-' : formatDate(announcement.closeDate) }}</td>
            <td class="ann-category">{{ announcement.announcementCategory }}</td>
          </tr>
        </tbody>
      </table>
      <h3 v-if="announcementData.length === 0 " class="mt-3 text-center text-red-600 font-bold">No Announcement</h3>
      
      <div class="w-full flex justify-start mt-10"> 
              <img class="w-52" src="../../assets/images/mafu.png"/>
      </div>
    </div>
    </div>
</template>

<style>

</style>
