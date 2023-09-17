// getUserById.js
const API_ROOT = import.meta.env.VITE_API_ROOT;

const getUserById = async (userId) => {
  return await fetch(`${API_ROOT}/api/users/${userId}`)
    .then((res) => {
      if (!res.ok) {
        alert('The requested page is not available');
        router.push({
          name: 'AdminUserView'
        });
        throw new Error(res.status);
      } else {
        return res.json();
      }
    })
    .then((data) => {
      return data; // Return the fetched data
    })
    .catch((err) => err);
};

export { getUserById };
