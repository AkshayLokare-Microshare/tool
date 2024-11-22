import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faNetworkWired, faHome, faTools } from '@fortawesome/free-solid-svg-icons';

import './Sidebar.css';

const Sidebar = () => {
  const [isVisible, setIsVisible] = useState(false);

  const toggleSidebar = () => {
    setIsVisible(!isVisible);
  };

  return (
    <>
      {/* Discreet button on the left when sidebar is hidden */}
      {!isVisible && (
        <button className="toggle-btn" onClick={toggleSidebar}>
          &#10095; {/* Right arrow symbol */}
        </button>
      )}

      <div className={`sidebar ${isVisible ? 'visible' : 'hidden'}`}>
        <ul className="sidebar-links">
          {/* <NavLink tag={Link} to="/iot/gateway" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faNetworkWired} />
      <span>Gateways</span>
    </NavLink> */}
          <li>
            <Link to="/">
              <FontAwesomeIcon icon={faHome} />
              <span>Home</span>
            </Link>
          </li>

          <li>
            <Link to="/iot/gateway">
              <FontAwesomeIcon icon={faNetworkWired} />
              <span>Gateways</span>
            </Link>
          </li>

          <li>
            <Link to="/iot/iottest">
              <FontAwesomeIcon icon={faTools} />
              <span>Test</span>
            </Link>
          </li>

          <li>
            <Link to="/">
              <FontAwesomeIcon icon={faTools} />
              <span>Coming Soon...</span>
            </Link>
          </li>
        </ul>

        {/* Button to hide the sidebar when it's open */}
        <button className="toggle-btn-close" onClick={toggleSidebar}>
          &#10094; {/* Left arrow symbol */}
        </button>
      </div>
    </>
  );
};

export default Sidebar;
