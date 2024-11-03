import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Paper, Typography, Button } from "@mui/material";
import DoneIcon from '@mui/icons-material/Done';
import CloseIcon from '@mui/icons-material/Close';
import userService from "../services/user.service";
import documentService from "../services/document.service"; 

const UserProfile = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [isSavingUploaded, setIsSavingUploaded] = useState(false);
  const [isWorksheetUploaded, setIsWorksheetUploaded] = useState(false);
  const navigate = useNavigate();

  const fetchUser = () => {
    userService
      .findById(id)
      .then((response) => {
        console.log("Detalles del usuario:", response.data);
        setUser(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener los detalles del usuario:", error);
      });
  };

  const checkUploadedDocuments = (id) => {
    documentService.isSavingUploaded(id)
      .then((response) => {
        setIsSavingUploaded(response.data); // Asume que response.data es un booleano
        console.log("El archivo Saving Account está subido:", response.data);
      })
      .catch((error) => {
        console.error("Error al verificar el archivo Saving Account:", error);
      });

    documentService.isWorksheetUploaded(id)
      .then((response) => {
        setIsWorksheetUploaded(response.data); // Asume que response.data es un booleano
        console.log("El archivo Worksheet está subido:", response.data);
      })
      .catch((error) => {
        console.error("Error al verificar el archivo Worksheet:", error);
      });
  };

  useEffect(() => {
    fetchUser();
    checkUploadedDocuments(id);
  }, [id]);

  if (!user) {
    return <div>Cargando...</div>;
  }

  const handleAcceptRequest = async () => {
    try {
      await userService.verifyCostumer(user.rut);
      navigate(`/UserList`);
    } catch (error) {
      console.error("Error al aceptar la solicitud:", error);
    }
  };

  const handleDeclineRequest = () => {
    const confirmDelete = window.confirm("¿Está seguro que desea borrar este usuario?");
    if (confirmDelete) {
      userService
        .deleteById(id)
        .then((response) => {
          console.log("Usuario ha sido eliminado.", response.data);
          navigate(`/accountrequest`);
        })
        .catch((error) => {
          console.log("Se ha producido un error al intentar eliminar al usuario", error);
        });
    }
  };

  const handleDownloadDocument = (filename) => {
    documentService.downloadFile(id, filename)
      .then((response) => {
        const blob = new Blob([response.data], { type: 'application/pdf' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      })
      .catch((error) => {
        console.error("Error al descargar el archivo", error);
      });
  };

  return (
    <Paper style={{ padding: "20px" }}>
      <Typography variant="h4" gutterBottom>
        Perfil del Usuario
      </Typography>
      <Typography variant="h6">RUT: {user.rut}</Typography>
      <Typography variant="h6">Nombre: {user.firstName}</Typography>
      <Typography variant="h6">Email: {user.email}</Typography>
      <Typography variant="h6">Teléfono: {user.phone}</Typography>

      <div style={{ marginTop: "40px" }}>
        {isSavingUploaded ? (
          <Button
            variant="contained"
            color="secondary"
            size="large"
            onClick={() => handleDownloadDocument("savingAccountFile")}
            style={{ marginRight: "10px" }}>
            Download Saving Account
          </Button>
        ) : (
          <Typography variant="body1">Saving Account doc is not uploaded.</Typography>
        )}

        {isWorksheetUploaded ? (
          <Button
            variant="contained"
            color="secondary"
            size="large"
            onClick={() => handleDownloadDocument("worksheetFile")}>
            Download Worksheet File
          </Button>
        ) : (
          <Typography variant="body1">Worksheet doc is not uploaded.</Typography>
        )}
      </div>

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

export default UserProfile;
