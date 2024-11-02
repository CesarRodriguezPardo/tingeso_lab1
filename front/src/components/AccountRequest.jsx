import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import customerService from "../services/customer.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import TaskAltIcon from '@mui/icons-material/TaskAlt';

const CustomerList = () => {
  const [customers, setCustomers] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    customerService
      .findByVerified(false)
      .then((response) => {
        console.log("Mostrando listado de todos los empleados.", response.data);
        setCustomers(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los empleados.",
          error
        );
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
              Rut
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              First Name
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Email
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Phone
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Action
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {customers.map((customer) => (
            <TableRow
              key={customer.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="left">{customer.rut}</TableCell>
              <TableCell align="left">{customer.firstName}</TableCell>
              <TableCell align="right">{customer.email}</TableCell>
              <TableCell align="right">{customer.phone}</TableCell>
              <TableCell align="right">
      
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  startIcon={<TaskAltIcon />}
                  onClick={() => navigate(`/customer/customerprofile/${customer.id}`)}>
                  Check Data
                </Button>

              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default CustomerList;