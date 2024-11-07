import httpClient from "../http-common";

const saveApply = (credit) => {
    return httpClient.post('/api/v1/credit/saveCredit', credit); 
};

const findByRut = (rut) => {
    return httpClient.get(`/api/v1/credit/findByRut/${rut}`);
};

const getAll = () => {
    return httpClient.get(`/api/v1/credit/getAll`);
};

const getById = (id) => {
    return httpClient.get(`/api/v1/credit/findById/${id}`);
};

const setStatus = (id, status) => {
    return httpClient.post(`/api/v1/credit/setStatus/${id}/${status}`);
};

const setType = (id, type) => {
    return httpClient.post(`/api/v1/credit/setType/${id}/${type}`);
};

const rejectedReason = (id) => {
    return httpClient.get(`/api/v1/credit/getRejectedReason/${id}`);
};

export default { saveApply , findByRut , getAll , getById , setStatus , setType , rejectedReason}