import httpClient from "../http-common";

const calculatePayment = (p, amount, n) => {
    return httpClient.get(`/api/v1/calculate/calculatePayment/${p}/${amount}/${n}`);
};

const calculateInsurance = (amount, n) => {
    return httpClient.get(`/api/v1/calculate/calculateInsurance/${amount}/${n}`);
};

const calculateAdministrationCost = (amount) => {
    return httpClient.get(`/api/v1/calculate/calculateAdministrationCost/${amount}`);
};

const calculateTotal = (m, n, insurance, administration) => {
    return httpClient.get(`/api/v1/calculate/calculateTotal/${m}/${n}/${insurance}/${administration}`);
};

export default { calculatePayment , calculateInsurance , calculateAdministrationCost , calculateTotal }