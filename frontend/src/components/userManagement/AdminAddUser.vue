<script setup>
import { ref, onMounted,computed } from 'vue';
import { useRouter } from 'vue-router'
import { getNewToken } from '../../composable/getNewToken';
const oldUsers = ref([])
onMounted(async()=>{
    oldUsers.value = await getUsers()
})

const API_ROOT = import.meta.env.VITE_API_ROOT
const router = useRouter()

const getToken = () =>{
  const token = localStorage.getItem("token")
  return "Bearer " + token
}

const getUsers = async () => {
  try {
    const res = await fetch(API_ROOT + "/api/users", {
      headers: {
        'Authorization': getToken()
      }
    });

    if (res.ok) {
      const userData = await res.json();
      return userData;
    } else if (res.status === 401) {
      // Token is invalid, attempt to refresh it
      await getNewToken();

      // Retry the API request with the new token
      const res2 = await fetch(API_ROOT + "/api/users", {
        headers: {
          'Authorization': getToken()
        }
      });

      if (res2.ok) {
        const userData = await res2.json();
        return userData;
      } else {
        throw new Error('Error, cannot get data even after token refresh!');
      }
    } else {
      throw new Error('Error, cannot get data!');
    }
  } catch (error) {
    console.log('error', error)
  }
};

//VALUE V-MODEL
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const email = ref('')
const role = ref('announcer')
const roles = ["admin","announcer"]
const name = ref('')





//SHOWING IF EMPTY
const isEmpty = ref(false)
const textAlertEmpty = ref('')


//SHOW REQUIREMENT PASSWORD & EMAIL
const isShowingUNRequire = ref('')
const usernameRequireText = ref('')
const isShowingCPWRequire = ref(false)
const isShowingEMRequire = ref(false)
const passRequireText = ref('must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters')
const confirmPassRequireText = ref('')
const nameRequireText = ref('')
const isShowingNameRequire = ref('')
const emailRequireText = ref('')

//PATTERM OF PASSWORD & EMAIL
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%^#&*()_+{}\[\]:;<>,.?/~\\|-])[\S]{8,14}$/

const emailPattern = /^\S+@\S+\.\S+$/


function removeSpace() {
    username.value.trim()
    password.value = password.value.replace(/\s+/g, ''); // ลบ space ออก
    confirmPassword.value.trim()
    email.value.trim()
}


//ถ้ากดปุ่ม cancel ก็จะมี pop up เด้งขึ้นมาถามว่า จะ cancel จริงไหม ถ้าจริง ก็จะกลับไปหน้า home
const cancel =()=>{
    let result = confirm('Confirm to cancel ')
    if(result)router.push({name : 'AdminUserView'})
}


