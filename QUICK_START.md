# Quick Start Guide

## Prerequisites Check
- ✅ Java 17+ installed (`java -version`)
- ✅ Maven installed (`mvn -version`)
- ✅ Node.js 16+ installed (`node -version`)
- ✅ npm installed (`npm -version`)

## Step 1: Start Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend will be available at: http://localhost:8080

## Step 2: Start Frontend (in a new terminal)

```bash
cd frontend
npm install
npm start
```

Frontend will be available at: http://localhost:3000

## Step 3: Configure WhatsApp (Optional)

1. Open `frontend/src/components/WhatsAppButton.js`
2. Update `whatsappNumber` variable with your clinic's WhatsApp number
3. Format: `+1234567890` (include country code)

## Step 4: Test the Application

1. Visit http://localhost:3000
2. Navigate through Home, Services, and Contact pages
3. Try booking an appointment from the Contact page
4. Click the WhatsApp button to test WhatsApp integration

## API Testing

You can test the API endpoints using:
- Postman
- curl commands
- Or use the frontend forms

Example API calls:
```bash
# Create appointment
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientName": "John Doe",
    "phoneNumber": "+1234567890",
    "email": "john@example.com",
    "appointmentDateTime": "2024-12-25T10:00:00",
    "reason": "General checkup"
  }'

# Get all appointments
curl http://localhost:8080/api/appointments

# Export leads to Excel
curl http://localhost:8080/api/leads/export/excel -o leads.xlsx
```

## Troubleshooting

### Port Already in Use
- Backend: Change port in `backend/src/main/resources/application.properties`
- Frontend: React will prompt to use a different port

### CORS Errors
- Ensure backend is running before frontend
- Check CORS configuration in `CorsConfig.java`

### Module Not Found (Frontend)
- Run `npm install` again in the frontend directory
- Delete `node_modules` and `package-lock.json`, then reinstall

### Database Issues
- H2 database is in-memory (data resets on restart)
- Access H2 console at: http://localhost:8080/h2-console

## Next Steps

1. Customize content for your clinic (see README.md)
2. Set up production database
3. Configure WhatsApp Business API for production
4. Deploy to hosting platform
