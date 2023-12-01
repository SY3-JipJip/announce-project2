<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute ,useRouter } from 'vue-router'
import { formatDate } from '../../composable/formatDate'
import { getNewToken } from '../../composable/getNewToken';
import { useAuthorize } from '../../Store/authorize';
import { storeToRefs } from 'pinia';
const myRole = useAuthorize()
const {userRole} = storeToRefs(myRole)
const API_ROOT = import.meta.env.VITE_API_ROOT
const { params } = useRoute()
const router = useRouter()


//SHOW REQUIREMENT PASSWORD & EMAIL
const isShowingUNRequire = ref('')
const usernameRequireText = ref('')
const isShowingEMRequire = ref(false)
const nameRequireText = ref('')
const isShowingNameRequire = ref('')
const emailRequireText = ref('')

//ข้อมูลเก่า
const oldData = ref([])

//ข้อมูล user ทั้งหมด เพื่อเอา role
const userDatas = ref([])

//ข้อมูลของ user id นั้นๆ
const userDetail = ref([])


onMounted(async () => {
    if(userRole.value !== 'admin'){
    alert('Access Deny')
    router.back()
  }

        await getUserById(params.id);

    oldData.value = {
            "username": userDetail.value.username,
            "name": userDetail.value.name,
            "email": userDetail.value.email,
            "role" : userDetail.value.role,
            "createdOn": userDetail.value.createdOn,
            "updatedOn" : userDetail.value.updatedOn
    }

    // console.log(oldData.value)

})

const getUserById = async (userId) => {

  try {
    const res = await fetch(`${API_ROOT}/api/users/${userId}`, {
      headers: {
        "Content-Type": "application/json",
        'Authorization': "Bearer " + localStorage.getItem('token')
      },
    });

    if (!res.ok) {
      if (res.status === 401) {
            try {
                await getNewToken();
                const newRes = await fetch(`${API_ROOT}/api/users/${userId}`, {
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': "Bearer " + localStorage.getItem('token')
                    },
                });

                if (newRes.ok) {
                    userDetail.value = await newRes.json();
                } 
                
            } catch (error) {
                // console.error('Failed to get new token:', error);
            }
        } 

    } else {
        userDetail.value = await res.json();
    }

  } catch (error) {
    console.error('error ', error);
  }
};



const roles = ["admin","announcer"]



//ถ้ากดปุ่ม Cancle ก็จะ Pop up ถามว่า จะยกเลิกจริงไหม
const cancle =()=>{
    confirm('Are you sure you want to cancel? User data will be not save.')
    router.push({name : 'AdminUserView'})
}


// ค่าของ edittingAnnouncement ค่าเริ่มต้นจะเป็นของข้อมูลเดิม และเมื่อ user แก้ไข ตัวแปรก็จะเปลี่ยนค่าตามที่ user input ค่าเข้ามา
const edittingUser = computed(()=>{
    return {
        "username": userDetail.value.username,
        "name": userDetail.value.name,
        "email": userDetail.value.email,
        "role" : userDetail.value.role
    }
})



//
const submit = async () => {
    const result = confirm('The data will be changed!! Are you sure?');

    if (result) {
        const username = String(edittingUser.value.username).trim();
        const name = String(edittingUser.value.name).trim();
        const email = String(edittingUser.value.email).trim();
        const role = String(edittingUser.value.role).trim();

        const data = {
            "username": username,
            "name": name,
            "email": email,
            "role": role
            
        };
        if (JSON.stringify(data) === JSON.stringify(oldData.value)) {
            router.push('/admin/user');
 
        }else {
            try {
                const response = await fetch(API_ROOT + '/api/users/' + params.id, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': "Bearer " + localStorage.getItem('token'),
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    alert('Updated user successfully.');
                    router.push('/admin/user');
                }else if (res.status == 401) {
                // 401 Unauthorized: รีเริ่มหน้า login
                router.push({ name: 'login' });
                }else {
                    const errorData = await response.json();
                    let errorMessage = "Could not update data!!! :";
                for (const error of errorData.detail) {
                    errorMessage += ` ${error.field}: ${error.errorMessage},`;
                }
                    errorMessage = errorMessage.slice(0, -1);
                    alert(errorMessage);
                }
            } catch (err) {
                alert(`An error occurred: ${err}`);
                router.push('/admin/user');
            }
        }
    } 

// USERNAME UNIQUE   
const isUnique = userDatas.value.find((user) => user.username.toLowerCase() === userDetail.value.username.toLowerCase());

if (isUnique) {
    usernameRequireText.value = 'does not unique';
    isShowingUNRequire.value = true;
} else {
    isShowingUNRequire.value = false;
}



// NAME UNIQUE   
const isUniqueName = userDatas.value.find((user) => user.name.toLowerCase() === userDetail.value.name.toLowerCase());
if (isUniqueName) {
    nameRequireText.value = 'does not unique';
    isShowingNameRequire.value = true;
} else {
    isShowingNameRequire.value = false;
}


// EMAIL UNIQUE   
const isUniqueEmail = userDatas.value.find((user) => user.email.toLowerCase() === userDetail.value.email.toLowerCase());
if (isUniqueEmail) {
    emailRequireText.value = 'does not unique';
    isShowingEMRequire.value = true;
} else {
    isShowingEMRequire.value = false;
}
};


