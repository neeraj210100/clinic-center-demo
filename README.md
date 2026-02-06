# Clinic Center Demo Website

A comprehensive clinic management website with appointment booking, lead management, WhatsApp integration, and Excel export capabilities. Built with React.js frontend and Spring Boot backend.

## Features

- 🏠 **Home Page**: Welcome page with clinic information and features
- 🩺 **Services Page**: Detailed list of medical services offered
- 📞 **Contact Page**: Appointment booking and contact form
- 💬 **WhatsApp Integration**: Direct WhatsApp chat button and appointment confirmations
- 📊 **Lead Management**: Capture leads from contact forms
- 📈 **Excel Export**: Export leads to Excel for analysis
- 🔗 **Google Forms Integration**: Optional integration with Google Forms webhook
- 📚 **Swagger/OpenAPI Documentation**: Interactive API documentation with Swagger UI

## Project Structure

```
clinic-center-demo/
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   └── main/
│   │       ├── java/com/clinic/
│   │       │   ├── config/      # Configuration classes
│   │       │   ├── controller/  # REST controllers
│   │       │   ├── dto/         # Data transfer objects
│   │       │   ├── model/       # Entity models
│   │       │   ├── repository/  # JPA repositories
│   │       │   └── service/     # Business logic
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
│
└── frontend/                # React.js frontend
    ├── public/
    ├── src/
    │   ├── components/      # Reusable components
    │   ├── pages/           # Page components
    │   ├── services/        # API service layer
    │   ├── App.js
    │   └── index.js
    └── package.json
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Node.js 16+** and npm
- **WhatsApp Business API** credentials (optional, for production)

## Setup Instructions

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Configure application properties (optional):
   Edit `src/main/resources/application.properties` to customize:
   - WhatsApp API token and phone number
   - Google Forms webhook URL
   - Database configuration (currently using H2 in-memory database)

3. Build and run the Spring Boot application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

4. Access H2 Console (for development):
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:clinicdb`
   - Username: `sa`
   - Password: (leave empty)

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Create a `.env` file (optional):
   ```env
   REACT_APP_API_URL=http://localhost:8080/api
   ```

4. Start the development server:
   ```bash
   npm start
   ```

   The frontend will start on `http://localhost:3000`

## Configuration

### WhatsApp Integration (Twilio)

The project uses **Twilio** for WhatsApp messaging. See `TWILIO_SETUP.md` for detailed instructions.

**Quick Setup**:

