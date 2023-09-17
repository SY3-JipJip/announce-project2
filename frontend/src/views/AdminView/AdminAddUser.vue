<script setup>
import { ref, onMounted,computed } from 'vue';
import { useRouter } from 'vue-router'

const API_ROOT = import.meta.env.VITE_API_ROOT
const router = useRouter()

//VALUE V-MODEL
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const email = ref('')
const role = ref('announcer')
const roles = ["admin","announcer"]
const name = ref('')


const errorMessage = ref()



//SHOWING IF EMPTY
const isEmpty = ref(false)
const textAlertEmpty = ref('')


//SHOW REQUIREMENT PASSWORD & EMAIL
const isShowingPWRequire = ref(false)
const isShowingEMRequire = ref(false)
const passRequireText = ref('')
const confirmPassRequireText = ref('')
const emailRequireText = ref('')

//PATTERM OF PASSWORD & EMAIL
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%^&*()_+{}\[\]:;<>,.?/~\\|-])[\S]{8,14}$/
const emailPattern = /^\S+@\S+\.\S+$/

//PAGE



//ถ้ากดปุ่ม cancel ก็จะมี pop up เด้งขึ้นมาถามว่า จะ cancel จริงไหม ถ้าจริง ก็จะกลับไปหน้า home
const cancel =()=>{
    let result = confirm('Confirm to cancel ')
    if(result)router.push({name : 'AdminAddUserView'})
}


//EMPTY FILED
const enableAdd = computed(()=>{
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
    if(!passwordPattern.test(password.value)){
        passRequireText.value = 'Password must be include number, uppercase, lowercase and special characters. Do not include whitespace'
        isShowingPWRequire.value = true
    }else{
        passRequireText.value = ''
        isShowingPWRequire.value = false
    }


    //EMAIL PATTERN
    if(!emailPattern.test(email.value)){
        isShowingEMRequire .value = true
        emailRequireText.value = "example : exemple@email.com"
    }else{
        isShowingEMRequire .value = false
    }

    
    //CONFIRM PASSWORD
    if(password.value !== confirmPassword.value){
        confirmPassRequireText.value = 'The password DOES NOT match'
    }

    return isEmpty.value || isShowingEMRequire.value || isShowingPWRequire.value || password.value !== confirmPassword.value
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
        const response = await fetch(API_ROOT + 'api/users', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newUser)
        });

        if (response.ok) {
            alert('Added user successfully');
            router.push('api/admin/user');
        } else {
            alert('Unable to add new user!!!');
        }
    } catch (err) {
        errorMessage.value = err;
    }
};



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
                    <!-- Username -->
                    <div class="detail">
                        <p>Username<span class="text-red-700">*</span></p>
                        <input type="text" placeholder="Type here" class="ann-username input input-bordered w-full max-w-xs" maxlength="45" v-model="username"/>
                        <span class="text-sm font-medium text-slate-700 ml-2">({{ username.length }}/45)</span> 
                    </div>
                
                    <!-- Password -->
                    <label class="block">
                        <span class="block text-sm font-medium text-slate-700 mb-2 mt-4 ">Password <span class="text-red-700">*</span></span>
                        <div class="flex">
                        <input type="password" placeholder="Type your password here..." class="ann-password input input-bordered textarea-md  w-3/5 max-w-xs" maxlength="14" v-model="password" required/>
                        <div class="flex flex-row justify-between w-20">
                            <span class="text-sm font-medium text-slate-700 ml-2 mt-3">({{ password.length }}/14) </span> 
                        </div>
                    </div>

                    <div class="flex gap-1 items-center justify-start text-black" v-if="isShowingPWRequire">
                        <span class="font-serif text-sm mt-3 w-1/2 m-1 text-black">{{ passRequireText }}</span>
                    </div>

                    </label>    

                    <!-- Confirm Password -->
                    <label class="block">
                        <span class="block text-sm font-medium text-slate-700 mb-2 mt-4 ">Confirm password <span class="text-red-700">*</span></span>
                        <div class="flex">
                        <input type="password" placeholder="Confirm your password here..." class="ann-confirm-password input input-bordered textarea-md  w-3/5 max-w-xs" maxlength="14" v-model="confirmPassword" required/>
                            <div class="flex flex-row justify-between w-20">
                                <span class="text-sm font-medium text-slate-700 ml-2 mt-3">({{ confirmPassword.length }}/14) </span>
                            </div>
                        </div>
                    </label>

                    <!-- Name -->
                    <label class="block">
                        <span class="block text-sm font-medium text-slate-700 mb-2 mt-4">Name <span class="text-red-700">*</span></span>
                        <input  type="text" placeholder="Type your name here..." class="ann-name  input input-bordered textarea-md  w-3/5 max-w-xs" maxlength="100" v-model="name" required/>
                        <span class="text-sm font-medium text-slate-700 ml-2">({{ name.length }}/100)</span> 
                    </label>

                    <!-- Email -->
                    <div class="block">
                        <p>Email<span class="text-red-700">*</span></p>
                        <input type="text" placeholder="Type here" class="ann-email input input-bordered w-full max-w-xs"  v-model="email"/>
                        <div class="flex flex-row justify-between w-20">
                            <span class="text-sm font-medium text-slate-700 ml-2 mt-3">({{ email.length }}/150)</span>
                        </div>

                        <div class="flex gap-1 items-center justify-start text-red-600" v-if="isShowingEMRequire ">
                            <span class="font-serif text-sm mt-3 w-1/2 m-1 text-black">{{ emailRequireText }}</span>
                        </div>
                    </div>

                    <!-- Role -->
                    <label class="block  mt-3 ">
                        <span class="block text-sm font-medium text-slate-700">Role<span class="text-red-700">*</span></span>
                        <select name="role" class="ann-role rounded-md border p-1" v-model="role">
                            <option v-for="role in roles" :value="role">{{ role }}</option>
                        </select>
                    </label>

                    <!-- Warning Empty -->
                    <div class="alert alert-error justify-start w-full h-12 mt-3 overflow-scroll overflow-x-hidden overflow-y-hidden" v-if="isEmpty">
                        <span class="font-semibold">{{ textAlertEmpty }}</span>
                    </div>
                </div>

                <!-- SUBMIT & CANCEL -->
                <div class="m-3 flex space-x-5 pb-3 mt-5">
                    <button @click="submit" class="ann-button ann-submit ml-10 btn border border-green-500 bg-green-500 pl-5 pr-5" type="submit" :disabled="enableAdd">Save</button>
                    <button @click="cancel" class="ann-button ann-submit ml-10 btn border border-gray-500 bg-gray-500 pl-5 pr-5">Cancel</button>
                </div>

            </div>
    </div>
</div>
</template>
 
<style scoped>
</style>