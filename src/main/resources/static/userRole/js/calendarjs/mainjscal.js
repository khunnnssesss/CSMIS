import showCalendar from "./showCalendar.js";
import getUserObj from "../userComponents/getUserObj.js";


var adcalendar = document.querySelector('#calendar')
var empId = document.querySelector('#registerEmpId').innerHTML
var emailElement = document.querySelector('#registerEmpMail')
var empAvoidMeat = document.querySelectorAll('.avoidMeats')
var avoidMeatElement = document.querySelector('.avoidMeatList')
var sendDataButton = document.querySelector('#sendData')

console.log('a clanendar',adcalendar,$('#calendarModal'));
sendDataButton.setAttribute('disabled',true)
var avoidMeatList = [];
var dateList = [];
console.log('log emp ID : ',empId);
var curDate = moment().format('Y-MM-DD')
var curMonth = moment().format('MMMM')
var userObj = {};


  if(registerUser=='true'){
  
          sendDataButton.innerHTML = 'Update Register Dates'
            getUserObj(empId).then(user=> {
              
              getuserfetch(user)
              getAllMeats()   
            })
}else{
  console.log('in register user false : " ',registerUser);
  sendDataButton.innerHTML = 'Register'
    getAllMeats() 
  }

 


empAvoidMeat.forEach(w=>{
    w.addEventListener('click',(w)=>{

        var meat = w.target.value

        if(w.target.checked) avoidMeatList.push(meat)
        else {
            var avoidIndex = avoidMeatList.indexOf(meat)
            avoidMeatList.splice(avoidIndex,1)
        }
        console.log('avoid meat : ',avoidMeatList,' mail : ',emailElement.checked);
    })

    console.log(meat,avoidMeatList);
})


// send data to backend
sendDataButton.addEventListener('click',()=>{
  avoidMeatList = avoidMeatList.map(w=>{
    return {avoid: w}
  })
  dateList = dateList.map(w=>{
    return {date: w}
  })


var empData = {
    empId,
    emailNoti: emailElement.checked,
    avoidList: avoidMeatList,
    dateList,
    doorlog: empuserDoorlog,
    registerDate: curDate
}

console.log('json format :: ',JSON.stringify(empData));

sendDataToBackEnd(empData)
console.log("emp data to send to backend d fk;akjfkl djklaj klfdjkfjd: : ",empData);
})

 function sendDataToBackEnd(dataobj){

  fetch('/myDaDa',
  {
    method: 'POST',
    body: JSON.stringify(dataobj),
    headers: {
      'Content-type': 'application/json; charset=UTF-8',
    }
  }
  ).then((response) => {
    console.log('data dafdfdfdf : ',response);
    if (response.ok) {
    return response;
    }
    throw new Error(response.status);
    })
    .then((responseJson) => {
      console.log('registered data adfdfdf : ',responseJson);
      console.log('user role : ',userRole == 'USER');
    if(registerUser=='true'){
     
      swal({
        title: 'Register Updated Successful!',
        text: `You have updated ${dateList.length} days for ${curMonth} `,
        button: 'Back to Homepagesese'
      }).then(a => {
        if(userRole == 'USER') window.location.replace('/user/dashboard')
        else if(userRole == 'ADMIN') window.location.replace('/admin/dashboard')
      })

    }else{
      swal({
        title: 'Registered Successful!!',
        text: `You have registered ${dateList.length} days for ${curMonth} `,
        button: 'Back to Homepage'
      }).then(a =>{
        if(userRole == 'USER') window.location.replace('/user/dashboard')
        else if(userRole == 'ADMIN') window.location.replace('/admin/dashboard')
      })
    }
    })
    .catch(err => {

      swal({
        title: 'Error has occurred!',
        text: `Something went wrong while sending data to server!!`,
        button: 'Back to Homepage'
      }).then(a => {
        if(userRole == 'USER') window.location.replace('/user/dashboard')
        else if(userRole == 'ADMIN') window.location.replace('/admin/dashboard')
      })
      console.log('error obj ',err)
    })

}



