const API_ROOT = import.meta.env.VITE_API_ROOT
const getNewToken = async () => {
    try {
      const res = await fetch(`${API_ROOT}/api/token`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('refreshToken')
        }
      });
  
      if (res.ok) {
        const data = await res.json();
        const newToken = data.token;
  
        // บันทึก token ใหม่ใน localStorage และใน composition ref
        localStorage.setItem('token', newToken);
  
        console.log('Token refreshed successfully');
      } else {
        alert('Please Login');
        window.location.href = `${API_ROOT}/login`;
      }
    } catch (err) {
      console.error('An error occurred while refreshing the token', err);
      throw new Error(err);
    }
  };

  export {getNewToken}