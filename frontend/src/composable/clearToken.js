

const clearToken = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
  }
export {clearToken}
  