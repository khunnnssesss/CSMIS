import alerMessage from './showAlertMessage.js'
import dataobj from "../userComponents/sampledata.js"

checkRegisterData();

var registereatday = document.querySelector('#registerandeatdays')
var registernoeatday = document.querySelector('#registerandnoeatdays')
var unregistereatday = document.querySelector('#unregisterandeatdays')
var registerdays = document.querySelector('#registerdays')
var checkregistereat = document.querySelector('#flexSwitchCheckRegisterEat')
var checkregisternoeat = document.querySelector('#flexSwitchCheckRegisterNoEat')
var checkunregistereat = document.querySelector('#flexSwitchCheckUnRegisterEat')
var moncur = document.querySelectorAll('.curMonth')
var totalp = document.querySelector('#totalprice')
var calendar = document.querySelector('#calendar')
var data = []

var statusdata={
  'UE':[],
  'RE':[],
  'RNE':[]
}

var evetns=[];
var mycurmonth = moment().format('YYYY-MM');

console.log('test month ;:: ',mycurmonth);





console.log('testing testing',calendar);

var cal = document.querySelector('#calendar')

    var mycal = new FullCalendar.Calendar(cal,{
        showNonCurrentDates: false,
        fixedWeekCount: false,
        dayCellClassNames:(a)=>{
          var daycell = moment(a.date).format('dddd')

if(daycell == 'Sunday' || daycell == 'Saturday') return 'weekenddays'
        },
     customButtons: {
            myCustomNextButton: {
              text: 'Next',
              click: function() {
                mycal.next()
    mycurmonth = moment(mycal.getDate()).format('YYYY-MM');
                
                console.log('my cur month :: ',mycurmonth);
                eventPrevbtn(mycal);
              
              }
          },
                myCustomPrevButton: {
                text: 'Prev',
                click: function(e) {
                mycal.prev()
    mycurmonth = moment(mycal.getDate()).format('YYYY-MM');
                
                console.log('my cur month :: ',mycurmonth);
                eventPrevbtn(mycal);
               
                }
            },
            myCustomToday:{
              text: 'Today Date',
                click: function(e) {
                mycal.today()
    mycurmonth = moment(mycal.getDate()).format('YYYY-MM');
                
                console.log('my cur month :: ',mycurmonth);
                eventPrevbtn(mycal);

                }
            }
        },
          headerToolbar:{
            right: 'myCustomPrevButton myCustomNextButton',
            center: 'myCustomToday'
          }
}
)

var cal2 = document.querySelector('#calendar2')
var mycal2 = new FullCalendar.Calendar(cal2,{
  showNonCurrentDates: false,
  fixedWeekCount: false,
  dayCellClassNames:(a)=>{
    var daycell = moment(a.date).format('dddd')

if(daycell == 'Sunday' || daycell == 'Saturday') return 'weekenddays'
  },
customButtons: {
      myCustomNextButton: {
        text: 'Next',
        click: function() {
          mycal2.next()
    mycurmonth = moment(mycal2.getDate()).format('YYYY-MM');
          
          console.log('my cur month :: ',mycurmonth);
          eventPrevbtn(mycal2);
        }
    },
          myCustomPrevButton: {
          text: 'Prev',
          click: function(e) {
          mycal2.prev()
    mycurmonth = moment(mycal2.getDate()).format('YYYY-MM');
          console.log('my cur month :: ',mycurmonth);
          eventPrevbtn(mycal2);
         
          }
      },
      myCustomToday:{
        text: 'Today Date',
          click: function(e) {
            
          mycal2.today()
          mycurmonth = moment(mycal2.getDate()).format('YYYY-MM');

          console.log('my cur month :: ',mycurmonth);
          eventPrevbtn(mycal2);
          }
      }
  },
    headerToolbar:{
      right: 'myCustomPrevButton myCustomNextButton',
      center: 'myCustomToday'
    }

});


$('#testmodal').on('shown.bs.modal', function () {


 

    mycal2.render();
    eventPrevbtn(mycal);

    console.log('eve ": ',evetns);


    $('#testmodal').css('color','red');
    $('#testmodal').css('font-size','1rem');

 });

mycal.render();

    moncur.forEach(w=>{
      var curMon = moment(mycal.getDate()).format('MMMM')
      w.innerHTML = curMon;
   
    })

$('.btnNext').click(function () {
  mycal.next();
  mycal2.next();
  mycurmonth = moment(mycal.getDate()).format('YYYY-MM');
 console.log('my cur month :: ',mycurmonth);
  eventPrevbtn(mycal);
  })

  $('.btnPrev').click(function () {
    mycal.prev();
    mycal2.prev();
    mycurmonth = moment(mycal.getDate()).format('YYYY-MM');
    console.log('my cur month :: ',mycurmonth);
    eventPrevbtn(mycal);
    })


