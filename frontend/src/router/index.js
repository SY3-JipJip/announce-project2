import { createRouter, createWebHistory } from 'vue-router'

import AdminAnnDetail from '../components/adminView/AdminAnnDetail.vue'
import AdminAnnView from '../components/adminView/AdminAnnView.vue'
import AdminUserView from '../components/adminView/AdminUserView.vue'

import CreateAnnouncement from '../components/announceManagement/CreateAnnouncement.vue'
import UpdateAnnouncement from '../components/announceManagement/UpdateAnnouncement.vue'

import UserAnnView from '../components/userView/UserAnnView.vue'
import UserAnnDetail from '../components/userView/UserAnnDetail.vue'

import AdminAddUser from '../components/userManagement/AdminAddUser.vue'
import AdminEditUser from '../components/userManagement/AdminEditUser.vue' 

import MatchPassword from '../components/AdminView/MatchPassword.vue'
import LoginView from '../components/LoginView.vue'

import jwt_decode from 'jwt-decode';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin/announcement',
      name: 'home',
      component: AdminAnnView
    },
    {
      path: '/admin/announcement/:id',
      name: 'AdminAnnDetail',
      component: AdminAnnDetail
    },
    {
      path: '/admin/announcement/add',
      name: 'CreateAnnouncement',
      component: CreateAnnouncement
    },
    {
      path: '/admin/announcement/:id/edit',
      name: 'UpdateAnnouncement',
      component: UpdateAnnouncement
    },
    {
      path: '/announcement',
      name: 'UserAnnView',
      component: UserAnnView
    },
    {
      path: '/announcement/:id',
      name: 'UserAnnDetail',
      component: UserAnnDetail
    },
    {
      path: '/admin/user',
      name: 'AdminUserView',
      component: AdminUserView
    },
    {
      path: '/admin/user/add',
      name: 'AdminAddUser',
      component: AdminAddUser
    },
    {
      path: '/admin/user/:id/edit',
      name: 'AdminEditUser',
      component: AdminEditUser
    },
    {
      path: '/admin/user/match',
      name: 'MatchPassword',
      component: MatchPassword
    },
    {
      path: '/',
      redirect: '/login',
    },
    {
      path : '/login',
      name : 'login',
      component : LoginView
    }
  ]

})


router.beforeEach(async (to, from, next) => {
  if (to.path === "/") {
    next('/announcement');
  } else if (localStorage.getItem("token") === null && localStorage.getItem("refreshToken") === null) {
    if (to.fullPath.includes("/admin/")) {
      alert("กรุณาเข้าสู่ระบบ");
      next('/login');
    } else {
      next();
    }
  } else {
    const userRole = localStorage.getItem("userRole")
    if (to.fullPath.includes("/user/") && userRole !== 'admin') {
      next('/announcement'); // หรือสามารถเปลี่ยนเส้นทางไปยังหน้าที่เหมาะสมได้
    } else {
      next();
    }
  }
});






export default router
