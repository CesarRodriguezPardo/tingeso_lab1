import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CreditService from "../services/credit.service"; // Importa el servicio de crédito
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";

// Mapeo de estado a descripciones
const statusDescriptions = {
  1: "Revisión inicial",
  2: "Pendiente de documentación",
  3: "En evaluación",
  4: "Pre aprobada",
  5: "En aprobación final",
  6: "Aprobada",
  7: "Rechazada",
  8: "Cancelada por cliente",
  9: "En desembolso"
};

const AllCreditList = () => {
  const [credits, setCredits] = useState([]);
  const [rejectedReasons, setRejectedReasons] = useState({});  // Estado para almacenar las razones de rechazo
  const navigate = useNavigate();

  const init = () => {
    CreditService
      .getAll() // Método para obtener todos los créditos
      .then((response) => {
        console.log("Mostrando listado de todos los créditos.", response.data);
        setCredits(response.data);

        // Obtener las razones de rechazo para cada crédito
        response.data.forEach(credit => {
          if (credit.status === 7) {  // Solo obtener la razón si el crédito está rechazado
            CreditService.rejectedReason(credit.id)
              .then((res) => {
                setRejectedReasons(prevState => ({
                  ...prevState,
                  [credit.id]: res.data // Guardamos la razón con el ID del crédito
                }));
              })
              .catch((error) => {
                console.log("Error al obtener la razón de rechazo.", error);
              });
          }
        });
      })
      .catch((error) => {
        console.log("Se ha producido un error al intentar mostrar listado de créditos.", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <TableContainer component={Paper}>
      <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Credit ID
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Amount
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Term
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Interest
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Status
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Description
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Action
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {credits.map((credit) => (
            <TableRow
              key={credit.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="left">{credit.id}</TableCell>
              <TableCell align="left">{credit.requestedAmount}</TableCell>
              <TableCell align="left">{credit.requestedTerm}</TableCell>
              <TableCell align="left">{credit.interestRate}</TableCell>
              <TableCell align="right">
                {statusDescriptions[Number(credit.status)] || "Desconocido"} {/* Muestra la descripción del estado */}
              </TableCell>
              <TableCell align="right">
                {credit.status === 7 && rejectedReasons[credit.id] ? (
                  rejectedReasons[credit.id]  // Muestra la razón de rechazo si está disponible
                ) : (
                  "N/A"
                )}
              </TableCell>
              <TableCell align="right">
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => navigate(`/creditdetail/${credit.id}`)}>
                  View Details
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default AllCreditList;
