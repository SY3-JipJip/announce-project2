
const clearToken = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("userRole")
  
};

export { clearToken };