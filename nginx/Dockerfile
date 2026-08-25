FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d
COPY conf.d/eventapp.conf /etc/nginx/conf.d
COPY ./frontend/public /usr/share/nginx/html
