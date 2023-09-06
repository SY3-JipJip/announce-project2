<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
import { convertDate, convertTime } from '../../composable/formatDate.js'

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
    categories.value = await getCategories()
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

//Get Announcement By ID
const loadDetail = async () =>{
    return await fetch(`${API_ROOT}/api/announcements/AnnCatId/${params.id}`)
    .then(res => {
        if(!res.ok){
            alert('The request page is not available')
            router.push({
                name : 'home'
            })
            throw new Error(res.status)
        }else{
            return res.json()
        }
    })
    .then(data => {
        announcementDetail.value = data
    })
    .catch((err) => err)
}



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
<div class="sm:ml-64 p-5">

    <div class="border border-2">
    <div class="w-full flex flex-row items-center">
        <img src="../../assets/images/user-avatar.png" class="h-7 ml-7">
        <h1 class="text-3xl font-bold p-5">User Detail:</h1>
    </div>

    <div class="w-full flex flex-col p-5">
        <div class="detail">
            <p>Username</p>
            <input type="text" placeholder="Type here" class="input input-bordered w-full max-w-xs" />
        </div>
        <div class="detail">
            <p>Name</p>
            <input type="text" placeholder="Type here" class="input input-bordered w-full max-w-xs" />
        </div>
        <div class="detail">
            <p>Email</p>
            <input type="text" placeholder="Type here" class="input input-bordered w-full max-w-xs" />
        </div>
        <div class="form-control w-full max-w-xs detail">
           <p>Role</p>
            <select class="select select-bordered">
                <option disabled selected>announcer</option>
                <option>Star Wars</option>
                <option>Harry Potter</option>
                <option>Lord of the Rings</option>
                <option>Planet of the Apes</option>
                <option>Star Trek</option>
            </select>
            <label class="label">
                <span class="label-text-alt">Alt label</span>
                <span class="label-text-alt">Alt label</span>
            </label>
        </div>
    </div>
    </div>
</div>
</template>
 
<style scoped>
.detail{
    padding-bottom:30px;
}
</style>