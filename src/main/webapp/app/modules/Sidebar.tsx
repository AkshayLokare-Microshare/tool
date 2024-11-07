import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faNetworkWired, faHome, faTools } from '@fortawesome/free-solid-svg-icons';

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

      {/* Sidebar */}
      <div className={`sidebar ${isVisible ? 'visible' : 'hidden'}`}>
        <ul className="sidebar-links">
          {/* <NavLink tag={Link} to="/iot/gateway" className="d-flex align-items-center">
      <FontAwesomeIcon icon={faNetworkWired} />
      <span>Gateways</span>
    </NavLink> */}
          <li>
            <Link to="/">
              <span>Home &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              <FontAwesomeIcon icon={faHome} />
            </Link>
          </li>

          <li>
            <Link to="/iot/gateway">
              <span>Gateways &nbsp; &nbsp;&nbsp;</span>
              <FontAwesomeIcon icon={faNetworkWired} />
            </Link>
          </li>

          <li>
            <Link to="/">
              <span>Coming Soon... &nbsp; &nbsp;&nbsp;</span>
              <FontAwesomeIcon icon={faTools} />
            </Link>
          </li>

          <li>
            <Link to="/">
              <span>Coming Soon... &nbsp; &nbsp;&nbsp;</span>
              <FontAwesomeIcon icon={faTools} />
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
