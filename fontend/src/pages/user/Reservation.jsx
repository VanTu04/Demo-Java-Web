import React, { useState, useEffect } from "react";
import "./Reservation.scss";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import {
  fetchReservation,
  createReservation,
  cancelReservation,
} from "../../services/ApiService"; // Thêm hàm cancelReservation

const Reservation = () => {
  const [reservations, setReservations] = useState([]);
  const [formData, setFormData] = useState({
    customer_name: "",
    guest_count: "",
    reservation_time: "",
  });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(false);

  // Lấy customerId từ Redux store
  const { id, jwt } = useSelector((state) => state.auth);

  const fetchReservationsData = async () => {
    setLoading(true);
    try {
      const response = await fetchReservation(id, jwt); // API call
      setReservations(response.data);
    } catch (err) {
      toast.error("Error fetching reservations.");
    } finally {
      setLoading(false);
    }
  };
  // Fetch reservations when component loads or when id or jwt changes
  useEffect(() => {
    fetchReservationsData();
  }, [id, jwt]);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Submit form
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.guest_count < 2) {
      toast.error("Guest count must be at least 2.");
      return;
    }
    setLoading(true);

    const payload = { ...formData, customerId: id };

    try {
      const response = await createReservation(payload, jwt); // Gửi API tạo đặt bàn
      toast.success(response.data);

      fetchReservationsData();

      setFormData({
        customer_name: "",
        guest_count: "",
        reservation_time: "",
      });

      setIsModalOpen(false);
    } catch (err) {
      toast.error("Failed to create reservation. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  // Handle cancel reservation
  const handleCancelReservation = async (reservationId) => {
    setLoading(true);
    try {
      const res = await cancelReservation(reservationId, jwt); // Gọi API hủy đặt bàn
      toast.success(res.data);
      fetchReservationsData();
    } catch (error) {
      if (error.response.status === 400) {
        toast.error(error.response.data);
      } else {
        // Lỗi không phản hồi từ server
        toast.error(
          "Unable to connect to the server. Please check your internet connection."
        );
      }
    } finally {
      setLoading(false);
    }
  };

  // Handle view invoice
  const handleViewInvoice = (reservationId) => {
    // Giả sử bạn có chức năng để hiển thị hóa đơn cho đơn đặt hàng
    // Ví dụ: Mở modal hoặc chuyển hướng đến trang xem hóa đơn
    alert(`Viewing invoice for reservation ID: ${reservationId}`);
  };

  return (
    <div className="reservation-container">
      {/* Button to open modal */}
      <button
        className="btn-add-reservation"
        onClick={() => setIsModalOpen(true)}
      >
        Đặt Bàn
      </button>

      {/* Display Reservations */}
      <div className="reservation-list">
        <h2>Current Reservations</h2>
        {reservations.length > 0 ? (
          <table className="reservation-table">
            <thead>
              <tr>
                <th>Reservation Code</th>
                <th>Customer Name</th>
                <th>Guest Count</th>
                <th>Reservation Time</th>
                <th>Status</th>
                <th>Actions</th> {/* Thêm cột Actions */}
              </tr>
            </thead>
            <tbody>
              {reservations.map((res) => (
                <tr key={res.id}>
                  <td>{res.reservationCode}</td>
                  <td>{res.customerName}</td>
                  <td>{res.guestCount}</td>
                  <td>{new Date(res.reservationTime).toLocaleString()}</td>
                  <td>{res.status}</td>
                  <td>
                    {/* Cột chứa 2 nút */}
                    <button
                      className="btn-cancel"
                      onClick={() => handleCancelReservation(res.id)}
                    >
                      Hủy Đặt Hàng
                    </button>
                    <button
                      className="btn-view-invoice"
                      onClick={() => handleViewInvoice(res.id)}
                    >
                      Xem Hóa Đơn
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No reservations found.</p>
        )}
      </div>

      {/* Modal */}
      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>Create New Reservation</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Customer Name:</label>
                <input
                  type="text"
                  name="customer_name"
                  value={formData.customer_name}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Guest Count:</label>
                <input
                  type="number"
                  name="guest_count"
                  value={formData.guest_count}
                  onChange={handleChange}
                  required
                  min={2} // Số khách tối thiểu là 2
                />
              </div>
              <div className="form-group">
                <label>Reservation Time:</label>
                <input
                  type="datetime-local"
                  name="reservation_time"
                  value={formData.reservation_time}
                  onChange={handleChange}
                  min={new Date().toISOString().slice(0, 16)} // Chỉ cho phép chọn thời gian sau hiện tại
                  required
                />
              </div>
              <div className="modal-actions">
                <button type="submit" disabled={loading} className="btn-submit">
                  {loading ? "Submitting..." : "Create Reservation"}
                </button>
                <button
                  type="button"
                  className="btn-cancel"
                  onClick={() => setIsModalOpen(false)}
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Reservation;
