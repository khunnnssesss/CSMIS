

async function getAlldatelistforAemp(userid) {

    try {
        var resp = await fetch('/getallDailyEatView?userId='+userid)
console.log('in get all data list funciton',resp);

        if(!resp.ok) {
            throw new Error({
            message: 'something went wrong!!',
            cause: resp
        })
    }
else{
        var userdata= resp.json();
console.log('in get all data list funciton else clause',userdata);
        return userdata;
}
    } catch (error) {
        console.log(error.message, error.cause);
    }

  
  }

  
export default getAlldatelistforAemp;