import { createRouter, createWebHistory } from 'vue-router'
import AdminAnnView from '../views/AdminView/AdminAnnView.vue'
import AdminAnnDetail from '../views/AdminView/AdminAnnDetail.vue'
import CreateAnnouncement from '../views/CreateAnnouncement.vue'
import DeleteAnnouncement from '../views/DeleteAnnouncement.vue'
import UpdateAnnouncement from '../views/UpdateAnnouncement.vue'
import UserAnnView from '../views/UserView/UserAnnView.vue'
import UserAnnDetail from '../views/UserView/UserAnnDetail.vue'
import AdminUserView from '../views/AdminView/AdminUserView.vue'
import AdminAddUser from '../views/AdminView/AdminAddUser.vue'
import AdminEditUser from '../views/AdminView/AdminEditUser.vue' 
import AdminDeleteUser from '../views/AdminView/AdminDeleteUser.vue' 
import MatchPassword from '../views/AdminView/MatchPassword.vue'
import LoginView from '../views/LoginView.vue'
import { getNewToken } from '../composable/getNewToken'

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
      path: '/admin/announcement/:id/delete',
      name: 'deleteAnnounce',
      component: DeleteAnnouncement
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
      path: '/admin/user/:id',
      name: 'AdminDeleteUser',
      component: AdminDeleteUser
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
