import { defineStore, acceptHMRUpdate } from 'pinia';
import { ref, computed } from 'vue';
import jwt_decode from 'jwt-decode';

export const useAuthorize = defineStore('authorize', () => {
  const token = localStorage.getItem('token');
  const decodedToken = token ? jwt_decode(token) : null;
  
  // ตรวจสอบว่า decodedToken ไม่ใช่ค่าว่างหรือ null ก่อนที่จะอ่านค่าบทบาท
  const userRoleRef = ref(decodedToken ? decodedToken.role : '');

  // หรือหากคุณต้องการให้ userRole มีการอัปเดตอัตโนมัติเมื่อ decodedToken มีการเปลี่ยนแปลง
  const decodedTokenRef = ref(decodedToken);
  const userRoleComputed = computed(() => decodedTokenRef.value ? decodedTokenRef.value.role : '');

  return { userRole: userRoleRef, userRoleComputed };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useAuthorize, import.meta.hot))
}
