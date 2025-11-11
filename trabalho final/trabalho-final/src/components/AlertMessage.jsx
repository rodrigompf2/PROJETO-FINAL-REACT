import React from 'react';
import './AlertMessage.css';

const AlertMessage = ({ message, type = 'error' }) => {
  if (!message) return null;

  return (
    <div className={`alert ${type}`}>
      {message}
    </div>
  );
};

export default AlertMessage;