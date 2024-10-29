const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const sgMail = require('@sendgrid/mail'); //sendgrid dependency
const { Pool } = require('pg'); // PostgreSQL dependency

require('dotenv').config(); // Load environment variables

const app = express();
const port = 5000;

// Middleware
app.use(cors()); //allows cross-origin access
app.use(bodyParser.json());

// Set SendGrid API Key from .env file
sgMail.setApiKey(process.env.SENDGRID_API_KEY);

// PostgreSQL connection pool setup
const pool = new Pool({
  user: process.env.POSTGRES_USER,
  host: process.env.POSTGRES_HOST,
  database: process.env.POSTGRES_DATABASE,
  password: process.env.POSTGRES_PWD,
  port: process.env.DB_PORT,
});
console.log('Connecting to PostgreSQL with:');
console.log(`User: ${process.env.POSTGRES_USER}`);
console.log(`Host: ${process.env.POSTGRES_HOST}`);
console.log(`Database: ${process.env.POSTGRES_DATABASE}`);
console.log(`Port: ${process.env.DB_PORT}`);

// Test PostgreSQL connection
app.get('/test-db', async (req, res) => {
  try {
    await pool.query('SELECT NOW()'); // Simple query to check connection
    //console.log('PostgreSQL connected successfully');
    res.status(200).json({ message: 'PostgreSQL connected successfully' });
  } catch (error) {
    console.error('Error connecting to PostgreSQL:', error);
    res.status(500).json({ error: 'Failed to connect to PostgreSQL', details: error.message });
  }
});

// Fetch gateways from the PostgreSQL table
app.get('/gateways', async (req, res) => {
  const { page = 1, limit = 5 } = req.query; // Default values for page and limit
  const offset = (page - 1) * limit;

  try {
    // Fetch paginated gateways and the total count of gateways
    const result = await pool.query('SELECT * FROM gateways ORDER BY id LIMIT $1 OFFSET $2', [limit, offset]);
    const countResult = await pool.query('SELECT COUNT(*) FROM gateways;');

    res.status(200).json({
      total: parseInt(countResult.rows[0].count, 10), // Total number of records
      gateways: result.rows, // Paginated gateways
    });
  } catch (error) {
    console.error('Error fetching gateways:', error);
    res.status(500).json({ error: error.message });
  }
});

// Route to send the email which we will use on the frontend
app.post('/send-email', (req, res) => {
  const { email1, email2, subject, body } = req.body; //Destructures to, subject, and body from the request body.

  console.log('Received request to send email:');
  console.log('Email1:', email1);
  console.log('Email2:', email2);
  console.log('Subject:', subject);
  console.log('Body:', body);

  //List of emails
  const emails = [email1];
  if (email2) {
    emails.push(email2);
  }

  const msg = {
    to: emails,
    from: process.env.EMAIL_USER, // The sender's email address
    subject,
    text: body,
  };

  // Send email using SendGrid
  sgMail
    .send(msg)
    .then(() => {
      console.log('Email sent successfully');
      res.status(200).json({ message: 'Email sent successfully' });
    })
    .catch(error => {
      if (error.response) {
        console.error('SendGrid error response:', error.response.body);
        res.status(500).json({ error: error.response.body });
      } else {
        console.error('Error sending email:', error.message);
        res.status(500).json({ error: error.message });
      }
    });
});

// Start the server
app.listen(port, () => {
  console.log(`Server listening on port ${port}`);
});
