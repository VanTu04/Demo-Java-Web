import Home from "../pages/user/Home";
import Login from "../pages/auth/Login";
import Signup from "../pages/auth/Signup";
import Reservation from "../pages/user/Reservation";
import ManageFood from "../pages/admin/ManageFood";
import { Route, Routes } from "react-router-dom";
import { PrivateRoute } from "./PrivateRoute";
import ManageCategories from "../pages/admin/ManageCategories";
import ManageReservation from "../pages/admin/ManageReservation";
import ManageOrders from "../pages/admin/ManageOrders";

const AppRoutes = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        <Route
          path="/Reservation"
          element={
            <PrivateRoute>
              <Reservation />
            </PrivateRoute>
          }
        />

        <Route
          path="/ManageFood"
          element={
            <PrivateRoute>
              <ManageFood />
            </PrivateRoute>
          }
        />

        <Route
          path="/ManageCategories"
          element={
            <PrivateRoute>
              <ManageCategories />
            </PrivateRoute>
          }
        />

        <Route
          path="/ManageReservation"
          element={
            <PrivateRoute>
              <ManageReservation />
            </PrivateRoute>
          }
        />

        <Route
          path="/ManageOrders"
          element={
            <PrivateRoute>
              <ManageOrders />
            </PrivateRoute>
          }
        />
      </Routes>
    </>
  );
};

export default AppRoutes;
