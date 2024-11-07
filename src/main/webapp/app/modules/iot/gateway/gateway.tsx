import React, { useState, useEffect } from 'react';
import { toast, Slide } from 'react-toastify';

import Screen from './Screen';
import Sidebar from 'app/modules/Sidebar';
import './gatewayStyles.css';

function Gateways() {
  const [selectedGateway, setSelectedGateway] = useState(null);
  const [isModalOpen, setModalOpen] = useState(false);
  const [gateways, setGateways] = useState([]);
  const [error, setError] = useState(null);
  const [filter, setFilter] = useState('All');
  const [search, setSearch] = useState('');
  const [searchFilter, setSearchFilter] = useState('');
  const [page, setPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const limit = 5;

  // Fetch gateways from the API
  useEffect(() => {
    const fetchGateways = async () => {
      setIsLoading(true);
      try {
        const response = await fetch(`http://localhost:5000/gateways?page=${page}&limit=${limit}`);
        if (!response.ok) throw new Error('Error fetching gateways...');
        const data = await response.json();
        setGateways(data.gateways);
        setTotal(data.total);
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };
    fetchGateways();
  }, [page]);

  // Open email modal with the selected gateway
  const handleOpenModal = gateway => {
    setSelectedGateway(gateway);
    setModalOpen(true);
  };

  // Close email modal
  const handleCloseModal = () => {
    setModalOpen(false);
    setSelectedGateway(null);
  };

  // Handle email sending
  const handleEmailSend = async (emailSubject, emailBody, recipientEmails) => {
    if (!selectedGateway) return;

    const subject = emailSubject || `Disconnected Gateway - ${selectedGateway.deveui}`;
    const body =
      emailBody ||
      `The following gateway is disconnected. Please check!\n\nGateway Dev Eui: ${selectedGateway.deveui}\nLocation: ${selectedGateway.locs}`;

    try {
      const response = await fetch('http://localhost:5000/send-email', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email1: recipientEmails[0], subject, body }),
      });

      if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

      toast.success(`Email sent successfully to ${recipientEmails.join(', ')}`, {
        position: 'top-right',
        autoClose: 2000,
        theme: 'light',
        transition: Slide,
      });
    } catch (err) {
      console.error('Error sending email:', err);
      toast.error('Failed to send the email. Check console logs!', {
        position: 'top-right',
        autoClose: 2000,
        theme: 'light',
        transition: Slide,
      });
    } finally {
      handleCloseModal();
    }
  };

  // Filter gateways based on search and filter criteria
  const filteredGateways = gateways.filter(gateway => {
    const matchedSearch = searchFilter
      ? gateway[searchFilter.toLowerCase()] && // Check if the value is not null
        gateway[searchFilter.toLowerCase()].toString().toLowerCase().includes(search.toLowerCase())
      : Object.values(gateway).some(
          value =>
            value && // Check if the value is not null
            value.toString().toLowerCase().includes(search.toLowerCase()),
        );
    if (filter === 'All') return matchedSearch;
    if (filter === 'Connected') return gateway.connection_status && matchedSearch;
    if (filter === 'Disconnected') return !gateway.connection_status && matchedSearch;
    return matchedSearch;
  });

  const totalPages = Math.ceil(total / limit);

  return (
    <div className="gateway-container">
      <Sidebar />
      <h2 className="gateway-title">List of All Gateways</h2>

      {/* Filter and Search UI */}
      <div className="filter-search">
        <select value={filter} onChange={e => setFilter(e.target.value)}>
          <option value="All">All</option>
          <option value="Connected">Connected</option>
          <option value="Disconnected">Disconnected</option>
        </select>
        <input type="text" placeholder="Search..." value={search} onChange={e => setSearch(e.target.value)} />
        <select value={searchFilter} onChange={e => setSearchFilter(e.target.value)}>
          <option value="">All Fields</option>
          <option value="deveui">Dev Eui</option>
          <option value="id">ID</option>
          <option value="fleet">Fleet</option>
          {/* Add other fields as necessary */}
        </select>
      </div>

      <div className="gateway-list">
        {error ? (
          <p>{error}</p>
        ) : isLoading ? (
          <p>Loading gateways...</p>
        ) : filteredGateways.length > 0 ? (
          filteredGateways.map((gateway, index) => (
            <div key={index} className={`gateway-item ${gateway.connection_status ? 'connected' : 'disconnected'}`}>
              <div className="gateway-info">
                <p>
                  {gateway.id} | {gateway.deveui} | {gateway.fleet} | {gateway.locs}
                </p>
              </div>
              {!gateway.connection_status && (
                <button className="gateway-btn" onClick={() => handleOpenModal(gateway)}>
                  Send Email
                </button>
              )}
            </div>
          ))
        ) : (
          <p>No Gateways</p>
        )}
      </div>

      {/* Pagination Controls */}
      <div className="pagination-controls">
        <button onClick={() => setPage(prev => Math.max(prev - 1, 1))} disabled={page === 1}>
          Previous
        </button>
        <span>
          {page} of {totalPages}
        </span>
        <button onClick={() => setPage(prev => Math.min(prev + 1, totalPages))} disabled={page === totalPages}>
          Next
        </button>
      </div>

      {/* Email Modal */}
      {isModalOpen && (
        <Screen
          gateway={selectedGateway}
          recipient={selectedGateway.email1}
          subject={`Disconnected Gateway - ${selectedGateway.deveui}`}
          body={`The following gateway is disconnected. Please check!\n\nGateway Dev Eui: ${selectedGateway.deveui}\nLocation: ${selectedGateway.locs}`}
          onClose={handleCloseModal}
          onSend={handleEmailSend}
        />
      )}
    </div>
  );
}

export default Gateways;
