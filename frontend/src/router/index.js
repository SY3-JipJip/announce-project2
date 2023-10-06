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
import { storeToken } from '../composable/storeToken'

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
    },
  ]
})

//จะถูกเรียกในทุกครั้งที่มีการเปลี่ยนหน้าในแอปพลิเคชัน
router.beforeEach(async(to, from, next) => {
  //นกรณีที่ผู้ใช้ไม่ได้เข้าสู่ระบบและกำลังพยายามเข้าถึงหน้าอื่นที่ไม่ใช่หน้า "/login" และไม่มี Token หรือ refreshToken ในคุกกี้ โค้ดจะทำการนำทางผู้ใช้ไปยังหน้า "/login".
  if(to.path !== '/login' && (localStorage.getItem("token") === null && localStorage.getItem("refreshToken") === null)){
    next('/login')
  //ในกรณีที่ผู้ใช้ไม่ได้เข้าสู่ระบบและกำลังพยายามเข้าถึงหน้าอื่นที่ไม่ใช่หน้า "/login" และมี refreshToken ในคุกกี้ โค้ดจะทำการเรียก getNewToken() 
  //เพื่อรับ Token ใหม่โดยใช้ refreshToken และจากนั้นตั้งค่า Token ใหม่ในคุกกี้ด้วย app.$cookies.set() แล้วนำทางผู้ใช้ไปยังหน้าปลายทาง.
  }else if(to.path !== '/login' && (localStorage.getItem("token") === null && localStorage.getItem("refreshToken") !== null)){
    storeToken("token",await getNewToken(localStorage.setItem("refreshToken")))
    next()
  //ในกรณีที่ไม่เข้าใกล้เงื่อนไขที่แล้วเหล่านี้ โค้ดจะเรียก next() เพื่ออนุญาตให้ทำการนำทางไปยังหน้าปลายทางที่ถูกเลือก
  }else{
    next()
  }

})

export default router
