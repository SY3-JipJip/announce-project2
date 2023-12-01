<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
import { convertDate, convertTime } from '../../composable/formatDate.js'
import { getNewToken } from '../../composable/getNewToken';

const API_ROOT = import.meta.env.VITE_API_ROOT
const { params } = useRoute()
const router = useRouter()

let old = ref('')
let pDate = ref('')
let pTime = ref('')
let cDate = ref('')
let cTime = ref('')
let display = ref('')
let displayStr = ref('')

const categories = ref({})
const displays = {
    yes : "Y",
    no  : "N"
}



//ข้อมูลของ Announcement ทั้งหมด ที่ไป fetch มาจาก back
let announcementDetail = ref({})
onMounted(async ()=>{
    await loadDetail()
    await getCategories()
   
    //เราต้องแปลงเวลา ที่เป็น format UTC ให้กลายเป็น format time กับ date แยกกัน
    if(announcementDetail.value.publishDate !== null){
        pDate.value = convertDate(announcementDetail.value.publishDate)
        pTime.value = convertTime(announcementDetail.value.publishDate)
    }
    if(announcementDetail.value.closeDate !== null){
        cDate.value = convertDate(announcementDetail.value.closeDate)
        cTime.value = convertTime(announcementDetail.value.closeDate)
    }
    display.value = announcementDetail.value.announcementDisplay === "Y"

    old.value = {
        "announcementTitle": announcementDetail.value.announcementTitle,
        "announcementDescription": announcementDetail.value.announcementDescription,
        "publishDate": pDate.value,
        "publishTime" : pTime.value,
        "closeDate": cDate.value,
        "closeTime": cTime.value,
        "announcementDisplay": display.value,
        "announcementCategory" : announcementDetail.value.announcementCategory,
        "categoryId": announcementDetail.value.categoryId
    }

})

const getCategories = async () => {
    try {
        const res = await fetch(API_ROOT+"/api/categories", {
      headers: {
        'Authorization': "Bearer " + localStorage.getItem('token')
      }
    });
        // if(res.status===201)        
        if (res.ok) {
            categories.value = await res.json()   
        } 
            else throw new error('Error, cannot get data!')
    } catch (error) {
        return error
    }
}

const loadDetail = async () => {
  try {
    const res = await fetch(`${API_ROOT}/api/announcements/AnnCatId/${params.id}`, {
      headers: {
        'Authorization': "Bearer " + localStorage.getItem('token')
      }
    });

    if (!res.ok) {
      if (res.status === 401) {
        try {
            await getNewToken();
            const newRes = await fetch(`${API_ROOT}/api/announcements/AnnCatId/${params.id}`, {
                headers: {
                'Authorization': "Bearer " + localStorage.getItem('token')
                }
            });

            if (newRes.ok) {
                announcementDetail.value = await newRes.json();
            } 

        } catch (error) {
        //   console.error('Failed to get new token:', error);
        }

      } 

    } else {
      announcementDetail.value = await res.json();
    }
  } catch (error) {
    console.error('error ', error);
  }
};




//ปุ่มเคลียร์ข้อมูลเก่า เผื่อ user ขี้เกียจลบ
const clearOldData =()=> {
    announcementDetail.value = {
        "announcementTitle": undefined,
        "announcementDescription": undefined,
        "publishDate": pDate.value,
        "publishTime" : pTime.value,
        "closeDate": cDate.value,
        "closeTime": cTime.value,
        "announcementDisplay": display.value,
        "announcementCategory" : announcementDetail.value.announcementCategory,
        "categoryId": announcementDetail.value.categoryId
    }
}

//ถ้ากดปุ่ม Cancle ก็จะ Pop up ถามว่า จะยกเลิกจริงไหม
const cancle =()=>{
    confirm('Are you sure you want to cancel? Announcement will be not save.')
    router.push({name : 'home'})
}

//จะ return ค่าเป็น Boolean ถ้า user เลือก category
const isEdit = computed(()=>{
    // console.log(edittingAnnouncement.value)
    return  (edittingAnnouncement.value.announcementTitle===undefined || edittingAnnouncement.value.announcementTitle==="") ||
    (edittingAnnouncement.value.announcementDescription===undefined || edittingAnnouncement.value.announcementDescription==="") 
    || JSON.stringify(edittingAnnouncement.value) === JSON.stringify(old.value) 
})

