import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import HomeIcon from "@mui/icons-material/Home";
import { useNavigate } from "react-router-dom";

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
    >
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/user/UserList")}>
          <ListItemIcon>
            <PeopleAltIcon />
          </ListItemIcon>
          <ListItemText primary="Account Request" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/user/verifieduserlist")}>
          <ListItemIcon>
            <PeopleAltIcon />
          </ListItemIcon>
          <ListItemText primary="Verified Accounts" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/allcreditlist")}>
          <ListItemIcon>
            <AttachMoneyIcon />
          </ListItemIcon>
          <ListItemText primary="Requested Credits" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/loanapplication")}>
          <ListItemIcon>
            <AttachMoneyIcon />
          </ListItemIcon>
          <ListItemText primary="Simulate Credit" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/loanrequest")}>
          <ListItemIcon>
            <AttachMoneyIcon />
          </ListItemIcon>
          <ListItemText primary="Request Credit" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/creditlist")}>
          <ListItemIcon>
            <AttachMoneyIcon />
          </ListItemIcon>
          <ListItemText primary="My requested Credits" />
        </ListItemButton>

      </List>
    </Box>
  );

  return (
    <div>
      <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
        {listOptions()}
      </Drawer>
    </div>
  );
}
