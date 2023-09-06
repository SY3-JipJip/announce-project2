const formatDate = (date) => {
  return new Date(date).toLocaleString('en-GB', {
    day: "numeric",
    month: "short",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    hour12: false
  });
}

const convertDate = (dateTime) => {
  let date = new Date(dateTime)
  let dateObj = date.toLocaleDateString().split('/')
  return dateTime === null ? undefined : Number(dateObj[2]) + '-' + ("0" + (Number(dateObj[0]))).slice(-2) + '-' + ("0" + Number(dateObj[1])).slice(-2) 
}

const convertTime = (dateTime) => {
  let date = new Date(dateTime)
  return dateTime === null ? undefined : date.toLocaleTimeString("en-US",{ hour : "2-digit",minute : "2-digit",hour12 : false })
}
export { formatDate, convertDate, convertTime }