</script>
 
<template>
<div class="sm:ml-64">

    <form @submit.prevent="submit" class="border border-2">
    <div class="w-full flex flex-row items-center">
        <img src="../../assets/images/user-avatar.png" class="h-7 ml-7">
        <h1 class="text-3xl font-bold p-5">User Detail:</h1>
    </div>

    <div class="w-full flex flex-col p-5 space-y-4">
        <!-- Username -->
        <div class="w-full">
            <p>Username<span class="text-red-700">*</span></p>
            <input @input="removeSpace" type="text" placeholder="Type here" class="ann-username input input-bordered w-full max-w-xs" maxlength="45" v-model.trim="userDetail.username" required/>
            <div class="flex gap-1 items-center justify-start text-red-600" v-show="isShowingUNRequire ">
                <span class="ann-error-username input:invalid text-sm mt-3 w-1/2 m-1 text-red-500 font-semibold">{{ usernameRequireText }}</span> 
            </div>
        </div>

       <!-- Name -->
       <div class="w-full">
            <p>Name <span class="text-red-700">*</span></p>
            <input type="text" placeholder="Your name" class="ann-name input input-bordered textarea-md  w-3/5 max-w-xs" maxlength="100" v-model.trim="userDetail.name" required/>
            <div class="flex gap-1 items-center justify-start text-red-600" v-show="isShowingNameRequire ">
                <span class="ann-error-name text-sm mt-3 w-1/2 m-1 text-red-500 font-semibold">{{ nameRequireText }}</span>
            </div>
        </div>

       <!-- Email -->
       <div class="w-full">
            <p>Email<span class="text-red-700">*</span></p>

            <div class="flex">
                <input @input="removeSpace" type="email" placeholder="example@example.com" class="ann-email input input-bordered w-full max-w-xs" maxlength="150"  v-model.trim="userDetail.email" required/>
            </div>
            <div class="flex gap-1 items-center justify-start text-red-600" v-show="isShowingEMRequire ">
                <span class="ann-error-email text-sm mt-3 w-1/2 m-1 text-red-500 font-semibold">{{ emailRequireText }}</span>
             </div>
        </div>

        <div class="form-control w-full max-w-xs detail">
            <p>Role</p>
            <select name="role" class="ann-role rounded-md p-1 border-4 border-blue-900" v-model="userDetail.role">
                <option v-for="role in roles" :value="role">{{ role }}</option>
            </select>
        </div>

        <div class="w-full">
            <div>Created On <span class="ann-created-on">{{ formatDate(userDetail.createdOn) }}</span></div>
            <div>Updated On <span class="ann-updated-on">{{ formatDate(userDetail.updatedOn) }}</span></div>
        </div>
    </div>

    <div class="flex justify-center">
        <button class="ann-button ann-submit ml-10 btn bg-green-600 pl-5 pr-5" type="submit">submit</button>
        <button @click="cancle()" class="ann-button ml-5 mb-6 btn buttonCancle" >Cancle</button>
    </div>
</form>
</div>

</template>
 
<style scoped>
.detail{
    padding-bottom:30px;
}
</style>