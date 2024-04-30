FROM node:16-alpine
WORKDIR /app 
COPY package.json ./
RUN npm install react react-dom react-toastify@8.0.0 --save
COPY . ./
EXPOSE 3000
CMD ["npm", "start"]