const enableAdd = computed(()=>{

    //EMPTY 
    if(String(username.value).trim().length === 0){
        isEmpty.value = true
        textAlertEmpty.value = "Username cannot be empty!!!"

    }else if(password.value.trim().length === 0){
        isEmpty.value = true
        textAlertEmpty.value = "Password cannot be empty!!!" 

    }else if(confirmPassword.value.trim().length === 0){
        isEmpty.value = true
        textAlertEmpty.value = "Confirm password cannot be empty!!!" 

    }else if(String(name.value).trim().length === 0){
        isEmpty.value = true
        textAlertEmpty.value = "Name cannot be empty!!!" 

    }else if(String(email.value).trim().length === 0){
        isEmpty.value = true
        textAlertEmpty.value = "Email cannot be empty!!!"

    }else{
        isEmpty.value = false
        textAlertEmpty.value = ""
    }
    

   
    // PASSWORD PATTERN
    if(!passwordPattern.test(password.value) && password.value === 0 || password.value === ""){
        passRequireText.value = 'must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters'
    }
    else if (password.value !== '' && password.value.length < 8) {
        passRequireText.value = 'Password size must be between 8 and 14'
    } else if (password.value.length > 0 && !passwordPattern.test(password.value)) {
        // Check if the user entered at least one special character
        passRequireText.value = 'must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters';
    } else {
        passRequireText.value = '';
    }


    //CONFIRM PASSWORD
    if(password.value != confirmPassword.value && confirmPassword.value != ""){
        isShowingCPWRequire.value = true
        confirmPassRequireText.value = 'The password DOES NOT match'
    }else{
        isShowingCPWRequire.value = false
    }


        // Email Pattern

        if (!emailPattern.test(email.value) ) {
            isShowingEMRequire.value = true;
            if (email.value.length === 0) {
                emailRequireText.value = '';
            } else if (email.value.indexOf('@') === -1) {
                emailRequireText.value = "Please enter a part following '@'.";
            } else if (email.value.indexOf('@') !== email.value.lastIndexOf('@')) {
                emailRequireText.value = "A part following '@' should not contain the symbol '@'.";
            } else {
                emailRequireText.value = "Please include an '@' in the email address.";
            } 
        }else {
            emailRequireText.value = ""
            isShowingEMRequire.value = false;
        }

        // USERNAME UNIQUE
    const isUniqueUsername = oldUsers.value.find((user) => user.username.toLowerCase() === username.value.toLowerCase());
    if (isUniqueUsername) {
        usernameRequireText.value = 'does not unique';
        isShowingUNRequire.value = true;
    } else {
        isShowingUNRequire.value = false;
    }

    // NAME UNIQUE (case-insensitive)
    const isUniqueName = oldUsers.value.find((user) => user.name.toLowerCase() === name.value.toLowerCase());
    if (isUniqueName) {
        nameRequireText.value = 'does not unique';
        isShowingNameRequire.value = true;
    } else {
        isShowingNameRequire.value = false;
    }


    // EMAIL UNIQUE
    const isUniqueEmail = oldUsers.value.find((user) => user.email.toLowerCase() === email.value.toLowerCase());
    if (isUniqueEmail) {
        emailRequireText.value = 'does not unique';
        isShowingEMRequire.value = true;
    } else {
        isShowingEMRequire.value = false;
    }

})


//ถ้ากดปุ่ม submit ก็จะมี POP UP ถามก่อนว่า จะ submit จริงไหม ถ้าจริงก็จะทำตามที่เขียนข้างล่าง
const submit = async () => {
    const newUser = {
        username: username.value.trim(),
        password: password.value.trim(),
        name: name.value.trim(),
        email: email.value.trim(),
        role: role.value
    };

    try {
        // ทำการ POST ข้อมูลใหม่ไปยัง API
        const response = await fetch(API_ROOT + '/api/users', {
            method: 'POST',
            headers : {
            "Content-Type": "application/json",
            'Authorization': getToken()
        },
            body: JSON.stringify(newUser)
        });

        if (response.ok) {
            alert('Added user successfully');
            router.push('/admin/user');
        } else {
            // ถ้าไม่สำเร็จในการเพิ่มผู้ใช้ใหม่
            const errorData = await response.json();
            let errorMessage = "Unable to add new user!!! :";
            for (const error of errorData.detail) {
            errorMessage += ` ${error.field}: ${error.errorMessage},`;
            }
            errorMessage = errorMessage.slice(0, -1);
            alert(errorMessage);
        }
    } catch (err) {
        alert('Please login');
        router.push('/login');
    }

};


 




</script>
 
<template>
    
