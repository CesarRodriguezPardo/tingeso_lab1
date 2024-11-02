import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/v1/customer/getAll');
}

const findById = (id) => {
    return httpClient.get(`/api/v1/customer/findById/${id}`);
};

const findByVerified = (verified) => {
    return httpClient.get(`/api/v1/customer/findByVerified/${verified}`);
};

const login = (email, password) => {
    return httpClient.post(`/api/v1/customer/login/${email}/${password}`)
};

const saveApply = (customer) => {
    return httpClient.post('/api/v1/customer/saveApply', customer); 
}


export default { getAll , findById , findByVerified , login}