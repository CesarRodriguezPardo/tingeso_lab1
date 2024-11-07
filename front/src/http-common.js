import axios from "axios";

//const backendServer = "104.41.56.72";
//const backendPort = import.meta.env.VITE_BACKEND_PORT;

//const backendServer = import.meta.env.VITE_BACKEND_SERVER;
//const backendPort = import.meta.env.VITE_BACKEND_PORT;

//console.log(backendServer, backendPort);

export default axios.create({
    baseURL: `http://localhost:8090`,
    headers: {
        'Content-Type': 'application/json'
    }
});