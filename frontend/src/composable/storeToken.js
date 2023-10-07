const storeToken = (tokens) =>{
    localStorage.setItem("token",tokens.token)
    localStorage.setItem("refreshToken",tokens.refreshToken)
     
}

export{storeToken}