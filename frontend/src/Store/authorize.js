import { defineStore, acceptHMRUpdate } from 'pinia';
import { ref, computed } from 'vue';
import jwt_decode from 'jwt-decode';

export const useAuthorize = defineStore('authorize', () => {
  const token = localStorage.getItem('token');
  const decodedToken = token ? jwt_decode(token) : null;
  
  // ต้องการให้ userRole มีการอัปเดตอัตโนมัติเมื่อ decodedToken มีการเปลี่ยนแปลง
  const decodedTokenRef = ref(decodedToken);
  // const userRole  = computed(() => decodedTokenRef.value ? decodedTokenRef.value.role : '');

  const userRole = ref('admin')
  return { userRole };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useAuthorize, import.meta.hot))
}