1. **Get Twilio Credentials**:
   - Sign up at [Twilio](https://www.twilio.com/)
   - Get Account SID and Auth Token from dashboard
   - Join WhatsApp Sandbox (free for testing)

2. **Set Environment Variables**:
   ```bash
   export TWILIO_ACCOUNT_SID="your_account_sid"
   export TWILIO_AUTH_TOKEN="your_auth_token"
   export TWILIO_WHATSAPP_FROM="whatsapp:+14155238886"  # Sandbox number
   export TWILIO_ENABLED="true"
   ```

3. **WhatsApp Chat Button** (Frontend):
   - Edit `frontend/src/components/WhatsAppButton.js`
   - Update the `whatsappNumber` variable with your clinic's WhatsApp number
   - This uses WhatsApp web link (works without API)

4. **For Production**:
   - Apply for WhatsApp Business API access in Twilio Console
   - Update `TWILIO_WHATSAPP_FROM` with your production number
   - See `TWILIO_SETUP.md` for complete guide

### Google Forms Integration

1. Create a Google Form with fields matching the Lead model
2. Set up a Google Apps Script webhook
3. Update `backend/src/main/resources/application.properties`:
   ```properties
   google.forms.webhook.url=https://script.google.com/macros/s/YOUR_SCRIPT_ID/exec
   ```

## API Endpoints

### Appointments
- `POST /api/appointments` - Create a new appointment
- `GET /api/appointments` - Get all appointments
- `GET /api/appointments/{id}` - Get appointment by ID
- `PUT /api/appointments/{id}/status?status={status}` - Update appointment status
- `GET /api/appointments/status/{status}` - Get appointments by status

### Leads
- `POST /api/leads` - Create a new lead
- `GET /api/leads` - Get all leads
- `GET /api/leads/{id}` - Get lead by ID
- `PUT /api/leads/{id}/status?status={status}` - Update lead status
- `GET /api/leads/export/excel` - Export leads to Excel

## Swagger/OpenAPI Documentation

The API is fully documented using Swagger/OpenAPI. Once the backend is running:

1. **Swagger UI**: Visit `http://localhost:8080/swagger-ui.html`
   - Interactive API documentation
   - Test endpoints directly from the browser
   - View request/response schemas

2. **OpenAPI JSON**: Visit `http://localhost:8080/api-docs`
   - Raw OpenAPI 3.0 specification
   - Can be imported into Postman, Insomnia, or other API clients

### Features:
- ✅ Complete API documentation with descriptions
- ✅ Request/response schemas
- ✅ Try-it-out functionality
- ✅ Parameter descriptions and examples
- ✅ Response codes and error handling

### Customization:
- Edit `OpenApiConfig.java` to customize API info, contact details, and servers
- Add more annotations to controllers for detailed documentation
- Configure paths in `application.properties` if needed

## Cloning and Customization Guide

This project is designed to be easily cloned and customized for different clinics. Here's how to adapt it:

### 1. Replace Content

#### Frontend Content:
- **Clinic Name**: Search and replace "Clinic Center" throughout the frontend
- **Services**: Update `frontend/src/pages/Services.js` with your clinic's services
- **Contact Information**: Update contact details in `frontend/src/pages/Contact.js`
- **WhatsApp Number**: Update in `frontend/src/components/WhatsAppButton.js`

#### Backend Content:
- **Package Name**: Update package structure if needed (currently `com.clinic`)
- **Application Name**: Update in `pom.xml` and `application.properties`

### 2. Customize Styling

- **Colors**: Update CSS variables or gradient colors in component CSS files
- **Logo**: Replace emoji icons with actual logo images
- **Theme**: Modify color scheme in all CSS files

### 3. WhatsApp Integration

1. **Quick Setup** (Using WhatsApp Web Link):
   - Already implemented in `WhatsAppButton.js`
   - Just update the phone number

2. **Full API Integration**:
   - Choose a WhatsApp Business API provider
   - Update `WhatsAppService.java` with provider-specific implementation
   - Configure API credentials in `application.properties`

### 4. Database Configuration

For production, replace H2 with a production database:

1. Update `pom.xml` with your database driver dependency
2. Update `application.properties` with database connection details
3. Configure JPA settings for your database

### 5. Deployment

#### Backend:
- Build JAR: `mvn clean package`
- Deploy to cloud platform (AWS, Heroku, etc.)
- Configure environment variables

#### Frontend:
- Build: `npm run build`
- Deploy `build/` folder to static hosting (Netlify, Vercel, etc.)
- Update API URL in production environment

## Development

### Running Tests

**Backend:**
```bash
cd backend
mvn test
```

**Frontend:**
```bash
cd frontend
npm test
```

### Building for Production

**Backend:**
```bash
cd backend
mvn clean package
java -jar target/clinic-center-backend-1.0.0.jar
```

**Frontend:**
```bash
cd frontend
npm run build
```

## Troubleshooting

### CORS Issues
- Ensure backend CORS configuration allows your frontend URL
- Check `CorsConfig.java` and `application.properties`

### WhatsApp Not Working
- Verify phone number format (include country code)
- Check API credentials if using WhatsApp Business API
- For demo, the WhatsApp button uses web link (works without API)

### Database Issues
- H2 database is in-memory and resets on restart
- For persistent data, switch to MySQL/PostgreSQL

## License

This project is provided as-is for demonstration and cloning purposes.

## Support

For issues or questions, please refer to the code comments or create an issue in the repository.

---

**Note**: This is a demo project. For production use, ensure proper security measures, error handling, and compliance with healthcare data regulations (HIPAA, GDPR, etc.).
