import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Paper, Typography, Button } from "@mui/material";
import DoneIcon from '@mui/icons-material/Done';
import CloseIcon from '@mui/icons-material/Close';
import employeeService from "../services/employee.service";

const Profile = () => {
  const [customer, setCustomer] = useState(null);
  const navigate = useNavigate();

  // Obtener los detalles del cliente desde localStorage
  const fetchCustomerFromLocalStorage = () => {
    const customerData = localStorage.getItem("customer"); 
    if (customerData) {
      setCustomer(JSON.parse(customerData)); // Parsear los datos del cliente
    } else {
      console.error("No hay datos de cliente en el localStorage.");
    }
  };

  useEffect(() => {
    fetchCustomerFromLocalStorage();
  }, []); // Solo se ejecuta al montar el componente

  if (!customer) {
    return <div>Cargando...</div>; // Puedes personalizar esto
  }

  const handleAcceptRequest = async () => {
    try {
      await employeeService.verifyCostumer(customer.rut);
      navigate(`/accountrequest`);
    } catch (error) {
      console.error("Error al aceptar la solicitud:", error);
    }
  };

  const handleDeclineRequest = () => {
    const confirmDelete = window.confirm(
      "¿Esta seguro que desea borrar este empleado?"
    );
    if (confirmDelete) {
      employeeService
        .deleteById(customer.id) // Usar el ID del cliente desde localStorage
        .then((response) => {
          console.log("Empleado ha sido eliminado.", response.data);
          navigate(`/accountrequest`);
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar al empleado",
            error
          );
        });
    }
  };

  return (
    <Paper style={{ padding: "20px" }}>
      <Typography variant="h4" gutterBottom>
        Perfil del Cliente
      </Typography>
      <Typography variant="h6">Rut: {customer.rut}</Typography>
      <Typography variant="h6">Nombre: {customer.firstName}</Typography>
      <Typography variant="h6">Email: {customer.email}</Typography>
      <Typography variant="h6">Teléfono: {customer.phone}</Typography>

      <Button
        variant="contained"
        color="primary"
        size="large"
        startIcon={<DoneIcon />}
        onClick={handleAcceptRequest}
        style={{ marginTop: "20px", marginRight: "40px" }}>
        Accept request
      </Button>

      <Button
        variant="contained"
        color="error"
        size="large"
        startIcon={<CloseIcon />}
        onClick={handleDeclineRequest}
        style={{ marginTop: "20px" }}>
        Decline request
      </Button>
    </Paper>
  );
};

export default Profile;