function mycurdate(obj,currmonth) {
var myobjs ={}

for (const w in obj) {

  var myobj=[]
  obj[w].forEach(d=>{

    var mon = moment(d.date).format('MMMM');

    if(mon == currmonth) myobj.push({date:d.date,price: d.empPrice});
  })
  myobjs[w] = myobj;

}

  return myobjs;
  }

  async function fetchdatas(url){
    try {
      const data = await fetch(url);

      if(!data.ok) throw new Error({
        message: 'something went wrong!!!',
        cause: data
      });
const resp = data.json();
      return [resp,null];
    } catch (error) {
      console.log('error :: ',error.message);
      return [null,data];
    }
  }

  function eventPrevbtn(myc) {
    console.log('price table ;;; ',data);
    console.log('status table ;;; ',statusdata);
  moncur.forEach(w=>{
    var curMon = moment(myc.getDate()).format('MMMM')
    mycurmonth = moment(myc.getDate()).format('YYYY-MM');
    w.innerHTML = curMon;
 
    var mydayobj = mycurdate(statusdata,curMon);
    var redays = mydayobj.RE || []
    var rnedays = mydayobj.RNE || []
    var uedays = mydayobj.UE || []
let retotalprice = 0;
redays.forEach(w=> retotalprice += w.price)


let rnetotalprice = 0;
rnedays.forEach(w=> rnetotalprice += w.price)
let uetotalprice = 0;
uedays.forEach(w=> uetotalprice += w.price)

registerdays.innerHTML = redays.length + rnedays.length

    // if(registerdays.length == 0 ) alerMessage('no registered days for '+curMon,'Alert')
    console.log('data :: ',redays,rnedays,uedays);
    console.log('status data :: ',statusdata);
console.log('cost price :: ',retotalprice,rnetotalprice,uetotalprice);
console.log('register day ',registerdays);

if(registerdays.innerHTML <1 ) alerMessage('no registered days for '+curMon,'Alert')

    registereatday.innerHTML = redays.length;
    registernoeatday.innerHTML = rnedays.length
    unregistereatday.innerHTML = uedays.length


    totalp.innerHTML = retotalprice + rnetotalprice + uetotalprice

  })
  
}

    

	async function checkRegisterData(){
console.log("in check register data");

var [data1,error1] = await fetchdatas("/getAUser/aung")

if(error1) {
  console.log("something went woring!!!!");
  return;
}


      var userlunchregistered = await dataobj(userId)
 
    var cardStaffId = document.querySelector('#card-staffid')

  

    data = [...userlunchregistered]
    console.log(',my lunch data :: ',data);
    console.log('status data :: ',statusdata);
var mydataobj = {
  'UE':[],
  'RE':[],
  'RNE':[]
};


data.forEach(w=>{
  if(w.status == 'unregister' &&  w.eat =='yes') {
    mydataobj['UE'].push(w.date);
    statusdata['UE'].push({date: w.date,empPrice : w.empPrice});
  }
  if(w.status == 'register' && w.eat == 'yes') {
    mydataobj['RE'].push(w.date);
    statusdata['RE'].push({date: w.date,empPrice : w.empPrice});

  }
  if(w.status == 'register' && w.eat == 'no') {
    mydataobj['RNE'].push(w.date);
    statusdata['RNE'].push({date: w.date,empPrice : w.empPrice});

  }
})
data.push(mydataobj);
    showHistoryEvents(statusdata);
    cardStaffId.innerHTML = userId

    eventPrevbtn(mycal);
	}



  function showHistoryEvents(data){
    
var reonlydate = data['RE'] || ''
var rneonlydate = data['RNE'] || ''
var ueonlydate = data['UE'] || ''

let myreEvents = reonlydate != '' && reonlydate.map(w=>({
    id: 're',
    title: 'price '+w.empPrice,
    start: w.date,
   className: 'rebox'
}))


let myueEvents = ueonlydate != '' && ueonlydate.map(w=>({
    id: 'ue',
    title: 'price '+w.empPrice,
    start: w.date,
    className: 'uebox'

}))


let myrneEvents = rneonlydate != '' && rneonlydate.map(w=>({
    id: 'rne',
    title: 'price '+w.empPrice,
    start: w.date,
    textColor: 'purple',
    className: 'rnebox'

}))


evetns.push({re: myreEvents});
evetns.push({rne: myrneEvents});
evetns.push({ue: myueEvents});

checkregistereat.addEventListener('change',(e)=>{
  var checkv = e.target.checked


 if(myreEvents != ''){
  if(checkv){
    myreEvents.forEach(w=> mycal.addEvent(w))
    myreEvents.forEach(w=>mycal2.addEvent(w));
}else{
  
    var events = mycal.getEvents();
    var events2 = mycal2.getEvents();

    for (var i = 0; i < events.length; i++) {
      if (events[i].id === 're') {
        events[i].remove();
        events2[i].remove();
      }

}
}
 }
})


  checkregisternoeat.addEventListener('change',(e)=>{
      var checkv = e.target.checked
  
      if(myrneEvents != ''){
        if(checkv){
          myrneEvents.forEach(w=> mycal.addEvent(w))
          myrneEvents.forEach(w=> mycal2.addEvent(w))
      }else{

         
          var events = mycal.getEvents();
          var events2 = mycal2.getEvents();
       

for (var i = 0; i < events.length; i++) {
if (events[i].id === 'rne') {
events[i].remove();
events2[i].remove();
}
      }
      }
      }
     })


      checkunregistereat.addEventListener('change',(e)=>{
          var checkv = e.target.checked
      
          if(myueEvents != ''){
            if(checkv){
              
              myueEvents.forEach(w=> mycal.addEvent(w))
              myueEvents.forEach(w=> mycal2.addEvent(w))
          }else{
            

              var events = mycal.getEvents();
              var events2 = mycal2.getEvents();

for (var i = 0; i < events.length; i++) {
if (events[i].id === 'ue') {
events[i].remove();
events2[i].remove();
}
}
          }
          }
          })


  }


