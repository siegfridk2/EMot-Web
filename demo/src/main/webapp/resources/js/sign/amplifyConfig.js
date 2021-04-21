let CognitoRegion = 'ap-northeast-1';
let CognitoUserPool = 'ap-northeast-1_TM4RRXTSY';
let CognitoUserPoolClient = '5ee2muulcbjo7nns0lgpjcgqd0';

let CognitoDomainPrefix = 'dev-maasjapan';

let amplifyConfig = {
    Auth: {
        region: CognitoRegion,
        userPoolId: CognitoUserPool,
        userPoolWebClientId : CognitoUserPoolClient,
        oauth: {
            domain: `${CognitoDomainPrefix}.auth.${CognitoRegion}.amazoncognito.com`,
            /*scope: ['openid'],*/
            redirectSignIn: 'http://localhost:3000/auth/index',
            redirectSignOut: 'http://localhost:3000/auth/index',
            responseType: 'code'
        }
    }
};