async function getName(token) {
    try {
        const response = await fetch('/api/jwt/getName', {
            method: 'GET',
        });

        const data = await response.json();

        if (data && data.username) {
            console.log("Get name :" + data.username);
            return data.username;
        } else {
            console.error('No username found in response');
            return null;
        }
    } catch (error) {
        console.error('Error fetching user info:', error);
        return null;
    }
}
async function getToken() {
    try {
        const response = await fetch('/api/jwt/getToken', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();

        if (data && data.token) {
            return data.token;
        } else {
            console.error('No token found in response');
            return null;
        }
    } catch (error) {
        console.error('Error fetching token:', error);
        return null;
    }
}