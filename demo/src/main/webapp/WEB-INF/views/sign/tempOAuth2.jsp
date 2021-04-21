<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body onload="onLoad()">
<div>
    <div>
        Cognito User Pools sample with external IDP
    </div>
    <div>
        id token <input type="text" id="idTokenBox"></input><button id="copyIdToken">Copy</button>
    </div>
    <div>
        access token <input type="text" id="accessTokenBox"></input><button id="copyAccessToken">Copy</button>
    </div>
    <div>
        cognito:username <input type="text" id="cognitousernameBox"></input><button id="">Copy</button>
    </div>
    <div>
        email: <span id="emailText"></span>
    </div>
    <div>
        MaaS Japan ID: <span id="MJIDText"></span>
    </div>
    <div>
        aliasID: <span id="MJAliasText"></span>
    </div>
    <div>
        <a id="signOutButton" href="javascript:void(0)" title="Sign Out">로그아웃</a>
    </div>
    <div>
        <a href="/signForm" title="Sign Out">탑으로</a>
    </div>
    <div>
        <pre id="sessionInfo"></pre>
    </div>
</div>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/sign/amplifyConfig.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/sign/aws-amplify.min.js"></script>
<script>
    async function onLoad() {
        let Amplify = window['aws_amplify'].Amplify;
        let Auth = window['aws_amplify'].Auth;


        Amplify.configure(amplifyConfig);

        /* document.getElementById('copyIdToken').addEventListener("click", {textBoxid: 'idTokenBox', handleEvent: copy});
        document.getElementById('copyAccessToken').addEventListener("click", {textBoxid: 'accessTokenBox', handleEvent: copy});*/
        document.getElementById("signOutButton").addEventListener("click", function() {userSingOutButton(Auth);}); 

        await Auth.currentSession().then(session => {
            /* document.getElementById('idTokenBox').value = session.idToken.jwtToken;
            document.getElementById('accessTokenBox').value = session.accessToken.jwtToken; */
            /* document.getElementById('cognitousernameBox').value = session.idToken.cognito:username; */
            window.location.href = 'http://localhost:3000/authorized?idToken='+session.idToken.jwtToken+'&accessToken='+session.accessToken.jwtToken;
            
            /* document.getElementById('emailText').innerHTML = session.idToken.payload.email;
            document.getElementById('MJIDText').innerHTML = session.idToken.payload.sub;
            document.getElementById('MJAliasText').innerHTML = session.idToken.payload['mjp:alias_id'];
            document.getElementById('sessionInfo').innerText = JSON.stringify(session, null, 2); */
        }).catch(e => {
            console.log(e);
        });

        /* alert(idToken);
        alert(accessToken); */
        
    }

    function userSingOutButton(Auth) {
        Auth.signOut();
    }
    
    /* function copy(e) {
        var copyText = document.getElementById(this.textBoxid);
        copyText.select();
        document.execCommand("copy");
    } */
</script>
</body>
</html>