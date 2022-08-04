FROM node:18-alpine3.15 as build
WORKDIR /app
COPY package.json ./

COPY . ./

ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

RUN yarn build


FROM nginx:1.17.0-alpine

COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]

