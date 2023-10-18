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
      name: 'desc',
      component: AdminAnnDetail
    },
    {
      path: '/admin/announcement/add',
      name: 'addAnnounce',
      component: CreateAnnouncement
    },
    {
      path: '/admin/announcement/:id/edit',
      name: 'updateAnnounce',
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


export default router
