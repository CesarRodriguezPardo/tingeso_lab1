import httpClient from "../http-common";

const deleteById = (id) => {
    return httpClient.delete(`/api/v1/employee/deleteById/${id}`);
};

const verifyCostumer = (rut) => {
    return httpClient.post(`/api/v1/employee/verifyCostumer/${rut}`);
};

export default { deleteById , verifyCostumer }