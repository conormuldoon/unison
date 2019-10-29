FROM node:12.4.0-alpine as build
WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn install
COPY . ./
RUN yarn build


FROM nginx:1.17.0-alpine
RUN mkdir -p /var/www/unison
COPY --from=build /app/build /var/www/unison
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]

