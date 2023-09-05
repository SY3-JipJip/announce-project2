<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
import { getUsers } from '../../composable/getUsers';
import { getUserById } from '../../composable/getUserById'
import { formatDate } from '../../composable/formatDate'
const API_ROOT = import.meta.env.VITE_API_ROOT
const { params } = useRoute()
const router = useRouter()


//ข้อมูลเก่า
const oldData = ref('')

//ข้อมูล user ทั้งหมด เพื่อเอา role
const userDatas = ref('')

//ข้อมูลของ user id นั้นๆ
const userDetail = ref({})


onMounted(async () => {
    userDetail.value = await getUserById(params.id);
    userDatas.value = await getUsers()

    oldData.value = {
            "username": userDetail.value.username,
            "name": userDetail.value.name,
            "email": userDetail.value.email,
            "role" : userDetail.value.role,
            "createdOn": userDetail.value.createdOn,
            "updatedOn" : userDetail.value.updatedOn
    }

    console.log(oldData.value)

})


const uniqueRoles = computed(() => {
    const rolesSet = new Set();

    for (const user of userDatas.value) {
        rolesSet.add(user.role);
    }

    return Array.from(rolesSet);
});



//ถ้ากดปุ่ม Cancle ก็จะ Pop up ถามว่า จะยกเลิกจริงไหม
const cancle =()=>{
    confirm('Are you sure you want to cancel? User data will be not save.')
    router.push({name : 'AdminUserView'})
}

//จะ return ค่าเป็น Boolean ถ้า user เลือก category
const isEdit = computed(()=>{
    // console.log(edittingAnnouncement.value)
    return  (edittingUser.value.username===undefined|| edittingUser.value.username==="" || edittingUser.value.name===undefined || edittingUser.value.name==="" || edittingUser.value.email===undefined || edittingUser.value.email==="" || edittingUser.value.role==="" || JSON.stringify(edittingUser.value) === JSON.stringify(oldData.value) )
})

//ค่าของ edittingAnnouncement ค่าเริ่มต้นจะเป็นของข้อมูลเดิม และเมื่อ user แก้ไข ตัวแปรก็จะเปลี่ยนค่าตามที่ user input ค่าเข้ามา
const edittingUser = computed(()=>{
    console.log(edittingUser.value)
    return {
        "username": userDetail.value.username,
        "name": userDetail.value.name,
        "email": userDetail.value.email,
        "role" : userDetail.value.role
    }
})



//
const submit = async () =>{
    await fetch(API_ROOT+'/api/users/'+params.id,{
        method : "PUT",
        headers: {
        "Content-Type": "application/json",
        },
        body: JSON.stringify({
        "username": edittingUser.value.username,
        "name": edittingUser.value.name,
        "email": edittingUser.value.email,
        "role" : edittingUser.value.role,
        })  
    }
    )
    .then(console.log("Update successfully"))
    .then(() => {
        alert('Update successfully');
        router.push({ name: 'AdminUserView' });
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
            <input type="text" placeholder="Type here" class="ann-username input input-bordered w-full max-w-xs" v-model="userDetail.username" />
        </div>
        <div class="detail">
            <p>Name</p>
            <input type="text" placeholder="Type here" class="ann-name input input-bordered w-full max-w-xs" v-model.trim="userDetail.name" />
        </div>
        <div class="detail">
            <p>Email</p>
            <input type="text" placeholder="Type here" class="ann-email input input-bordered w-full max-w-xs" v-model.trim="userDetail.email" />
        </div>
        <div class="form-control w-full max-w-xs detail">
            <p>Role</p>
            <select name="role" class="ann-role rounded-md p-1 border-4 border-blue-900" v-model="userDetail.role">
                <option v-for="role in uniqueRoles" :value="role">{{ role }}</option>
            </select>
        </div>
        <div class="w-full">
            <div>Created On <span class="ann-created-on">{{ formatDate(userDetail.createdOn) }}</span></div>
            <div>Updated On <span class="ann-updated-on">{{ formatDate(userDetail.updatedOn) }}</span></div>
        </div>
    </div>

    <div class="flex justify-center">
        <button @click="submit()" class="ann-button ann-submit ml-10 btn bg-green-600 pl-5 pr-5" :disabled="isEdit">submit</button>
        <button @click="cancle()" class="ann-button ml-5 mb-6 btn buttonCancle" >Cancle</button>
    </div>

    </div>
</div>
</template>
 
<style scoped>
.detail{
    padding-bottom:30px;
}
</style>