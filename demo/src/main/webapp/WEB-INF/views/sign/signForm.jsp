<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/sign/amplifyConfig.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/sign/aws-amplify.min.js"></script>

</head>
<body onload="onLoad()">

	<div align="center">
	    <div>
	        Cognito User Pools sample with external IDP
	    </div>
	    <div>
	        <a id="signInButton" href="javascript:void(0)" style="display:block;" title="Sign in">Sign In / Sign Up</a>
	    </div>
	    <div>
	        <a id="signOutButton" href="javascript:void(0)" style="display:none;" title="Sign Out">Sign Out</a>
	    </div>
	    <div><pre id="sessionInfo"></pre></div>
	</div>
	


<script>
function onLoad() {
    let Amplify = window['aws_amplify'].Amplify;
    let Auth = window['aws_amplify'].Auth;

    Amplify.configure(amplifyConfig);

    document.getElementById("signInButton").addEventListener("click", function() {
        userSingInButton(Auth);
    });

    document.getElementById("signOutButton").addEventListener("click", function() {
        userSingOutButton(Auth);
    });

    Auth.currentSession().then(session => {
        document.getElementById("signInButton").style.display = 'none';
        document.getElementById("signOutButton").style.display = 'block';
        document.getElementById('sessionInfo').innerText = JSON.stringify(session, null, 2);
    }).catch(e => {
        console.log(e);
    });
}

function userSingInButton(Auth) {
    Auth.federatedSignIn();
}

function userSingOutButton(Auth) {
    document.getElementById("signInButton").style.display = 'block';
    document.getElementById("signOutButton").style.display = 'none';
    sessionStorage.clear();
    Auth.signOut();
}
</script>
</body>
</html>