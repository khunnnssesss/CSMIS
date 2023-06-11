
var timecur = moment().format('h')
var daycur = moment().format('dddd')
var weekcurrents = getCurrentWeek();
var datecurrent = moment().format('Y-MM-DD')
const startWeekOfMonth = moment().startOf('month').week();
const endWeekOfMonth = moment().endOf('month').week();
var curweekw = moment(datecurrent).week()
var weekwcur = moment().week()
var iscurwek = curweekw == weekwcur
var getfirstandlastweek = firstAndLastWeek()





// If Condition True change Current Week to Next Week
if(((timecur >= 2 && daycur =='Friday') || daycur =='Saturday') && iscurwek)  weekcurrents= getCurrentWeek(true)
var curdatesdates = [...weekcurrents.curdays,...weekcurrents.nextdays]


//check is it last week
var isLastWeek = checkIsLastWeek(curdatesdates,getfirstandlastweek.lastweek)


// get Calendar and holiday dates
async function showCalendar(mainobj) {
  console.log('is it last week!! : ',isLastWeek);
  console.log('all cur days dates :: ',curdatesdates);
  console.log('first and last week ',getfirstandlastweek);
  console.log('week currents :: ',weekcurrents);
  console.log('user data object :: ',mainobj.userdataobj);
  var {getByid,dateclickfunc,empdatelists,calheader,dataBtnCustom} = mainobj;

 var holidays = await fetch("/getAllHolidays").then(resp => resp.json()).then(result => result)
 var holidayDates = holidays.map(w => w.date);
 var holidayevents = holidays.map(w=>{
  return {
    title: w.holidays,
    start: w.date
  }
 });


      var cal = new FullCalendar.Calendar(getByid, {
  
  fixedWeekCount: false,
  headerToolbar: calheader,
  dayCellClassNames :(data)=>{
    var celldate = moment(data.date).format('Y-MM-DD')

    var empdateobj ={
      celldata: celldate,
      dateobj: data,
      holidaydata: holidayDates,
      registerdatelist: empdatelists
    }
return addClassCol(empdateobj)  
  },
  dateClick: function (data){
    console.log('test test',this);
    dateclickfunc(data,holidayDates,weekcurrents)
  },
  customButtons: dataBtnCustom
      })

      holidayevents.forEach(w=> cal.addEvent(w))
      
      if(isLastWeek) cal.next()
      
return cal
  }


  // ADD BACKGROUND COLOR TO DATES
  function addClassCol(userdateobj) {

    var {dateobj,celldata,holidaydata,registerdatelist} = userdateobj;

    var getday=moment(dateobj.date).format('Y-MM-DD')
    var wdate = moment(dateobj.date).format('dddd')
    
      
      if(holidaydata.includes(getday))    return 'holidays'

    
    
    if(wdate=='Sunday' || wdate=='Saturday') return "weekends"

    if(weekcurrents.curdays.includes(getday)) return 'curweek'
    else if(weekcurrents.nextdays.includes(getday)) return 'nextweek'
   
    if(dateobj.isPast) return "pastdays"

    if(registerdatelist.includes(getday)) return 'registerDate'
  
    }


  //  Get current week and if last week, change Current Week to Next Week
    function getCurrentWeek(nextw=false) {

  var currentDate = moment()
  var weekStart = currentDate.clone().startOf('Week')
    var nextweekStart = moment().add(1,'week')
  var curdays = [];
  var nextdays =[];

  for (var i = 0; i <= 6; i++) {
    curdays.push(moment(weekStart).day(i).format("Y-MM-DD"));
  }

  if(nextw) {
    for (var i = 0; i <= 6; i++) {
      nextdays.push(moment(nextweekStart).day(i).format("Y-MM-DD"));
    }
  }
  return {curdays,nextdays};
}




function firstAndLastWeek(){
var firstweek = []
var lastweek = []

var firstWeek = moment().startOf('month').startOf('isoWeek');
for (var i = 0; i <= 6; i++) {
  firstweek.push(moment(firstWeek).day(i).format("Y-MM-DD"));
}
var lastWeek = moment().endOf('month').startOf('isoWeek');
for (var i = 0; i <= 6; i++) {
  lastweek.push(moment(lastWeek).day(i).format("Y-MM-DD"));
}

return {firstweek,lastweek}

}
 
function checkIsLastWeek(curwe,lastwe){

  return lastwe.every(w=> curwe.includes(w))
}

export default showCalendar;