<script setup>
import { ref, onMounted,computed } from 'vue';
import { useRouter } from 'vue-router'

const API_ROOT = import.meta.env.VITE_API_ROOT
const router = useRouter()

//ALL CATEGORIES
const categories = ref({})
onMounted(async()=>{
    await getCategories()
    
})

const getCategories = async () => {
    
  try {
    const res = await fetch(API_ROOT + "/api/categories", {
      headers: {
        "Content-Type": "application/json",
        'Authorization': "Bearer " + localStorage.getItem('token')
      }
    });

    if (!res.ok) {
        if (res.status === 401) {
            alert('Please login!')
            router.push({ name: 'login' })
            
        }else if(res.status === 403){
            alert('Sorry, you do not have permission to access this page.')
            router.push({name:'UserAnnView'})

        }else {
            throw new Error('Error, cannot get data!');
      }

    } else {
        categories.value  = await res.json();
    }
  } catch (error) {
    console.error('error ', error);
  }
}

//ถ้า USER ไม่กรอกข้อมูล title หรือ category หรือ description ก็จะไม่ยอมให้ กดปุ่ม submit 
const submitBtn = computed(()=>{
   return title.value.trim().length === 0 || category.value === undefined  || description.value.trim().length === 0
})


//เก็บค่า display เพื่อเอามาเช็คเงื่อนไข ถ้าเป็นจริง ก็จะตั้งเป็น Y
const displays = {
    yes : "Y",
    no  : "N"
}

//INPUT FROM USER เก็บค่าโดยใช้ v-model
const title = ref('')
const description = ref('')
const category = ref()
const pDate = ref()
const pTime = ref()
const cDate = ref()
const cTime = ref()
const display = ref(false)
const publishDate = ref()
const closeDate = ref()

//ถ้ากดปุ่ม cancel ก็จะมี pop up เด้งขึ้นมาถามว่า จะ cancel จริงไหม ถ้าจริง ก็จะกลับไปหน้า home
const cancel =()=>{
    let isCancel = confirm('Confirm to cancel')
    if(isCancel)router.push({name : 'home'})
}


//ตั้งโครงตัวแปรเอาไว้เก็บข้อมูล
const announcementObj = ref({})
announcementObj.value = {
        "announcementTitle": '',
        "announcementDescription": '',
        "publishDate": null,
        "closeDate": null,
        "announcementDisplay": '',
        "categoryId": ''
    }

//ถ้ากดปุ่ม submit ก็จะมี POP UP ถามก่อนว่า จะ submit จริงไหม ถ้าจริงก็จะทำตามที่เขียนข้างล่าง
const submit = async () => {
    let isConfirm = confirm('Confirm to submit ');

    if (isConfirm) {
        // ตรวจสอบและกำหนดค่า publishDate และ closeDate ตามเงื่อนไข
        pDate.value !== undefined && pTime.value !== undefined ? publishDate.value = new Date(`${pDate.value}T${pTime.value}`).toISOString() : publishDate.value = null;
        cDate.value !== undefined && cTime.value !== undefined ? closeDate.value = new Date(`${cDate.value}T${cTime.value}`).toISOString() : closeDate.value = null;

        // กำหนดค่า display เป็น "Y" หรือ "N" ตามค่าที่ user เลือก
        display.value ? display.value = displays.yes : display.value = displays.no;

        announcementObj.value = {
            "announcementTitle": title.value,
            "announcementDescription": description.value,
            "publishDate": publishDate.value,
            "closeDate": closeDate.value,
            "announcementDisplay": display.value,
            "categoryId": category.value
        }

        try {
            const res = await fetch(API_ROOT + "/api/announcements", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': "Bearer " + localStorage.getItem('token')
                },
                body: JSON.stringify(announcementObj.value)
            })

            if (res.status === 200) {
                const response = await res.json()
                router.push('/admin/announcement')
            } else if (res.status === 401) {
                // 401 Unauthorized: เรียกใช้งานการเปลี่ยนเส้นทางไปยังหน้า login
                router.push({ name: 'login' });
            } else {
                alert('Could not create this announcement, please try again.');
            }
        } catch (error) {
            alert(error);
        }
    }
}

</script>
 