//ค่าของ edittingAnnouncement ค่าเริ่มต้นจะเป็นของข้อมูลเดิม และเมื่อ user แก้ไข ตัวแปรก็จะเปลี่ยนค่าตามที่ user input ค่าเข้ามา
const edittingAnnouncement = computed(()=>{
    return {
        "announcementTitle": announcementDetail.value.announcementTitle,
        "announcementDescription": announcementDetail.value.announcementDescription,
        "publishDate": pDate.value,
        "publishTime" : pTime.value,
        "closeDate": cDate.value,
        "closeTime": cTime.value,
        "announcementDisplay": display.value,
        "announcementCategory" : announcementDetail.value.announcementCategory,
        "categoryId": announcementDetail.value.categoryId
    }
})

//
const submit = async () =>{

    let publishDateTime = null
    let closeDateTime = null

    displayStr.value = edittingAnnouncement.value.announcementDisplay ? displays.yes : displays.no

    if(edittingAnnouncement.value.publishDate !== "" && edittingAnnouncement.value.publishTime !== ""){
        publishDateTime = new Date(`${edittingAnnouncement.value.publishDate}T${edittingAnnouncement.value.publishTime}`).toISOString().split('.')[0]+"Z"
    }else if(edittingAnnouncement.value.publishDate !== "" && edittingAnnouncement.value.publishTime === ""){
        publishDateTime = new Date(`${edittingAnnouncement.value.publishDate}T06:00`).toISOString().split('.')[0]+"Z"
    }
    if(edittingAnnouncement.value.closeDate !== "" && edittingAnnouncement.value.closeTime !== ""){
        closeDateTime = new Date(`${edittingAnnouncement.value.closeDate}T${edittingAnnouncement.value.closeTime}`).toISOString().split('.')[0]+"Z"
    }else if(edittingAnnouncement.value.closeDate !== "" && edittingAnnouncement.value.closeTime === ""){
        closeDateTime = new Date(`${edittingAnnouncement.value.closeDate}T18:00`).toISOString().split('.')[0]+"Z"
    }
    
    // console.log(JSON.stringify({
    //     "announcementTitle": edittingAnnouncement.value.announcementTitle,
    //     "announcementDescription": edittingAnnouncement.value.announcementDescription,
    //     "publishDate": publishDateTime,
    //     "closeDate": closeDateTime,
    //     "announcementDisplay": displayStr.value,
    //     "announcementCategory" : edittingAnnouncement.value.announcementCategory,
    //     "categoryId": edittingAnnouncement.value.categoryId
    //     }))

    await fetch(API_ROOT+'/api/announcements/'+params.id,{
        method : "PUT",
        headers: {
        "Content-Type": "application/json",
        'Authorization' : "Bearer " + localStorage.getItem('token')
        },
        body: JSON.stringify({
        "announcementTitle": edittingAnnouncement.value.announcementTitle,
        "announcementDescription": edittingAnnouncement.value.announcementDescription,
        "publishDate": publishDateTime,
        "closeDate": closeDateTime,
        "announcementDisplay": displayStr.value,
        "announcementCategory" : edittingAnnouncement.value.announcementCategory,
        "categoryId": edittingAnnouncement.value.categoryId
        })
    }
    )
    .then(console.log("Update successfully"))
    .then(() => {
        alert('Update successfully');
        router.push({ name: 'home' });
        })
    .catch((err)=>err)
    
}

</script>
 
<template>
<div class="p-4 sm:ml-64">