// get all checked Avoid Meat List 
function getSelectedCheckboxValues() {
    const checkboxes = document.querySelectorAll('.avoidMeats:checked');
    const values = [];
    checkboxes.forEach((checkbox) => {
      values.push(checkbox.value);
    });
   
    console.log('select box :: ',values);
    return values;
  }

  var calheaderobj={
    left: 'prev next',
    center: 'title',
    right: ''
  }

// showCalendar(adcalendar).then(res => res.render())
var datatomainobj = {
    getByid: adcalendar,
    dateclickfunc: mydateclick,
    empdatelists: dateList,
    calheader: calheaderobj
}

// $('#calendarModal').on('shown.bs.modal', function () {
//   // $('#myInput').trigger('focus')
//   console.log('in calendar func');
// })


$('#calendarModal').on('shown.bs.modal', function () {
  avoidMeatList = getSelectedCheckboxValues()
  console.log('in calendar mode');
  if(JSON.stringify(userObj) != '{}') datatomainobj.userdataobj = userObj

    showCalendar(datatomainobj).then(res => {
     console.log('calendar render () ;;'); 
      res.render()
      
    })

 });


console.log('in main js calendar script!!! [[${empObj}]]');

 function mydateclick(info,holidaydates,weekscur){

    var weekendday=moment(info.date).format('dddd');
     var clickdate = moment(info.date).format('Y-MM-DD')
    console.log('date click',dateList);
        
        if(weekendday =='Sunday' || weekendday=='Saturday'){
          swal({
            title: 'Weekend day',
            text: 'Weekend days can\'t be registered',
            timer: 3000
          })
        console.log('in weekends!!');
        }
        else if(holidaydates.includes(clickdate)) {
          swal({
            title: 'Holiday date',
            text: 'Holiday dates can\'t be registered',
            timer: 3000
          })
          console.log('in holidays!!');
        }
       else if(clickdate<curDate){
        swal({
          title: 'Past day!!',
          text: 'Past days can\'t be registered',
          timer: 3000
        })
        console.log('past date???!!1');
       }
         else if(weekscur.curdays.includes(info.dateStr)) {
          swal({
            title: 'In Current Week',
            text: 'Current Week days can\'t be registered',
            timer: 3000
          })
          console.log(" in current week!!!")
         }
         else if(weekscur.nextdays.includes(info.dateStr)){
          swal({
            title: 'In Next Week',
            text: 'Next Week days can\'t be registered',
            timer: 3000
          })
          console.log('in next week!!');
         }
         else if(dateList.includes(info.dateStr)) {
           info.dayEl.classList.remove("registerDate")
           var myin = dateList.indexOf(info.dateStr);
           dateList.splice(myin,1);
         
        }
         else{
          info.dayEl.classList.add("registerDate")
          dateList.push(info.dateStr)
         }
    console.log(dateList);
    if(dateList.length<3) sendDataButton.setAttribute('disabled',true)
    else sendDataButton.removeAttribute('disabled')

}


//get all meats for lunch register
async function getAllMeats(){

  var response = await fetch('/getAllMeatList').then(resp => resp.json())

  console.log("meat lists :: ",response);
  var c=1;
  response.forEach(w=>{

    let aviddiv =[
        `<div class="form-check form-check-inline list-group-item">`,
        `<input class="form-check-input avoidMeats" type="checkbox" id="inlineCheckbox${c}"  value="${w.meat}" ${avoidMeatList.includes(w.meat) && 'checked'}>`,
        `<label class="form-check-label" for="inlineCheckbox${c}">${w.meat}</label>`,
        `</div>`].join('');

    avoidMeatElement.insertAdjacentHTML('afterend',aviddiv)
    c++;
  })
  console.log('meat avoid ele div :: ',avoidMeatElement);
  
  console.log('all meat :: ',response);
}

// Update registration form for registered user
function getuserfetch(userobj){

  userObj = {...userobj};

  if(userObj.emailNoti) emailElement.setAttribute('checked',true)

userObj.avoidList.forEach(w => avoidMeatList.push(w.avoid))
userObj.dateList.forEach(w => dateList.push(w.date))
  console.log('user object : ',userobj);
}


// </div><div class="form-check form-check-inline list-group-item">
// <input class="form-check-input avoidMeats" type="checkbox" id="inlineCheckbox5"  value="Flower" >
// <label class="form-check-label" for="inlineCheckbox5">Flower</label>
// </div>