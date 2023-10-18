import { defineStore,acceptHMRUpdate } from "pinia";
import { ref } from "vue";
export const useAuthorize = defineStore('authorize',()=>{
    const userRole = ref('admin')
    return { userRole }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAuthorize, import.meta.hot))
  }