<div class="w-full h-full flex flex-col" >

    <h1 class="ml-10 font-bold text-blue-900 text-2xl mb-6 mt-3"> Announcement Detail :</h1>

    <div class="flex m-5 flex-col mb-5 shadow-4xl border-blue-900 p-5 rounded-lg ">
    <div class="flex flex-row"> 
        <img class="w-72" src="../../assets/images/omen.gif" alt="GIF">
        
        <h1 class="text-2xl text-center mt-20 mb-5 text-gray-800" style="text-shadow: 1px 2px 2px rgba(0, 0, 0, 0.3);">You can clear all your history data. <span class="text-blue-500" style="font-weight: bold;">Just click clear button!</span></h1>
        
        <div class="w-full flex justify-end mr-5 p-4">
            <div ontouchstart="">
                <div class="button" @click="clearOldData">
                    <a href="#">Clear</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Title -->
    <label class="block mb-5">
            <span class="block text-lg font-medium text-slate-700 mb-2 ">Title<span class="text-red-700">*<span class="text-sm">กรอกได้ไม่เกิน 200 ตัวอักษร</span></span></span>
            <input rows="5" type="text" placeholder="Type your title here..." class="ann-title input input-bordered textarea-md  w-3/5 max-w-xs border-4 border-blue-900 shadow-lg"  maxlength="200" v-model="announcementDetail.announcementTitle" />
        </label>
    <!-- Category -->
    <label class="block  mt-3 mb-5">
            <span class="block text-lg font-medium text-slate-700 ">Category <span class="text-red-700">*</span></span>
            <select name="category" class="ann-category rounded-md p-1 border-4 border-blue-900" v-model="announcementDetail.categoryId">
                <option v-for="category in categories" :value="category.categoryId">{{ category.announcementCategory }}</option>
            </select>
        </label>

        <!-- Description -->
        <label class="block mt-3 mb-5 ">
            <span class="block text-lg font-medium text-slate-700  mb-2">Description<span class="text-red-700">*<span class="text-sm">กรอกได้ไม่เกิน 10000 ตัวอักษร</span></span></span>
            <textarea placeholder="Type your description here..." class="ann-description textarea textarea-bordered textarea-md w-full max-w-xs border-4 border-blue-900"  maxlength="10000" v-model="announcementDetail.announcementDescription"></textarea>
        </label>
            
        

        
        <!-- Publish Date -->
        <label class="block mb-5">
            <span class="block text-lg font-medium text-slate-700  mb-2 ">Publish Date</span>
            <input type="date" class="ann-publish-date pl-3 pr-5 h-10 rounded-md mr-5 mb-3 border-4 border-blue-900" v-model="pDate">>
            <input type="time" class="ann-publish-time rounded-md pl-5 pr-5 h-10 ml-5 border-4 border-blue-900" v-model="pTime">
        </label>  

       <!-- Close Date -->
       <label class="block mb-5">
            <span class="block text-lg font-medium text-slate-700  mb-2 ">Close Date</span>
            <input type="date" class="ann-close-date pl-3 pr-5 h-10 rounded-md mr-5 mb-3 border-4 border-blue-900" v-model="cDate">>
            <input type="time" class="ann-close-time rounded-md pl-5 pr-5 h-10 ml-5 border-4 border-blue-900" v-model="cTime">
        </label>  

       <!-- Display -->
        <label class="block mb-5">
            <span class="block text-lg font-medium text-slate-700 ">Display</span>
            <input type="checkbox" class="ann-display checkbox-xs" @click="display = !display" v-model="display"/>
            <span class=" text-sm  ml-3 ">Check to show this announcement</span>
        </label>    
        
       <!-- Button -->
    </div >
    <div class="flex justify-center">
        <button @click="submit()" class="ann-button ann-submit ml-10 btn bg-green-600 pl-5 pr-5" :disabled="isEdit">submit</button>
        <button @click="cancle()" class="ann-button ml-5 mb-6 btn buttonCancle" >Cancle</button>
    </div>
    </div>
</div>

</template>

<style scoped>


.button{
  position:relative;
  display:inline-block;
  margin:20px;
}

.button a{
  color:white;
  font-family:Helvetica, sans-serif;
  font-weight:bold;
  font-size:36px;
  text-align: center;
  text-decoration:none;
  background-color:#008efa;
  display:block;
  position:relative;
  padding:20px 40px;
  
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  text-shadow: 0px 1px 0px #000;
  filter: dropshadow(color=#000, offx=0px, offy=1px);
  
  -webkit-box-shadow:inset 0 1px 0 #00d9ff, 0 10px 0 #00f2ff;
  -moz-box-shadow:inset 0 1px 0 #00ccff, 0 10px 0 #00d5ff;
  box-shadow:inset 0 1px 0 #00aeff, 0 10px 0 #008391;
  
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}

.button a:active{
  top:10px;
  background-color:#01f7ff;
  
  -webkit-box-shadow:inset 0 1px 0 #c4edff, inset 0 -3px 0 #0056b8;
  -moz-box-shadow:inset 0 1px 0 #c4edff, inset 0 -3pxpx 0 #0048c4;
  box-shadow:inset 0 1px 0 #c4f3ff, inset 0 -3px 0 #0d4bd1;
}

.button:after{
  content:"";
  height:100%;
  width:100%;
  padding:4px;
  position: absolute;
  bottom:-15px;
  left:-4px;
  z-index:-1;
  background-color:#00e0fe;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}
</style>