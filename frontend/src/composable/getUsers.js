const API_ROOT= import.meta.env.VITE_API_ROOT

const getUsers = async () => {
    try {
        const res = await fetch(API_ROOT+"/api/users")
        // if(res.status===201)        
        if (res.ok) {
            const userData = res.json()
            return userData     
        } 
            else throw new error('Error, cannot get data!')
    } catch (error) {
        console.log(error)
    }
}
export { getUsers }