FROM node:latest as build_env

ARG APP

WORKDIR /app
COPY . .

RUN npm install -g nx
RUN npm install
RUN nx run $APP:build:production

FROM nginx:latest as run_env

ARG APP
ENV APP=${APP}

WORKDIR /usr/share/nginx/html
COPY --from=build_env /app/dist/apps/$APP .

EXPOSE 80
