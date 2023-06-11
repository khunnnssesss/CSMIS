
export default function getUserObj(userid){

    return fetchUser(userid).then(res => res);
}

async function fetchUser(user) {

    var response = await fetch('/getAUser/'+user).then(resp => resp.json());

    return response;

  }