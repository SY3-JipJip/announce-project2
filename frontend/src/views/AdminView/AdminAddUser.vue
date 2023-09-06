<script setup>

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