<template>
<div  class="p-4 sm:ml-64 flex flex-col">


    <!-- Add Announcement Title -->

    <div class="flex flex-row items-center justify-center mt-2">
        <div><img src="../../assets/images/add.png" class="h-20"></div>
        <div><h1 class="ml-10 mb-3 font-bold flex w-full text-3xl m-5">Add Announcement</h1></div>
    </div>
       
        
    <!-- Announcements and Img -->
        <div  class="flex flex-row p-10 shadow-2xl rounded-lg ml-10 mr-10 mt-5 border  border-gray-200">
            
        
            
        <!-- ข้อมูลที่ต้องกรอก -->
        <div class="w-1/2 flex flex-col">

            <!-- Title -->
                <span class="block text-sm text-slate-700  mb-2 font-bold" style="font-size: 16px;">Title : <span class="text-red-700">*<span class="text-sm">กรอกได้ไม่เกิน 200 ตัวอักษร</span></span></span>
                <input rows="5" type="text" placeholder="Type your title here..." class="ann-title input input-bordered textarea-md  w-3/5 max-w-xs border-2" style="font-size: 16px;" maxlength="200" v-model="title" required/>
            

            <!-- Category -->
             <label class="block mt-5 ">
                <span class="block mb-2 text-sm font-bold text-slate-700" style="font-size: 16px;">
                  Category :
                  <span class="text-red-700">*</span>
                </span>
                <select name="category" class=" rounded-md p-1 ann-category border-2" v-model="category">
                    <option :value="undefined" disabled>--Please select category--</option>
                    <option v-for="category in categories" :value="category.categoryId">{{ category.announcementCategory }}</option>
                </select>
            </label>

            <!-- Description -->
            <label class="block  mt-5 ">
                <span class="block text-sm font-bold text-slate-700  mb-2" style="font-size: 16px;">Description : <span class="text-red-700">*<span class="text-sm">กรอกได้ไม่เกิน 10000 ตัวอักษร</span></span></span>
                <textarea placeholder="Type your description here..." class="ann-description textarea textarea-bordered textarea-md w-full max-w-xs border-2" style="font-size: 16px;" maxlength="10000" v-model="description"></textarea>
            </label>
                
            <!-- Publish Date -->
            <label class="block ">
                <span class="block text-sm font-bold text-slate-700  mb-2 " style="font-size: 16px;">Publish Date :</span>
                <input type="date" class="ann-publish-date pl-3 pr-5 h-10 rounded-md mr-5 mb-3 border-2" v-model="pDate">
                <input type="time" class="ann-publish-time rounded-md pl-5 pr-5 h-10 ml-5 border-2" v-model="pTime">
            </label>  

           <!-- Close Date -->
           <label class="block ">
                <span class="block text-sm font-bold text-slate-700   mt-2 " style="font-size: 16px;">Close Date :</span>
                <input type="date" class="ann-close-date pl-3 pr-5 h-10 rounded-md mr-5 mb-3 border-2" v-model="cDate">
                <input class="ann-close-time  rounded-md pl-5 pr-5 h-10 ml-5 border-2" type="time" v-model="cTime">
            </label>  

           <!-- Display -->
            <label class="block">
                <span class="block text-sm font-bold text-slate-700 mt-5" style="font-size: 16px;">Display :</span>
                <input type="checkbox" class="ann-display checkbox-md mt-2" @click="display = !display" v-model="display"/>
                <span class="ml-2 text-sm font-bold text-slate-700" style="font-size: 16px;">Check to show this announcement</span>
            </label>    
            
        </div>
        
     
        <!-- <img src="../assets/images/neko-mafumafu.gif" class="flex m-40 w-auto mb-28"/>
    -->


        </div>
        

            <!-- Button -->
        <div class="m-3 flex space-x-5 pb-3 mt-5">
            <button @click="submit()" class="ann-button ann-submit ml-10 btn border border-green-500 bg-green-500 pl-5 pr-5" type="submit" :disabled="submitBtn">Submit</button>
            <button @click="cancel()" class="ann-button ann-submit ml-10 btn border border-gray-500 bg-gray-500 pl-5 pr-5">Cancel</button>
        </div>

        
</div>


</template>

<style scoped>
.text-shadow {
  text-shadow: 2px 4px 4px rgba(0, 0, 0.5, 0.5); /* Add desired shadow properties */
}
</style>