<div class="sm:ml-64">

    <div class="border border-2 w-full">

            <div class="w-full flex flex-row items-center">
                <img src="../../assets/images/user-avatar.png" class="h-7 ml-7">
                <h1 class="text-3xl font-bold p-5">User Detail:</h1>
            </div>

            <form @submit.prevent="submit" class="w-full flex flex-col p-5 m-2">

                <div class="ann-item flex w-full flex-col space-y-5">

                    <!-- Username -->
                    <div class="w-full">
                        <p>Username<span class="text-red-700">*</span></p>
                        <input @input="removeSpace" type="text" placeholder="Type here" class="ann-username input input-bordered w-full max-w-xs" maxlength="45" v-model.trim="username" required/>
                        <span class="text-sm font-medium text-slate-700 ml-2">({{ username.length }}/45)</span> 
                        <div class="flex gap-1 items-center justify-start text-red-600" v-show="isShowingUNRequire ">
                            <span class="ann-error-username input:invalid text-sm mt-3 w-1/2 m-1 text-red-500 font-semibold">{{ usernameRequireText }}</span> 
                        </div>
                    </div>
                
                    <!-- Password -->
                    <div class="w-full">
                        <p>Password<span class="text-red-700">*</span></p>
                        <div class="flex">
                            <input @input="removeSpace" type="password" placeholder="Type your password here..." class="ann-password input input-bordered textarea-md  w-3/5 max-w-xs" minlength="8" maxlength="14" v-model.trim="password" required/>
                            <div class="flex flex-row justify-between w-20">
                                <span class="text-sm font-medium text-slate-700 ml-2 mt-3">({{ password.length }}/14) </span> 
                            </div>
                        </div>
                        <!-- information -->
                        
                         <div class='text-sm mt-3 w-1/3 m-1 text-red-500 flex'>
                            <span class="ann-error-password font-semibold">{{passRequireText}}</span>
                         </div>
                          
                </div>


                    <!-- Confirm Password -->
                    <div class="w-full">
                        <p>Confirm password <span class="text-red-700">*</span></p>
                        <div class="flex">
                            <input @input="removeSpace" type="password" placeholder="Confirm your password here..." class="ann-confirm-password input input-bordered textarea-md  w-3/5 max-w-xs" minlength="8" maxlength="14" v-model.trim="confirmPassword" required/>
                            <div class="flex flex-row justify-between w-20">
                                <span class="text-sm font-medium text-slate-700 ml-2 mt-3">({{ confirmPassword.length }}/14) </span>
                            </div>
                        </div>
                        <div class="flex gap-1 items-center justify-start" v-show="isShowingCPWRequire">
                            <span class="ann-error-password text-sm mt-3 w-1/3 m-1 text-red-500 flex font-semibold">{{ confirmPassRequireText }}</span>
                        </div>
                    </div>

                    <!-- Name -->
                    <div class="w-full">
                        <p>Name <span class="text-red-700">*</span></p>
                        <input type="text" placeholder="Your name" class="ann-name input input-bordered textarea-md  w-3/5 max-w-xs" maxlength="100" v-model.trim="name" required/>
                        <span class="text-sm font-medium text-slate-700 ml-2">({{ name.length }}/100)</span> 
                        <div class="flex gap-1 items-center justify-start text-red-600" v-show="isShowingNameRequire ">
                            <span class="ann-error-name text-sm mt-3 w-1/2 m-1 text-red-500 font-semibold">{{ nameRequireText }}</span>
                        </div>
                    </div>

                    <!-- Email -->
                    <div class="w-full">
                        <p>Email<span class="text-red-700">*</span></p>

                        <div class="flex">
                            <input @input="removeSpace" type="email" placeholder="example@example.com" class="ann-email input input-bordered w-full max-w-xs" maxlength="150"  v-model.trim="email" required/>
                            <div class="flex flex-row justify-between w-20">
                                <span class="text-sm font-medium text-slate-700 ml-2 mt-3">({{ email.length }}/150)</span>
                            </div>
                        </div>

                        <div class="flex gap-1 items-center justify-start text-red-600" v-show="isShowingEMRequire ">
                            <span class="ann-error-email text-sm mt-3 w-1/2 m-1 text-red-500 font-semibold">{{ emailRequireText }}</span>
                        </div>
                    </div>

                    <!-- Role -->
                    <div class="w-full">
                        <p>Role<span class="text-red-700">*</span></p>
                        <select name="role" class="ann-role rounded-md border p-1" v-model="role">
                            <option v-for="role in roles" :value="role">{{ role }}</option>
                        </select>
                    </div>

                    <!-- Warning Empty -->
                    <div class="alert alert-error w-full justify-start w-1/5 h-12 mt-3 overflow-scroll overflow-x-hidden overflow-y-hidden" v-if="isEmpty">
                        <span class="font-semibold">{{ textAlertEmpty }}</span>
                    </div>
                </div>

                <!-- SUBMIT & CANCEL -->
                <div class="m-3 flex space-x-5 pb-3 mt-5">
                    <button class="ann-button ann-submit ml-10 btn border border-green-500 bg-green-500 pl-5 pr-5" type="submit" :class="enableAdd">add</button>
                    <button @click="cancel" class="ann-button ann-submit ml-10 btn border border-gray-500 bg-gray-500 pl-5 pr-5">Cancel</button>
                </div>

            </form>
    </div>
</div>

</template>
 
<style scoped>
</style>