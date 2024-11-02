import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Paper, Typography, Button } from "@mui/material";
import DoneIcon from '@mui/icons-material/Done';
import CloseIcon from '@mui/icons-material/Close';
import customerService from "../services/customer.service";
import employeeService from "../services/employee.service";

const CustomerProfile = () => {
  const { id } = useParams(); // Obtener el id del cliente de la URL
  const [customer, setCustomer] = useState(null);
  const navigate = useNavigate();

  const fetchCustomer = () => {
    customerService
      .findById(id) // Método que debes implementar en tu servicio para obtener un cliente por ID
      .then((response) => {
        console.log("Detalles del cliente:", response.data);
        setCustomer(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener los detalles del cliente:", error);
      });
  };

  useEffect(() => {
    fetchCustomer();
  }, [id]);

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
  }

  const handleDeclineRequest = () => {
    console.log("Printing id", id);
    const confirmDelete = window.confirm(
      "¿Esta seguro que desea borrar este empleado?"
    );
    if (confirmDelete) {
      employeeService
        .deleteById(id)
        .then((response) => {
          console.log("empleado ha sido eliminado.", response.data);
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

export default CustomerProfile;
