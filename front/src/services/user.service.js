import httpClient from "../http-common";

const deleteById = (id) => {
    return httpClient.delete(`/api/v1/user/deleteById/${id}`);
};

const verifyCostumer = (rut) => {
    return httpClient.post(`/api/v1/user/verifyCostumer/${rut}`);
};

const getAll = () => {
    return httpClient.get('/api/v1/user/getAll');
}

const findById = (id) => {
    return httpClient.get(`/api/v1/user/findById/${id}`);
};

const findByVerified = (verified) => {
    return httpClient.get(`/api/v1/user/findByVerified/${verified}`);
};

const login = (email, password) => {
    return httpClient.post(`/api/v1/user/login/${email}/${password}`)
};

const saveApply = (customer) => {
    return httpClient.post('/api/v1/user/saveApply', customer); 
}

const getIdByRut = (rut) => {
    return httpClient.get(`/api/v1/user/getIdByRut/${rut}`);
};

const setDicom = (rut, dicom) => {
    return httpClient.post(`/api/v1/user/setDicom/${rut}/${dicom}`);
};

const setTotalDebts = (rut, totalDebts) => {
    return httpClient.post(`/api/v1/user/setTotalDebts/${rut}/${totalDebts}`);
};

const setIndependentJob = (rut, independtJob) => {
    return httpClient.post(`/api/v1/user/setIndependentJob/${rut}/${independtJob}`);
};

const setJobSeniority = (rut, jobSeniority) => {
    return httpClient.post(`/api/v1/user/setJobSeniority/${rut}/${jobSeniority}`);
};

const setSalary = (rut, salary) => {
    return httpClient.post(`/api/v1/user/setSalary/${rut}/${salary}`);
};

const setIndependentEvaluate = (rut, evaluate) => {
    return httpClient.post(`/api/v1/user/setIndependentEvaluate/${rut}/${evaluate}`);
};

const setLatePayment = (rut, latePayment) => {
    return httpClient.post(`/api/v1/user/setLatePayment/${rut}/${latePayment}`);
};

const setSeniority = (rut, seniority) => {
    return httpClient.post(`/api/v1/user/setSeniority/${rut}/${seniority}`);
};

const setBalance = (rut, balance) => {
    return httpClient.post(`/api/v1/user/setBalance/${rut}/${balance}`);
};


const setRetiredCash = (rut, retiredCash) => {
    return httpClient.post(`/api/v1/user/setRetiredCash/${rut}/${retiredCash}`);
};

const setPeriodicDeposit = (rut, periodicDeposit) => {
    return httpClient.post(`/api/v1/user/setPeriodicDeposit/${rut}/${periodicDeposit}`);
};

const setRecentlyRetiredCash = (rut, recentlyRetired) => {
    return httpClient.post(`/api/v1/user/setRecentlyRetiredCash/${rut}/${recentlyRetired}`);
};

export default { deleteById , verifyCostumer , 
    getAll , findById , 
    findByVerified , login , 
    saveApply, getIdByRut , 
    setDicom , setTotalDebts , 
    setIndependentJob , setJobSeniority , 
    setSalary , setIndependentEvaluate , 
    setLatePayment , setSeniority , setBalance, 
    setRetiredCash , setPeriodicDeposit , setRecentlyRetiredCash }