import { defineStore, acceptHMRUpdate } from 'pinia';
import { ref, watch } from 'vue';
import jwt_decode from 'jwt-decode';

export const useAuthorize = defineStore('authorize', () => {
  const userRole = ref(localStorage.getItem("userRole"));

  const setRole = (role) => {
    if (role === null) {
      userRole.value = 'viewer';
      localStorage.setItem("userRole", 'viewer');
    } else {
      const decodedRole = jwt_decode(role).role;
      userRole.value = decodedRole;
      localStorage.setItem("userRole", decodedRole);
    }
  };

  // อ่านค่า userRole จาก localStorage เมื่อแอปพลิเคชันถูกรีเฟรช
  const updateFromLocalStorage = () => {
    const storedUserRole = localStorage.getItem("userRole");
    if (storedUserRole) {
      userRole.value = storedUserRole;
    }
  };

  // ตรวจจับการเปลี่ยนแปลงใน localStorage และอัปเดตค่า userRole
  watch(() => localStorage.getItem("userRole"), (newValue) => {
    if (newValue !== userRole.value) {
      userRole.value = newValue;
    }
  });

  return { userRole, setRole, updateFromLocalStorage };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useAuthorize, import.meta.hot));
}
