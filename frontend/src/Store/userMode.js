import { defineStore, acceptHMRUpdate} from "pinia";
import { ref } from "vue";
export const userAnnouncement = defineStore('userAnnouncement',()=>{
    const mode = ref(true)
    const setMode = () => mode.value = !mode.value
    const getMode = () => mode.value
    return { setMode , getMode} 

})
if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(userAnnouncement, import.meta.hot))
  }