# Netlify Deployment Guide for Clinic Center

## ✅ Files Created for Deployment

1. **`public/_redirects`** - Handles React Router client-side routing
2. **`netlify.toml`** - Netlify build configuration

## 🚀 Step-by-Step Deployment Instructions

### Step 1: Prepare Your Repository

Make sure all changes are committed:
```bash
git add .
git commit -m "Add Netlify deployment configuration"
git push
```

### Step 2: Deploy to Netlify

#### Option A: Deploy via Netlify Dashboard (Recommended)

1. **Sign up/Login to Netlify**
   - Go to [https://app.netlify.com](https://app.netlify.com)
   - Sign up or log in with GitHub/GitLab/Bitbucket

2. **Add New Site**
   - Click "Add new site" → "Import an existing project"
   - Connect your Git provider (GitHub/GitLab/Bitbucket)
   - Select your `clinic-center-demo` repository

3. **Configure Build Settings**
   - **Base directory**: `frontend`
   - **Build command**: `npm run build`
   - **Publish directory**: `build`
   - **Node version**: `18` (or leave default)

4. **Set Environment Variables**
   - Go to Site settings → Environment variables
   - Click "Add variable"
   - **Key**: `REACT_APP_API_URL`
   - **Value**: `https://your-backend-url.com/api`
     - Replace with your actual backend API URL
     - Example: `https://api.clinic-center.com/api`
     - Or if using Heroku: `https://your-app.herokuapp.com/api`

5. **Custom Domain (Optional)**
   - Go to Site settings → Domain management
   - Click "Add custom domain"
   - Enter: `clinic-center.com`
   - Follow DNS configuration instructions
   - Netlify will provide DNS records to add to your domain registrar

6. **Deploy**
   - Click "Deploy site"
   - Netlify will automatically build and deploy your site
   - Wait for build to complete (usually 2-3 minutes)

#### Option B: Deploy via Netlify CLI

1. **Install Netlify CLI**
   ```bash
   npm install -g netlify-cli
   ```

2. **Login to Netlify**
   ```bash
   netlify login
   ```

3. **Navigate to Frontend Directory**
   ```bash
   cd frontend
   ```

4. **Initialize Netlify Site**
   ```bash
   netlify init
   ```
   - Follow prompts to create or link a site
   - Set build command: `npm run build`
   - Set publish directory: `build`

5. **Set Environment Variable**
   ```bash
   netlify env:set REACT_APP_API_URL "https://your-backend-url.com/api"
   ```

6. **Deploy**
   ```bash
   netlify deploy --prod
   ```

### Step 3: Verify Deployment

1. **Check Build Logs**
   - Go to Deploys tab in Netlify dashboard
   - Click on the latest deploy
   - Check for any build errors

2. **Test Your Site**
   - Visit your Netlify URL (e.g., `https://random-name-123.netlify.app`)
   - Or your custom domain: `https://clinic-center.com`
   - Test all routes: `/`, `/services`, `/contact`
   - Test form submissions and API calls

3. **Common Issues to Check**
   - ✅ All routes work (not just homepage)
   - ✅ API calls are successful (check browser console)
   - ✅ No CORS errors
   - ✅ Forms submit correctly

## 🔧 Configuration Details

### Build Configuration (`netlify.toml`)
- **Build command**: `npm run build`
- **Publish directory**: `build`
- **Node version**: `18`
- **Redirects**: All routes redirect to `/index.html` for React Router

### Environment Variables Required

| Variable | Description | Example |
|----------|-------------|---------|
| `REACT_APP_API_URL` | Backend API URL | `https://api.clinic-center.com/api` |

### React Router Configuration

The `_redirects` file ensures that:
- Direct URL access to routes like `/services` or `/contact` works
- All routes are handled by React Router
- No 404 errors on page refresh

## 📝 Post-Deployment Checklist

- [ ] Site loads successfully
- [ ] All routes work (`/`, `/services`, `/contact`)
- [ ] API calls are working (check browser console)
- [ ] Forms submit correctly
- [ ] WhatsApp button works
- [ ] Custom domain configured (if applicable)
- [ ] SSL certificate active (automatic with Netlify)
- [ ] Backend CORS allows `https://clinic-center.com`

## 🔄 Updating Your Site

After making changes:

1. **Commit and push to Git**
   ```bash
   git add .
   git commit -m "Update site"
   git push
   ```

2. **Netlify will auto-deploy** (if connected to Git)
   - Or manually trigger: Deploys → Trigger deploy

3. **Clear cache if needed**
   - Deploys → Clear cache and deploy site

## 🐛 Troubleshooting

### Build Fails
- Check build logs in Netlify dashboard
- Ensure `package.json` has all dependencies
- Verify Node version (should be 18+)

### Routes Return 404
- Verify `_redirects` file exists in `public/` folder
- Check `netlify.toml` redirect configuration
- Clear cache and redeploy

### API Calls Fail
- Verify `REACT_APP_API_URL` environment variable is set
- Check backend CORS configuration includes your domain
- Check browser console for errors

### CORS Errors
- Update backend `application.properties`:
  ```properties
  spring.web.cors.allowed-origins=http://localhost:3000,https://clinic-center.com,https://www.clinic-center.com
  ```
- Redeploy backend after updating CORS

## 📞 Support

If you encounter issues:
1. Check Netlify build logs
2. Check browser console for errors
3. Verify all environment variables are set
4. Ensure backend is deployed and accessible

---

**Your site will be live at**: `https://clinic-center.com` (after domain configuration)
