<script setup>
import { ref, onMounted,computed } from 'vue';
import { useRouter } from 'vue-router'
import { getUsers } from '../../composable/getUsers';

const API_ROOT = import.meta.env.VITE_API_ROOT
const router = useRouter()

const userData = ref([''])
onMounted(async()=>{
  userData.value = await getUsers()
})


//ถ้า USER ไม่กรอกข้อมูล title หรือ category หรือ description ก็จะไม่ยอมให้ กดปุ่ม submit 
const submitBtn = computed(()=>{
   return username.value.trim().length === 0 || names.value.trim().length === 0  || role.value == undefined
})



//ถ้ากดปุ่ม cancel ก็จะมี pop up เด้งขึ้นมาถามว่า จะ cancel จริงไหม ถ้าจริง ก็จะกลับไปหน้า home
const cancel =()=>{
    let isCancel = confirm('Confirm to cancel')
    if(isCancel)router.push({name : 'home'})
}

const uniqueRoles = computed(() => {
    const rolesSet = new Set();

    for (const user of userData.value) {
        rolesSet.add(user.role);
    }

    return Array.from(rolesSet);
});


const username = ref('')
const names = ref('')
const email = ref('')
const role = ref('announcer')

//ตั้งโครงตัวแปรเอาไว้เก็บข้อมูล
const userDetail = ref({})
userDetail.value = {
        "username": '',
        "name": '',
        "email": '',
        "role" : null
    }

//ถ้ากดปุ่ม submit ก็จะมี POP UP ถามก่อนว่า จะ submit จริงไหม ถ้าจริงก็จะทำตามที่เขียนข้างล่าง
const submit = async()=>{
    let isConfirm = confirm('Confirm to submit ')
    if (isConfirm) {

        userDetail.value = {
        "username": username.value,
        "name": names.value,
        "email": email.value,
        "role" : role.value
    }
    try {
        const res = await fetch(API_ROOT+"/api/users",{
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(userDetail.value)
                })
        if(res.status === 200){
            const response = await res.json()
            router.push('/admin/user')
        }else{
            alert('Could not create this user please try again.')
        }
    } catch (error) {
        alert(error)
    }
}
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
        <div class="ann-item">
            <div class="detail">
                <p>Username</p>
                <input type="text" placeholder="Type here" class="ann-username input input-bordered w-full max-w-xs" v-model="username"/>
            </div>
            <div class="detail">
                <p>Name</p>
                <input type="text" placeholder="Type here" class="ann-name input input-bordered w-full max-w-xs"  v-model="names"/>
            </div>
            <div class="detail">
                <p>Email</p>
                <input type="text" placeholder="Type here" class="ann-email input input-bordered w-full max-w-xs"  v-model="email"/>
            </div>
            <div class="form-control w-full max-w-xs detail">
            <p>Role</p>
            <select name="role" class="ann-role rounded-md p-1 border-4 border-blue-900" v-model="role">
                    <option v-for="role in uniqueRoles" :value="role">{{ role }}</option>
                </select>
            </div>
        </div>

        <div class="m-3 flex space-x-5 pb-3 mt-5">
            <button @click="submit()" class="ann-button ann-submit ml-10 btn border border-green-500 bg-green-500 pl-5 pr-5" type="submit" :disabled="submitBtn">Save</button>
            <button @click="cancel()" class="ann-button ann-submit ml-10 btn border border-gray-500 bg-gray-500 pl-5 pr-5">Cancel</button>
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