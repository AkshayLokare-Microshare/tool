import React, { useState, useEffect } from 'react';

import './gatewayStyles.css';

const Screen = ({ gateway, recipient, subject, body, onClose, onSend }) => {
  const [emailSubject, setEmailSubject] = useState(subject);
  const [emailBody, setEmailBody] = useState(body);
  const [isLoading, setIsLoading] = useState(false);
  const [isEditingRecipient, setIsEditingRecipient] = useState(!recipient);
  const [emailInput, setEmailInput] = useState(recipient || '');

  useEffect(() => {
    setEmailInput(recipient || '');
  }, [recipient]);

  const handleSendClick = async () => {
    setIsLoading(true);
    try {
      const recipients = isEditingRecipient ? [emailInput] : [recipient];
      await onSend(emailSubject, emailBody, recipients);
      onClose(); // Close modal after sending
    } catch (error) {
      console.error('Error sending email:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="screen-overlay">

      <div className="screen">

        <h2>Confirm Email</h2>

        <p><strong>Gateway Dev Eui:</strong> {gateway.deveui}</p>

        <p><strong>Recipient:</strong> 
          {isEditingRecipient ? (
            <input
              type="email"
              value={emailInput}
              onChange={(e) => setEmailInput(e.target.value)}
              className="screen-input"
              placeholder="Enter recipient email"
            />
            
          ) : (
            <span>{recipient}</span>
          )}
          
        </p>

        <div>
          <label>Subject:</label>
          <input
            type="text"
            value={emailSubject}
            onChange={(e) => setEmailSubject(e.target.value)}
            className="screen-input"
          />
        </div>

        <div>
          <label>Body:</label>
          <textarea
            value={emailBody}
            onChange={(e) => setEmailBody(e.target.value)}
            className="screen-textarea"
          />
        </div>

        <div className="screen-actions">

          <button onClick={onClose} className="btn-cancel">Cancel</button>
          <button onClick={handleSendClick} className="btn-ok" disabled={isLoading}>
            {isLoading ? 'Sending...' : 'Send'}
          </button>
          
        </div>

      </div>
    </div>
  );
};

export default Screen;
