import React, { useState } from 'react';
import '../gateway/gatewayStyles.css';

export default function IotTest() {
  const [isModalOpen, setModalOpen] = useState(false);
  const [inputs, setInputs] = useState([]);
  const [input, setInput] = useState('');

  const open = () => {
    setModalOpen(true);
  };

  const close = () => {
    setModalOpen(false);
  };

  const toggle = () => setModalOpen(prevState => !prevState);

  const addInput = () => {
    setInputs(prevInputs => [...prevInputs, input]);
    setInput('');
  };

  const deleteInput = index => {
    setInputs(prevInputs => prevInputs.filter((_, i) => i !== index));
  };

  return (
    <div>
      <h2 className="gateway-title">Modal Test</h2>
      <input type="text" placeholder="random shi..." value={input} onChange={e => setInput(e.target.value)} /> &nbsp;
      <button onClick={addInput}>Add</button> &nbsp; &nbsp;
      <button onClick={toggle} className="test-btn">
        {' '}
        {isModalOpen ? 'Close' : 'Open'}{' '}
      </button>
      {isModalOpen && (
        <div className="overlay-test">
          <div className="modal-test">
            <h2>Modal</h2>

            <div>
              {inputs.map((item, index) => (
                <div key={index} className="test-content">
                  <p> {item} </p>
                  <p onClick={() => deleteInput(index)} className="delete-btn-test">
                    Delete
                  </p>
                </div>
              ))}
            </div>

            <button onClick={close} className="test-btn">
              {' '}
              Close{' '}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
