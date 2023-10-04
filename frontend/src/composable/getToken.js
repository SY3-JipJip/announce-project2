import router from '../router'
const FETCH_API = import.meta.env.VITE_API
const getNewToken = async (refreshToken) =>{
  try{
    const res = await fetch(`${FETCH_API+'/api/token'}`,{
      headers:{
        'Authorization' : "Bearer " + refreshToken
      }
    })
    if(res.ok){
      const data = await res.json()
      return data.token
    }else{
      alert("Please Login")
      router.push("/login")
    }
  }catch(err){
      throw new Error(err)
  }
}
  export { getNewToken }