import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import CreditService from "../services/credit.service";
import userService from "../services/user.service"; 
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import documentService from "../services/document.service"; 

const CreditDetail = () => {
  const { id } = useParams();
  const [credit, setCredit] = useState(null);
  const [userId, setUserId] = useState(null);
  const [status, setStatus] = useState(credit?.status || null); // Estado para el status
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCreditDetails = async () => {
      try {
        const response = await CreditService.getById(id);
        setCredit(response.data);
        setStatus(response.data.status); // Inicializa el estado con el status actual
        const userResponse = await userService.getIdByRut(response.data.rut);
        setUserId(userResponse);

      } catch (error) {
        console.error("Error al obtener los detalles del crédito:", error);
      }
    };

    fetchCreditDetails();
  }, [id]);

  const handleDownloadDocument = (fileType) => {
    if (!userId.data) {
      console.error("El ID del usuario no está disponible");
      return;
    }

    documentService.downloadFile(userId.data, fileType)
      .then((response) => {
        const blob = new Blob([response.data], { type: 'application/pdf' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', fileType);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      })
      .catch((error) => {
        console.error("Error al descargar el archivo", error);
      });
  };

  const handleStatusChange = async () => {
    if (status) {
      try {
        await CreditService.setStatus(id, status);
        alert("Estado actualizado correctamente");
      } catch (error) {
        console.error("Error al actualizar el estado:", error);
      }
    } else {
      alert("Por favor, selecciona un estado.");
    }
  };

  if (!credit) {
    return <Typography variant="h6">Cargando detalles del crédito...</Typography>;
  }

  // Nombres de archivos según el tipo de crédito
  const fileNamesByType = {
    1: ["incomeFile", "appraisalFile", "creditHistoryFile"],
    2: ["incomeFile", "firstHomeFile", "creditHistoryFile"],
    3: ["financialStatement", "appraisalFile", "incomeFile", "businessPlanFile"],
    4: ["incomeFile", "remodelingBudgetFile", "updatedAppraisalFile"]
  };

  const fileTypesToDownload = fileNamesByType[credit.type] || []; // Obtiene los nombres de archivo según el tipo

  // Opciones de status
  const statusOptions = [
    { value: 2, label: "Pendiente de documentación" },
    { value: 3, label: "En evaluación" },
    { value: 4, label: "Pre aprobada" },
    { value: 5, label: "En aprobación final" },
    { value: 6, label: "Aprobada" },
    { value: 7, label: "Rechazada" },
    { value: 8, label: "Cancelada por cliente" },
    { value: 9, label: "En desembolso" }
  ];

  return (
    <Box sx={{ padding: 3 }}>
      <Paper elevation={3} sx={{ padding: 2 }}>
        <Typography variant="h5">Detalles del Crédito</Typography>
        <Typography variant="subtitle1"><strong>ID del Crédito:</strong> {credit.id}</Typography>
        <Typography variant="subtitle1"><strong>Monto Solicitado:</strong> {credit.requestedAmount}</Typography>
        <Typography variant="subtitle1"><strong>Término:</strong> {credit.requestedTerm} meses</Typography>
        <Typography variant="subtitle1"><strong>Tasa de Interés:</strong> {credit.interestRate}%</Typography>
        <Typography variant="subtitle1"><strong>Tipo de Crédito:</strong> {credit.type}</Typography>
        <Typography variant="subtitle1"><strong>Estado:</strong> {credit.status}</Typography>

        <div style={{ marginTop: "20px" }}>
          <select value={status} onChange={(e) => setStatus(e.target.value)} style={{ marginRight: '10px' }}>
            <option value="" disabled>Selecciona un estado</option>
            {statusOptions.map(option => (
              <option key={option.value} value={option.value}>{option.label}</option>
            ))}
          </select>
          <Button
            variant="contained"
            color="primary"
            onClick={handleStatusChange}
          >
            Cambiar Estado
          </Button>
        </div>

        <div style={{ marginTop: "20px" }}>
          {fileTypesToDownload.map((fileType) => (
            <Button
              key={fileType}
              variant="contained"
              color="secondary"
              onClick={() => handleDownloadDocument(fileType)}
              sx={{ marginRight: 1 }}
            >
              Descargar {fileType}
            </Button>
          ))}
        </div>

        <Button
          variant="contained"
          color="info"
          onClick={() => navigate("/allcreditlist")}
          sx={{ marginTop: 2 }}
        >
          Volver a la lista
        </Button>
      </Paper>
    </Box>
  );
};

export default CreditDetail;
