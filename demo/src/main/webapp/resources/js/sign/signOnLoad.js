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
    Auth.signOut();
}