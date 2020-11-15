import Axios from "axios";

// const API_KEY = '67bbdcc6-25de-40b7-8d0f-965181c30c8f';
// const ROOT_URL = 'http://api.intellexer.com';
// const clusterizeEndpoint = '/clusterize?apikey=';

//http://api.intellexer.com/[GET/POST method]?apikey={YourAPIKey}&options={options}

// 'http://api.intellexer.com/clusterize?apikey=67bbdcc6-25de-40b7-8d0f-965181c30c8f'



// var settings = {
//     "async": true,
//     "crossDomain": true,
//     "url": "http://api.intellexer.com/summarize?apikey=YourAPIKey&url=YourUrl&loadConceptsTree=true&loadNamedEntityTree=true&summaryRestriction=7&returnedTopicsCount=2&usePercentRestriction=true&conceptsRestriction=7&structure=general&fullTextTrees=true&textStreamLength=1000&useCache=false&wrapConcepts=false&options=%7B%7D",
//     "method": "POST",
//     "headers": {
//       "content-type": "application/json",
//       "cache-control": "no-cache"
//     },
//     "processData": false,
//     "data": "{}"
//   }

function clusterize() {
  const url = "https://api.intellexer.com/clusterizeText?apikey=67bbdcc6-25de-40b7-8d0f-965181c30c8f&url='https://api.intellexer.com/clusterizeText'&loadConceptsTree=true&loadNamedEntityTree=true&summaryRestriction=7&returnedTopicsCount=2&usePercentRestriction=true&conceptsRestriction=7&structure=general&fullTextTrees=true&textStreamLength=1000&useCache=false&wrapConcepts=false&options=%7B%7D";

  const options = {
    method: 'POST',
    mode: 'no-cors',
    body: '{kjskjhdskfhskdjfhskjdhfksjdhfksdjhfksdjhfksjdhfksdjfh}',
    headers:
      { 'cache-control': 'no-cache' }
  };

  const request = new Request(url, options);

  fetch(request).then(function (response) {
    return response;
  }).then(function (response) {
    console.log(`Response ${response}`);
    response.json().then(json => {
      console.log(`Response json ${json}`);
    }
    );
  }).catch(function (e) {
    console.log(e);
  });
  // return Axios({
  //     method: 'post',
  //     url: "https://api.intellexer.com/summarize?apikey=67bbdcc6-25de-40b7-8d0f-965181c30c8f&url='https://api.intellexer.com/summarize?apikey=67bbdcc6-25de-40b7-8d0f-965181c30c8f'&loadConceptsTree=true&loadNamedEntityTree=true&summaryRestriction=7&returnedTopicsCount=2&usePercentRestriction=true&conceptsRestriction=7&structure=general&fullTextTrees=true&textStreamLength=1000&useCache=false&wrapConcepts=false&options=%7B%7D",
  //     data: "{'LKHJ  lklkj summary'}",
  //     async: true,
  //     crossDomain: true,
  //     mode: 'no-cors',
  //     headers: {
  //       'Access-Control-Allow-Origin': '*',
  //       'Content-Type': 'application/json',
  //     },
  //     withCredentials: true,
  //     credentials: 'same-origin',
  //     processData: false
  // });
}

export { clusterize };