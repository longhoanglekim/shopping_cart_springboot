async function getName(token) {
    try {
        const response = await fetch('/api/jwt/getName', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
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
