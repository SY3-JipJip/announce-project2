FROM node:19 AS stage1
COPY ./frontend/ nodeproj/
WORKDIR /nodeproj/
RUN npm install
RUN npm run build

FROM nginx:alpine
COPY --from=stage1 /nodeproj/dist/ /usr/share/nginx/html/
EXPOSE 80
