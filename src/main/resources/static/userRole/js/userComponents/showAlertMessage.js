const alertPlaceholder = document.getElementById('liveAlertPlaceholder')
const appendAlert = (message, type) => {

  alertPlaceholder.innerHTML = [`<div class="alert alert-warning alert-dismissible fadeInLeft animated text-center" role="alert">`,
  `<strong>${type}</strong> ${message}`,
  `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`,
`</div>`].join('')
  
  
  // [
  //   `<div class="alert alert-${type} alert-dismissible w-50 m-auto fadeIn animated" role="alert">`,
  //   `   <div>${message}</div>`,
  //   '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
  //   '</div>'].join('')
  
}

export default appendAlert;


// <div class="alert alert-warning alert-dismissible fade show" role="alert">
//   <strong>Holy guacamole!</strong> You should check in on some of those fields below.
//   <button type="button" class="close" data-dismiss="alert" aria-label="Close">
//     <span aria-hidden="true">&times;</span>
//   </button>
// </div>