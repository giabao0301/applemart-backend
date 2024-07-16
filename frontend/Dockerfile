FROM node:20-alpine
ARG api_base_url
WORKDIR /app
COPY package*.json .
COPY yarn.lock .
RUN yarn --silent
COPY . .
RUN echo "VITE_API_BASE_URL=${api_base_url}" > .env
EXPOSE 5173
CMD ["yarn", "dev"]
