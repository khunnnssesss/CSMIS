async function fetchdatabackend(url){

    try {
        const req = await fetch(url);
        return [req,null];
    } catch (error) {
        console.log('error sth went wrong!!!');
        return [null,req];
    }

}

async function getData(doorlog,monthd){

    const [weeklydata,werror] = await fetchdatabackend("/user/getuserhistoryMonthly?doorlog="+doorlog+"&monthdate="+monthd);
    if(werror) {

    }
}